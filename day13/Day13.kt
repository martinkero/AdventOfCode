import kotlin.system.exitProcess

fun main(args: Array<String>) {
    testGetTripSeverity()
    val tripList = utils.readInputAsList("day13/Day13.in", "\n")
    println(getTripSeverity(tripList))
}

class Scanner(val range: Int) {
    var position = 0
    var direction = "down"
    fun scan() {
        if (range==0) return
        when (direction) {
            "down" -> {
                position++
                if (position == range-1) direction = "up"
            }
            "up" -> {
                position--
                if (position == 0) direction = "down"
            }
        }
    }
    fun canCatch(): Boolean {
        if (range == 0) {
            return false
        }
        if (position != 0) {
            return false
        }
        return true
    }
}

fun getTripSeverity(inputStrings: List<String>): Int {
    val firewall = createFirewall(inputStrings)

    val picoSeconds = (0..firewall.size)
    var currentPosition = 0
    var tripSeverity = 0
    picoSeconds.forEach {
        firewall.forEachIndexed { depth, scanner ->
            if (currentPosition == depth && scanner.canCatch()) {
                tripSeverity += scanner.range*depth
            }
            scanner.scan()
        }
        currentPosition++
    }

    return tripSeverity
}

fun createFirewall(inputStrings: List<String>): List<Scanner> {
    val firewallSpecification = mutableMapOf<Int, Int>()
    for (string in inputStrings) {
        val stringArray = string.split(":")
        val layer = stringArray[0].trim().toInt()
        val severity = stringArray[1].trim().toInt()
        firewallSpecification.put(layer, severity)
    }

    val firewall = mutableListOf<Scanner>()
    val deepestLayer = inputStrings.last().split(":")[0].trim().toInt()
    (0..deepestLayer).forEach {
        firewall.add(Scanner(firewallSpecification.getOrDefault(it, 0)))
    }
    return firewall
}

fun testGetTripSeverity() {
    val input = listOf<String>(
            "0: 3",
            "1: 2",
            "4: 4",
            "6: 4"
    )

    val result = getTripSeverity(input)
    val expectedResult = 24

    if (result!=expectedResult) {
        println("Test failed: got: " + result + ", expected: " + expectedResult)
        exitProcess(1)
    }
}