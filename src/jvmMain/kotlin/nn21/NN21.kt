package nn21

import kotlin.math.exp

class NN21(
    val learningRate: Double = 0.1
) {

    var w00: Double = 0.0
    var w01: Double = 0.0
    var w0b: Double = 0.0

    var w10: Double = 0.0
    var w11: Double = 0.0
    var w1b: Double = 0.0

    var wh0: Double = 0.0
    var wh1: Double = 0.0
    var whb: Double = 0.0

    var h0: Double = 0.0
    var h1: Double = 0.0

    var dwh0 : Double = 0.0
    var dwh1 : Double = 0.0
    var dwhb : Double = 0.0

    var dw00 : Double = 0.0
    var dw01 : Double = 0.0
    var dw0b : Double = 0.0

    var dw10 : Double = 0.0
    var dw11 : Double = 0.0
    var dw1b : Double = 0.0

    fun prepare() {
        w00 = (Math.random() - 0.5) * 2.0
        w01 = (Math.random() - 0.5) * 2.0
        w0b = (Math.random() - 0.5) * 2.0

        w10 = (Math.random() - 0.5) * 2.0
        w11 = (Math.random() - 0.5) * 2.0
        w1b = (Math.random() - 0.5) * 2.0

        wh0 = (Math.random() - 0.5) * 2.0
        wh1 = (Math.random() - 0.5) * 2.0
        whb = (Math.random() - 0.5) * 2.0
    }


    private fun guess(x0: Double, x1: Double): Double {
        val total0 = (x0 * w00) + (x1 * w01) + w0b
        val total1 = (x0 * w10) + (x1 * w10) + w1b

        h0 = sigmoid(total0)
        h1 = sigmoid(total1)

        val totalH = (h0 * wh0) + (h1 * wh1) + whb

        return sigmoid(totalH)
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
        val error = delta

        // hidden layer
        dwh0 = h0 * guess * (1.0 - guess) * error * learningRate
        dwh1 = h1 * guess * (1.0 - guess) * error * learningRate
        dwhb = guess * (1.0 - guess) * error * learningRate

        wh0 += dwh0
        wh1 += dwh1
        whb += dwhb

        // 0. layer
        dw00 = x0 * h0 * (1.0 - h0) * dwh0
        dw01 = x1 * h0 * (1.0 - h0) * dwh0
        dw0b = h0 * (1.0 - h0) * dwh0

        w00 += dw00
        w01 += dw01
        w0b += dw0b

        // 1. layer
        dw10 = x0 * h1 * (1.0 - h1) * dwh1
        dw11 = x1 * h1 * (1.0 - h1) * dwh1
        dw1b = h1 * (1.0 - h1) * dwh1

        w10 += dw10
        w11 += dw11
        w1b += dw1b

    }

    private fun sigmoid(value: Double): Double {
        return 1 / (1 + exp(-value))
    }

}