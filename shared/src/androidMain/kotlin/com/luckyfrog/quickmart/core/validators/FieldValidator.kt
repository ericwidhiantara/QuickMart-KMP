package com.luckyfrog.quickmart.core.validators


interface FieldValidator<T> {
    fun validate(value: T): Boolean
}

fun isLoginInputValid(
    email: String,
    password: String,
    usernameValidator: DefaultValidator,
    passwordValidator: PasswordValidator
): Boolean {
    return usernameValidator.validate(email) && passwordValidator.validate(password)
}


fun isRegisterInputValid(
    fullname: String,
    username: String,
    email: String,
    password: String,
    confirmPassword: String,
    fullnameValidator: DefaultValidator,
    usernameValidator: DefaultValidator,
    emailValidator: EmailValidator,
    passwordValidator: PasswordValidator,
    confirmPasswordValidator: PasswordValidator
): Boolean {
    return fullnameValidator.validate(fullname) &&
            usernameValidator.validate(username) &&
            emailValidator.validate(email) &&
            passwordValidator.validate(password) &&
            confirmPasswordValidator.validate(confirmPassword)
}
