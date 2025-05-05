package com.sic6.masibelajar.domain.resources.wrapper

enum class LoginError : ErrorBase {
    INVALID_CREDENTIALS,
    USERNAME_NOT_FOUND,
    INVALID_IDENTIFIER_FORMAT,
    UNKNOWN
}

enum class RegisterError : ErrorBase {
    USERNAME_INVALID_FORMAT,
    EMAIL_ALREADY_EXISTS,
    USERNAME_ALREADY_EXISTS,
    PASSWORDS_DO_NOT_MATCH,
    PASSWORD_INVALID_FORMAT,
    UNKNOWN
}

enum class LogoutError : ErrorBase {
    UNKNOWN
}

enum class ResetPasswordError : ErrorBase {
    EMAIL_NOT_FOUND,
    FAILED_TO_SEND_EMAIL,
    EMAIL_INVALID_FORMAT,
    UNKNOWN
}

enum class ChangePasswordError : ErrorBase {
    USER_IS_NOT_LOGGED_IN,
    NEW_PASSWORD_INVALID_FORMAT,
    NEW_PASSWORDS_DO_NOT_MATCH,
    INVALID_OLD_PASSWORD,
    UNKNOWN
}

enum class IsUserLoggedInError : ErrorBase {
    UNKNOWN
}