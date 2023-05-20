package nn21

import Samples
import java.util.*
import javax.swing.JFrame

fun main() {


    val basicPerception = NN21()
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


    val timer = Timer();
    var timerIdx = 0

    timer.scheduleAtFixedRate(object : TimerTask(){
        override fun run() {

            basicPerception.train(
                samples.input0s[timerIdx % samples.count],
                samples.input1s[timerIdx % samples.count],
                samples.outputs[timerIdx % samples.count]
            )
            samples.guesses.clear()
            samples.guesses.addAll(
                basicPerception.createGuesses(
                    samples.input0s, samples.input1s, samples.count
                )
            )

            samples.repaint()
            println(samples.trueRate)
            timerIdx++
        }
    }, 0, 1)



}
