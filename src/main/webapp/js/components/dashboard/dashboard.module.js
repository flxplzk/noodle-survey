(function () {
    var dashboard = angular.module("de.nordakademie.iaa.survey.dashboard", [
        "de.nordakademie.iaa.survey.core.domain",
        "de.nordakademie.iaa.survey.core",
        "de.nordakademie.iaa.i18n",
        "ngMaterial"
    ]);

    dashboard.controller("dashboardController", ["$scope", "surveyService", "$state", "$mdDialog", DashboardController]);

    function DashboardController($scope, surveyService, $state, $mdDialog) {
        $scope.model = {
            surveys: [],
            loading: true,
            tabs: [],
            selectedIndex: 0
        };

        // On init load all surveys from backend.
        var query = surveyService.query();
        query.$promise.then(function (surveys) {
            $scope.model.surveys = surveys;
            $scope.model.loading = false;
        });

        this.viewDetails = function (survey) {
            $state.go("detail", {surveyId: survey.getId()})
        };

        $scope.initiatorOfSurveyShort = function (survey) {
            return survey.initiator.firstName.substring(0, 1)
                + survey.initiator.lastName.substring(0, 1)
        };

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
}());