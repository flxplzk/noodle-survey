(function () {
    var domain = angular.module("de.nordakademie.iaa.survey.core.domain", [
        "rx"
    ]);

    domain.service("userService", ["$http", UserService]);
    domain.service("surveyService", ["$http", "rx", SurveyService]);

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

    function SurveyService($http, rx) {
        var $surveys = new rx.BehaviorSubject([]);
        this.loadAll = function () {
            $http.get("./surveys").then(function (success) {
                $surveys.onNext(success.data);
            }, function (error) {
                $surveys.onNext([]);
            });
            return $surveys;
        };

        this.createSurveyAsDraft = function(survey, options) {
            return createSurveyWithStatusAndOptions(survey, "PRIVATE", options);
        };

        this.createSurvey = function (survey, options) {
            return createSurveyWithStatusAndOptions(survey, "OPEN", options);
        };

        function createSurveyWithStatusAndOptions(survey, status, options) {
            survey.options = options;
            survey.surveyStatus = status;
            return $http.post("./surveys", survey);
        }
    }
}());