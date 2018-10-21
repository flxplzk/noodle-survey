(function () {
    var app = angular.module("de.nordakademie.iaa.survey.authentication", [
        "de.nordakademie.iaa.survey.core.domain",
        "de.nordakademie.iaa.survey.core",
        "de.nordakademie.iaa.i18n",
        "ui.router",
        "ngMaterial"
    ]);

    app.controller("loginController", ["$scope", "appService", LoginController]);

    app.controller("registerController", ["$scope", "$state", "userService", "notificationService", RegistrationController]);

    function LoginController($scope, appService) {
        $scope.username = "";
        $scope.password = "";

        this.login = function () {
            appService.login($scope.username, $scope.password);
        }
    }

    function RegistrationController($scope, $state, userService, errorService) {
        $scope.firstName = "";
        $scope.lastName = "";
        $scope.username = "";
        $scope.password = "";
        $scope.passwordRepetition = "";
        $scope.passwordError = "";

        this.register = function () {
            if ($scope.password === $scope.passwordRepetition) {
                $scope.passwordError = null;
                userService.register($scope.firstName,
                    $scope.lastName,
                    $scope.username,
                    $scope.password).then(successHandler, usernameAlreadyExistsHandler);
            } else {
                errorService.showNotification("AUTH_REGISTER_PASSWORD_NON_MATCH");
            }
        };

        function usernameAlreadyExistsHandler(success) {
           // if (success.status === 409) {
                errorService.showNotification("AUTH_REGISTER_USER_ALREADY_EXISTS");
           // }
        }

        function successHandler(success) {
            errorService.showNotification("AUTH_REGISTER_USER_CREATED");
            $state.go("login");
        }
    }
}());