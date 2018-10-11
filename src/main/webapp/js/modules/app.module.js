(function() {
    var app = angular.module("de.nordakademie.iaa.survey", [
        "ngRoute",
        "rx"
    ]);

    app.controller("mainController", ["$rootScope", function ($rootScope) {
       $rootScope.greeting = "Hallo Welt";
    }]);

    app.config(function ($routeProvider) {
        $routeProvider

            .when("/login", {
                templateUrl: "js/components/authentication/login.template.html"
            })

            .when("/register", {
                templateUrl: "js/components/authentication/register.template.html"
            })

            .otherwise({
                templateUrl: "js/components/authentication/login.template.html"
            })
    })
}());