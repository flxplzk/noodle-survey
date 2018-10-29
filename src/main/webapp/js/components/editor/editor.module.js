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
        this.showEditorDialog = function (ev) {
            $mdDialog.show({
                locals: {surveyId: $scope.surveyId},
                templateUrl: "/js/components/editor/editor.dialog.template.html",
                controller: ["$scope", "$mdDialog", "SurveyResource", "OptionResource", "$state", "notificationService", "surveyId", EditorController],
                controllerAs: "surveyEditorController",
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: false,
                fullscreen: true
            })
        };

        function EditorController($scope, $mdDialog, SurveyResource, OptionResource, $state, notificationService, surveyId) {
            var vm = this;
            $scope.createNew = angular.isUndefined(surveyId);
            $scope.caption = $scope.createNew ? "EDITOR_TITLE_NEW" : "EDITOR_TITLE_UPDATE";

            initialize();

            this.addEmptyOption = function () {
                $scope.options.push(new OptionResource({dateTime: new Date()}));
            };

            this.removeOption = function (optionToRemove) {
                $scope.options = $scope.options.filter(function (value) {
                    return !value.equals(optionToRemove);
                });
                if ($scope.options.length === 0) {
                    this.addEmptyOption();
                }
            };

            this.saveAsDraft = function () {
                saveWithStatus("PRIVATE");
            };

            this.saveAndPublish = function () {
                saveWithStatus("OPEN");
            };

            function saveWithStatus(status) {
                var survey = $scope.survey;
                survey.surveyStatus = status;
                survey.options = $scope.options;
                if ($scope.createNew) {
                    survey.$save(successHandler, createErrorHandler);
                } else {
                    survey.$update({survey: survey.getId()}, successHandler, createErrorHandler)
                }
            }

            function validOptions() {
                return optionsDefined && optionsValid() && optionsUnique()
            }

            function optionsDefined() {
                return angular.isUndefined($scope.options) || $scope.options.length === 0
            }

            function optionsValid() {
                for (var i = 0; i < $scope.options.length; i++) {
                    if (!$scope.options[i].isValid()) {
                        return false;
                    }
                }
                return true;
            }

            function optionsUnique() {
                var valueSoFar = Object.create(null);
                for (var i = 0; i < $scope.options.length; i++) {
                    var value = $scope.options[i];
                    if (value.dateTime in valueSoFar) {
                        return false;
                    }
                    valueSoFar[value.dateTime] = true;
                }
                return true;
            }

            this.valid = function () {
                return angular.isDefined($scope.survey)
                    && $scope.survey.isValid()
                    && validOptions();
            };
            this.cancel = function () {
                $mdDialog.cancel();
            };

            function initialize() {
                if ($scope.createNew) {
                    $scope.survey = new SurveyResource({title: "", description: ""});
                    $scope.options = [new OptionResource({dateTime: new Date()})];
                    $scope.busy = false;
                    return;
                }
                var requestParam = {survey: surveyId};
                $scope.survey = SurveyResource.get(requestParam);
                $scope.options = OptionResource.query(requestParam);
            }

            function successHandler(survey) {
                $state.go("detail", {surveyId: survey.getId()});
                vm.cancel()
            }

            function createErrorHandler(error) {
                if (error.status === 409) {
                    notificationService.showNotification("EDITOR_CONFLICT")
                } else if (error.status === 404) {
                    notificationService.showNotification("EDITOR_NOT_FOUND");
                    vm.cancel()
                } else {
                    notificationService.showNotification("EDITOR_NETWORK")
                }
            }

            function updateErrorHandler(error) {
                if (error.status === 409) {
                    notificationService.showNotification("EDITOR_CONFLICT")
                } else if (error.status === 404) {
                    notificationService.showNotification("EDITOR_NOT_FOUND");
                    vm.cancel()
                } else {
                    notificationService.showNotification("EDITOR_NETWORK")
                }
            }
        }
    }
}());