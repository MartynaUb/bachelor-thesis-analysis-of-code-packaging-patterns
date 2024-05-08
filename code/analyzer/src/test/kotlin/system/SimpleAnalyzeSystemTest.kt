package system

import lt.mif.packages.RecursivePackageParser
import lt.mif.system.JavaSystem
import lt.mif.system.SimpleAnalyzeSystem
import kotlin.test.Test
import kotlin.test.assertEquals

class SimpleAnalyzeSystemTest {
    @Test
    fun test_analyze() {
        val system = SimpleAnalyzeSystem(RecursivePackageParser()).analyze("src/test/kotlin/testdata")
        assertEquals(JavaSystem(), system)
    }
}