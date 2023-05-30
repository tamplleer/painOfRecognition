package smallestSquares

import kotlin.math.exp
import kotlin.math.pow
import kotlin.math.sqrt


fun leastSquaresMethod2(
    xArray: List<Double>,
    yArray: List<Double>,
    degree: Int,
    norm: Boolean,
    lambda: Double
): List<Double> {
    val weight = generateWeight(xArray, yArray)
    val coefficients = Array(degree + 1) { 0.0 }
    for (i in 0..degree) {
        for (j in xArray.indices) {
            if (norm) {
                coefficients[i] += weight[j] * xArray[j].pow(i) * yArray[j]
            } else {
                coefficients[i] += xArray[j].pow(i) * yArray[j]
            }
        }
    }
    val xMatrix = Array(degree + 1) { DoubleArray(degree + 1) }
    for (i in 0..degree) {
        for (j in 0..degree) {
            xMatrix[i][j] = xArray.sumOf { xi -> xi.pow(i + j) } + if (i == j) lambda else 0.0
        }
    }
    val yMatrix = DoubleArray(degree + 1) { i -> xArray.sumOf { xi -> xi.pow(i) * yArray[xArray.indexOf(xi)] } }
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
    val sigma = y.map { yi -> (yi - mu).pow(2) }.sum() / n
    val weights = y.map { yi ->
        val numerator = exp(-(yi - mu).pow(2) / (2 * sigma.pow(2)))
        val denominator = sqrt(2 * Math.PI * sigma.pow(2))
        numerator / denominator
    }

    return weights.toList()
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