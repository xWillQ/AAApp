import com.kafedra.aaapp.App
import kotlin.test.assertEquals
import org.spekframework.spek2.Spek

object AppTest : Spek({
    lateinit var app: App

    beforeEachTest {
        app = App()
    }

    group("Help") {

        test("No args") {
            assertEquals(1, app.run(arrayOf()).code)
        }

        test("Wrong argument") {
            assertEquals(1, app.run(arrayOf("-q")).code)
        }

        test("Not enough args for authentication") {
            assertEquals(0, app.run(arrayOf("-login", "vasya")).code)
        }
    }

    group("Authentication") {

        test("Right login and pass") {
            assertEquals(0, app.run(arrayOf("-login", "vasya", "-pass", "123")).code)
        }

        test("Right login and pass in different order") {
            assertEquals(0, app.run(arrayOf("-pass", "123", "-login", "vasya")).code)
        }

        test("Wrong login") {
            assertEquals(2, app.run(arrayOf("-login", "VASYA", "-pass", "123")).code)
        }

        test("Non-existent login") {
            assertEquals(3, app.run(arrayOf("-login", "asd", "-pass", "123")).code)
        }

        test("Right login, wrong pass") {
            assertEquals(4, app.run(arrayOf("-login", "admin", "-pass", "1234")).code)
        }

        test("Login from previous test with right pass") {
            assertEquals(0, app.run(arrayOf("-login", "admin", "-pass", "admin")).code)
        }
    }

    group("Authorization") {

        test("Access to resource with right role") {
            assertEquals(0, app.run(arrayOf("-login", "vasya", "-pass", "123", "-role", "READ", "-res", "A")).code)
        }

        test("Wrong role") {
            assertEquals(5, app.run(arrayOf("-login", "vasya", "-pass", "123", "-role", "DELETE", "-res", "A")).code)
        }

        test("No access") {
            assertEquals(6, app.run(arrayOf("-login", "vasya", "-pass", "123", "-role", "WRITE", "-res", "A")).code)
        }

        test("Access to child resource") {
            assertEquals(0, app.run(arrayOf("-login", "vasya", "-pass", "123", "-role", "READ", "-res", "A.B")).code)
        }

        test("Access to child, different user") {
            assertEquals(0, app.run(arrayOf("-login", "admin", "-pass", "admin", "-role", "WRITE", "-res", "A.B.C")).code)
        }

        test("Wrong pass and role (verify that authorization is after authentication)") {
            assertEquals(4, app.run(arrayOf("-login", "vasya", "-pass", "1234", "-role", "DELETE", "-res", "A")).code)
        }

        test("Access to resource with right role") {
            assertEquals(0, app.run(arrayOf("-login", "vasya", "-pass", "123", "-role", "WRITE", "-res", "A.B.C")).code)
        }

        test("Role without res, only authentication") {
            assertEquals(0, app.run(arrayOf("-login", "admin", "-pass", "admin", "-role", "READ")).code)
        }

        test("Access to res with permission to a child") {
            assertEquals(6, app.run(arrayOf("-login", "admin", "-pass", "admin", "-role", "EXECUTE", "-res", "A")).code)
        }

        test("Access to res with permission to a brother") {
            assertEquals(6, app.run(arrayOf("-login", "admin", "-pass", "admin", "-role", "WRITE", "-res", "A.A")).code)
        }
    }

    group("Accounting") {

        test("Successful accounting") {
            assertEquals(0, app.run(arrayOf("-login", "vasya", "-pass", "123", "-role", "READ", "-res", "A", "-ds", "2020-03-10", "-de", "2020-04-01", "-vol", "1024")).code)
        }

        test("Year is too big") {
            assertEquals(7, app.run(arrayOf("-login", "vasya", "-pass", "123", "-role", "READ", "-res", "A", "-ds", "20202-03-10", "-de", "2020-04-01", "-vol", "1024")).code)
        }

        test("Month doesn't exist") {
            assertEquals(7, app.run(arrayOf("-login", "vasya", "-pass", "123", "-role", "READ", "-res", "A", "-ds", "2020-12-10", "-de", "2020-13-01", "-vol", "1024")).code)
        }

        test("Day doesn't exist") {
            assertEquals(7, app.run(arrayOf("-login", "vasya", "-pass", "123", "-role", "READ", "-res", "A", "-ds", "2020-04-31", "-de", "2020-04-01", "-vol", "1024")).code)
        }

        test("Day doesn't exist") {
            assertEquals(7, app.run(arrayOf("-login", "vasya", "-pass", "123", "-role", "READ", "-res", "A", "-ds", "2020-02-32", "-de", "2020-04-01", "-vol", "1024")).code)
        }

        test("Negative vol") {
            assertEquals(7, app.run(arrayOf("-login", "vasya", "-pass", "123", "-role", "READ", "-res", "A", "-ds", "2020-03-10", "-de", "2020-04-01", "-vol", "-1024")).code)
        }

        test("Vol is NaN") {
            assertEquals(7, app.run(arrayOf("-login", "vasya", "-pass", "123", "-role", "READ", "-res", "A", "-ds", "2020-03-10", "-de", "2020-04-01", "-vol", "alot")).code)
        }

        test("No vol, only authorization") {
            assertEquals(0, app.run(arrayOf("-login", "vasya", "-pass", "123", "-role", "READ", "-res", "A", "-ds", "2020-03-10", "-de", "2020-04-01")).code)
        }

        test("Ds is after de") {
            assertEquals(0, app.run(arrayOf("-login", "admin", "-pass", "admin", "-role", "WRITE", "-res", "A.B.C", "-ds", "2020-03-10", "-de", "2020-01-01", "-vol", "1024")).code)
        }

        test("Day doesn't exist") {
            assertEquals(7, app.run(arrayOf("-login", "vasya", "-pass", "123", "-role", "WRITE", "-res", "A.B.C", "-ds", "2020-12-01", "-de", "2020-01-45", "-vol", "1024")).code)
        }

        test("Access without permission (verify that accounting is after authorization)") {
            assertEquals(6, app.run(arrayOf("-login", "vasya", "-pass", "123", "-role", "EXECUTE", "-res", "A.B.C", "-ds", "2020-12-01", "-de", "2020-01-45", "-vol", "1024")).code)
        }
    }
})
