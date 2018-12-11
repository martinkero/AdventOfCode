package demo

import java.io.File
import java.util.*

fun main(args : Array<String>) {

    val rows = File("input/day7").readLines()
    val testRows = File("input/day7test").readLines()


    val part1test = part1(testRows)
    val part1testExpected = "CABDFE"
    if (part1test != part1testExpected) println("part1 test failed!, got $part1test, expected $part1testExpected")

    println("part1: " + part1(rows))
}

data class Instruction(val a: Char, val b: Char)

private fun part1(rows: List<String>): String {

    val regex = ".* ([A-Z]) .* ([A-Z]) .*".toRegex()
    var instructions = rows.map { Instruction(
        regex.find(it)!!.groupValues[1].toCharArray()[0],
        regex.find(it)!!.groupValues[2].toCharArray()[0]) }.toMutableSet()
    var order = Stack<Char>()

    while (instructions.isNotEmpty()) {

        val allSteps = instructions.map { it.a }.union(instructions.map { it.b })
        val blockedSteps = instructions.map { it.b }.toSet()

        val nextStep = allSteps.filter { !blockedSteps.contains(it) and !order.contains(it) }.sorted()[0]

        order.push(nextStep)
        if (instructions.size == 1) order.push(instructions.reduce { _, instruction -> instruction }.b)

        instructions.removeAll { it.a == nextStep }
    }

    return order.joinToString("")
}