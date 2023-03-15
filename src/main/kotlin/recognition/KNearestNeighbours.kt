package recognition


class KNearestNeighbours(private val k: Int, private val dataset: List<Item>) {

    fun search(input: List<Int>, metric: Metric): Int {
        val x = dataset.associateWith { orig ->
            metric(orig.data, input)
        }.entries.sortedBy { it.value }

        return dataset.associateWith { orig ->
            metric(orig.data, input)
        }.entries.sortedBy { it.value }
            .take(k).groupingBy { it.key.number }
            .eachCount().maxByOrNull { it.value }!!.key
    }
}

fun runCheckWithImg(dataPaint: List<Int>, k: Int, dataset: List<Item>) {
    val knn = KNearestNeighbours(k, dataset)
    val metrics = listOf<Metric>(::manhattan, ::euclidean, ::chebyshyov)
        .associateWith { mutableMapOf<Int, MutableMap<Int, Int>>() }

    metrics.forEach { (metric, table) ->
        println("${metricsNameMap[metric]} - ${knn.search(dataPaint, metric)}")
    }
}

fun runCheckDefault(
    iteration: Int,
    noiseEvenly: Boolean,
    k: Int,
    allNoise: Double = 0.4,
    center: Double = 0.4,
    middle: Double = 0.3,
    edges: Double = 0.2,
    dataset: List<Item>
) {
    val knn = KNearestNeighbours(k, dataset)
    val metrics = listOf<Metric>(::manhattan, ::euclidean, ::euclideanPow, ::chebyshyov)
        .associateWith { mutableMapOf<Int, MutableMap<Int, Int>>() }


    dataset.forEach { standardNumber ->
        repeat(iteration) {
            val dataNoisy = if (noiseEvenly) noiseEvenly(allNoise, standardNumber.data) else noiseUneven(
                standardNumber.data,
                center,
                middle,
                edges
            )
            metrics.forEach { (metric, table) ->
                table.putIfAbsent(standardNumber.number, mutableMapOf())
                val selectNumber = knn.search(dataNoisy, metric)
                table[standardNumber.number].let {
                    it?.putIfAbsent(selectNumber, 0)
                    it?.put(selectNumber, it[selectNumber]!! + 1)
                }

            }
        }
    }

    metrics.forEach { (metric, table) ->
        println(metricsNameMap[metric])
        table.mapValues {
            it.value.toList()
                .sortedByDescending { (key, value) -> value }
                .toMap()
        }.entries.forEach {
            println(it.value)
         /*   println(
                "${it.key}:${
                    it.value.entries.map {
                        "${it.key}=${
                            String.format("%.0f",  getPercent(
                                it.value,
                                dataset.size,
                                iteration
                            ))
                          
                        }%"
                    }
                }"
            )*/
        }
        println()
    }
}

fun getPercent(number: Int, datasetSize: Int, iteration: Int): Double {
    return 100 / (iteration * (datasetSize / 10)).toDouble() * number
}