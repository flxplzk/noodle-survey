(function() {
    var app = angular.module("de.nordakademie.iaa.survey", [
        "ngRoute",
        "rx",
        "de.nordakademie.iaa.survey.core"
    ]);

    app.controller("mainController", ["$rootScope", function ($rootScope) {
       $rootScope.greeting = "Hallo Welt";
    }]);
    app.controller("loginController", ["$scope", "appService", LoginController]);
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
    });

    function LoginController($scope, appService) {
        $scope.username = "";
        $scope.password = "";

        this.login = function () {
            console.log("invoked login!");
            appService.login($scope.username, $scope.password);
        }
    }
}());