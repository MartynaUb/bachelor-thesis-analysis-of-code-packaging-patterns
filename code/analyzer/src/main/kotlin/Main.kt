package lt.mif

import com.github.javaparser.ParserConfiguration
import com.github.javaparser.StaticJavaParser
import guru.nidi.graphviz.engine.Graphviz
import guru.nidi.graphviz.engine.GraphvizV8Engine
import lt.mif.analyzer.latex.LatexAnalyzer
import lt.mif.graph.PackageGraphs
import lt.mif.metrics.CalculateMetrics
import lt.mif.packages.RecursivePackageParser

fun main(args: Array<String>) {
    val argsMap = args.toList().chunked(2).associate { it[0] to it[1] }
    val outputPath = argsMap["-output"] ?: "result"
    val systemPath  = argsMap["-system"] ?: throw Exception("-system argument not specified")

    Graphviz.useEngine(GraphvizV8Engine())
    StaticJavaParser.setConfiguration(ParserConfiguration().setLanguageLevel(ParserConfiguration.LanguageLevel.CURRENT))
    val analyzer = LatexAnalyzer(PackageGraphs(), RecursivePackageParser(), CalculateMetrics(), outputPath)

    analyzer.analyseSystem(systemPath)
}