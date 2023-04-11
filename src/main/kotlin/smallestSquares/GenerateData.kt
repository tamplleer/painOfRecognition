package smallestSquares

import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.random.Random


object GenerateData {

    var xValue = listOf<Double>();
    var yValue = listOf<Double>()

    var xValueNew = listOf<Double>();
    var yValueNew = listOf<Double>()

    var polenomName = ""
    var funName=""


    fun create(
        funType: String,
        degree: Int,
        changePercent: Double,
        startChange: Double,
        endChange: Double,
        range: Int
    ) {
        val (x, y) = createData(funType, changePercent, startChange, endChange, range)
        xValue = x
        yValue = y

        val coefficients = leastSquaresMethod(x, y, degree)
        polenomName = createPolenomName(coefficients);
        funName= funType

        xValueNew = x
        yValueNew = x.map { calculatePolenom(coefficients, it) }
    }

}

fun leastSquaresMethod(xValues: List<Double>, yValues: List<Double>, degree: Int): List<Double> {
    val n = xValues.size
    val a = Array(degree + 1) { DoubleArray(degree + 2) }

    for (i in 0..degree) {
        for (j in 0..degree) {
            a[i][j] = xValues.sumOf { Math.pow(it, (i + j).toDouble()) }
        }
        a[i][degree + 1] = xValues.zip(yValues).sumOf { Math.pow(it.first, i.toDouble()) * it.second }
    }

    for (k in 0 until degree) {
        for (i in k + 1..degree) {
            val factor = a[i][k] / a[k][k]
            for (j in k + 1..degree + 1) {
                a[i][j] -= factor * a[k][j]
            }
        }
    }

    val coefficients = DoubleArray(degree + 1)
    for (i in degree downTo 0) {
        var sum = 0.0
        for (j in i + 1..degree) {
            sum += a[i][j] * coefficients[j]
        }
        coefficients[i] = (a[i][degree + 1] - sum) / a[i][i]
    }

    return coefficients.toList()
}


fun calculateChange(changePercent: Double, startChange: Double, endChange: Double, value: Double): Double {
    if (Random.nextDouble() < changePercent) {
        val minus = if (Random.nextDouble() < 0.5) 1 else -1
        return value + Random.nextDouble(startChange, endChange) * minus
    }
    return value;

}

fun function3(x: Int): Double {
    return x * sin(x.toDouble())
}

fun function2(x: Int): Double {
    return 5 * x.toDouble().pow(3.0) + x * x + 5
}

fun function1(x: Int): Double {
    return cos(x.toDouble())
}

val mapFun = mapOf<String, (Int) -> Double>(
    "cos" to ::function1,
    "pow3" to ::function2,
    "sin" to ::function3
)

fun createData(
    funType: String,
    changePercent: Double,
    startChange: Double,
    endChange: Double,
    range: Int,
): Pair<List<Double>, List<Double>> {

    val listX = mutableListOf<Double>()
    val listY = mutableListOf<Double>()
    for (i in 0..range) {
        listX.add(i.toDouble())
        if (mapFun[funType] !== null) {
            listY.add(calculateChange(changePercent, startChange, endChange, mapFun[funType]!!.invoke(i)))
        }
    }
    return Pair(listX, listY)
}

fun calculatePolenom(coef: List<Double>, x: Double): Double {
    var result = 0.0;
    coef.forEachIndexed { index, v ->
        val roundedNum = BigDecimal(v).setScale(3, RoundingMode.HALF_UP).toDouble()
        result += if (0 == index) {
            roundedNum
        } else {
            roundedNum * Math.pow(x, index.toDouble())
        }
    }
    return result
}

fun createPolenomName(coef: List<Double>): String {
    val builder = StringBuilder();

    coef.forEachIndexed { index, v ->
        if (coef.size - 1 == index) {
            builder.append(String.format("%.2f", v))
        } else {
            builder.append("${String.format("%.2f", v)}x^${coef.size - 1 - index}+")
        }
    }
    return builder.toString()

}