package lt.mif.packages


data class JavaPackage(
    val name: String,
    val absractCount: Int,
    val concreteCount: Int,
    val dependencies: Set<String>,
    val dependants: Set<String>,
) {
    fun getEfferentCouplings(): Int = this.dependencies.size

    fun getAfferentCouplings(): Int = this.dependants.size

    fun getStability(): Double = (getEfferentCouplings().toDouble() / (getEfferentCouplings() + getAfferentCouplings()))

}
