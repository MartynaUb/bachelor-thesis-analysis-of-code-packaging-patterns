package lt.mif.system

import lt.mif.packages.PackageParser
import kotlin.io.path.Path


class SimpleAnalyzeSystem(private val parser: PackageParser) : AnalyzeSystem {
    override fun analyze(path: String): JavaSystem =
        parser.parse(Path(path)).fold(JavaSystem()) { system, javaPackage ->
            system.copy(
                absractCount = system.absractCount + javaPackage.absractCount,
                concreteCount = system.concreteCount + javaPackage.concreteCount,
                packageNames = system.packageNames + javaPackage.name,
                packageCount = system.packageCount + 1,
                afferentCouplings = system.afferentCouplings.plus(javaPackage.getAfferentCouplings()),
                efferentCouplings = system.efferentCouplings.plus(javaPackage.getEfferentCouplings()),
            )

        }

}