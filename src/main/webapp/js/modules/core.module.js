(function () {
    var core = angular.module("de.nordakademie.iaa.survey.core", [
        "rx",
        "base64"
    ]);
    core.constant("ACTION_TYPES", {
        LOAD: 'LOAD',
        ADD: 'ADD',
        EDIT: 'EDIT',
        REMOVE: 'REMOVE'
    });
    core.service("errorService", ["rx", ErrorService]);
    core.service("authService", ["$http", "$base64", AuthenticationService]);
    core.service("appService", ["$location", "authService", "errorService", "rx", AppService]);
    core.service("resourceStoreService", ["rx", "ACTION_TYPES", ResourceStoreService]);

    function AppService($location, authService, errorService, rx) {
        var isAuthenticated = false;
        this.$authenticated = new rx.BehaviorSubject(isAuthenticated);

        this.$authenticated.subscribeOnNext(function (authenticationStatus) {
            isAuthenticated = authenticationStatus;
        });

        this.login = function (username, password) {
            var authenticationStatus = this.$authenticated;
            authService.authenticate(username, password).then(
                function (success) {
                    $location.path("/dashboard");
                    authenticationStatus.onNext(true);
                },
                function (error) {
                    authenticationStatus
                        .onNext(authService.removeAuthorization());
                    errorService.showErrorNotification("login.wrong-data");
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

    function ErrorService(rx) {
        this.actualError = new rx.BehaviorSubject({message: ""});
        this.showErrorNotification = function (errorMessageKey) {
            this.actualError.onNext({message: errorMessageKey});
            console.log(errorMessageKey)
        }
    }

    function AuthenticationService($http, encoder) {
        this.authenticate = function (username, password) {
            // Try to log in
            var encodedCredentials = encoder.encode(username + ":" + password);
            setAuthorizationHeaders("Basic " + encodedCredentials);
            return $http.get("./user");
        };
        this.removeAuthorization = function () {
            setAuthorizationHeaders(null);
            return false;
        };

        function setAuthorizationHeaders(header) {
            $http.defaults.headers.common["authorization"] = header;
        }
    }

    function ResourceStoreService(rx, ACTION_TYPES) {
        var _createInstance = function (compare) {
            return new ResourceStore(rx, ACTION_TYPES, compare);
        };
        return {
            createResourceStore: _createInstance
        }
    }

    /**
     * Store for realising a reactive data storage to ensure the
     * state is equal on all views which are using observable pattern.
     *
     * @constructor
     */
    function ResourceStore(BehaviorSubject, ACTION_TYPES, compare) {
        this.resources = [];
        this.items$ = new rx.BehaviorSubject(this.resources);

        /**
         * Dispatch is designed for dispatching the actual state.
         * All observers will be notified of state change.
         *
         * @param ACTION_TYPE   describes the action type.
         *                      Must be a String with one
         *                      of the values defined in
         *                      {@link ACTION_TYPES}
         * @param resource      Entities which should be dispatched
         *                      IMPORTANT NOTE: When ACTION_TYPE
         *                      is {@link ACTION_TYPES#LOAD},
         *                      resource must be an array.
         *                      otherwise a single resource must be passed in.
         */
        this.dispatch = function (ACTION_TYPE, resource) {
            this.resources = this._reduce(ACTION_TYPE, resource);
            this.items$.onNext(this.resources);
            console.log('dispatched action: ' + ACTION_TYPE)
        };

        /**
         * Reduces the given resource with the given ACTION_TYPE to the
         * actual state of the store.
         *
         * @param ACTION_TYPE   describes the action type.
         *                      Must be a String with one
         *                      of the values defined in
         *                      {@link ACTION_TYPES}
         * @param resource      Entities which should be dispatched
         *                      IMPORTANT NOTE: When ACTION_TYPE
         *                      is {@link ACTION_TYPES#LOAD},
         *                      resource must be an array.
         *                      otherwise a single resource must be passed in.
         * @returns {*}         current state of the store after action. always an
         *                      array of objects
         * @private
         */
        this._reduce = function (ACTION_TYPE, resource) {
            switch (ACTION_TYPE) {
                case ACTION_TYPES.ADD:
                    this.resources.push(resource);
                    return this.resources;
                case ACTION_TYPES.EDIT:
                    return this.resources.map(function (entity) {
                        if (!compare(entity, resource)) {
                            return entity;
                        }
                        return resource;
                    });
                case ACTION_TYPES.REMOVE:
                    return this.resources.filter(function (entity) {
                        return !compare(entity, resource);
                    });
                case ACTION_TYPES.LOAD:
                    return resource;
                default:
                    return this.resources;
            }
        };
    }
}());