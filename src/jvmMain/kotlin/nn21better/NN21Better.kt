package nn21better

import java.awt.Color
import kotlin.math.exp
import kotlin.math.max

/**
 * Neural network 2x1
 * activation func: sigmoid
 */
class NN21Better(
    val learningRate: Double = 0.01
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

    var dwh0: Double = 0.0
    var dwh1: Double = 0.0
    var dwhb: Double = 0.0

    var dw00: Double = 0.0
    var dw01: Double = 0.0
    var dw0b: Double = 0.0

    var dw10: Double = 0.0
    var dw11: Double = 0.0
    var dw1b: Double = 0.0

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

        h0 = act(total0)
        h1 = act(total1)

        val totalH = (h0 * wh0) + (h1 * wh1) + whb

        return sigmoid(totalH)
    }

    fun createGuesses(
        input0s: DoubleArray,
        input1s: DoubleArray,
        count: Int
    ): IntArray {
        val results = IntArray(count)
        for (i in 0 until count) {
            results[i] = if (guess(input0s[i], input1s[i]) > 0.5) 1 else 0
        }
        return results
    }

    fun accuracy(
        outputs: IntArray,
        guesses: IntArray,
        colors: Array<Color>,
        count: Int
    ): Double {
        var trueOne = 0.0

        for (i in 0 until count) {
            if (guesses[i] == outputs[i]){
                trueOne++
                colors[i] = Color.white
            }else{
                colors[i] = Color.black
            }
        }
        return trueOne / count
    }

    fun train(x0: Double, x1: Double, output: Int) {
        val guess = guess(x0, x1)
        val errorGuess = output - guess

        // hidden layer
        // dy = e(y) * dSigmoid(y)
        val dGuess = errorGuess * dSigmoid(guess)
        dwh0 = h0 * dGuess
        dwh1 = h1 * dGuess
        dwhb = dGuess

        val eH0 = dGuess * wh0
        val eH1 = dGuess * wh1

        // 0. layer
        // d0 = e(h0) * der(h0)
        val dH0 = eH0 * dAct(h0)
        dw00 = x0 * dH0
        dw01 = x1 * dH0
        dw0b = dH0

        // 1. layer
        // d1 = e(h1) * der(h1)
        val dH1 = eH1 * dAct(h1)
        dw10 = x0 * dH1
        dw11 = x1 * dH1
        dw1b = dH1

        // hidden layer fix
        wh0 += dwh0 * learningRate
        wh1 += dwh1 * learningRate
        whb += dwhb * learningRate

        // 0. layer fix
        w00 += dw00 * learningRate
        w01 += dw01 * learningRate
        w0b += dw0b * learningRate

        // 1. layer fix
        w10 += dw10 * learningRate
        w11 += dw11 * learningRate
        w1b += dw1b * learningRate

    }

    private fun act(value: Double): Double {
        return reluLeaky(value)
    }

    private fun sigmoid(value: Double): Double {
        return 1 / (1 + exp(-value))
    }

    private fun relu(value: Double): Double {
        return max(0.0, value)
    }

    val leakyAlpha = 0.3
    private fun reluLeaky(value: Double): Double {
        return max(value * leakyAlpha, value)
    }

    // ∂Act(x);derivative of act
    private fun dAct(value: Double): Double {
        return dReluLeaky(value)
    }

    // ∂Sigmoid(x);derivative of sigmoid
    private fun dSigmoid(value: Double): Double {
        return value * (1.0 - value)
    }

    // ∂Relu(x);derivative of relu
    private fun dRelu(value: Double): Double {
        return if (value > 0.0) 1.0 else 0.0
    }

    // ∂ReluLeaky(x);derivative of reluLeaky
    private fun dReluLeaky(value: Double): Double {
        return if (value > 0.0) 1.0 else leakyAlpha
    }

}