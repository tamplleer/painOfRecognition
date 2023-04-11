package recognition

import kotlin.random.Random

fun noiseEvenly(v: Double, data: List<Int>, shadows: Boolean) = data.map {
    if (Random.nextDouble() < v) {
        if (shadows) {
            return@map( Random.nextDouble() * 256).toInt()
        }
        return@map if (it == 0) 1 else 0
    }
    return@map it
}


fun noiseUneven(data: List<Int>, shadows: Boolean, center: Double, middle: Double, edges: Double): List<Int> {
    val noise = mutableListOf<Int>()
    for (i in 0 until data.size) {
        val probability = when (i) {
            in 27..28, in 35..36 -> center
            in 18..21, in 42..45, 26, 34, 29, 37 -> middle
            else -> edges
        }

        if (Random.nextDouble() < probability) {
            if (shadows) {
                noise.add((Random.nextDouble() * 256).toInt())
            }
            else {
                noise.add(if (data[i].toDouble() == 1.0) 0 else 1)
            }
        } else {
            noise.add(data[i])
        }
    }
    return noise.toList()
}

fun noiseWithShadows(matrix: List<Int>, center: Double, middle: Double, edges: Double): List<Double> {
    val noise = mutableListOf<Double>()

    for (i in 0 until matrix.size) {
        val probability = when (i) {
            in 27..28, in 35..36 -> center
            in 18..21, in 42..45, 26, 34, 29, 37 -> middle
            else -> edges
        }

        if (Random.nextDouble() < probability) {
            val amplitude = when (i) {
                in 27..28, in 35..36 -> 0.6
                in 18..21, in 42..45, 26, 34, 29, 37 -> 0.4
                else -> 0.2
            }
            if (amplitude > 0.5) println(amplitude)
            val noisedValue = (Random.nextDouble(0.0, amplitude))
            noise.add(noisedValue)
        } else {
            noise.add(matrix[i].toDouble())
        }
    }
    return noise.toList()
}