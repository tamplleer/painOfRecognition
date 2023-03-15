package recognition

import java.math.BigDecimal
import java.math.RoundingMode

val shadows = false
val isIcon = true
fun ShowData(dataFromPaint: List<Double>) {
    println(dataFromPaint)

    val matrix = listOf(
        0, 0, 1, 1, 1, 1, 1, 0,
        0, 0, 1, 0, 0, 0, 0, 0,
        0, 0, 1, 0, 0, 0, 0, 0,
        0, 0, 1, 1, 1, 0, 0, 0,
        0, 0, 0, 0, 0, 1, 0, 0,
        0, 0, 0, 0, 0, 1, 0, 0,
        0, 0, 0, 0, 0, 1, 0, 0,
        0, 0, 1, 1, 1, 0, 0, 0,
    )

    val noise = if (shadows) {
        noiseWithShadows(matrix)
    } else {
        noiseUneven(matrix,0.4,0.2,0.1)
    }


    matrix.forEachIndexed { index, v ->
        if (index % 8 == 0) {
            println()
        }
        writeWithIcon(isIcon, v.toDouble())
    }
    println()
    noise.forEachIndexed { index, v ->
        if (index % 8 == 0) {
            println()
        }
        writeWithIcon(isIcon, v.toDouble())
    }

    println()
    noise.forEachIndexed { index, v ->
        if (index % 8 == 0) {
            println()
        }
        val roundedNum = BigDecimal(v.toDouble()).setScale(1, RoundingMode.HALF_UP).toDouble()
        writeWithIcon(isIcon, roundedNum)
    }

    println()
    dataFromPaint.forEachIndexed { index, v ->
        if (index % 8 == 0) {
            println()
        }
        val roundedNum = BigDecimal(v).setScale(1, RoundingMode.HALF_UP).toDouble()
        writeWithIcon(isIcon, roundedNum)
    }
}


fun writeWithIcon(icon: Boolean, data: Double) {
    if (icon) {
        when (data) {
            1.0 -> print("\uD83C\uDDE8\uD83C\uDDF3") // большая амплитуда шума в центре матрицы
            in 0.1..0.9 -> print("\uD83D\uDC4B") // меньшая амплитуда шума в ближайшем окружении центра
            else -> print("🇬🇺")// маленькая амплитуда шума на краю матрицы
        }
    } else {
        print("${String.format("%.2f", data.toDouble())} ")
    }
}

