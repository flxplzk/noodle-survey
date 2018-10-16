(function() {
    var app = angular.module("de.nordakademie.iaa.i18n", [
        "pascalprecht.translate"
    ]);

    app.config(function ($translateProvider) {
        // german
        $translateProvider.translations("de_DE", {

            APP_HEADLINE:  "Angular Umfragetool",
            APP_LOGOUT: "Abmelden",

            AUTH_LOGIN_HEADLINE: "Please Sign in",
            AUTH_LOGIN_EMAIL_LABEL: "Email adress",
            AUTH_LOGIN_PASSWORD_LABEL: "Password",
            AUTH_LOGIN_REGISTER: "No Account yet? Register!",
            AUTH_LOGIN_SIGN_IN: "Sign in",
            AUTH_REGISTER_HEADLINE: "Register",
            AUTH_REGISTER_FIRST_NAME_LABEL: "First name",
            AUTH_REGISTER_LAST_NAME_LABEL: "Last name",
            AUTH_REGISTER_REPEAT_PASSWORD_LABEL: "Repeat Password",
            AUTH_REGISTER_BUTTON_REGISTER: "Register",
            AUTH_REGISTER_ERROR_EMAIL: "Your email must be between 10 and 100 characters long and look like an e-mail address.",
            AUTH_REGISTER_ERROR_PASSWORD_STRENGTH: "Password is required and must be secure! with at least 8 characters."

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
            AUTH_REGISTER_HEADLINE: "Register",
            AUTH_REGISTER_FIRST_NAME_LABEL: "First name",
            AUTH_REGISTER_LAST_NAME_LABEL: "Last name",
            AUTH_REGISTER_REPEAT_PASSWORD_LABEL: "Repeat Password",
            AUTH_REGISTER_BUTTON_REGISTER: "Register",
            AUTH_REGISTER_ERROR_EMAIL: "Your email must be between 10 and 100 characters long and look like an e-mail address.",
            AUTH_REGISTER_ERROR_PASSWORD_STRENGTH: "Password is required and must be secure! with at least 8 characters."

        });

        $translateProvider.preferredLanguage("en_US");
    })
}());