package lt.mif.packages

import lt.mif.classes.JavaFile
import java.nio.file.Path
import kotlin.io.path.isDirectory
import kotlin.io.path.listDirectoryEntries

class RecursivePackageParser : PackageParser {

    override fun parse(systemPath: Path): Set<JavaPackage> {
        return filterOutExternalDependencies(resolveDependencies(HashMap(readPackages(systemPath))))
    }

    private fun readPackages(systemPath: Path): Map<String, JavaPackage> {
        val result = systemPath.listDirectoryEntries().partition { it.isDirectory() }
        val packages = result.first.map { readPackages(it) }.fold(emptyMap<String, JavaPackage>()) { acc, t -> acc + t }


        val files = result.second.filter { it.fileName.toString().endsWith(".java") }.map { JavaFile.fromPath(it) }
        val packageName = files.firstOrNull()?.packageName ?: return packages
        val absractCount = files.filter { it.abstract }.size
        val concreteCount = files.size - absractCount

        return packages + Pair(
            packageName,
            JavaPackage(packageName, absractCount, concreteCount, files.flatMap { it.dependencies }.toSet(), emptySet())
        )
    }

    private fun resolveDependencies(javaPackages: MutableMap<String, JavaPackage>): MutableMap<String, JavaPackage> {
        javaPackages.forEach {
            it.value.dependencies.forEach { dependency ->
                val javaPackage = javaPackages[dependency]
                if (javaPackage != null) {
                    javaPackages[dependency] = javaPackage.copy(dependants = javaPackage.dependants + it.key)
                }
            }
        }
        return javaPackages
    }

    private fun filterOutExternalDependencies(javaPackages: MutableMap<String, JavaPackage>): Set<JavaPackage> =
        javaPackages.map {
            val dependencies = it.value.dependencies.filter { dependency ->
                javaPackages.contains(dependency)
            }.toSet()
            it.value.copy(dependencies = dependencies)
        }.toSet()

}