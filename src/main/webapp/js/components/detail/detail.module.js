(function () {
    var detail = angular.module("de.nordakademie.iaa.survey.detail", [
        "de.nordakademie.iaa.survey.core.domain",
        "de.nordakademie.iaa.survey.core",
        "de.nordakademie.iaa.i18n",
        "ngMaterial"
    ]);

    detail.controller("detailController", ["$scope", "$stateParams", "surveyService", "appService", DetailController]);

    function DetailController($scope, $stateParams, surveyService, appService) {
        var currentUser = appService.getAuthenticatedUser();
        $scope.survey = {
            title: $stateParams.surveyId,
            initiator: {username: "felix@admin.de"},
            description: "Lorem Ipsum Dolor et amet Lorem Ipsum Dolor et amet Lorem Ipsum Dolor et amet Lorem Ipsum Dolor et amet Lorem Ipsum Dolor et amet Lorem Ipsum Dolor et amet Lorem Ipsum Dolor et amet Lorem Ipsum Dolor et amet Lorem Ipsum Dolor et amet Lorem Ipsum Dolor et amet Lorem Ipsum Dolor et amet Lorem Ipsum Dolor et amet Lorem Ipsum Dolor et amet Lorem Ipsum Dolor et amet Lorem Ipsum Dolor et amet Lorem Ipsum Dolor et amet Lorem Ipsum Dolor et amet Lorem Ipsum Dolor et amet Lorem Ipsum Dolor et amet Lorem Ipsum Dolor et amet Lorem Ipsum Dolor et amet Lorem Ipsum Dolor et amet Lorem Ipsum Dolor et amet "
        };
        $scope.options = [];
        $scope.participations = [];
        $scope.ownParticipation = {options:[]};

        surveyService.loadAllOptionsForSurveyWithId($stateParams.surveyId)
            .subscribeOnNext(function (options) {
                $scope.options = options;
            });

        surveyService.loadAllParticipationsForSurveyWithId($stateParams.surveyId)
            .subscribeOnNext(function (participations) {
                $scope.participations = participations;
                filterOwnParticipation();
            });

        this.participates = function (participation, option) {
            for (var i = 0; i < participation.options.length; i++) {
                if (option.dateTime === participation.options[i].dateTime) {
                    return true;
                }
            }
            return false;
        };

        this.sumParticipationsForOption = function (option) {
            var sum = 0;
            for (var participation in $scope.participations) {
                for (var entry in participation.options) {
                    if (entry === option) {
                        sum++;
                    }
                }
            }
            return sum;
        };

        this.participate = function (option) {
            if (!option.checked) {
                $scope.ownParticipation.options.push(option);
            } else {
                $scope.ownParticipation.options = $scope.ownParticipation.options.filter(function (value) {
                    return value !== option;
                })
            }
        };

        this.isOwnSurvey = function () {
            return $scope.survey.initiator.username === currentUser.principal.username;
        };

        this.isSurveyOpen = function () {
            return $scope.survey.surveyStatus &&
                $scope.survey.surveyStatus === "OPEN";
        };

        function filterOwnParticipation() {
            $scope.participations = $scope.participations.filter(function (value, index) { 
               if (value.user.username === currentUser.username) {
                   $scope.ownParticipation = value;
                   return false
               }
               return true;
            });
        }
    }
}());