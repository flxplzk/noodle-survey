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

    function _getId() {
        return this._id;
    }
    function withIdGetter(service) {
        angular.extend(service.prototype, {
            getId: _getId
        });
        return service;
    }
    function UserServiceFactory($resource) {
        return $resource("./users");
    }

    function SurveyServiceFactory($resource) {
        var service = $resource(
            "./surveys/:survey",
            {survey: "@survey"},
            {update: {method: "PUT"}});
        withIdGetter(service);
        return service
    }

    function ParticipationServiceFactory($resource) {
        var service = $resource(
            "./surveys/:survey/participations",
            {survey: "@survey"},
            {save: {method: "PUT", isArray: true}});
        withIdGetter(service);
        return service
    }

    function OptionServiceFactory($resource) {
        var service = $resource(
            "./surveys/:survey/options",
            {survey: "@survey"});
        withIdGetter(service);
        return service
    }

    function NotificationServiceFactory($resource) {
        var service = $resource("./users/me/notifications/:notification", {notification: "@notification"});
        withIdGetter(service);
        return service
    }

    function EventServiceFactory($resource) {
        var service = $resource("./users/me/events/:event", {event: "@event"});
        withIdGetter(service);
        return service
    }

}());