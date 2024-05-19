package lt.mif.analyzer

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import lt.mif.graph.PackageGraphs
import lt.mif.metrics.CalculateMetrics
import lt.mif.metrics.MetricResult
import lt.mif.packages.PackageParser
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.exists

abstract class Analyzer(
    private val packageGraphs: PackageGraphs,
    private val packageParser: PackageParser,
    private val metricsCalculator: CalculateMetrics,
    private val resultDir: String
) {

    fun analyseSystem(pathToSystem: String) = runBlocking {
        Path(resultDir).exists().let {
            if (!it) {
                Path(resultDir).createDirectories()
            }
        }
        val packages = packageParser.parse(Path(pathToSystem))
        val metricsResult = async {
            println("Writing metrics result")
            writeMetricsTable(metricsCalculator.calculateMetrics(packages))
        }

        val graphResult = async {
            println("Writing system graph")
            packageGraphs.getGraph(packages).toImage("$resultDir/packages.png")
        }
        val externalGraphResult =
            async {
                println("Writing system graph with external dependencies")
                packageGraphs.getGraphWithExternalDeps(packages).toImage("$resultDir/packages-external-deps.png")
            }
        val packageTreeResult = async {
            println("Writing system package tree")
            writePackageTree(pathToSystem)
        }
        metricsResult.await()
        graphResult.await()
        externalGraphResult.await()
        packageTreeResult.await()
    }

    protected abstract fun writeMetricsTable(metrics: MetricResult)
    protected abstract fun writePackageTree(pathToSystem: String)
}