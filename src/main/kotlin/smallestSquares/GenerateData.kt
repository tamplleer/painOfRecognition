package smallestSquares

import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.*
import kotlin.random.Random


object GenerateData {

    var xValue = listOf<Double>();
    var yValue = listOf<Double>()

    var xValueNew = listOf<Double>();
    var yValueNew = listOf<Double>()

    var polynomName = ""
    var funName = ""


    fun create(
        funType: String,
        degree: Int,
        changePercent: Double,
        startChange: Double,
        endChange: Double,
        range: Int,
        norm: Boolean,
    ) {
        val (x, y) = createData(funType, changePercent, startChange, endChange, range)
        xValue = x
        yValue = y

        val coefficients = leastSquaresMethod2(x, y, degree, norm)
        polynomName = createPolynomName(coefficients);
        funName = funType

        xValueNew = x
        yValueNew = x.map { calculatePolynom(coefficients, it) }
    }

}

/* fun leastSquaresMethod(xValues: List<Double>, yValues: List<Double>, degree: Int): List<Double> {
    val n = xValues.size
    val a = Array(degree + 1) { DoubleArray(degree + 2) }

    for (i in 0..degree) {
        for (j in 0..degree) {
            a[i][j] = xValues.sumOf { it.pow((i + j).toDouble()) }
        }
        a[i][degree + 1] = xValues.zip(yValues).sumOf { it.first.pow(i.toDouble()) * it.second }
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
}*/

fun leastSquaresMethod2(x: List<Double>, y: List<Double>, degree: Int, norm: Boolean): List<Double> {
    val weight = generateWeight(x, y)
    val coefficients = Array(degree + 1) { 0.0 }
    for (i in 0..degree) {
        for (j in 0 until x.size) {
            if (norm) {
                coefficients[i] += weight[j] * x[j].pow(i) * y[j]
            } else {
                coefficients[i] += x[j].pow(i) * y[j]
            }
        }
    }
    val xMatrix = Array(degree + 1) { DoubleArray(degree + 1) }
    for (i in 0..degree) {
        for (j in 0..degree) {
            xMatrix[i][j] = x.map { xi -> xi.pow(i + j) }.sum()
        }
    }
    val yMatrix = DoubleArray(degree + 1) { i -> x.map { xi -> xi.pow(i) * y[x.indexOf(xi)] }.sum() }
    val matrix = Matrix(xMatrix)
    val solution = matrix.solve(yMatrix)
    for (i in 0..degree) {
        coefficients[i] = solution[i]
    }
    return coefficients.toList()
}

fun generateWeight(x: List<Double>, y: List<Double>): List<Double> {
    // Определяем функцию весовых коэффициентов
    val n = y.size
    val mu = y.average()
    val sigma = y.map { xi -> (xi - mu).pow(2) }.sum() / n
    val weights = y.map { xi ->
        val numerator = exp(-(xi - mu).pow(2) / (2 * sigma.pow(2)))
        val denominator = sqrt(2 * Math.PI * sigma.pow(2))
        numerator / denominator
    }

    return weights.toList()
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

fun calculatePolynom(coef: List<Double>, x: Double): Double {
    var result = 0.0;
    coef.forEachIndexed { index, v ->
        val roundedNum = BigDecimal(v).setScale(3, RoundingMode.HALF_UP).toDouble()
        result += if (0 == index) {
            roundedNum
        } else {
            roundedNum * x.pow(index.toDouble())
        }
    }
    return result
}

fun createPolynomName(coef: List<Double>): String {
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

class Matrix(private val data: Array<DoubleArray>) {

    fun solve(vector: DoubleArray): DoubleArray {
        val n = vector.size
        for (i in 0 until n) {
            var maxRow = i
            for (j in i + 1 until n) {
                if (Math.abs(data[j][i]) > Math.abs(data[maxRow][i])) {
                    maxRow = j
                }
            }
            val temp = data[i]
            data[i] = data[maxRow]
            data[maxRow] = temp
            val temp1 = vector[i]
            vector[i] = vector[maxRow]
            vector[maxRow] = temp1
            for (j in i + 1 until n) {
                val factor = data[j][i] / data[i][i]
                vector[j] -= factor * vector[i]
                for (k in i until n) {
                    data[j][k] -= factor * data[i][k]
                }
            }
        }
        val solution = DoubleArray(n)
        for (i in n - 1 downTo 0) {
            var sum = 0.0
            for (j in i + 1 until n) {
                sum += data[i][j] * solution[j]
            }
            solution[i] = (vector[i] - sum) / data[i][i]
        }
        return solution
    }
}