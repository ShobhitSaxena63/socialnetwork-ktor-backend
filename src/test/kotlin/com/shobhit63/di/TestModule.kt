package com.shobhit63.di

import com.shobhit63.repository.user.FakeUserRepository
import org.koin.dsl.module

val testModule = module {
    single {
        FakeUserRepository()
    }
}