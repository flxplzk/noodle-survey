(function () {
    var domain = angular.module("de.nordakademie.iaa.survey.core.domain", [
        "ngResource"
    ]);

    domain.factory("userService", ["$resource", UserServiceFactory]);
    domain.factory("surveyService", ["$resource", SurveyServiceFactory]);
    domain.factory("participationService", ["$resource", ParticipationServiceFactory]);
    domain.factory("optionService", ["$resource", OptionServiceFactory]);
    domain.factory("userNotificationService", ["$resource", NotificationServiceFactory]);
    domain.factory("eventService", ["$resource", EventServiceFactory]);

    function UserServiceFactory($resource) {
        return $resource("./users");
    }

    function SurveyServiceFactory($resource) {
        return $resource(
            "./surveys/:survey",
            {survey: "@survey"},
            {update: {method: "PUT"}})
    }

    function ParticipationServiceFactory($resource) {
        return $resource(
            "./surveys/:survey/participations",
            {survey: "@survey"},
            {save: {method: "PUT", isArray: true}})
    }

    function OptionServiceFactory($resource) {
        return $resource(
            "./surveys/:survey/options",
            {survey: "@survey"})
    }

    function NotificationServiceFactory($resource) {
        return $resource("./users/me/notifications/:notification", {notification: "@notification"})
    }

    function EventServiceFactory($resource) {
        return $resource("./users/me/events/:event", {event: "@event"})
    }

}());