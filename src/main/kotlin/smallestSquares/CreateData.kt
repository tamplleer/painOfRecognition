package smallestSquares

import kotlin.math.cos
import kotlin.math.log
import kotlin.math.pow
import kotlin.math.sin

fun createData(
    count: Int = 20,
    maxRange: Double = 16.0,
    funType: String,
    noisePart: Double = 0.8,
    maxError: Double = 0.0001,
    isNorm: Boolean = false
): Triple<DoubleArray, DoubleArray, DoubleArray> {

    val function = { x: Double ->
        mapFun[funType]!!.invoke(x).noise(noisePart = noisePart, maxErr = maxError, isNormErr = isNorm)
    }

    val rangeInput = 0.0..maxRange
    val xInput = generateByCount(rangeInput, count)
    val yInput = xInput.map(function).toDoubleArray()

    val stepOut = (rangeInput.endInclusive - rangeInput.start) / 1000
    val xOutput = generateByStep(rangeInput, stepOut)
    return Triple(xInput, yInput, xOutput)
}


fun function4(x: Double): Double {
    return log(x, 10.0)
}

fun function3(x: Double): Double {
    return x * sin(x)
}

fun function2(x: Double): Double {
    return 5 * x.pow(3.0) + x * x + 5
}

fun function1(x: Double): Double {
    return cos(x)
}

val mapFun = mapOf<String, (Double) -> Double>(
    "cos" to ::function1,
    "pow3" to ::function2,
    "sin" to ::function3,
    "log" to ::function4
)