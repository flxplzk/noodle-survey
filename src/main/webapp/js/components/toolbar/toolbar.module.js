(function () {
    var toolbar = angular.module("de.nordakademie.iaa.survey.toolbar", [
        "de.nordakademie.iaa.survey.core",
        "de.nordakademie.iaa.i18n",
        "ngMaterial"
    ]);

    toolbar.controller("mainController", ["appService", ToolBarController]);
    toolbar.directive("surveyToolBar", ToolBarDirective);
    function ToolBarController(appService) {
        this.authenticated = false;
        var vm = this;
        appService.$authenticated.subscribeOnNext(function (authenticationStatus) {
            vm.authenticated = authenticationStatus;
        });
        this.logout = function () {
            appService.logout();
        }
    }

    function ToolBarDirective() {
       return {
           restrict: "E",
           template: "<md-toolbar ng-controller=\"mainController as toolBarCrtl\">\n" +
               "    <div class=\"md-toolbar-tools\">\n" +
               "        <h2 flex md-truncate>{{\"APP_HEADLINE\"|translate}}</h2>\n" +
               "        <md-button ng-show=\"toolBarCrtl.authenticated\" ng-click=\"toolBarCrtl.logout()\"\n" +
               "                   class=\"md-raised md-accent md-hue-3\"\n" +
               "                   aria-label=\"Learn More\">\n" +
               "            {{\"APP_LOGOUT\"|translate}}\n" +
               "        </md-button>\n" +
               "    </div>\n" +
               "</md-toolbar>"
       }
    }

}());