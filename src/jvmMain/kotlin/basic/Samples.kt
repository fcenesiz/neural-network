package basic

import formatS
import map
import java.awt.Color
import java.awt.Graphics
import javax.swing.JPanel
import kotlin.math.absoluteValue

class Samples : JPanel() {

    val sampleCount = 2000
    val input0s = mutableListOf<Double>()
    val input1s = mutableListOf<Double>()
    val outputs = mutableListOf<Int>()
    val guesses = mutableListOf<Int>()
    var interceptX0 = 0
    var interceptX1 = 0

    val sampleMin = -10.0
    val sampleMax = 10.0

    val screenMin = 0
    val screenMax = 500

    fun create(): Samples {
        for (i in 0 until sampleCount) {
            input0s.add((Math.random() - 0.5) * 20.0)
            input1s.add((Math.random() - 0.5) * 20.0)

            outputs.add(if ( creationCondition(input0s[i], input1s[i]) ) 1 else 0)
        }
        return this
    }

    private fun creationCondition(x0: Double, x1: Double): Boolean {
        return x0 + 7 > x1

        // f(x,y) = |x - y| < 3
        //return (x0 - x1).absoluteValue < 3
    }

    override fun paint(g: Graphics?) {
        super.paint(g)
        g?.let {
            it.color = Color.white
            it.fillRect(0, 0, screenMax, screenMax)
            it.color = Color.black
            it.drawLine((screenMax * 0.5).toInt(), 0, (screenMax * 0.5).toInt(), screenMax)
            it.drawLine(0, (screenMax * 0.5).toInt(), screenMax, (screenMax * 0.5).toInt())


            it.drawLine(map(interceptX0), screenMax - map(sampleMax), map(interceptX1), screenMax - map(sampleMin))

            for (i in 0 until sampleCount) {
                val i0 = map(input0s[i])
                val i1 = map(input1s[i])
                it.color = if (outputs[i] == 1) Color.green else Color.red
                it.fillOval(i0, screenMax - i1, (screenMax * 0.02).toInt(), (screenMax * 0.02).toInt())

                it.color = if (guesses[i] == outputs[i]) Color.white else Color.black
                it.fillOval(
                    i0 + (screenMax * 0.005).toInt(),
                    screenMax - i1 + (screenMax * 0.005).toInt(),
                    (screenMax * 0.01).toInt(),
                    (screenMax * 0.01).toInt()
                )

            }

        }
    }

    fun map(value: Int): Int {
        return value.map(sampleMin, sampleMax, screenMin, screenMax)
    }

    fun map(value: Double): Int {
        return value.map(sampleMin, sampleMax, screenMin, screenMax).toInt()
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