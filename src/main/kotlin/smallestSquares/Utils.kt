package smallestSquares

import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.ln
import kotlin.math.sqrt
import kotlin.random.Random

fun generateByStep(range: ClosedRange<Double>, step: Double): DoubleArray {
    return (range step step).toList().toDoubleArray()
}

fun generateByCount(range: ClosedRange<Double>, count: Int): DoubleArray {
    return Array(count) { Random.nextDouble(range.start, range.endInclusive) }.toDoubleArray()
}

infix fun ClosedRange<Double>.step(step: Double): Iterable<Double> {
  /*  require(start.isFinite())
    require(endInclusive.isFinite())
    require(step > 0.0) { "Step must be positive, was: $step." }*/
    val sequence = generateSequence(start) { previous ->
        if (previous == Double.POSITIVE_INFINITY) return@generateSequence null
        val next = previous + step
        if (next > endInclusive) null else next
    }
    return sequence.asIterable()
}

fun Double.noise(noisePart: Double, maxErr: Double, isNormErr: Boolean) = run {
    if (Random.nextDouble() < noisePart) {
        if (isNormErr) {
            this + makeNorm() * maxErr
        } else {
            this + Random.nextDouble(-maxErr, maxErr)
        }
    } else this
}


fun makeNorm() = sqrt(-2 * ln(Random.nextDouble())) * cos(2 * PI * Random.nextDouble())