(function () {
    var detail = angular.module("de.nordakademie.iaa.survey.detail", [
        "de.nordakademie.iaa.survey.editor",
        "de.nordakademie.iaa.survey.core.domain",
        "de.nordakademie.iaa.survey.core",
        "de.nordakademie.iaa.i18n",
        "ngMaterial"
    ]);

    detail.controller("detailController", ["$scope", "$stateParams", "surveyService", "appService", "notificationService", DetailController]);

    function DetailController($scope, $stateParams, surveyService, appService, notificationService) {
        var currentUser = appService.getAuthenticatedUser();
        var vm = this;
        $scope.survey = {};
        $scope.options = [];
        $scope.participations = [];
        $scope.ownParticipation = {options: []};
        $scope.surveyId = $stateParams.surveyId;

        init();

        function init() {
            surveyService.loadSurveyWithId($stateParams.surveyId)
                .subscribeOnNext(function (survey) {
                    $scope.survey = survey;
                });

            surveyService.loadAllOptionsForSurveyWithId($stateParams.surveyId)
                .subscribeOnNext(function (options) {
                    $scope.options = options;
                });

            surveyService.loadAllParticipationsForSurveyWithId($stateParams.surveyId)
                .subscribeOnNext(function (participations) {
                    $scope.participations = participations;
                    filterOwnParticipation();
                });
        }

        this.participates = function (participation, option) {
            for (var i = 0; i < participation.options.length; i++) {
                if (option.id === participation.options[i].id) {
                    return true;
                }
            }
            return false;
        };

        this.sumParticipationsForOption = function (option) {
            var sum = 0;
            $scope.participations.forEach(function (participation) {
                participation.options.forEach(function (entry) {
                    if (entry.id === option.id) {
                        sum++;
                    }
                })
            });
            return sum;
        };

        this.participate = function (option) {
            if (!option.checked) {
                $scope.ownParticipation.options.push(option);
            } else {
                $scope.ownParticipation.options = $scope.ownParticipation.options.filter(function (value) {
                    return value.id !== option.id;
                })
            }
        };

        this.isOwnSurvey = function () {
            return $scope.survey.initiator
                && $scope.survey.initiator.username === currentUser.principal.username;
        };

        this.isSurveyOpen = function () {
            return $scope.survey.surveyStatus &&
                $scope.survey.surveyStatus === "OPEN";
        };

        this.save = function () {
            $scope.computing = true;
            surveyService.saveParticipationforSurvey($scope.ownParticipation, $scope.survey)
                .then(function (value) {
                    $scope.computing = false;
                    init();
                    notificationService.showNotification("DETAIL_SAVE_SUCCESS");
                });
        };

        function filterOwnParticipation() {
            $scope.participations = $scope.participations.filter(function (value, index) {
                if (value.user.username === currentUser.principal.username) {
                    $scope.ownParticipation = value;
                    $scope.options.forEach(function (option) {
                        option.checked = vm.participates($scope.ownParticipation, option);
                    });
                    return false
                }
                return true;
            });
        }
    }
}());