package lt.mif.metrics

import lt.mif.packages.JavaPackage
import lt.mif.packages.roundTo


data class PackageMetrics(
    val name: String,
    val classes: Int,
    val afferentCouplings: Int,
    val efferentCouplings: Int,
    val stability: Double,
    val abstraction: Double,
    val distance: Double
)

data class MetricResult(val packageMetrics: Set<PackageMetrics>, val average: PackageMetrics)


class CalculateMetrics {

    fun calculateMetrics(packages: Set<JavaPackage>): MetricResult {
        val metrics = packages.map { p ->
            PackageMetrics(
                p.name,
                p.classCount(),
                p.getAfferentCouplings(),
                p.getEfferentCouplings(),
                p.getStability(),
                p.getAbstraction(),
                p.getDistance()
            )
        }.toSet()
        val average = metrics.fold(PackageMetrics("average", 0, 0, 0, 0.0, 0.0, 0.0)) { acc, curr ->
            acc.copy(
                afferentCouplings = acc.afferentCouplings + curr.afferentCouplings,
                efferentCouplings = acc.efferentCouplings + curr.efferentCouplings,
                classes = acc.classes + curr.classes,
                stability = acc.stability + curr.stability,
                abstraction = acc.abstraction + curr.abstraction,
                distance = acc.distance + curr.distance
            )
        }
        return MetricResult(
            metrics,
            average.copy(
                afferentCouplings = Math.round(average.afferentCouplings.toDouble() / metrics.size).toInt(),
                efferentCouplings = Math.round(average.efferentCouplings.toDouble() / metrics.size).toInt(),
                classes = Math.round(average.classes.toDouble() / metrics.size).toInt(),
                stability = (average.stability / metrics.size).roundTo(3),
                abstraction = (average.abstraction / metrics.size).roundTo(3),
                distance = (average.distance / metrics.size).roundTo(3),
            )
        )
    }
}