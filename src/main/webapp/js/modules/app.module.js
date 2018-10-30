(function() {
    var app = angular.module("de.nordakademie.iaa.survey", [
        "de.nordakademie.iaa.survey.authentication",
        "de.nordakademie.iaa.survey.toolbar",
        "de.nordakademie.iaa.survey.dashboard",
        "de.nordakademie.iaa.survey.detail",
        "de.nordakademie.iaa.survey.editor"
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
        var dashboardState = {
            name: 'dashboard',
            url: '/dashboard',
            templateUrl: "js/components/dashboard/dashboard.template.html",
            onEnter: enteringGuard
        };
        var detailState ={
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
    });
}());