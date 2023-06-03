package nnmulti

import shuffle
import java.awt.Color
import kotlin.math.exp
import kotlin.math.max
import kotlin.random.Random
import kotlin.time.times

fun main() {
    val hiddenBiasWeights = DoubleArray(10)
    hiddenBiasWeights.fill(Math.random())
    hiddenBiasWeights.shuffle(random = Random)
}

/**
 * Neural network multi
 * activation func: sigmoid
 */
class NNMulti(
    val sizeInput: Int,
    val sizeHidden: Int,
    val sizeOutput: Int,
    val learningRate: Double = 0.01
) {

    val inputs = DoubleArray(sizeInput)
    val outputs = DoubleArray(sizeOutput)
    val hiddens = DoubleArray(sizeHidden)

    val hiddenWeights = Array(sizeHidden) { DoubleArray(sizeInput) }
    val hiddenBiasWeights = DoubleArray(sizeHidden)

    val outputWeights = Array(sizeOutput) { DoubleArray(sizeHidden) }
    val outputBiasWeights = DoubleArray(sizeOutput)


    val eOutputs = DoubleArray(sizeOutput)
    val eHiddens = DoubleArray(sizeHidden)

    val dOutputs = DoubleArray(sizeOutput)
    val dHiddens = DoubleArray(sizeHidden)

    val dHiddenWeights = Array(sizeHidden) { DoubleArray(sizeInput) }
    val dHiddenBiasWeights = DoubleArray(sizeHidden)

    val dOutputWeights = Array(sizeOutput) { DoubleArray(sizeHidden) }
    val dOutputBiasWeights = DoubleArray(sizeOutput)

    init {
        hiddenBiasWeights.shuffle(0.5, 2.0)
        hiddenWeights.shuffle(0.5, 2.0)

        outputBiasWeights.shuffle(0.5, 2.0)
        outputWeights.shuffle(0.5, 2.0)
    }

    fun setInputs(values: DoubleArray) {
        for (i in 0 until sizeInput) {
            inputs[i] = values[i]
        }
    }


    private fun guess() {

        // hiddens
        for (i in 0 until sizeHidden) {
            hiddens[i] = 0.0
            for (j in 0 until sizeInput) {
                hiddens[i] += inputs[j] * hiddenWeights[i][j]
            }
            hiddens[i] += hiddenBiasWeights[i]
            hiddens[i] = act(hiddens[i])
        }

        // outputs
        for (i in 0 until sizeOutput) {
            outputs[i] = 0.0
            for (j in 0 until sizeHidden) {
                outputs[i] += hiddens[j] * outputWeights[i][j]
            }
            outputs[i] += outputBiasWeights[i]
            outputs[i] = sigmoid(outputs[i])
        }

        // normalize outputs
        var totalOutput = 0.0;
        for (i in 0 until sizeOutput) {
            totalOutput += outputs[i];
        }
        for (i in 0 until sizeOutput) {
            outputs[i] /= totalOutput;
        }

    }


    fun train(targets : IntArray) {
        this.guess()

        for (i in 0 until sizeOutput){
            eOutputs[i] = targets[i] - outputs[i]

        }

        for (i in 0 until sizeOutput){
            dOutputs[i] = eOutputs[i] * dSigmoid(outputs[i])
        }

        for (i in 0 until sizeOutput) {
            for (j in 0 until sizeHidden) {
                dOutputWeights[i][j] = hiddens[j] * dOutputs[i]
            }
            dOutputBiasWeights[i] = dOutputs[i]
        }

        for (i in 0 until sizeOutput){
            eOutputs[i] = targets[i] - outputs[i]
        }

        for (i in 0 until sizeHidden){
            eHiddens[i] = 0.0
            for (j in 0 until sizeOutput){
                eHiddens[i] += dOutputs[j] * outputWeights[j][i]
            }
        }

        for (i in 0 until sizeHidden){
            dHiddens[i] = eHiddens[i] * dAct(hiddens[i])
        }

        for (i in 0 until sizeHidden){
            for (j in 0 until sizeInput){
                dHiddenWeights[i][j] = inputs[j] * dHiddens[i]
            }
            dHiddenBiasWeights[i] = dHiddens[i]
        }

        // weights
        for (i in 0 until sizeOutput) {
            for (j in 0 until sizeHidden) {
                outputWeights[i][j] += dOutputWeights[i][j] * learningRate
            }
            outputBiasWeights[i] += dOutputBiasWeights[i] * learningRate
        }

        for (i in 0 until sizeHidden){
            for (j in 0 until sizeInput){
                hiddenWeights[i][j] += dHiddenWeights[i][j] * learningRate
            }
            hiddenBiasWeights[i] += dHiddenBiasWeights[i] * learningRate
        }

    }

    private fun classify() : Int{
        var max = 0.0
        var maxIdx = 0
        for (i in 0 until sizeOutput){
            if (outputs[i] >= max){
                maxIdx = i
                max = outputs[maxIdx]
            }
        }
        return maxIdx
    }

    fun calculateAccuracy(data : Array<DoubleArray>, count : Int, targets: Array<IntArray>) : Double{
        var dog = 0.0
        for (i in 0 until count){

            setInputs(data[i])
            this.guess()

            for (j in 0 until sizeOutput){
                if (targets[i][j] == 1){
                    if (classify() == j){
                        dog++

                    }

                }
            }

        }

        return dog / count.toDouble()
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