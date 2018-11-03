(function () {
    // ########################## MODULE DECLARATION #####################################

    /**
     * @name "de.nordakademie.iaa.survey.error"
     *
     * Module for reacting to unexpected server errors.
     *
     * @author Felix Plazek
     * @author Bengt-Lasse Arndt
     * @author Robert Peters
     * @author Sascha Pererva
     *
     * @type {angular.Module}
     */
    var error = angular.module("de.nordakademie.iaa.survey.error", [
        "de.nordakademie.iaa.survey.routes",
        "de.nordakademie.iaa.survey.cage",
        "de.nordakademie.iaa.i18n",
        "ngMaterial"
    ]);

    var ERROR_STATE = 'error';
    error.directive("error", ["ROUTE_STATES", ErrorDirective]);
    function ErrorDirective(ROUTE_STATES) {
        return {
            restrict: "E",
            template:
               "<div layout='column' layout-align='center center' flex>\n" +
                "    <h1>{{'ERROR_HEADING'|translate}}</h1>\n" +
                "    <h4>{{'ERROR_SUB_HEADING'|translate}}</h4>\n" +
                "    <what-would-nicolas-say></what-would-nicolas-say>\n" +
                "<md-button class='md-icon-button md-raised' ui-sref=" + ROUTE_STATES.DASHBOARD_STATE +">" +
                "<i class='material-icons'>home</i> "+
                "</md-button> " +
                "</div>"
        }
    }

    /**
     * show error page in case of 500
     */
    error.factory('errorInterceptor', function ($q, $state) {
        return {
            request: function (config) {
                return config || $q.when(config);
            },
            requestError: function (request) {
                $state.go(ERROR_STATE);
                return $q.reject(request);
            },
            response: function (response) {
                return response || $q.when(response);
            },
            responseError: function (response) {
                if (response && response.status === 500 || response && response.status < 0) {
                    $state.go(ERROR_STATE);
                }
                return $q.reject(response);
            }
        };
    });

    /**
     * config is done here for easy use of error module.
     */
    error.config(function ($httpProvider, $stateProvider) {
        $httpProvider.interceptors.push('errorInterceptor');
        var errorState = {
            name: ERROR_STATE,
            url: "/oops",
            template: "<error></error>"
        };
        $stateProvider.state(errorState);
    });

}());