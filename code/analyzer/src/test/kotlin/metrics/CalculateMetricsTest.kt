package metrics

import lt.mif.metrics.CalculateMetrics
import lt.mif.metrics.PackageMetrics
import lt.mif.packages.JavaPackage
import kotlin.test.Test
import kotlin.test.assertEquals

class CalculateMetricsTest {

    @Test
    fun test_calculateMetrics() {
        val packages = setOf(
            JavaPackage("testdata", 2, 4, setOf("java.math", "java.util", "java.util.function"), emptySet()),
            JavaPackage("testdata.next", 0, 2, setOf("java.time", "testdata.another"), setOf("testdata.another")),
            JavaPackage("testdata.another", 1, 1, setOf("testdata.next"), setOf("testdata.next")),
        )

        val result = CalculateMetrics().calculateMetrics(packages)

        assertEquals(
            PackageMetrics(
                name = "average",
                classes = 3,
                afferentCouplings = 1,
                efferentCouplings = 2,
                stability = 0.722,
                abstraction = 0.278,
                distance = 0.222,
            ), result.average
        )
        assertEquals(
            setOf(
                PackageMetrics(
                    name = "testdata",
                    classes = 6,
                    afferentCouplings = 0,
                    efferentCouplings = 3,
                    stability = 1.0,
                    abstraction = 0.333,
                    distance = 0.333
                ),
                PackageMetrics(
                    name = "testdata.next",
                    classes = 2,
                    afferentCouplings = 1,
                    efferentCouplings = 2,
                    stability = 0.667,
                    abstraction = 0.0,
                    distance = 0.333
                ),
                PackageMetrics(
                    name = "testdata.another",
                    classes = 2,
                    afferentCouplings = 1,
                    efferentCouplings = 1,
                    stability = 0.5,
                    abstraction = 0.5,
                    distance = 0.0
                ),
            ), result.packageMetrics
        )
    }
}