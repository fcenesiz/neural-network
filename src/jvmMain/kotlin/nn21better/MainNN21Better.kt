package nn21better

import SamplesNN21
import formatS
import java.util.*
import javax.swing.JFrame


fun main() {


    val network = NN21Better(0.1)
    network.prepare()

    val samplesNN21 = SamplesNN21()
    samplesNN21.create()

    samplesNN21.guesses = network
        .createGuesses(samplesNN21.input0s, samplesNN21.input1s, samplesNN21.count)

    val frame = JFrame()
    frame.add(samplesNN21)
    frame.setSize(samplesNN21.screenMax, samplesNN21.screenMax)
    frame.isVisible = true
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

    println(samplesNN21)

    other(network, samplesNN21, 10000)
    //limited(network, samples, 10000)
    //endless(network, samples)

}

fun limited(network: NN21Better, samplesNN21: SamplesNN21, limit: Int) {
    var idx = 0

    for (i in 0 until limit) {
        network.train(
            samplesNN21.input0s[idx],
            samplesNN21.input1s[idx],
            samplesNN21.outputs[idx]
        )

        idx += 1
        if (idx >= samplesNN21.count) {
            samplesNN21.repaint()
            // Passed over all samples,
            // calculate accuracy,
            samplesNN21.guesses = network.createGuesses(samplesNN21.input0s, samplesNN21.input1s, samplesNN21.count)
            val accuracy = network.accuracy(samplesNN21.outputs, samplesNN21.guesses, samplesNN21.colors, samplesNN21.count)
            println("accuracy: ${accuracy.formatS(2)}")

            // mixing for new "epoch"
            samplesNN21.mix()
            idx = 0
        }
    }

}


fun endless(network: NN21Better, samplesNN21: SamplesNN21) {
    val timer = Timer();
    var idx = 0

    timer.scheduleAtFixedRate(object : TimerTask() {
        override fun run() {
            network.train(
                samplesNN21.input0s[idx],
                samplesNN21.input1s[idx],
                samplesNN21.outputs[idx]
            )

            idx += 1
            if (idx >= samplesNN21.count) {
                // Passed over all samples,
                // calculate accuracy,
                samplesNN21.guesses = network.createGuesses(samplesNN21.input0s, samplesNN21.input1s, samplesNN21.count)
                val accuracy = network.accuracy(samplesNN21.outputs, samplesNN21.guesses, samplesNN21.colors, samplesNN21.count)
                println("accuracy: ${accuracy.formatS(2)}")

                // mixing for new "epoch"
                samplesNN21.mix()
                idx = 0
            }

            samplesNN21.repaint()
        }
    }, 0, 1)
}

fun other(network: NN21Better, samplesNN21: SamplesNN21, limit: Int) {

    for (epoch in 0 until limit) {
        for (idx in 0 until samplesNN21.count) {
            network.train(samplesNN21.input0s[idx], samplesNN21.input1s[idx], samplesNN21.outputs[idx])
        }

        samplesNN21.guesses = network.createGuesses(samplesNN21.input0s, samplesNN21.input1s, samplesNN21.count)
        val accuracy = network.accuracy(samplesNN21.outputs, samplesNN21.guesses, samplesNN21.colors, samplesNN21.count)
        println("accuracy: ${accuracy.formatS(2)}")
        samplesNN21.repaint()

        // mixing for new "epoch"
        samplesNN21.mix()

    }
}