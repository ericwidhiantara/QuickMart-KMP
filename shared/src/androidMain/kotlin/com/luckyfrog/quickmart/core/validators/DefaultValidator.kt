package com.luckyfrog.quickmart.core.validators

// Password validation class
class DefaultValidator : FieldValidator<String> {
    override fun validate(value: String): Boolean {
        return value.isNotEmpty()
    }
}