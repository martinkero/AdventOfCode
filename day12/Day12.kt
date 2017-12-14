import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val programStrings = utils.readInputAsList("day12/Day12.in", "\n")
    testGetGroupCount()
    println(getGroupSize(programStrings))
}


fun getGroupSize(programStrings: List<String>): Int {
    val seenPrograms = mutableListOf<Int>()
    val programs = getProgramsFromRawStrings(programStrings)
    traversePipes(programs, seenPrograms, programs[0])
    return seenPrograms.distinct().size
}

fun getProgramsFromRawStrings(programStrings: List<String>): List<List<Int>> {
    val programs = mutableListOf<MutableList<Int>>()
    for ((index, programString) in programStrings.withIndex()) {
        programs.add(mutableListOf())
        val pipeStrings = programString.split("<->")[1].split(",")
        for (pipeString in pipeStrings) {
            programs[index].add(pipeString.trim().toInt())
        }
    }
    return programs
}

fun traversePipes(programs: List<List<Int>>, seenPrograms: MutableList<Int>, programPipeToTraverse: List<Int>) {
    for (program in programPipeToTraverse) {
        if (!seenPrograms.contains(program)) {
            seenPrograms.add(program)
            traversePipes(programs, seenPrograms, programs[program])
        }
    }
}

fun testGetGroupCount() {
    val programStrings = listOf(
            "0 <-> 2",
            "1 <-> 1",
            "2 <-> 0, 3, 4",
            "3 <-> 2, 4",
            "4 <-> 2, 3, 6",
            "5 <-> 6",
            "6 <-> 4, 5"
    )
    val result = getGroupSize(programStrings)

    if (result != 6) {
        println("Test failed, got " + result + ", expected 6")
        exitProcess(1)
    }
}