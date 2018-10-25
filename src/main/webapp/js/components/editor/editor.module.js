(function () {
    var editor = angular.module("de.nordakademie.iaa.survey.editor", [
        "de.nordakademie.iaa.survey.core",
        "de.nordakademie.iaa.i18n",
        "ngMaterial",
        "md.time.picker",
        "ui.router"
    ]);

    editor.directive("surveyEditorActionButton", FloatingActionButtonDirective);
    editor.controller("floatingEditorController", ["$mdDialog", "$scope", FloatingActionButtonController]);

    function FloatingActionButtonDirective() {
        return {
            restrict: "E",
            transclude: false,
            controller: "floatingEditorController",
            controllerAs: "dialogManager",
            link: function(scope){
                scope.$watch('surveyId', function (newVal, oldVal) {
                    console.log("inner", newVal, oldVal);
                });
                scope.$parent.$watch('surveyId', function (newVal, oldVal) {
                   scope.surveyId = oldVal;
                })
            },
            scope: {
                surveyId: "=",
                icon: "@"
            },
            template: "<div>" +
                "<md-button class=\"md-primary md-raised md-accent md-icon-button\"" +
                " ng-click=\"dialogManager.showEditorDialog($event)\">" +
                "<i class=\"material-icons\"> {{icon}} </i></md-button>" +
                "</div>"
        }
    }

    function FloatingActionButtonController($mdDialog, $scope) {
        var surveyId = $scope.surveyId;
        this.showEditorDialog = function (ev) {
            $mdDialog.show({
                templateUrl: "/js/components/editor/editor.dialog.template.html",
                controller: EditorController,
                controllerAs: "surveyEditorController",
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: false,
                fullscreen: true
            })
        };

        function EditorController($scope, $mdDialog, surveyService, $state, notificationService) {
            var vm = this;
            $scope.createNew = angular.isUndefined(surveyId);
            $scope.caption = $scope.createNew ? "EDITOR_TITLE_NEW" : "EDITOR_TITLE_UPDATE";

            initialize();

            this.addEmptyOption = function () {
                $scope.options.push({dateTime: new Date()})
            };

            this.removeOption = function (optionToRemove) {
                $scope.options = $scope.options.filter(function (value) {
                    return value.dateTime !== optionToRemove.dateTime
                });
                if ($scope.options.length === 0) {
                    this.addEmptyOption();
                }
            };

            this.saveAsDraft = function () {
                surveyService.createSurveyAsDraft($scope.survey, $scope.options)
                    .then(successHandler, errorHandler);
            };

            this.saveAndPublish = function () {
                surveyService.createSurvey($scope.survey, $scope.options)
                    .then(successHandler, errorHandler);
            };

            function validOptions() {
                for (var i = 0; i < $scope.options.length; i++) {
                    if ($scope.options[i].dateTime === "") {
                        return false;
                    }
                }
                return true;
            }

            this.valid = function () {
                return $scope.survey.title !== ""
                    && $scope.survey.description !== ""
                    && validOptions();
            };
            this.cancel = function () {
                $mdDialog.cancel();
            };

            function initialize() {
                $scope.busy = true;
                if ($scope.createNew) {
                    $scope.survey = {
                        title: "",
                        description: ""
                    };
                    $scope.options = [
                        {
                            dateTime: new Date()
                        }
                    ];
                    $scope.busy = false;
                    return;
                }
                surveyService.loadSurveyWithId(surveyId)
                    .subscribeOnNext(function (survey) {
                        $scope.survey = survey;
                    });

                surveyService.loadAllOptionsForSurveyWithId(surveyId)
                    .subscribeOnNext(function (options) {
                        $scope.options = options;
                    });

            }

            function successHandler(success) {
                $state.go("detail", {surveyId: success.data.id});
                vm.cancel()
            }

            function errorHandler(error) {
                if (error.status === 409) {
                    notificationService.showNotification("EDITOR_CONFLICT")
                } else {
                    notificationService.showNotification("EDITOR_NETWORK")
                }
            }
        }
    }


}());