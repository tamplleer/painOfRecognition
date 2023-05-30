package smallestSquares

import kotlin.math.*

object Calculation {

    var xValue = listOf<Double>();
    var yValue = listOf<Double>()

    var xValueNew = mutableListOf<Double>()
    var yValueNew = listOf<Double>()

    var polynomName = ""
    var funName = ""


    fun create(
        funType: String,
        count: Int,
        noisePercent: Double,
        maxErrorChange: Double,
        range: Double,
        norm: Boolean,
        degrees: IntArray,
        k: Int,
        lambda: DoubleArray,
        isChange: Boolean
    ) {
        val (x, y, xNew) = createData(count, range, funType, noisePercent, maxErrorChange, norm)
        xValue = x.toList()
        yValue = y.toList()
        xValueNew = xNew.toMutableList()

        // Вызываем функцию полиномиальной регрессии
        val coefficients = polynomialRegressionWithCrossValidation(
            xValue.toDoubleArray(),
            yValue.toDoubleArray(),
            degrees,
            k,
            lambda,
            isChange
        )
        polynomName = createPolynomName(coefficients.toList())
        funName = funType
        yValueNew = xValueNew.map { calculatePolynom(coefficients, it) }
    }

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

fun calculatePolynom(coef: DoubleArray, x: Double): Double {
    return coef.mapIndexed { index, v ->
        v * x.pow(index.toDouble())
    }.sum()
}


fun polynomialRegressionWithCrossValidation(
    x: DoubleArray,
    y: DoubleArray,
    degValues: IntArray,
    numFolds: Int,
    lambdaValues: DoubleArray,
    isChange: Boolean
): DoubleArray {
    val nSamples = x.size
    var bestDegree = 0
    var bestLambda = 0.0
    var smallestMSE = Double.POSITIVE_INFINITY

    for (lambda in lambdaValues) {
        println("Lambda = $lambda")
        for (degree in degValues) {
            var mseSum = 0.0

            for (i in 0 until numFolds) {
                val start = i * nSamples / numFolds
                val end = (i + 1) * nSamples / numFolds

                val xTrain = x.sliceArray(0 until start) + x.sliceArray(end until nSamples)
                val yTrain = y.sliceArray(0 until start) + y.sliceArray(end until nSamples)
                val xTest = x.sliceArray(start until end)
                val yTest = y.sliceArray(start until end)

                val coefficients = leastSquaresMethod2(
                    if (isChange) xTest.toList() else xTrain.toList(),
                    if (isChange) yTest.toList() else yTrain.toList(),
                    degree,
                    false,
                    lambda
                )
                val yPred = xTest.map { calculatePolynom(coefficients.toDoubleArray(), it) }

                val mse = meanSquaredError(yTest, yPred.toDoubleArray())
                mseSum += mse
            }

            val meanMSE = mseSum / numFolds

            println("degree = $degree  mse = ${String.format("%.5f",meanMSE)}  smallest = ${String.format("%.5f",smallestMSE)}   ${meanMSE < smallestMSE}")
            if (meanMSE < smallestMSE) {
                bestDegree = degree
                bestLambda = lambda
                smallestMSE = meanMSE
            }
        }
    }
  
    println("\nthe best lambda is $bestLambda")
    println("the best degree is $bestDegree")
    println("then the smallest MSE is $smallestMSE")

    return leastSquaresMethod2(x.toList(), y.toList(), bestDegree, false, bestLambda).toDoubleArray()
}

fun meanSquaredError(yTrue: DoubleArray, yPred: DoubleArray): Double {
    var sum = 0.0
    for (i in yTrue.indices) {
        sum += (yTrue[i] - yPred[i]) * (yTrue[i] - yPred[i])
    }
    return sum / yTrue.size
}

/*fun createData(
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
            listY.add(calculateChange(changePercent, startChange, endChange, mapFun[funType]!!.invoke(i.toDouble())))
        }
    }
    return Pair(listX, listY)
}*/

/*fun calculateChange(changePercent: Double, startChange: Double, endChange: Double, value: Double): Double {
    if (Random.nextDouble() < changePercent) {
        val minus = if (Random.nextDouble() < 0.5) 1 else -1
        return value + Random.nextDouble(startChange, endChange) * minus
    }
    return value

}
*/