import com.kafedra.aaapp.Role
import com.kafedra.aaapp.domain.DBWrapper
import com.kafedra.aaapp.domain.Permission
import com.kafedra.aaapp.service.Authorization
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.spekframework.spek2.Spek

object AuthorizationTest : Spek({
    val dbWrapperMock: DBWrapper = mock(DBWrapper::class.java)

    var authorization: Authorization
    group("Test hadPermissions") {

        group("Positive tests") {

            test("vasya WRITE A.B.C (access granted)") {
                `when`(dbWrapperMock.hasPermission(
                        "vasya",
                        "WRITE",
                        """^A(\.B(\.C)?)?$"""
                )).thenReturn(true)

                authorization = Authorization(dbWrapperMock)
                assertTrue(authorization.hasPermission(Permission("vasya", Role.WRITE, "A.B.C")))
            }

            test("vasya WRITE A.B.C.D (access granted)") {
                `when`(dbWrapperMock.hasPermission(
                        "vasya",
                        "WRITE",
                        """^A(\.B(\.C(\.D)?)?)?$"""
                )).thenReturn(true)

                authorization = Authorization(dbWrapperMock)
                assertTrue(authorization.hasPermission(Permission("vasya", Role.WRITE, "A.B.C.D")))
            }
        }

        group("Negative tests") {

            test("vasya WRITE A.A.A (access denied)") {
                `when`(dbWrapperMock.hasPermission(
                        "vasya",
                        "WRITE",
                        """^A(\.A(\.A)?)?$"""
                )).thenReturn(false)

                authorization = Authorization(dbWrapperMock)
                assertFalse(authorization.hasPermission(Permission("vasya", Role.WRITE, "A.A.A")))
            }

            test("vasya EXECUTE A.B (access denied)") {
                `when`(dbWrapperMock.hasPermission(
                        "vasya",
                        "WRITE",
                        """^A(\.B)?$"""
                )).thenReturn(false)

                authorization = Authorization(dbWrapperMock)
                assertFalse(authorization.hasPermission(Permission("vasya", Role.EXECUTE, "A.B")))
            }
        }
    }

    authorization = Authorization(dbWrapperMock)
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
