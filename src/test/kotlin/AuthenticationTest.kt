import org.spekframework.spek2.Spek
import org.mockito.Mockito

import com.kafedra.aaapp.service.Authentication
import com.kafedra.aaapp.domain.DBWrapper
import kotlin.test.*

object AuthenticationTest: Spek({
    // Setup DBWrapper mock
    val dbMock = Mockito.mock(DBWrapper::class.java)
    val logins = listOf("bruh", "vasya")
    Mockito.`when`(dbMock.loginExists(Mockito.argThat { logins.contains(it) }?:"")).thenReturn(true)

    // Create new Authentication object for each test
    lateinit var auth: Authentication
    beforeEachTest {
        auth = Authentication(dbMock)
    }

    group("Login validation") {
        group("Valid login") {

            test("Login with arbitrary size") {
                val login = "test"
                assertTrue(auth.validateLogin(login))
            }

            test("Login with smallest valid size") {
                val login = "a"
                assertTrue(auth.validateLogin(login))
            }

            test("Login with biggest valid size") {
                val login = "abcdefghij"
                assertTrue(auth.validateLogin(login))
            }

        }

        group("Invalid login") {

            test("Empty login") {
                val login = ""
                assertFalse(auth.validateLogin(login))
            }

            test("Login too long") {
                val login = "abcdefghijk"
                assertFalse(auth.validateLogin(login))
            }

            test("Login with non-letter characters") {
                val login = "vasya!)"
                assertFalse(auth.validateLogin(login))
            }

            test("Login with numbers") {
                val login = "vasya2005"
                assertFalse(auth.validateLogin(login))
            }

            test("Login with uppercase letters") {
                val login = "Vasyan"
                assertFalse(auth.validateLogin(login))
            }

            test("Login with non-latin letters") {
                val login = "вася"
                assertFalse(auth.validateLogin(login))
            }

        }
    }

    group("Checking if login exists") {

        test("Existing login") {
            val login = "bruh"
            assertTrue(auth.loginExists(login))
        }

        test("Non-existing login") {
            val login = "test"
            assertFalse(auth.loginExists(login))
        }

    }


})