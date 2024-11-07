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
