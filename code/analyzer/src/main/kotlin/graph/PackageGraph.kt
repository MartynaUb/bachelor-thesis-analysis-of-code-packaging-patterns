package lt.mif.graph

import guru.nidi.graphviz.attribute.Color
import guru.nidi.graphviz.attribute.Shape
import guru.nidi.graphviz.engine.Format
import guru.nidi.graphviz.engine.Graphviz
import guru.nidi.graphviz.model.Factory.graph
import guru.nidi.graphviz.model.Factory.node
import guru.nidi.graphviz.model.Graph
import lt.mif.packages.JavaPackage
import java.io.File


interface PackageGraph {
    fun toImage(imagePath: String)
}


data class GraphvizGraph(val graph: Graph) : PackageGraph {
    override fun toImage(imagePath: String) {
        val g = Graphviz.fromGraph(graph)
        g.render(Format.PNG).toFile(File(imagePath))
        g.render(Format.SVG).toFile(File("$imagePath.svg"))
    }

}

class PackageGraphs {

    fun getGraph(packages: Set<JavaPackage>): PackageGraph = GraphvizGraph(
        graph("packageGraph").directed()
            .with(packages.map { p ->
                node(p.name).with(Shape.BOX).link(p.dependencies.filter { d -> packages.find { it.name == d } != null }
                    .map { d -> node(d).with(Shape.BOX) })
            })
    )

    fun getGraphWithExternalDeps(packages: Set<JavaPackage>): PackageGraph =
        GraphvizGraph(
            graph("packageGraphWithExternalDeps").directed()
                .with(packages.map { p ->
                    node(p.name).with(Shape.BOX).link(p.dependencies.map { d ->
                        if (packages.find { it.name == d } != null) node(d).with(Shape.BOX) else node(d).with(Color.BROWN1)
                            .with(Shape.BOX)
                    })
                })
        )
}