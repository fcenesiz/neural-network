class Samples {

    val sampleCount = 50
    val input0s = mutableListOf<Double>()
    val input1s = mutableListOf<Double>()
    val outputs = mutableListOf<Int>()

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

}