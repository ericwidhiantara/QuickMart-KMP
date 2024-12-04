package com.luckyfrog.quickmart.core.validators

// Password validation class
class PasswordValidator : FieldValidator<String> {
    override fun validate(value: String): Boolean {
        return value.length >= 6
    }
}

class ConfirmPasswordValidator(
    private val originalPassword: String
) : FieldValidator<String> {
    override fun validate(value: String): Boolean {
        return value == originalPassword
    }
}


