import futures.util.format
import futures.util.formatS
import java.awt.Frame
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



    println(samples)


    val frame = JFrame()
    frame.add(samples)
    frame.setSize(samples.screenMax, samples.screenMax)
    frame.isVisible = true
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

}