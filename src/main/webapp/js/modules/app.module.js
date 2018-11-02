(function () {
    // ########################## MODULE DECLARATION #####################################

    /**
     * @name "de.nordakademie.iaa.survey"
     *
     * this is the app
     * @author Felix Plazek
     * @author Bengt-Lasse Arndt
     * @author Robert Peters
     * @author Sascha Pererva
     *
     * @type {angular.Module}
     */
    var app = angular.module("de.nordakademie.iaa.survey", [
        "de.nordakademie.iaa.survey.authentication",
        "de.nordakademie.iaa.survey.toolbar",
        "de.nordakademie.iaa.survey.dashboard",
        "de.nordakademie.iaa.survey.detail",
        "de.nordakademie.iaa.survey.editor",
        "de.nordakademie.iaa.survey.error"
    ]);

    app.config(function ($stateProvider) {
        var enteringGuard = function ($transition$, $state$) {
            var appService = $transition$.injector().get("appService");
            var stateService = $transition$.injector().get("$state");
            appService.testAuthentication(function (success) {
                console.log("user authenticated; access granted!");
            }, function (reject) {
                $transition$.abort();
                stateService.go("login");
                console.log("user not authenticated; redirected to login!");

            });
        };

        var loginState = {
            name: 'login',
            url: '/login',
            templateUrl: "js/components/authentication/login.template.html"
        };
        var registerState = {
            name: 'register',
            url: '/register',
            templateUrl: "js/components/authentication/register.template.html"
        };
        var errorState = {
            name: 'error',
            url: "/oops",
            template: "<error></error>"
        };
        var dashboardState = {
            name: 'dashboard',
            url: '/dashboard',
            templateUrl: "js/components/dashboard/dashboard.template.html",
            onEnter: enteringGuard
        };
        var detailState = {
            name: "detail",
            url: "/detail/{surveyId}",
            templateUrl: "js/components/detail/detail.template.html",
            onEnter: enteringGuard
        };
        var defaultState = {
            name: 'home',
            url: '',
            templateUrl: "js/components/dashboard/dashboard.template.html",
            onEnter: enteringGuard
        };

        $stateProvider.state(loginState);
        $stateProvider.state(registerState);
        $stateProvider.state(dashboardState);
        $stateProvider.state(defaultState);
        $stateProvider.state(detailState);
        $stateProvider.state(errorState);
    });

    app.factory('errorInterceptor', function ($q, $state) {
        return {
            request: function (config) {
                return config || $q.when(config);
            },
            requestError: function (request) {
                $state.go("error");
                return $q.reject(request);
            },
            response: function (response) {
                return response || $q.when(response);
            },
            responseError: function (response) {
                if (response && response.status === 500 || response && response.status < 0) {
                    $state.go("error");
                }
                return $q.reject(response);
            }
        };
    });

    app.config(function ($httpProvider) {
        $httpProvider.interceptors.push('errorInterceptor');
    });
}());