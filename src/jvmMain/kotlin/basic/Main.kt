package basic

import javax.swing.JFrame

fun main() {

    val basicPerception = BasicPerception()
    basicPerception.prepare()

    val samples = Samples()
    samples.create()

    samples.guesses.addAll(
        basicPerception
            .createGuesses(samples.input0s, samples.input1s, samples.sampleCount)
    )

    val frame = JFrame()
    frame.add(samples)
    frame.setSize(samples.screenMax, samples.screenMax)
    frame.isVisible = true
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

    for (i in 0 until 534) {
        basicPerception.train(
            samples.input0s[i % samples.sampleCount],
            samples.input1s[i % samples.sampleCount],
            samples.outputs[i % samples.sampleCount]
        )
        samples.guesses.clear()
        samples.guesses.addAll(
            basicPerception.createGuesses(
                samples.input0s, samples.input1s, samples.sampleCount
            )
        )

        samples.interceptX0 = basicPerception.interceptX0().toInt()
        samples.interceptX1 = basicPerception.interceptX1().toInt()

        samples.repaint()
        //frame.repaint()

        Thread.sleep(25)
    }

}
