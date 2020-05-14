import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.kafedra.aaapp.dao.UserDao
import com.kafedra.aaapp.domain.User
import com.kafedra.aaapp.service.Authentication
import kotlin.test.*
import org.mockito.Mockito
import org.spekframework.spek2.Spek

object AuthenticationTest : Spek({
    // Setup dao mock
    val daoMock = Mockito.mock(UserDao::class.java)
    Mockito.`when`(daoMock.loginExists("bruh")).thenReturn(true)
    Mockito.`when`(daoMock.getUser("bruh")).thenReturn(User(1, "bruh",
            "iYqHUi2<2zPhrGIL8]?p8m;bteA?ETaT",
            "dc6a8709e9fc8de1acea34fdc98c842911686ca0c2a0b12127c512a5ed7ab382"))
    Mockito.`when`(daoMock.getUser("test")).thenReturn(User(2, "test",
            "olMMIDct3GkrY:?Xp1WDJOPTw2IY0`a[",
            "c6d6ced902fe90f039f168837f7ce3d313df040e071281317fc6781a60cac2bc"))

    // Configure injector to use daoMock instead of UserDao
    val injector = Guice.createInjector(object : AbstractModule() {
        override fun configure() {
            bind(UserDao::class.java).toInstance(daoMock)
        }
    })

    // Create new Authentication object for each test
    lateinit var auth: Authentication
    beforeEachTest {
        auth = injector.getInstance(Authentication::class.java)
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

    group("Authentication") {

        test("Right login-password combination") {
            val login = "bruh"
            val pass = "123"
            assertTrue(auth.authenticate(login, pass))
        }

        test("Existing login, wrong password") {
            val login = "bruh"
            val pass = "asdf"
            assertFalse(auth.authenticate(login, pass))
        }

        test("Password of another user") {
            val login = "bruh"
            val pass = "admin"
            assertFalse(auth.authenticate(login, pass))
        }
    }
})
