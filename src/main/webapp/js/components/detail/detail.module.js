(function () {
    var detail = angular.module("de.nordakademie.iaa.survey.detail", [
        "de.nordakademie.iaa.survey.core.domain",
        "de.nordakademie.iaa.survey.core",
        "de.nordakademie.iaa.i18n",
        "ngMaterial"
    ]);

    detail.controller("detailController", ["$scope", "$stateParams", "surveyService", DetailController]);

    function DetailController($scope, $stateParams, surveyService) {
        $scope.survey = {title: $stateParams.surveyId};
        $scope.options = [];
        $scope.participations = [];

        surveyService.loadAllOptionsForSurveyWithId($stateParams.surveyId)
            .subscribeOnNext(function (options) {
                $scope.options = options;
            });

        surveyService.loadAllParticipationsForSurveyWithId($stateParams.surveyId)
            .subscribeOnNext(function (participations) {
                $scope.participations = participations;
            });

        this.participates = function (participation, option) {
            for (var i = 0; i < participation.options.length; i++) {
                if (option.dateTime === participation.options[i].dateTime) {
                    return true;
                }
            }
            return false;
        };
    }
}());