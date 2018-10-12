(function() {
    var app = angular.module("de.nordakademie.iaa.survey.authentication", [
        "de.nordakademie.iaa.survey.core"
    ]);

    app.controller("loginController", ["$scope", "appService", LoginController]);
    app.controller("registerController", ["appService", "$state", RegistrationController]);

    function LoginController($scope, appService) {
        $scope.model = {
            username: "",
            password: ""
        };

        this.login = function () {
            console.log("invoked login!");
            appService.login($scope.username, $scope.password);
        }
    }

    function RegistrationController($scope, $state) {
        $scope.model = {
            firstName: "",
            lastName: "",
            userName: "",
            password: "",
            passwordRepetition: "",
            passwordError: null
        };

        this.register = function () {
            var isSamePw = this.validPassword();
            if (isSamePw) {
                $scope.model.passwordError = null;
                // userService.register($scope.model)
                $state.go("login");
            } else {
                $scope.model.passwordError = "passwords must match";
            }

        };

        this.validPassword = function () {
            return $scope.model.password === $scope.model.passwordRepetition;
        }
    }
}());