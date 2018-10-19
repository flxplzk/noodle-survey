(function () {
    var editor = angular.module("de.nordakademie.iaa.survey.editor", [
        "de.nordakademie.iaa.survey.core",
        "de.nordakademie.iaa.i18n",
        "ngMaterial",
        "md.time.picker"
    ]);

    editor.directive("surveyEditor", EditorDirective);
    editor.directive("surveyEditorActionButton", FloatingActionButtonDirective);
    editor.controller("floatingEditorController", ["$mdDialog", FloatingActionButtonController]);
    editor.controller("surveyEditorController", ["$scope", "$mdDialog", "surveyService", EditorController]);

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

    function EditorController($scope, $mdDialog, surveyService) {
        $scope.survey = {
            title: "",
            description: ""
        };
        $scope.options =[
            {
                date: "",
                time: ""
            }
        ];

        this.addEmptyOption = function () {
            $scope.options.push({date: "", time:""})
        };

        this.removeOption = function (optionToRemove) {
            for (opt in $scope.options) {
                if (opt === optionToRemove) {
                    // TODO
                }
            }
        };

        this.saveAsDraft = function () {
            surveyService.createSurveyAsDraft($scope.survey);
        };

        this.saveAndPublish = function () {
            surveyService.createSurveyAsDraft($scope.survey);
        };

        this.cancel = function () {
            $mdDialog.cancel();
        }
    }

}());