import kotlin.system.exitProcess

fun main(args: Array<String>) {
    testGetFinalCount()
    val input = listOf(
            "Generator A starts with 116",
            "Generator B starts with 299"
    )
    println("Part1: ")
    println(getFinalCount(input))
}

fun getFinalCount(input: List<String>): Int {
    val startingValues = input.map { it.filter { it.isDigit() }.toLong() }
    var generatorA = startingValues[0]
    var generatorB = startingValues[1]
    val generatorAMultiplier = 16807
    val generatorBMultiplier = 48271

    var finalCount = 0
    for (i in (1..40000000)) {
        generatorA = calcValue(generatorA, generatorAMultiplier)
        generatorB = calcValue(generatorB, generatorBMultiplier)
        if (binaryKey(generatorA) == binaryKey(generatorB)) {
            finalCount++
        }
    }
    return finalCount
}

fun calcValue(value: Long, multiplier: Int): Long {
    val divider = 2147483647
    val v1 = value * multiplier
    val v2 = v1 % divider
    return (value * multiplier) % divider
}

fun binaryKey(value: Long): String {
    val binaryString = Integer.toBinaryString(value.toInt())
    return if (binaryString.length <= 16) {
        binaryString
    } else {
        binaryString.substring(binaryString.length - 16)
    }
}

fun testGetFinalCount() {
    val input = listOf<String>(
            "Generator A starts with 65",
            "Generator B starts with 8921"
    )
    val expectedResult = 588
    val result = getFinalCount(input)
    if (result != expectedResult) {
        println("Test failed, got " + result + ", expected " + expectedResult)
        exitProcess(1)
    }
}