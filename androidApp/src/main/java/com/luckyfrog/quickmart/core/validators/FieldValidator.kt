package com.luckyfrog.quickmart.core.validators


interface FieldValidator<T> {
    fun validate(value: T): Boolean
}

fun isLoginInputValid(
    email: String,
    password: String,
    emailValidator: EmailValidator,
    passwordValidator: PasswordValidator
): Boolean {
    return emailValidator.validate(email) && passwordValidator.validate(password)
}
