package lt.mif.classes

import com.github.javaparser.StaticJavaParser
import java.nio.file.Path
import kotlin.io.path.name
import kotlin.jvm.optionals.getOrElse
import kotlin.jvm.optionals.toSet

data class JavaFile(
    val packageName: String,
    val fileName: String,
    val dependencies: Set<String>,
    val abstract: Boolean,
) {
    companion object {
        // https://javaparser.org/
        fun fromPath(path: Path): JavaFile {
            val parsedFile = StaticJavaParser.parse(path)
            val abstract =
                parsedFile.types.filter { it.isClassOrInterfaceDeclaration }.map { it.asClassOrInterfaceDeclaration() }
                    .find { it.isInterface || it.isAbstract } != null
            val imports = parsedFile.imports.flatMap { i ->
                if (i.isAsterisk) {
                    setOf(i.nameAsString)
                } else {
                    i.name.qualifier.map { it.asString() }.toSet()
                }
            }.toSet()
            val packageName = parsedFile.packageDeclaration.map { it.nameAsString}.getOrElse { "/" }
            return JavaFile(
                packageName,
                path.name,
                imports,
                abstract
            )
        }
    }
}
