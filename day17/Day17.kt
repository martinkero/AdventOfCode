import kotlin.system.exitProcess

fun main(args: Array<String>) {
    testIdentifySpinlockValueP1()

    println("Part 1:")
    println(identifySpinlockValueP1(2017, 345))
    val time = System.currentTimeMillis()
    println("Part 2:")
    println(identifySpinlockValueP2(50000000, 345))
    println(System.currentTimeMillis()-time)
}

data class CircularBuffer(val positions: MutableList<Int>, var currentPosition: Int)

fun identifySpinlockValueP1(totalInsertions: Int, stepsToTake: Int): Int {
    val cb = CircularBuffer(mutableListOf(0), 0)
    insertValues(cb, totalInsertions, stepsToTake)
    return cb.positions[cb.currentPosition+1]
}

fun identifySpinlockValueP2(totalInsertions: Int, stepsToTake: Int): Int {
    val cb = CircularBuffer(mutableListOf(0), 0)
    insertValues(cb, totalInsertions, stepsToTake)
    return cb.positions[cb.positions.indexOf(0)+1]
}

fun insertValues(cb: CircularBuffer, totalInsertions: Int, stepsToTake: Int) {
    for (insertionValue in (1..totalInsertions)) {
        cb.currentPosition = ((cb.currentPosition + stepsToTake) % cb.positions.size) + 1
        if (cb.currentPosition > cb.positions.size) {
            cb.positions.add(insertionValue)
        } else {
            cb.positions.add(cb.currentPosition, insertionValue)
        }
    }
}

fun testIdentifySpinlockValueP1() {
    val result = identifySpinlockValueP1(2017, 3)
    val expectedResult = 638
    if (result != expectedResult) {
        println("Test failed, got " + result + ", expected " + expectedResult)
        exitProcess(1)
    }
}
