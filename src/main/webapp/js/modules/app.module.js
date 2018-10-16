(function() {
    var app = angular.module("de.nordakademie.iaa.survey", [
        "de.nordakademie.iaa.survey.authentication",
        "de.nordakademie.iaa.survey.toolbar",
        "de.nordakademie.iaa.survey.dashboard"
    ]);

    app.config(function ($stateProvider) {
        var enteringGuard = function ($transition$, $state$) {
            var appService = $transition$.injector().get("appService");
            var stateService = $transition$.injector().get("$state");
            if (!appService.isAuthenticated()) {
                $transition$.abort();
                stateService.go("login");
            }
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
        var dashboardState = {
            name: 'dashboard',
            url: '/dashboard',
            templateUrl: "js/components/dashboard/dashboard.template.html",
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
    });

    app.config(function ($translateProvider) {
        // german
        $translateProvider.translations("de_DE", {
            APP_HEADLINE:  "Angular Umfragetool"
        });

        // english
        $translateProvider.translations("en_US", {
            APP_HEADLINE:  "Angular survey tool"
        });

        $translateProvider.preferredLanguage("en_US");
    })
}());