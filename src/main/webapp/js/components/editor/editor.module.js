(function () {
    var editor = angular.module("de.nordakademie.iaa.survey.editor", [
        "de.nordakademie.iaa.survey.core",
        "de.nordakademie.iaa.i18n",
        "ngMaterial"
    ]);

    function FloatingActionButtonDirective($mdDialog) {
        $scope.showAdvanced = function (ev) {
            $mdDialog.show({
                templateUrl: "/js/components/editor/editor.dialog.template.html",
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
                fullscreen: false
            })
        };
    }

    function EditorController($scope, $mdDialog) {
        this.cancel = function () {
            $mdDialog.cancel();
        }
    }
}());