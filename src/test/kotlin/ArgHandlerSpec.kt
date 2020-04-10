import com.kafedra.aaapp.service.ArgHandler
import org.spekframework.spek2.Spek
import org.spekframework.spek2.lifecycle.CachingMode
import org.spekframework.spek2.style.specification.describe
import kotlin.test.Asserter
import kotlin.test.assertEquals


object ArgHandlerSpec : Spek({

    //val argHandler1 by memoized(CachingMode.EACH_GROUP) { ArgHandler(args) }

    lateinit var args: Array<String>
    lateinit var argHandler: ArgHandler

    /*beforeEachGroup { argHandler = ArgHandler(args) }*/

    group("No args or wrong args") {

        test("No args") {
            args = emptyArray()
            argHandler = ArgHandler(args) //вот это по идее beforeEachTest, но args каждый раз разный должен быть

            assertEquals(expected = false, actual = argHandler.isArgs())
        }

        test("Wrong args") {
            args = arrayOf("-lagin aaaa -psss 13456")
            argHandler = ArgHandler(args)

            assertEquals(expected = true, actual = argHandler.help)
        }
    }

    group("Authentication arguments") {

        test("No login"){
            args = arrayOf("-pass 123")
            argHandler = ArgHandler(args)

            assertEquals(expected = false, actual = argHandler.needAuthentication())
        }
    }

})
