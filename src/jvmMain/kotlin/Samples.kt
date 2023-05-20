import java.awt.Color
import java.awt.Graphics
import javax.swing.JPanel
import kotlin.math.absoluteValue

class Samples : JPanel() {

    val count = 250
    val input0s = DoubleArray(count)
    val input1s = DoubleArray(count)
    val outputs = IntArray(count)
    var guesses = IntArray(count)

    val sampleMin = -10.0
    val sampleMax = 10.0

    val screenMin = 0
    val screenMax = 640

    var trueRate = 0.0


    fun create(): Samples {
        for (i in 0 until count) {
            input0s[i] = (Math.random() - 0.5) * 20.0
            input1s[i] = (Math.random() - 0.5) * 20.0

            outputs[i] = if ( creationCondition(input0s[i], input1s[i]) ) 1 else 0
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

            var trueOne = 0.0
            for (i in 0 until count) {
                val i0 = map(input0s[i])
                val i1 = map(input1s[i])

                val bigCircleSize = (screenMax * 0.025).toInt()
                it.color = if (outputs[i] == 1) Color.green else Color.red
                it.fillOval(i0 - bigCircleSize / 2, screenMax - i1 - bigCircleSize / 2, bigCircleSize, bigCircleSize)


                val circleColor = if (guesses[i] == outputs[i]) Color.white else Color.black

                if (circleColor == Color.white) trueOne++

                val smallCircleSize = (screenMax * 0.0125).toInt()
                it.color = circleColor
                it.fillOval(
                    i0 - smallCircleSize / 2,
                    screenMax - i1 - smallCircleSize / 2,
                    smallCircleSize,
                    smallCircleSize
                )

            }
            trueRate = trueOne / count
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
        for (i in 0 until count) {
            string += "x0: ${input0s[i].formatS(2)}\t|\tx1: ${input1s[i].formatS(2)}" +
                    "\t->\t original: ${outputs[i]}" +
                    "\t|\tguess: ${guesses[i]}" +
                    "\t|\t${if (outputs[i] == guesses[i]) "Success" else "Failure"}" +
                    "\n"
        }
        return string
    }

}