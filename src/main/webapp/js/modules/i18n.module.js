(function () {
    var app = angular.module("de.nordakademie.iaa.i18n", [
        "pascalprecht.translate"
    ]);

    app.config(function ($translateProvider) {
        // german
        $translateProvider.translations("de_DE", {

            APP_HEADLINE: "Angular Umfragetool",
            APP_LOGOUT: "Abmelden",

            AUTH_LOGIN_HEADLINE: "Bitte melde Dich an",
            AUTH_LOGIN_EMAIL_LABEL: "Email Adresse",
            AUTH_LOGIN_PASSWORD_LABEL: "Passwort",
            AUTH_LOGIN_REGISTER: "Nocht kein Konto? Registrieren!",
            AUTH_LOGIN_SIGN_IN: "Anmelden",
            AUTH_LOGIN_ERROR_CREDENTIALS: "Die eingegebenen Anmeldedaten sind leider falsch!",
            AUTH_REGISTER_HEADLINE: "Registrieren",
            AUTH_REGISTER_FIRST_NAME_LABEL: "Vorname",
            AUTH_REGISTER_LAST_NAME_LABEL: "Nachname",
            AUTH_REGISTER_REPEAT_PASSWORD_LABEL: "Passwort wiederholen",
            AUTH_REGISTER_BUTTON_REGISTER: "Konto erstellen",
            AUTH_REGISTER_ERROR_EMAIL: "Deine Email Adresse muss aussehen wie eine Email adresse und zwischen 10 und 100 Zeichen enthalten.",
            AUTH_REGISTER_ERROR_PASSWORD_STRENGTH: "Das Passwort darf nicht leer sein, muss midestens eine Zahl, ein Sonderzeichen und jeweils einen Groß und einen kleinbuchstaben enthalten.",
            AUTH_REGISTER_USER_ALREADY_EXISTS: "Es existiert bereits ein Benutzer mit dieser Emailadresse. Bitte wähle eine Andere.",
            AUTH_REGISTER_PASSWORD_NON_MATCH: "Die Passwörter müssen übereinstimmen.",
            AUTH_REGISTER_USER_CREATED: "Benutzer erfolgreich erstellt.",

            DASHBOARD_OPEN_DETAILS: "Öffnen",
            DASHBOARD_TITLE: "Alle Umfragen im Überblick",
            EDITOR_TITLE: "Neue Umfrage erstellen",

            EDITOR_SAVE: "Speichern",
            EDITOR_CANCEL: "Abbrechen",
            EDITOR_FORM_TITLE: "Titel",
            EDITOR_FORM_ERROR_REQUIRED: "Dieses Feld muss gefüllt sein",
            EDITOR_FORM_ERROR_MAX_LENGTH: "Der Titel einer Umfrage darf nicht länger als 30 Zeichen sein!",
            EDITOR_FORM_DESCRIPTION: "Description",
            EDITOR_FORM_ERROR_MIN_LENGTH: "Der Beschreibung einer Umfrage darf nicht kürzer als 20 Zeichen sein!",
            EDITOR_FORM_TYPE_DATE: "Datum - Umfrage um einen Tag zu bestimmen",
            EDITOR_FORM_TYPE_TIME: "Uhrzeit - Umfrage um einen Tag mit Uhrzeit zu bestimmen",
            EDITOR_FORM_TYPE_TIME_RANGE: "Zeitspanne - Umfrage um einen Tag mit Zeitspanne bestimmen",
            EDITOR_FORM_DATE_RANGE: "Tagesspanne - Umfrage um eine Datumsbereich zu bestimmen",
            EDITOR_FORM_SURVEY_TYPE: "Umfragentyp",
            EDITOR_FORM_OPTION_HEADING: "Bitte füge ein paar Optionen hinzu",
            EDITOR_FORM_OPTION_FROM: "Datum auswählen"
        });

        // english
        $translateProvider.translations("en_US", {
            APP_HEADLINE: "Angular survey tool",
            APP_LOGOUT: "Logout",

            AUTH_LOGIN_HEADLINE: "Please Sign in",
            AUTH_LOGIN_EMAIL_LABEL: "Email adress",
            AUTH_LOGIN_PASSWORD_LABEL: "Password",
            AUTH_LOGIN_REGISTER: "No Account yet? Register!",
            AUTH_LOGIN_SIGN_IN: "Sign in",
            AUTH_LOGIN_ERROR_CREDENTIALS: "Your login data is wrong",
            AUTH_REGISTER_HEADLINE: "Register",
            AUTH_REGISTER_FIRST_NAME_LABEL: "First name",
            AUTH_REGISTER_LAST_NAME_LABEL: "Last name",
            AUTH_REGISTER_REPEAT_PASSWORD_LABEL: "Repeat Password",
            AUTH_REGISTER_BUTTON_REGISTER: "Register",
            AUTH_REGISTER_ERROR_EMAIL: "Your email must be between 10 and 100 characters long and look like an e-mail address.",
            AUTH_REGISTER_ERROR_PASSWORD_STRENGTH: "Password is required and must be secure! with at least 8 characters.",
            AUTH_REGISTER_USER_ALREADY_EXISTS: "Username is already in use. please select a new one",
            AUTH_REGISTER_PASSWORD_NON_MATCH: "The passwords must match.",
            AUTH_REGISTER_USER_CREATED: "User created successfully.",

            DASHBOARD_OPEN_DETAILS: "Öffnen",
            DASHBOARD_TITLE: "All surveys",
            EDITOR_TITLE: "Create new Survey",

            EDITOR_SAVE: "Save",
            EDITOR_CANCEL: "Cancel",
            EDITOR_FORM_TITLE: "Title",
            EDITOR_FORM_ERROR_REQUIRED: "This field is required",
            EDITOR_FORM_ERROR_MAX_LENGTH: "The title must be less than 30 characters long.",
            EDITOR_FORM_DESCRIPTION: "Description",
            EDITOR_FORM_ERROR_MIN_LENGTH: "The description of a survey must not contain less than 20 characters.",
            EDITOR_FORM_TYPE_DATE: "Date",
            EDITOR_FORM_TYPE_TIME: "Time",
            EDITOR_FORM_TYPE_TIME_RANGE: "Timerange",
            EDITOR_FORM_DATE_RANGE: "Daterange",
            EDITOR_FORM_SURVEY_TYPE: "Survey type",
            EDITOR_FORM_OPTION_FROM: "Select Date",
            EDITOR_FORM_OPTION_HEADING: "Please add options"
        });

        $translateProvider.preferredLanguage("en_US");
    })
}());