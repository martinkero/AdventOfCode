import kotlin.system.exitProcess

fun main(args: Array<String>) {
    testIdentifySpinlockValueP1()

    println("Part 1:")
    println(identifySpinlockValueP1(2017, 345))
    println("Part 2:")
    println(identifySpinlockValueP2(50000000, 345))
}

data class CircularBuffer(val positions: MutableList<Int>, var currentPosition: Int)

fun identifySpinlockValueP1(totalInsertions: Int, stepsToTake: Int): Int {
    val cb = CircularBuffer(mutableListOf(0), 0)
    insertValues(cb, totalInsertions, stepsToTake)
    return cb.positions[cb.currentPosition+1]
}

fun identifySpinlockValueP2(totalInsertions: Int, stepsToTake: Int): Int {
    var currentPosition = 0
    var size = 1
    var value = 0
    for (i in (0..totalInsertions)) {
        currentPosition = (currentPosition + stepsToTake) % size
        currentPosition++
        size++
        if (currentPosition==1) {
            value = i+1
        }
    }
    return value
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
