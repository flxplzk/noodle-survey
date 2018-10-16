(function () {
    var core = angular.module("de.nordakademie.iaa.survey.core", [
        "de.nordakademie.iaa.i18n",
        "rx",
        "base64",
        "ui.router",
        "ngMaterial"
    ]);

    core.service("errorService", ["$mdToast", "$translate", ErrorService]);
    core.service("authService", ["$http", "$base64", AuthenticationService]);
    core.service("appService", ["$state", "authService", "errorService", "rx", AppService]);

    core.service("userService", ["$http", UserService]);
    core.service("surveyService", ["$http", "rx", "errorService", SurveyService]);

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
        this.$authenticated = new rx.BehaviorSubject(isAuthenticated);

        this.$authenticated.subscribeOnNext(function (authenticationStatus) {
            isAuthenticated = authenticationStatus;
            if (!isAuthenticated) {
                $state.go("login");
            }
        });

        this.login = function (username, password) {
            authService.authenticate(username, password).then(
                function (success) {
                    $state.go("dashboard");
                    vm.$authenticated.onNext(true);
                },
                function (error) {
                    vm.$authenticated
                        .onNext(authService.removeAuthorization());
                    errorService.showErrorNotification("AUTH_LOGIN_ERROR_CREDENTIALS");
                }
            );
        };

        this.logout = function () {
            this.$authenticated
                .onNext(authService.removeAuthorization());
        };
        this.isAuthenticated = function () {
            return isAuthenticated;
        }
    }

    /**
     *
     * @param rx
     * @constructor
     */
    function ErrorService($mdToast, $translate) {
        var position = {
            bottom: false,
            top: true,
            left: false,
            right: true
        };
        this.showErrorNotification = function (errorMessageKey) {
            var toast = $mdToast.simple()
                .content($translate.instant(errorMessageKey))
                .position('left top right');
            $mdToast.show(toast);
        }
    }

    /**
     *
     * @param $http
     * @constructor
     */
    function UserService($http) {
        this.register = function (firstName, lastName, userName, password) {
            var model = {
                firstName: firstName,
                lastName: lastName,
                password: password,
                username: userName
            };
            return $http.post("./registration", model);
        }
    }

    function SurveyService($http, rx, errorService) {
        this.loadAll = function () {
            var $surveys = new rx.BehaviorSubject([{},{},{}]);
            $http.get("./surveys").then(function (success) {
                $surveys.onNext(success);
            }, function (error) {
                errorService.showErrorNotification("DASHBOARD_NETWORK_ERROR")
            });
            return $surveys;
        }
    }

    /**
     *
     * @param $http
     * @param encoder
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