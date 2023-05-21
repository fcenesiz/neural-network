package nn21

import Samples
import SamplesOld
import java.util.*
import javax.swing.JFrame

fun main() {


    val basicPerception = NN21()
    basicPerception.prepare()

    val samples = SamplesOld()
    samples.create()

    samples.guesses = basicPerception
        .createGuesses(samples.input0s, samples.input1s, samples.count)

    val frame = JFrame()
    frame.add(samples)
    frame.setSize(samples.screenMax, samples.screenMax)
    frame.isVisible = true
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

    println(samples)


    val timer = Timer();
    var idx = 0

    timer.scheduleAtFixedRate(object : TimerTask(){
        override fun run() {

            basicPerception.train(
                samples.input0s[idx % samples.count],
                samples.input1s[idx % samples.count],
                samples.outputs[idx % samples.count]
            )
            samples.guesses = basicPerception
                .createGuesses(samples.input0s, samples.input1s, samples.count)

            samples.repaint()
            println(samples.trueRate)
            idx = (idx + 1) % samples.count
        }
    }, 0, 1)



}
