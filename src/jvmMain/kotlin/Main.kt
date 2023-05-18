import futures.util.format
import futures.util.formatS
import java.awt.Frame
import javax.swing.JFrame

fun main() {

    val basicPerception = BasicPerception()

    val samples = Samples()
    samples.create()

    for (i in 0 until samples.sampleCount) {
        val guess = basicPerception.guess(
            samples.input0s[i],
            samples.input1s[i]
        )
        print(
            "x0: ${samples.input0s[i].formatS(2)}\t|\tx1: ${samples.input1s[i].formatS(2)}" +
                    "\t->\t original: ${samples.outputs[i]}" +
                    "\t|\tguess: $guess" +
                    "\t|\t${if (samples.outputs[i] == guess) "Success" else "Failure"}" +
                    "\n"
        )
    }


    val frame = JFrame()
    frame.add(samples)
    frame.setSize(samples.screenMax, samples.screenMax)
    frame.isVisible = true
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

}