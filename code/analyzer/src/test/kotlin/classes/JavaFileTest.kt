package classes

import lt.mif.classes.JavaFile

import kotlin.io.path.Path
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class JavaFileTest {
    @Test
    fun test_TestConcreteClass() {
        val javaFile =
            JavaFile.fromPath(Path("src/test/kotlin/testdata/TestClass.java"))

        assertEquals("TestClass.java", javaFile.fileName)
        assertEquals("testdata", javaFile.packageName)
        assertFalse(javaFile.abstract)
        assertEquals(
            setOf("java.math", "java.util"),
            javaFile.dependencies
        )
    }

    @Test
    fun test_TestInterface() {
        val javaFile =
            JavaFile.fromPath(Path("src/test/kotlin/testdata/TestInterface.java"))

        assertEquals("TestInterface.java", javaFile.fileName)
        assertEquals("testdata", javaFile.packageName)
        assertTrue(javaFile.abstract)
        assertEquals(
            setOf("java.util", "java.util.function"),
            javaFile.dependencies
        )
    }

    @Test
    fun test_TestAnotherConcreteClass() {
        val javaFile =
            JavaFile.fromPath(Path("src/test/kotlin/testdata/next/AnotherTestClass.java"))

        assertEquals("AnotherTestClass.java", javaFile.fileName)
        assertEquals("testdata.next", javaFile.packageName)
        assertFalse(javaFile.abstract)
        assertEquals(
            setOf("java.time", "testdata.another"),
            javaFile.dependencies
        )
    }
}