import java.awt.Color
import java.awt.Graphics
import javax.swing.JPanel
import kotlin.math.absoluteValue

class SamplesNN21 : JPanel() {

    val count = 250
    val input0s = DoubleArray(count)
    val input1s = DoubleArray(count)
    val outputs = IntArray(count)
    var guesses = IntArray(count)
    var colors  = Array<Color>(count) {Color.blue}

    val sampleMin = -10.0
    val sampleMax = 10.0

    val screenMin = 0
    val screenMax = 640


    fun create(): SamplesNN21 {
        for (i in 0 until count) {
            input0s[i] = (Math.random() - 0.5) * 20.0
            input1s[i] = (Math.random() - 0.5) * 20.0

            outputs[i] = if ( creationCondition(input0s[i], input1s[i]) ) 1 else 0
        }
        return this
    }

    private fun creationCondition(x0: Double, x1: Double): Boolean {
        //return x0 + 7 > x1

        // more complex
        // f(x,y) = |x - y| < 3
        return (x0 - x1).absoluteValue < 3
    }

    fun mix(){
        var tempIdx: Int
        var tempColor: Color
        var temp: Double
        for (i in 0 until count) {
            tempIdx = (Math.random() * count).toInt()
            temp = input0s[i]
            input0s[i] = input0s[tempIdx]
            input0s[tempIdx] = temp

            temp = input1s[i]
            input1s[i] = input1s[tempIdx]
            input1s[tempIdx] = temp

            temp = outputs[i].toDouble()
            outputs[i] = outputs[tempIdx]
            outputs[tempIdx] = temp.toInt()

            tempColor = colors[i]
            colors[i] = colors[tempIdx]
            colors[tempIdx] = tempColor
        }
    }

    override fun paint(g: Graphics?) {
        super.paint(g)
        g?.let {
            it.color = Color.white
            it.fillRect(0, 0, screenMax, screenMax)
            it.color = Color.black
            it.drawLine((screenMax * 0.5).toInt(), 0, (screenMax * 0.5).toInt(), screenMax)
            it.drawLine(0, (screenMax * 0.5).toInt(), screenMax, (screenMax * 0.5).toInt())


            for (i in 0 until count) {
                val i0 = map(input0s[i])
                val i1 = map(input1s[i])

                val bigCircleSize = (screenMax * 0.025).toInt()
                it.color = if (outputs[i] == 1) Color.green else Color.red
                it.fillOval(
                    i0 - bigCircleSize / 2,
                    screenMax - i1 - bigCircleSize / 2,
                    bigCircleSize,
                    bigCircleSize
                )

                val smallCircleSize = (screenMax * 0.0125).toInt()
                it.color = colors[i]
                it.fillOval(
                    i0 - smallCircleSize / 2,
                    screenMax - i1 - smallCircleSize / 2,
                    smallCircleSize,
                    smallCircleSize
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
        for (i in 0 until count) {
            string += "x0: ${input0s[i].formatS(2)}\t|\tx1: ${input1s[i].formatS(2)}" +
                    "\t->\t output: ${outputs[i]}" +
                    "\t|\tguess: ${guesses[i]}" +
                    "\t|\t${if (outputs[i] == guesses[i]) "Success" else "Failure"}" +
                    "\n"
        }
        return string
    }

}