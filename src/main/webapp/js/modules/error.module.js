(function () {
    // ########################## MODULE DECLARATION #####################################

    /**
     * @name "de.nordakademie.iaa.survey.error"
     *
     * @author Felix Plazek
     * @author Bengt-Lasse Arndt
     * @author Robert Peters
     * @author Sascha Pererva
     *
     * @type {angular.Module}
     */
    var error = angular.module("de.nordakademie.iaa.survey.error", [
        "de.nordakademie.iaa.survey.cage",
        "de.nordakademie.iaa.i18n",
        "ui.router",
        "ngMaterial"
    ]);

    error.directive("error", ErrorDirective);
    function ErrorDirective() {
        return {
            restrict: "E",
            template:
               "<div layout='column' layout-align='center center' flex>\n" +
                "    <h1>{{'ERROR_HEADING'|translate}}</h1>\n" +
                "    <h4>{{'ERROR_SUB_HEADING'|translate}}</h4>\n" +
                "    <what-would-nicolas-say></what-would-nicolas-say>\n" +
                "</div>"
        }
    }


    error.factory('errorInterceptor', function ($q, $state) {
        return {
            request: function (config) {
                return config || $q.when(config);
            },
            requestError: function (request) {
                $state.go("error");
                return $q.reject(request);
            },
            response: function (response) {
                return response || $q.when(response);
            },
            responseError: function (response) {
                if (response && response.status === 500 || response && response.status < 0) {
                    $state.go("error");
                }
                return $q.reject(response);
            }
        };
    });

    error.config(function ($httpProvider) {
        $httpProvider.interceptors.push('errorInterceptor');
    });

    app.config(function ($stateProvider) {
        var errorState = {
            name: 'error',
            url: "/oops",
            template: "<error></error>"
        };
        $stateProvider.state(errorState);
    });

}());