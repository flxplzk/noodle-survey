(function () {
    var detail = angular.module("de.nordakademie.iaa.survey.detail", [
        "de.nordakademie.iaa.survey.editor",
        "de.nordakademie.iaa.survey.core.domain",
        "de.nordakademie.iaa.survey.core",
        "de.nordakademie.iaa.i18n",
        "ngMaterial"
    ]);

    detail.controller("detailController",
        ["$scope",
            "$stateParams",
            "appService",
            "notificationService",
            "SurveyResource",
            "OptionResource",
            "ParticipationResource",
            DetailController]);

    function DetailController($scope, $stateParams, appService, notificationService,
                              SurveyResource, OptionResource, ParticipationResource) {
        var currentUser = appService.getAuthenticatedUser();
        var vm = this;
        $scope.ownParticipation = new ParticipationResource({options: []});
        $scope.surveyId = $stateParams.surveyId;

        init();

        function init() {
            $scope.survey = SurveyResource.get({survey: $stateParams.surveyId});
            $scope.options = OptionResource.query({survey: $stateParams.surveyId});
            var query = ParticipationResource.query({survey: $stateParams.surveyId});
            query.$promise.then(function (data) {
                $scope.participations = data;
                filterOwnParticipation();
            })

        }

        this.participates = function (participation, option) {
            if (!isInitialized()) { return; }
            for (var i = 0; i < participation.options.length; i++) {
                if (option.equals(participation.options[i])) {
                    return true;
                }
            }
            return false;
        };

        this.sumParticipationsForOption = function (option) {
            if (!isInitialized()) { return 0; }
            var sum = 0;
            $scope.participations.forEach(function (participation) {
                participation.options.forEach(function (entry) {
                    if (option.equals(entry)) {
                        sum++;
                    }
                })
            });
            return sum;
        };

        this.isOwnSurvey = function () {
            if (!isInitialized()) { return; }
            return angular.isDefined($scope.survey.initiator) && angular.isDefined(currentUser)
                && $scope.survey.initiator.equals(currentUser.principal);
        };

        this.save = function () {
            $scope.computing = true;
            $scope.ownParticipation.survey = $scope.survey;
            $scope.ownParticipation.options = $scope.options.filter(function (option) {
                    return option.checked;
            });
            $scope.ownParticipation.$persist($scope.surveyId)
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

        function isInitialized() {
            return angular.isDefined($scope.survey) &&
                angular.isDefined($scope.options) &&
                angular.isDefined($scope.participations)
        }
    }
}());