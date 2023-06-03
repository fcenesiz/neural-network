import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

fun List<Any>.println(){
    for (it in this)
        println(it)
}


fun Double.percent(value: Double) : Double{
    if (value == 0.0)
        return 0.0
    return if (value > 0){
        this * (1 + value / 100.0)
    }else{
        this - (this * (value * -1 / 100.0))
    }
}



fun Long.timeDiffInSeconds() : Double{
    return this / 1000.0
}

fun Long.timeDiffInSecondsFrom(start: Long) : Double{
    return (this - start) / 1000.0
}

fun <T> MutableList<T>.average(count: Int): Double {
    var sum = 0.0
    for (number in this) {
        sum += number as Double
    }
    return sum / count
}

fun List<Double>.ewm(src: List<Double>, alpha: Double): Double {
    var ewm = src[0]
    for (i in 1 until src.size) {
        ewm = alpha * src[i] + (1 - alpha) * ewm
    }
    return ewm
}

fun Double.isUnderOf(double: Double): Boolean{
    return this < double
}

fun Double.isOverOf(double: Double): Boolean{
    return this > double
}

fun Double.format(digits: Int) = "%.${digits}f".format(this)

fun Double.formatS(digits: Int): String {
    val f = "%.${digits}f".format(this)
    return f.replace(",", ".")
}

fun Int.map(iStart: Int, iEnd: Int, oStart: Int, oEnd: Int) : Int{
    return oStart + (oEnd - oStart) * ((this - iStart) / (iEnd - iStart))
}

fun Int.map(iStart: Double, iEnd: Double, oStart: Double, oEnd: Double) : Int{
    return (oStart + (oEnd - oStart) * ((this - iStart) / (iEnd - iStart))).toInt()
}

fun Int.map(iStart: Double, iEnd: Double, oStart: Int, oEnd: Int) : Int{
    return (oStart + (oEnd - oStart) * ((this - iStart) / (iEnd - iStart))).toInt()
}

fun Double.map(iStart: Double, iEnd: Double, oStart: Double, oEnd: Double) : Double{
    return oStart + (oEnd - oStart) * ((this - iStart) / (iEnd - iStart))
}

fun Double.map(iStart: Double, iEnd: Double, oStart: Int, oEnd: Int) : Double{
    return oStart + (oEnd - oStart) * ((this - iStart) / (iEnd - iStart))
}

fun DoubleArray.shuffle(dim : Double, mul: Double){
   for (idx in indices){
       this[idx] = (Math.random() - dim) * mul
   }
}

fun Array<DoubleArray>.shuffle(dim: Double, mul: Double){
    for (i in indices){
        for (j in this[i].indices){
            this[i][j] = (Math.random() - dim) * mul
        }
    }
}

/**
 * example:
 *
 *      list: [1 2 3 4 5 6 7 8], n:3, scroll:1
 *      returns [5 6 7]
 */
fun <T> List<T>.takeLast(n: Int, scroll: Int) : List<T>{

    return this.subList(this.size - n - scroll, this.size - scroll)
}

fun Long.timeStampToDate(
    withSecond : Boolean = true
): String {
    val pattern = if (withSecond) "yyyy MM dd HH:mm:ss" else "yyyy MM dd HH:mm"
    return  DateTimeFormatter.ofPattern(pattern)
        .withLocale( Locale.getDefault() )
        .withZone( ZoneId.systemDefault() )
        .format(Instant.ofEpochSecond(this))
}

fun Long.toSeconds(): String{
    return (this / 1000.0).format(2)
}

fun Map<String, String>.asString() : String{
    return this.entries.joinToString(
        separator = ",",
        prefix = "{",
        postfix = "}",
        transform = {(key, value) -> "$key:$value\n"}
    )
}

