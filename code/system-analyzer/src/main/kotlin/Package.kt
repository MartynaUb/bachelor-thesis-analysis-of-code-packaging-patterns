package lt.mif

import java.lang.Package

data class Package(
    val absractCount: Int,
    val concreteCount: Int,
    val dependencies: List<Package>,
    val dependants: List<Package>
)
