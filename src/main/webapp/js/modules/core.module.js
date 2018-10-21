(function () {
    var core = angular.module("de.nordakademie.iaa.survey.core", [
        "de.nordakademie.iaa.i18n",
        "rx",
        "base64",
        "ui.router",
        "ngMaterial"
    ]);

    core.service("notificationService", ["$mdToast", "$translate", NotificationService]);
    core.service("authService", ["$http", "$base64", AuthenticationService]);
    core.service("appService", ["$state", "authService", "notificationService", "rx", AppService]);

    /**
     * Central service for login and logout + retrieving info concerning logged in user
     *
     * @param $state from ui.router
     * @param authService from core module
     * @param errorService from core module
     * @param rx from rx-angular
     * @constructor default constructor
     */
    function AppService($state, authService, errorService, rx) {
        var isAuthenticated = false;
        var vm = this;
        this.authenticatedUser = null;
        this.$authenticated = new rx.BehaviorSubject(isAuthenticated);

        this.$authenticated.subscribeOnNext(function (authenticationStatus) {
            isAuthenticated = authenticationStatus;
            if (!isAuthenticated) {
                vm.authenticatedUser = null;
                $state.go("login");
            }
        });

        this.login = function (username, password) {
            authService.authenticate(username, password).then(
                function (success) {
                    $state.go("dashboard");
                    vm.authenticatedUser = success.data;
                    vm.$authenticated.onNext(true);
                },
                function (error) {
                    vm.$authenticated
                        .onNext(authService.removeAuthorization());
                    errorService.showNotification("AUTH_LOGIN_ERROR_CREDENTIALS");
                }
            );
        };

        this.logout = function () {
            this.$authenticated
                .onNext(authService.removeAuthorization());
        };
        this.isAuthenticated = function () {
            return isAuthenticated;
        };
        this.getAuthenticatedUser = function () {
            return this.authenticatedUser;
        }
    }

    /**
     * NotificationService; informs user via toast on events.
     *
     * @param $mdToast ngMaterial ToastService
     * @param $translate ngTranslate translationService
     * @constructor
     */
    function NotificationService($mdToast, $translate) {
        var position = {
            bottom: false,
            top: true,
            left: false,
            right: true
        };
        this.showNotification = function (errorMessageKey) {
            var toast = $mdToast.simple()
                .content($translate.instant(errorMessageKey))
                .position('left bottom right');
            $mdToast.show(toast);
        }
    }

    /**
     * Service for ensuring httpbasic authentication, by setting http authorization header information.
     *
     * @param $http http service
     * @param encoder base 64 encoder
     * @constructor
     */
    function AuthenticationService($http, encoder) {
        this.authenticate = function (username, password) {
            // Try to log in
            var encodedCredentials = encoder.encode(username + ":" + password);
            setAuthorizationHeaders("Basic " + encodedCredentials);
            return $http.get("./authentication");
        };
        this.removeAuthorization = function () {
            setAuthorizationHeaders(null);
            return false;
        };

        function setAuthorizationHeaders(header) {
            $http.defaults.headers.common["authorization"] = header;
        }
    }
}());