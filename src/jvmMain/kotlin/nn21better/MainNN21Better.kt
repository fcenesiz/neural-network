package nn21better

import Samples
import formatS
import java.util.*
import javax.swing.JFrame


fun main() {


    val network = NN21Better(0.1)
    network.prepare()

    val samples = Samples()
    samples.create()

    samples.guesses = network
        .createGuesses(samples.input0s, samples.input1s, samples.count)

    val frame = JFrame()
    frame.add(samples)
    frame.setSize(samples.screenMax, samples.screenMax)
    frame.isVisible = true
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

    println(samples)

    other(network, samples, 10000)
    //limited(network, samples, 10000)
    //endless(network, samples)

}

fun limited(network: NN21Better, samples: Samples, limit: Int) {
    var idx = 0

    for (i in 0 until limit) {
        network.train(
            samples.input0s[idx],
            samples.input1s[idx],
            samples.outputs[idx]
        )

        idx += 1
        if (idx >= samples.count) {
            samples.repaint()
            // Passed over all samples,
            // calculate accuracy,
            samples.guesses = network.createGuesses(samples.input0s, samples.input1s, samples.count)
            val accuracy = network.accuracy(samples.outputs, samples.guesses, samples.colors, samples.count)
            println("accuracy: ${accuracy.formatS(2)}")

            // mixing for new "epoch"
            samples.mix()
            idx = 0
        }
    }

}


fun endless(network: NN21Better, samples: Samples) {
    val timer = Timer();
    var idx = 0

    timer.scheduleAtFixedRate(object : TimerTask() {
        override fun run() {
            network.train(
                samples.input0s[idx],
                samples.input1s[idx],
                samples.outputs[idx]
            )

            idx += 1
            if (idx >= samples.count) {
                // Passed over all samples,
                // calculate accuracy,
                samples.guesses = network.createGuesses(samples.input0s, samples.input1s, samples.count)
                val accuracy = network.accuracy(samples.outputs, samples.guesses, samples.colors, samples.count)
                println("accuracy: ${accuracy.formatS(2)}")

                // mixing for new "epoch"
                samples.mix()
                idx = 0
            }

            samples.repaint()
        }
    }, 0, 1)
}

fun other(network: NN21Better, samples: Samples, limit: Int) {

    for (epoch in 0 until limit) {
        for (idx in 0 until samples.count) {
            network.train(samples.input0s[idx], samples.input1s[idx], samples.outputs[idx])
        }

        samples.guesses = network.createGuesses(samples.input0s, samples.input1s, samples.count)
        val accuracy = network.accuracy(samples.outputs, samples.guesses, samples.colors, samples.count)
        println("accuracy: ${accuracy.formatS(2)}")
        samples.repaint()

        // mixing for new "epoch"
        samples.mix()

    }
}