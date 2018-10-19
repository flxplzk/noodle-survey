(function () {
    var editor = angular.module("de.nordakademie.iaa.survey.editor", [
        "de.nordakademie.iaa.survey.core",
        "de.nordakademie.iaa.i18n",
        "ngMaterial"
    ]);

    editor.directive("surveyEditor", EditorDirective);
    editor.directive("surveyEditorActionButton", FloatingActionButtonDirective);
    editor.controller("floatingEditorController", ["$mdDialog", FloatingActionButtonController]);
    editor.controller("surveyEditorController", ["$scope", "$mdDialog", EditorController]);

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
                    clickOutsideToClose: true,
                    fullscreen: false
                })
        }
    }

    function EditorController($scope, $mdDialog) {
        this.cancel = function () {
            $mdDialog.cancel();
        }
    }

}());