(function () {
    var app = angular.module("de.nordakademie.iaa.survey.authentication", [
        "de.nordakademie.iaa.survey.core.domain",
        "de.nordakademie.iaa.survey.core",
        "de.nordakademie.iaa.i18n",
        "ui.router",
        "ngMaterial"
    ]);

    app.controller("loginController", ["$scope", "appService", LoginController]);
    app.controller("registerController", ["$scope", "$state", "UserResource", "notificationService", RegistrationController]);

    function LoginController($scope, appService) {
        $scope.username = "";
        $scope.password = "";

        this.login = function () {
            appService.login($scope.username, $scope.password);
        }
    }

    function RegistrationController($scope, $state, UserResource, errorService) {
        $scope.user = new UserResource({
            firstName: "",
            lastName: "",
            username: "",
            password: ""
        });
        $scope.passwordRepetition = "";

        this.register = function () {
            if ($scope.user.password === $scope.passwordRepetition) {
                $scope.user.$save($scope.user, successHandler, usernameAlreadyExistsHandler);
            } else {
                errorService.showNotification("AUTH_REGISTER_PASSWORD_NON_MATCH");
            }
        };

        function usernameAlreadyExistsHandler(success) {
            if (success.status === 409) {
                errorService.showNotification("AUTH_REGISTER_USER_ALREADY_EXISTS");
            }
        }

        function successHandler(success) {
            errorService.showNotification("AUTH_REGISTER_USER_CREATED");
            $state.go("login");
        }
    }
}());