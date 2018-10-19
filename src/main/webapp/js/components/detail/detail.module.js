(function () {
        var detail = angular.module("de.nordakademie.iaa.survey.detail", [
            "de.nordakademie.iaa.survey.core.domain",
            "de.nordakademie.iaa.survey.core",
            "de.nordakademie.iaa.i18n",
            "ngMaterial"
        ]);

        detail.controller("detailController", ["$scope", "$stateParams", DetailController]);

        function DetailController($scope, $stateParams) {
            $scope.options = [
                {
                    "id": -11,
                    "dateTime": "2018-10-25T15:55:25.024"
                },
                {
                    "id": -10,
                    "dateTime": "2018-10-25T15:55:25.023"
                },
                {
                    "id": -11,
                    "dateTime": "2018-10-25T15:55:25.024"
                },
                {
                    "id": -10,
                    "dateTime": "2018-10-25T15:55:25.023"
                },
                {
                    "id": -11,
                    "dateTime": "2018-10-25T15:55:25.024"
                },
                {
                    "id": -10,
                    "dateTime": "2018-10-25T15:55:25.023"
                },
                {
                    "id": -11,
                    "dateTime": "2018-10-25T15:55:25.024"
                },
                {
                    "id": -10,
                    "dateTime": "2018-10-25T15:55:25.023"
                },
                {
                    "id": -11,
                    "dateTime": "2018-10-25T15:55:25.024"
                },
                {
                    "id": -10,
                    "dateTime": "2018-10-25T15:55:25.023"
                },
                {
                    "id": -11,
                    "dateTime": "2018-10-25T15:55:25.024"
                },
                {
                    "id": -10,
                    "dateTime": "2018-10-25T15:55:25.023"
                }
            ];
            $scope.participations = [
                {
                    user: {
                        username: "Hans"
                    },
                    options: [
                        {
                            "id": -10,
                            "dateTime": "2018-10-25T15:55:25.021"
                        }
                    ]
                },
                {
                    user: {
                        username: "Torben"
                    },
                    options: [
                        {
                            "id": -10,
                            "dateTime": "2018-10-25T15:55:25.022"
                        }
                    ]
                },
                {
                    user: {
                        username: "Mongo"
                    },
                    options: [
                        {
                            "id": -10,
                            "dateTime": "2018-10-25T15:55:25.025"
                        }
                    ]
                },
                {
                    user: {
                        username: "Klaus"
                    },
                    options: [
                        {
                            "id": -10,
                            "dateTime": "2018-10-25T15:55:25.024"
                        }
                    ]
                }

            ];

            this.participates = function (participation, option) {
                for (var i = 0; i < participation.options.length; i++) {
                    if (option.dateTime === participation.options[i].dateTime) {
                        return true;
                    }
                }
                return false;
            };
            $scope.greeting = "details works: " + $stateParams.surveyId
        }
    }
    ()
)
;