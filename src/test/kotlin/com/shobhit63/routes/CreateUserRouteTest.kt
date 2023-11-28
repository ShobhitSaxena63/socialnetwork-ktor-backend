package com.shobhit63.routes

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.shobhit63.data.models.User
import com.shobhit63.data.requests.CreateAccountRequest
import com.shobhit63.di.testModule
import com.shobhit63.repository.user.FakeUserRepository
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import io.ktor.util.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.inject


class CreateUserRouteTest : KoinTest {
    private val userRepository by inject<FakeUserRepository>()
    private val gson = Gson()

    @Before
    fun setUp() {
        startKoin {
            modules(testModule)
        }
    }

    @Test
    fun `Create user, no body attached, responds with BadRequest`() = testApplication {
        application {
            routing {
                createUser(userRepository)
            }
        }
        client.post("/api/user/create").apply {
            assertThat(status).isEqualTo(HttpStatusCode.BadRequest)
        }
    }

    @Test
    fun `Create user, user already exists, responds with unsuccessful`() = testApplication {
        val user = User(
            email = "test@test.com",
            username = "test",
            password = "test",
            profileImageUrl = "",
            bio = "",
            gitHubUrl = null,
            instagramURl = null,
            linkedInURl = null
        )
        userRepository.createUser(user)
        application {
            routing {
                createUser(userRepository)
            }
        }

        client.post("/api/user/create") {
            val body = CreateAccountRequest(
                email = "test@test.com",
                username = "sss",
                password = "asd"
            )

            val serializedUser = Json.encodeToString(body)
            setBody(serializedUser)
        }.apply {
            assertThat(status).isEqualTo(HttpStatusCode.BadRequest)
        }


    }

    @OptIn(InternalAPI::class)
    @Test
    fun `Create user, email is empty, responds with unsuccessful`() = testApplication {

        application {
            routing {
                createUser(userRepository)
            }
        }

        val request = client.post("/api/user/create") {
            val body = CreateAccountRequest(
                email = "ssss",
                username = "sss",
                password = "asd"
            )

            val json = gson.toJson(body)
            setBody(json)
        }.apply {
            println("***********************************START*************************************************")
            println(this.call)
            println(this.request.attributes.allKeys)
            println(this.requestTime)
            println(this.content)
            println(this.headers)

            println("***********************************END***************************************************")
        }




    }

}

