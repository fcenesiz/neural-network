import java.awt.Color
import java.awt.Graphics
import java.lang.Math.random
import javax.swing.JPanel
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

class SamplesNNMulti(
    val maxAcc : Int = 100
) : JPanel() {

    val count = 250
    val inputs = Array(count) { DoubleArray(2) }
    val outputs = Array(count) { IntArray(2) }


    val screenMin = 0
    val screenMax = 640


    val accuracies = DoubleArray(maxAcc)
    var curAcc = 0
    val accWidth : Double = screenMax / maxAcc.toDouble()

    fun create(): SamplesNNMulti {
        for (i in 0 until count) {
            inputs[i][0] = random() + 0.0001
            inputs[i][1] = random() + 0.0001

            if (creationCondition(inputs[i][0], inputs[i][1])) {
                outputs[i][0] = 1
            } else {
                outputs[i][1] = 1
            }

        }

        return this
    }


    private fun creationCondition(x0: Double, x1: Double): Boolean {
        //return x0 + 7 > x1

        // more complex
        // f(x,y) = |x - y| < 3
        return (x0 - x1).absoluteValue < 3
    }

    fun mix() {
        var tempIdx: Int

        var tempDoubleArray: DoubleArray
        var tempIntArray: IntArray
        for (i in 0 until count) {
            tempIdx = (Math.random() * count).toInt()

            tempDoubleArray = inputs[i]
            inputs[i] = inputs[tempIdx]
            inputs[tempIdx]= tempDoubleArray

            tempIntArray = outputs[i]
            outputs[i] = outputs[tempIdx]
            outputs[tempIdx]= tempIntArray


        }


    }

    override fun paint(g: Graphics?) {
        super.paint(g)
        g?.let {
            it.color = Color.white
            it.fillRect(0, 0, screenMax, screenMax)
            it.color = Color.black

            for (i in 0 until maxAcc - 1 ){

                val x1 =  (i * accWidth).roundToInt()
                val y1 = ((1 - accuracies[i]) * screenMax).roundToInt()
                val x2 =  ((i+1) * accWidth).roundToInt()
                val y2 = ((1 - accuracies[i+1]) * screenMax).roundToInt()



                it.drawLine(x1, y1, x2, y2)

            }

        }
    }

    fun insertAccuracy(acc : Double){
        accuracies[curAcc] = acc
        curAcc++
        if (curAcc > maxAcc - 1){
            for (i in 1 until maxAcc){
                accuracies[i-1] = accuracies[i]
            }
            curAcc--
        }
    }

}