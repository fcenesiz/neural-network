package basic

import SamplesNN21
import javax.swing.JFrame

fun main() {

    val basicPerception = BasicPerception()
    basicPerception.prepare()

    val samplesNN21 = SamplesNN21()
    samplesNN21.create()

    samplesNN21.guesses = basicPerception
        .createGuesses(samplesNN21.input0s, samplesNN21.input1s, samplesNN21.count)

    val frame = JFrame()
    frame.add(samplesNN21)
    frame.setSize(samplesNN21.screenMax, samplesNN21.screenMax)
    frame.isVisible = true
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

    println(samplesNN21)

    for (i in 0 until 5034) {
        basicPerception.train(
            samplesNN21.input0s[i % samplesNN21.count],
            samplesNN21.input1s[i % samplesNN21.count],
            samplesNN21.outputs[i % samplesNN21.count]
        )

        samplesNN21.guesses = basicPerception
            .createGuesses(samplesNN21.input0s, samplesNN21.input1s, samplesNN21.count)



        samplesNN21.repaint()
        //frame.repaint()

        Thread.sleep(5)
    }


}
