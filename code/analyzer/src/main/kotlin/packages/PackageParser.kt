package lt.mif.packages

import java.nio.file.Path

interface PackageParser {

    fun parse(systemPath: Path): Set<JavaPackage>
}