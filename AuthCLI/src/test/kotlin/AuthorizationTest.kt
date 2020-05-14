import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.kafedra.aaapp.Role
import com.kafedra.aaapp.dao.AuthorityDao
import com.kafedra.aaapp.service.Authorization
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.spekframework.spek2.Spek

object AuthorizationTest : Spek({
    // Setup dao mock
    val daoMock = mock(AuthorityDao::class.java)
    `when`(daoMock.hasAuthority("vasya", Role.WRITE, "A.B.C")).thenReturn(true)
    `when`(daoMock.hasAuthority("vasya", Role.WRITE, "A.B.C.D")).thenReturn(true)
    `when`(daoMock.hasAuthority("vasya", Role.WRITE, "A.A.A")).thenReturn(false)
    `when`(daoMock.hasAuthority("vasya", Role.WRITE, "A.B")).thenReturn(false)

    // Configure injector to use daoMock instead of AuthorityDao
    val injector = Guice.createInjector(object : AbstractModule() {
        override fun configure() {
            bind(AuthorityDao::class.java).toInstance(daoMock)
        }
    })

    // Create new Authorization object for each test
    lateinit var authorization: Authorization
    beforeEachTest {
        authorization = injector.getInstance(Authorization::class.java)
    }

    group("Test hadPermissions") {
        group("Positive tests") {

            test("vasya WRITE A.B.C (access granted)") {
                assertTrue(authorization.hasAuthority("vasya", Role.WRITE, "A.B.C"))
            }

            test("vasya WRITE A.B.C.D (access granted)") {
                assertTrue(authorization.hasAuthority("vasya", Role.WRITE, "A.B.C.D"))
            }
        }

        group("Negative tests") {

            test("vasya WRITE A.A.A (access denied)") {
                assertFalse(authorization.hasAuthority("vasya", Role.WRITE, "A.A.A"))
            }

            test("vasya EXECUTE A.B (access denied)") {
                assertFalse(authorization.hasAuthority("vasya", Role.EXECUTE, "A.B"))
            }
        }
    }

    group("Test validateRole") {

        test("Valid role (WRITE)") {
            assertTrue(authorization.validateRole("WRITE"))
        }

        test("Valid role (READ)") {
            assertTrue(authorization.validateRole("READ"))
        }

        test("Invalid role (read)") {
            assertFalse(authorization.validateRole("read"))
        }

        test("Invalid role (DEFAULT)") {
            assertFalse(authorization.validateRole("DEFAULT"))
        }
    }
})
