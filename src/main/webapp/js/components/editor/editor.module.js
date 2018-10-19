(function () {
    var editor = angular.module("de.nordakademie.iaa.survey.editor", [
        "de.nordakademie.iaa.survey.core",
        "de.nordakademie.iaa.i18n",
        "ngMaterial",
        "md.time.picker",
        "ui.router"
    ]);

    editor.directive("surveyEditor", EditorDirective);
    editor.directive("surveyEditorActionButton", FloatingActionButtonDirective);
    editor.controller("floatingEditorController", ["$mdDialog", FloatingActionButtonController]);
    editor.controller("surveyEditorController", ["$scope", "$mdDialog", "surveyService", "$state", "errorService", EditorController]);

    function FloatingActionButtonDirective() {
        return {
            restrict: "E",
            template: "<div ng-controller='floatingEditorController as dialogManager'>" +
                "<md-button class=\"md-raised md-accent md-hue-3\" ng-click=\"dialogManager.showEditorDialog($event)\">Anlegen</md-button>" +
                "</div>"
        }
    }

    function EditorDirective() {
        return {
            restrict: "E",
            templateUrl: "/js/components/editor/editor.dialog.template.html"
        }
    }

    function FloatingActionButtonController($mdDialog) {
        this.showEditorDialog = function (ev) {
            $mdDialog.show({
                templateUrl: "/js/components/editor/editor.dialog.template.html",
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: false,
                fullscreen: true
            })
        }
    }

    function EditorController($scope, $mdDialog, surveyService, $state, errorService) {
        var vm = this;
        $scope.survey = {
            title: "",
            description: ""
        };
        $scope.options = [
            {
                dateTime: ""
            }
        ];

        this.addEmptyOption = function () {
            $scope.options.push({dateTime: ""})
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

        function successHandler(success) {
            $state.go("detail", {surveyId: success.data.title});
            vm.cancel()
        }

        function errorHandler(error) {
            if (error.status === 409) {
                errorService.showErrorNotification("EDITOR_CONFLICT")
            } else {
                errorService.showErrorNotification("EDITOR_NETWORK")
            }
        }
    }

}());