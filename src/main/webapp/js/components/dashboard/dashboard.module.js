(function () {
    var dashboard = angular.module("de.nordakademie.iaa.survey.dashboard", [
        "de.nordakademie.iaa.survey.core.domain",
        "de.nordakademie.iaa.survey.core",
        "de.nordakademie.iaa.i18n",
        "ngMaterial"
    ]);

    dashboard.controller("dashboardController", ["$scope", "surveyService", DashboardController]);

    function DashboardController($scope, surveyService) {
        $scope.model = {
            surveys: [],
            loading: true,
            tabs: [],
            selectedIndex: 0
        };

        // On init load all surveys from backend.
        surveyService.loadAll().subscribeOnNext(function (surveys) {
            $scope.model.surveys = surveys;
            $scope.model.loading = false;
        });

        this.addToSelectedSurveys = function (survey) {
            $scope.model.tabs.push(survey)
        }
    }
}());