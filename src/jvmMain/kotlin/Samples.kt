import futures.util.formatS
import futures.util.map
import java.awt.Color
import java.awt.Graphics
import java.awt.Panel
import javax.swing.JPanel

class Samples : JPanel() {

    val sampleCount = 50
    val input0s = mutableListOf<Double>()
    val input1s = mutableListOf<Double>()
    val outputs = mutableListOf<Int>()
    val guesses = mutableListOf<Int>()

    val sampleMin = -10.0
    val sampleMax = 10.0

    val screenMin = 0
    val screenMax = 500

    fun create(): Samples {
        for (i in 0 until sampleCount) {
            input0s.add((Math.random() - 0.5) * 20.0)
            input1s.add((Math.random() - 0.5) * 20.0)
            if (input0s[i] > input1s[i]) {
                outputs.add(1)
            } else {
                outputs.add(0)
            }
        }
        return this
    }

    override fun paint(g: Graphics?) {
        super.paint(g)
        g?.let {
            it.color = Color.white
            it.fillRect(0, 0, screenMax, screenMax)
            it.color = Color.black
            it.drawLine((screenMax * 0.5).toInt(), 0, (screenMax * 0.5).toInt(), screenMax)
            it.drawLine(0, (screenMax * 0.5).toInt(), screenMax, (screenMax * 0.5).toInt())

            for (i in 0 until sampleCount) {
                val i0 = input0s[i].map(sampleMin, sampleMax, screenMin, screenMax).toInt()
                val i1 = input1s[i].map(sampleMin, sampleMax, screenMin, screenMax).toInt()
                it.color = if (outputs[i] == 1) Color.green else Color.red
                it.fillOval(i0, screenMax - i1, (screenMax * 0.02).toInt(), (screenMax * 0.02).toInt())

                it.color = if (guesses[i] == outputs[i]) Color.white else Color.black
                it.fillOval(i0 + (screenMax * 0.005).toInt(), screenMax - i1 + (screenMax * 0.005).toInt(), (screenMax * 0.01).toInt(), (screenMax * 0.01).toInt())

            }

        }
    }

    override fun toString(): String {
        var string = ""
        for (i in 0 until sampleCount) {
            string += "x0: ${input0s[i].formatS(2)}\t|\tx1: ${input1s[i].formatS(2)}" +
                    "\t->\t original: ${outputs[i]}" +
                    "\t|\tguess: ${guesses[i]}" +
                    "\t|\t${if (outputs[i] == guesses[i]) "Success" else "Failure"}" +
                    "\n"
        }
        return string
    }

}