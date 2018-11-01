(function () {
    /**
     * @name "de.nordakademie.iaa.survey.dashboard"
     *
     * ToolBarModule
     * @author Felix Plazek
     * @author Sascha Pererva
     *
     * @type {angular.Module}
     */
    var dashboard = angular.module("de.nordakademie.iaa.survey.dashboard", [
        "de.nordakademie.iaa.survey.core.domain",
        "de.nordakademie.iaa.survey.core",
        "de.nordakademie.iaa.i18n",
        "ngMaterial"
    ]);

    dashboard.controller("dashboardController", ["$scope", "SurveyResource", "$state", "$mdDialog", DashboardController]);
    dashboard.controller("dashboardSideNavController", ["$scope", "NotificationResource", "EventResource", "appService", "$timeout", DashboardSideNavController]);
    dashboard.directive("dashboardSideNav", DashboardSideNavDirective);

    function DashboardSideNavController($scope, NotificationResource, EventResource, appService, $timeout) {
        $scope.loggedIn = appService.isAuthenticated();
        init();

        function init(){
            if (appService.isAuthenticated()) {
                $scope.events = EventResource.query();
                var query = NotificationResource.query();
                query.$promise.then(function (success) {
                    $scope.notifications = success;
                });
                $timeout(init, 30000);
            }
        }
        appService.$authenticated.subscribeOnNext(function (authenticationStatus) {
            $scope.loggedIn = authenticationStatus;
            if (!authenticationStatus) {
                $scope.events = [];
                $scope.notifications = [];
            } else {
                init();
            }
        })
    }

    function DashboardSideNavDirective() {
        return {
            restrict: "E",
            templateUrl: "/js/components/dashboard/dashboard.side.nav.template.html",
            controller: "dashboardSideNavController",
            controllerAs: "sideNavCrtl"
        }
    }

    function DashboardController($scope, SurveyResource, $state, $mdDialog) {
        $scope.model = {
            surveys: [],
            loading: true,
            tabs: [],
            selectedIndex: 0
        };

        // On init load all surveys from backend.
        var query = SurveyResource.query();
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