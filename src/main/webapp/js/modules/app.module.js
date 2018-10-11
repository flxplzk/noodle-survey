(function() {
    var app = angular.module("de.nordakademie.iaa.survey", [
        "ngRoute",
        "ngResource",
        "rx"
    ]);

    app.controller("mainController", ["$rootScope", function ($rootScope) {
       $rootScope.greeting = "Hallo Welt";
    }]);
}());