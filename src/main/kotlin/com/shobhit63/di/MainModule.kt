package com.shobhit63.di

import com.shobhit63.repository.follow.FollowRepository
import com.shobhit63.repository.follow.FollowRepositoryImpl
import com.shobhit63.repository.post.PostRepository
import com.shobhit63.repository.post.PostRepositoryImpl
import com.shobhit63.repository.user.UserRepository
import com.shobhit63.repository.user.UserRepositoryImpl
import com.shobhit63.util.Constants.DATABASE_NAME
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val mainModule = module {
    single {
        KMongo.createClient().coroutine
            .getDatabase(DATABASE_NAME)
    }
    single<UserRepository> {
        UserRepositoryImpl(get())
    }
    single<FollowRepository> {
        FollowRepositoryImpl(get())
    }
    single<PostRepository> {
        PostRepositoryImpl(get())
    }
}