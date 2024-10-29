package com.luckyfrog.quickmart.core.generic.usecase

interface UseCase<In, Out> {
    suspend fun execute(input: In): Out
}