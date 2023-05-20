package basic

import kotlin.math.exp
import kotlin.math.pow
import kotlin.math.sign

data class BasicPerception(
    var w0: Double = (Math.random() - 0.5) * 2.0,
    var w1: Double = (Math.random() - 0.5) * 2.0,
    var wBias: Double = (Math.random() - 0.5) * 2.0,
    var bias: Double = 1.0,
    val learningRate: Double = 0.01
) {

    fun prepare() {
        wBias = (Math.random() - 0.5) * 2.0
        w0 = (Math.random() - 0.5) * 2.0
        w1 = (Math.random() - 0.5) * 2.0
    }



    private fun guess(x0: Double, x1: Double): Double {
        val total = x0 * w0 + x1 * w1 + bias * wBias
        return sigmoid(total)
    }

    fun createGuesses(input0s: List<Double>, input1s: List<Double>, count: Int): List<Int> {
        val results = mutableListOf<Int>()
        for (i in 0 until count) {
            results.add(if(guess(input0s[i], input1s[i]) > 0.5) 1 else 0)
        }
        return results
    }

    fun train(x0: Double, x1: Double, output: Int) {
        val guess = guess(x0, x1)
        val delta = output - guess
        // error rate
        val error = -delta

        wBias -= guess * (1.0 - guess) * error * learningRate
        w0 -= x0 * guess * (1.0 - guess) * error * learningRate
        w1 -= x1 * guess * (1.0 - guess) * error * learningRate
    }

    fun interceptX0(): Double {
        return ((-w1 * 10.0) - wBias) / w0
    }

    fun interceptX1(): Double {
        return ((-w1 * -10.0) - wBias) / w0
    }

    private fun sigmoid(value: Double): Double{
        return 1 / (1 + exp(-value))
    }

}