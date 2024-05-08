package lt.mif.system

data class JavaSystem(
    val absractCount: Int,
    val concreteCount: Int,
    val packageCount: Int,
    val packageNames: Set<String>,
    val circularDependencyCount: Int,
    val efferentCouplings: List<Int>,
    val afferentCouplings: List<Int>,
) {
    constructor() : this(0, 0, 0, emptySet(), 0, emptyList(), emptyList())
}