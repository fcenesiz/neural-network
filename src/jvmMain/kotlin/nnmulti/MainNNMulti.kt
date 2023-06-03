package nnmulti

import SamplesNNMulti
import java.util.*
import javax.swing.JFrame

fun main() {

    val epoch = 1000

    val network = NNMulti(2,16, 2, 0.1)

    val samples = SamplesNNMulti(maxAcc = 100)
    samples.create()

    val frame = JFrame()
    frame.add(samples)
    frame.setSize(samples.screenMax, samples.screenMax)
    frame.isVisible = true
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE



    other(network, samples, epoch)
    //endless(network, samples)

}

fun other(network: NNMulti, samples: SamplesNNMulti, limit: Int) {

    for (epoch in 0 until limit) {
        for (idx in 0 until samples.count) {
            network.setInputs(samples.inputs[idx])
            network.train(samples.outputs[idx])
        }
        var accuracy = network.calculateAccuracy(samples.inputs, samples.count, samples.outputs)

        //println("accuracy: $accuracy\t|\tepoch: $epoch")

        // mixing for new "epoch"
        samples.mix()
        samples.insertAccuracy(accuracy)


        samples.repaint()
        Thread.sleep(25)
    }
}