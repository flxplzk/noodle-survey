(function () {
    var detail = angular.module("de.nordakademie.iaa.survey.detail", [
        "de.nordakademie.iaa.survey.core.domain",
        "de.nordakademie.iaa.survey.core",
        "de.nordakademie.iaa.i18n",
        "ngMaterial"
    ]);

    detail.controller("detailController", ["$scope", "$stateParams", DetailController]);

    function DetailController($scope, $stateParams) {
        $scope.greeting = "details works: " + $stateParams.surveyId
    }
}());