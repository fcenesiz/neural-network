data class BasicPerception(
    var w0: Double,
    var w1: Double
) {

    fun guess(x0: Double, x1: Double): Int {
        val total = x0 * w0 + x1 * w1
        return if (total > 0) 1 else -1
    }

}