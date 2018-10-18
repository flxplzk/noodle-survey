(function() {
    var app = angular.module("de.nordakademie.iaa.i18n", [
        "pascalprecht.translate"
    ]);

    app.config(function ($translateProvider) {
        // german
        $translateProvider.translations("de_DE", {

            APP_HEADLINE:  "Angular Umfragetool",
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

            DASHBOARD_TAB_OWN: "Meine Umfragen",
            DASHBOARD_TAB_ALL: "Alle Umfragen",
            DASHBOARD_TAB_OPEN: "Offen für Teilnahme"

        });

        // english
        $translateProvider.translations("en_US", {
            APP_HEADLINE:  "Angular survey tool",
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

            DASHBOARD_TAB_OWN: "My surveys",
            DASHBOARD_TAB_ALL: "All Surveys",
            DASHBOARD_TAB_OPEN: "Open for participation"
        });

        $translateProvider.preferredLanguage("en_US");
    })
}());