import java.math.BigInteger
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    testGetUsedSquareCount()
    println("Part 1: ")
    println(getUsedSquareCount("ugkiagan"))
}

fun getUsedSquareCount(input: String): Int {
    var usedSquareCount = 0
    for (i in (0..127)) {
        val hash = Day10.knotHash(input + "-" + i)
        val binaryHash = hexToBinary(hash)
        usedSquareCount += binaryHash.replace("0", "").count()
    }
    return usedSquareCount
}

fun hexToBinary(hex: String): String {
    return BigInteger(hex, 16).toString(2)
}

fun testGetUsedSquareCount() {
    val input = "flqrgnkx"
    val expectedResult = 8108
    val result = getUsedSquareCount(input)

    if (result != expectedResult) {
        println("Test failed, got " + result + ", expected " + expectedResult)
        exitProcess(1)
    }
}