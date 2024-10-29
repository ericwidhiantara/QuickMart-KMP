package com.luckyfrog.quickmart.core.validators


interface FieldValidator<T> {
    fun validate(value: T): Boolean
}