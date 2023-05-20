package basic

import Samples
import javax.swing.JFrame

fun main() {

    val basicPerception = BasicPerception()
    basicPerception.prepare()

    val samples = Samples()
    samples.create()

    samples.guesses.addAll(
        basicPerception
            .createGuesses(samples.input0s, samples.input1s, samples.count)
    )

    val frame = JFrame()
    frame.add(samples)
    frame.setSize(samples.screenMax, samples.screenMax)
    frame.isVisible = true
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

    println(samples)

    for (i in 0 until 5034) {
        basicPerception.train(
            samples.input0s[i % samples.count],
            samples.input1s[i % samples.count],
            samples.outputs[i % samples.count]
        )
        samples.guesses.clear()
        samples.guesses.addAll(
            basicPerception.createGuesses(
                samples.input0s, samples.input1s, samples.count
            )
        )



        samples.repaint()
        //frame.repaint()

        Thread.sleep(5)
    }


}
