package packages

import lt.mif.packages.JavaPackage
import lt.mif.packages.RecursivePackageParser
import kotlin.io.path.Path
import kotlin.test.Test

class RecursivePackageParserTest {
    @Test
    fun test_parse() {
        val packages = RecursivePackageParser().parse(Path("src/test/kotlin/testdata"))
        kotlin.test.assertEquals(
            setOf(
                JavaPackage("testdata", 1, 1, setOf("java.math", "java.util", "java.util.function"), emptySet()),
                JavaPackage("testdata.next", 0, 1, setOf("java.time", "testdata.another"), setOf("testdata.another")),
                JavaPackage("testdata.another", 0, 1, setOf("testdata.next"), setOf("testdata.next")),
            ),
            packages
        )
    }
}