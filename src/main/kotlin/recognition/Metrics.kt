package recognition


import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.pow
import kotlin.math.sqrt

typealias Data = List<Int>
typealias Metric = (Data, Data) -> Double

data class Item(val data: Data, val number: Int)

infix fun Data.itm(that: Int) = Item(this, that)

// Метрики Минковского

fun manhattan(v1: Data, v2: Data) =
    v1.zip(v2).sumOf {
        abs(it.first - it.second)
    }.toDouble()

fun euclidean(v1: Data, v2: Data) =
    v1.zip(v2).sumOf {
        (it.first - it.second).toDouble().pow(2)
    }.let { sqrt(it) }

fun euclideanPow(v1: Data, v2: Data) =
    v1.zip(v2).sumOf {
        (it.first - it.second).toDouble().pow(2)
    }

fun chebyshyov(v1: Data, v2: Data) =
    v1.zip(v2).maxOf { abs(it.first - it.second) }.toDouble()

val metricsNameMap = mapOf<Metric, String>(
    ::manhattan to "manhattan",
    ::euclidean to "euclidean",
    ::euclideanPow to "euclidean pow",
    ::chebyshyov to "chebyshyov",

)