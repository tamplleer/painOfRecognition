import javafx.application.Application
import smallestSquares.Charts
import smallestSquares.Calculation

fun main() {


    // степени полинома
    val degrees = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    // коэффициенты регуляризации lambda
    val lambda = doubleArrayOf(0.0, 0.01, 0.05, 0.1, 0.5)
    // k - фолды
    val k = 5
    val count = 6
    val maxRange = 7.0
    val noisePercent = 0.8
    val maxErrorChanges = 0.1
    val isChange = false
    val isNorm = true

    val funName = "sin"

    Calculation.create(
        funName,
        count,
        noisePercent,
        maxErrorChanges,
        maxRange,
        isNorm,
        degrees,
        k,
        lambda,
        isChange
    )
    Application.launch(Charts::class.java)
}


/*

fun main() {
    // Задаем данные
    val x = listOf(1.0, 2.0, 3.0, 4.0, 5.0)
    val y = listOf(2.5, 3.7, 4.8, 6.1, 7.3)

    // Задаем степень полинома
    val degree = 3

    // Решаем систему уравнений для определения коэффициентов полинома
    val coefficients = Array(degree + 1) { 0.0 }
    for (i in 0..degree) {
        for (j in 0 until x.size) {
            coefficients[i] += x[j].pow(i) * y[j]
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

    // Выводим результаты
    println("Оптимальные коэффициенты полинома: ${coefficients.contentToString()}")
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

    override fun toString(): String {
        val builder = StringBuilder()
        for (i in data.indices) {
            builder.append(data[i].contentToString())
            builder.append("\n")
        }
        return builder.toString()
    }

}*/
