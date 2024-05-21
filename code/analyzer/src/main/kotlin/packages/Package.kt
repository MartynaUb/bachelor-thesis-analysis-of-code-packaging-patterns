package lt.mif.packages

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.roundToInt


data class JavaPackage(
    val name: String,
    val absractCount: Int,
    val concreteCount: Int,
    val dependencies: Set<String>,
    val dependants: Set<String>,
) {

    fun classCount(): Int = absractCount + concreteCount

    fun getEfferentCouplings(): Int = this.dependencies.size

    fun getAfferentCouplings(): Int = this.dependants.size

    fun getStability(): Double = if (getEfferentCouplings() + getAfferentCouplings() == 0) 0.0 else
        (getEfferentCouplings().toDouble() / (getEfferentCouplings() + getAfferentCouplings())).roundTo(3)

    fun getAbstraction(): Double =
        if (this.classCount() == 0) 0.0 else (absractCount.toDouble() / this.classCount()).roundTo(3)

    fun getDistance(): Double = abs(this.getAbstraction() + this.getStability() - 1).roundTo(3)
}

fun Double.roundTo(numFractionDigits: Int): Double {
    val factor = 10.0.pow(numFractionDigits.toDouble())
    return (this * factor).roundToInt() / factor
}
