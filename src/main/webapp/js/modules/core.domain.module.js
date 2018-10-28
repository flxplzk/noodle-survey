(function () {

    // ########################## MODULE DECLARATION #####################################

    /**
     * @name "de.nordakademie.iaa.survey.core.domain"
     *
     * This module provides domain Objects for this application.
     * Domain Objects are:
     *      {@link UserResource}
     *      {@link SurveyResource}
     *      {@link ParticipationResource}
     *      {@link OptionResource}
     *      {@link NotificationResource}
     *      {@link EventResource}
     *
     * @author Felix Plazek
     * @author Bengt Lasse Arndt
     * @author Robert Peters
     * @author Sascha Pererva
     *
     * @type {angular.Module}
     */
    var domain = angular.module("de.nordakademie.iaa.survey.core.domain", [
        "ngResource"
    ]);

    domain.factory("UserResource", ["$resource", UserServiceFactory]);
    domain.factory("SurveyResource", ["$resource", SurveyResourceFactory]);
    domain.factory("ParticipationResource", ["$resource", ParticipationResourceFactory]);
    domain.factory("OptionResource", ["$resource", OptionResourceFactory]);
    domain.factory("NotificationResource", ["$resource", NotificationResourceFactory]);
    domain.factory("EventResource", ["$resource", EventResourceFactory]);

    // ########################## FACTORY FUNCTIONS #####################################

    function UserServiceFactory($resource) {
        var resource = $resource("./users");
        angular.extend(resource, user);
        return resource;
    }

    function SurveyResourceFactory($resource) {
        var resource = $resource(
            "./surveys/:survey",
            {survey: "@survey"},
            {update: {method: "PUT"}});
        angular.extend(resource, survey);
        return survey
    }

    function ParticipationResourceFactory($resource) {
        var resource = $resource(
            "./surveys/:survey/participations",
            {survey: "@survey"},
            {save: {method: "PUT"}});
        angular.extend(resource, participation);
        return resource
    }

    function OptionResourceFactory($resource) {
        var resource = $resource(
            "./surveys/:survey/options",
            {survey: "@survey"},
            {
                query: {
                    method: "GET",
                    isArray: true,
                    transformResponse: transformOptions
                }
            },
            {
                get: {
                    method: "GET",
                    isArray: false,
                    transformResponse: transformOptions
                }
            });
        angular.extend(resource, option);
        return resource
    }

    function NotificationResourceFactory($resource) {
        var resource = $resource("./users/me/notifications/:notification", {notification: "@notification"});
        angular.extend(resource, notification);
        return resource
    }

    function EventResourceFactory($resource) {
        var resource = $resource("./users/me/events/:event", {event: "@event"});
        angular.extend(resource, event);
        return resource
    }

    // ########################## DOMAIN OBJECTS #####################################

    var survey = {
        getId: _getId,
        isValid: function () {
            return this.title !== ""
                && this.description !== ""
        }
    };

    var option = {
        getId: _getId,
        isValid: function () {
            return this.dateTime > new Date()
        },
        equals: function (other) {
            return other !== null &&
                this.dateTime.getTime() === other.dateTime.getTime()
        }
    };

    var participation = {
        getId: _getId
    };

    var user = {
        getId: _getId,
        isValid: function () {
            return this.firstName && this.lastName && this.username && this.password
                && this.firstName !== "" && this.lastName !== ""
                && this.username !== "" && this.password !== ""
        }
    };

    var notification = {
        getId: _getId
    };

    var event = {
        getId: _getId
    };

    // ########################## HELPER FUNCTIONS #####################################

    function _getId() {
        return this._id;
    }

    function transformOptions(data, header) {
        var options = angular.fromJson(data);
        if (angular.isArray(options)) {
            options.forEach(transformDateTime);
        } else {
            transformDateTime(options);
        }
        return options;
    }
    function transformDateTime(option) {
        option.dateTime = new Date(option.dateTime)
    }
}());