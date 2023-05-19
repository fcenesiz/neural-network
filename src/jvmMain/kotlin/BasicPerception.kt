data class BasicPerception(
    var w0: Double = (Math.random() - 0.5) * 2.0,
    var w1: Double = (Math.random() - 0.5) * 2.0
) {

    fun prepare(){
        w0 = (Math.random() - 0.5) * 2.0
        w1 = (Math.random() - 0.5) * 2.0
    }

    fun guess(x0: Double, x1: Double): Int {
        val total = x0 * w0 + x1 * w1
        return if (total > 0) 1 else 0
    }

    fun createGuesses(input0s : List<Double>, input1s : List<Double>, count: Int) : List<Int>{
        val results = mutableListOf<Int>()
        for (i in 0 until count){
            results.add(guess(input0s[i], input1s[i]))
        }
        return results
    }



}