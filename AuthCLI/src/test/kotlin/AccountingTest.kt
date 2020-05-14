import com.google.inject.Guice
import com.kafedra.aaapp.domain.DBWrapper
import com.kafedra.aaapp.service.Accounting
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.mockito.Mockito.mock
import org.spekframework.spek2.Spek

object AccountingTest : Spek({
    lateinit var accounting: Accounting
    beforeEachTest {
        accounting = Accounting()
    }
    group("Test validateVol") {

        test("vol = 100") {
            assertTrue(accounting.validateVol(100))
        }
        test("vol = 0") {
            assertFalse(accounting.validateVol(0))
        }
        test("vol = -1") {
            assertFalse(accounting.validateVol(-1))
        }
        test("vol = null") {
            assertFalse(accounting.validateVol(null))
        }
    }

    group("Test validateDate") {
        test("Valid date (2020-04-14") {
            assertTrue(accounting.validateDate("2020-04-14"))
        }
        test("Invalid date (14-04-2020)") {
            assertFalse(accounting.validateDate("14-04-2020"))
        }
        test("Invalid date (2020/04/14") {
            assertFalse(accounting.validateDate("2020/04/14"))
        }
        test("Invalid date (2020-4-14") {
            assertFalse(accounting.validateDate("2020-4-14"))
        }
    }
})
