(function () {
    // ########################## MODULE DECLARATION #####################################

    /**
     * @name "de.nordakademie.iaa.survey"
     *
     * this is the app
     * @author Felix Plazek
     * @author Bengt-Lasse Arndt
     * @author Robert Peters
     * @author Sascha Pererva
     *
     * @type {angular.Module}
     */
    var app = angular.module("de.nordakademie.iaa.survey", [
        "de.nordakademie.iaa.survey.authentication",
        "de.nordakademie.iaa.survey.toolbar",
        "de.nordakademie.iaa.survey.dashboard",
        "de.nordakademie.iaa.survey.detail",
        "de.nordakademie.iaa.survey.editor",
        "de.nordakademie.iaa.survey.error"
    ]);
}());