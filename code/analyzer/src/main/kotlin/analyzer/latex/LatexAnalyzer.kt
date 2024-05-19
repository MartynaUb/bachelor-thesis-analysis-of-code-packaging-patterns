package lt.mif.analyzer.latex

import lt.mif.analyzer.Analyzer
import lt.mif.graph.PackageGraphs
import lt.mif.metrics.CalculateMetrics
import lt.mif.metrics.MetricResult
import lt.mif.packages.PackageParser
import java.io.File

class LatexAnalyzer(
    packageGraphs: PackageGraphs,
    packageParser: PackageParser,
    metricCalculator: CalculateMetrics,
    private val resultDir: String,
) : Analyzer(packageGraphs, packageParser, metricCalculator, resultDir) {
    override fun writeMetricsTable(metrics: MetricResult) {
        getLatexColumns(
            metrics.packageMetrics.joinToString("\n") {
                """
                |${it.name} & ${it.classes} & ${it.afferentCouplings} & ${it.efferentCouplings} & ${it.stability} & ${it.abstraction} & ${it.distance} \\
                |\hline
            """.trimMargin()
            },
            "${metrics.average.classes} & ${metrics.average.afferentCouplings} & ${metrics.average.efferentCouplings} & ${metrics.average.stability} & ${metrics.average.abstraction} & ${metrics.average.distance} \\\\"
        ).let { File("$resultDir/metrics").writeText(it) }
    }

    override fun writePackageTree(pathToSystem: String) {
        File(pathToSystem).walk().fold(
            StringBuilder().appendLine("\\dirtree{%").appendLine(".1 {/} .")
        ) { acc, file ->
            val path = file.relativeTo(File(pathToSystem)).path.toString().split("\\")
            if (path.last() != "" && file.isDirectory) {
                acc.appendLine(".${path.size + 1} {${path.last().replace("_", "\\_")}}.")
            } else {
                acc
            }
        }.appendLine("}").toString()
            .let { File("$resultDir/tree").writeText(it) }
    }

    private fun getLatexColumns(columns: String, averageColumn: String): String = """
        |\begin{center}
        |\begin{tabular}{|c|c|c|c|c|c|c|} 
        |\hline
        |Paketo vardas & \textit{N} & \textit{A} & \textit{E} & \textit{S} & \textit{A} & \textit{D} \\ [0.5ex] 
        |\hline\hline
        |$columns    
        |\end{tabular}
        |\begin{tabular}{|c|c|c|c|c|c|} 
        |\hline
        |$\bar{N}$ & $\bar{A}$ & $\bar{E}$ & $\bar{S}$ & $\bar{A}$ & $\bar{D}$ \\ [0.5ex] 
        |\hline\hline
        |$averageColumn
        |\hline
        |\end{tabular}
        |\end{center}
    """.trimMargin()
}