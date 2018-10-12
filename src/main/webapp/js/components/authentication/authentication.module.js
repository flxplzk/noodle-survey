(function () {
    var app = angular.module("de.nordakademie.iaa.survey.authentication", [
        "de.nordakademie.iaa.survey.core",
        "ui.router",
        "ngMaterial"
    ]);

    app.controller("loginController", ["$scope", "appService", LoginController]);
    app.controller("registerController", ["appService", "$state", RegistrationController]);

    function LoginController($scope, appService) {
        $scope.username = "";
        $scope.password = "";

        this.login = function () {
            console.log("invoked login!");
            appService.login($scope.username, $scope.password);
        }
    }

    function RegistrationController($scope, $state) {
        $scope.firstName = "";
        $scope.lastName = "";
        $scope.userName = "";
        $scope.password = "";
        $scope.passwordRepetition = "";
        $scope.passwordError = "";


        this.register = function () {
            var isSamePw = this.validPassword();
            if (isSamePw) {
                $scope.passwordError = null;
                // userService.register($scope.model)
                $state.go("login");
            } else {
                $scope.passwordError = "passwords must match";
            }

        };

        this.validPassword = function () {
            return $scope.password === $scope.passwordRepetition;
        }
    }
}());