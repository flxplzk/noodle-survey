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
        var surveys$ = new rx.BehaviorSubject([]);
        this.loadAll = function () {
            dispatch($http.get("./surveys"), surveys$);
            return surveys$;
        };

        this.createSurveyAsDraft = function(survey, options) {
            return createSurveyWithStatusAndOptions(survey, "PRIVATE", options);
        };

        this.createSurvey = function (survey, options) {
            return createSurveyWithStatusAndOptions(survey, "OPEN", options);
        };

        this.loadAllOptionsForSurveyWithId = function (surveyId) {
            var options$ = new rx.BehaviorSubject([]);
            dispatch($http.get("./surveys/"+surveyId+"/options"), options$);
            return options$;
        };

        this.loadAllParticipationsForSurveyWithId = function (surveyId) {
            var participations$ = new rx.BehaviorSubject([]);
            dispatch($http.get("./surveys/"+surveyId+"/participations"), participations$);
            return participations$;
        };

        function createSurveyWithStatusAndOptions(survey, status, options) {
            survey.options = options;
            survey.surveyStatus = status;
            return $http.post("./surveys", survey);
        }

        function dispatch(promise, subject$) {
            promise.then(function (success) {
                subject$.onNext(success.data);
            }, function (error) {
                subject$.onNext([]);
            });
        }
    }
}());