package demo

import java.io.File
import java.util.function.BiConsumer
import java.util.function.BiFunction
import kotlin.math.abs

fun main(args : Array<String>) {

    val rows = File("input/day6").readLines()
    val testRows = File("input/day6test").readLines()


    val part1test = part1(testRows)
    val part1testExpected = 17
    if (part1test != part1testExpected) println("part1 test failed!, got $part1test, expected $part1testExpected")

    println("part1: " + part1(rows))
}

data class Pos(val x: Int, val y: Int)

private fun part1(rows: List<String>): Int {

    val positions = rows.map { Pos(it.split(", ")[0].toInt(), it.split(", ")[1].toInt()) }

    val nw = positions.reduce { acc, pos -> if ((acc.x < pos.x) or (acc.y < pos.y)) acc else pos}
    val ne = positions.reduce { acc, pos -> if ((acc.x < pos.x) or (acc.y > pos.y)) pos else acc}
    val sw = positions.reduce { acc, pos -> if ((acc.x < pos.x) or (acc.y > pos.y)) acc else pos}
    val se = positions.reduce { acc, pos -> if ((acc.x < pos.x) or (acc.y < pos.y)) pos else acc}

    val positionsWithClosestCount = positions.associateWith { 0 }.toMutableMap()

    val n = if (nw.y < ne.y) nw.y else ne.y
    val e = if (ne.x > se.x) ne.x else se.x
    val s = if (sw.y > se.y) sw.y else se.y
    val w = if (nw.x < sw.x) nw.x else sw.x

    for (y in n-1..s+1) {
        for (x in w-1..e+1) {

            step(Pos(x, y), positionsWithClosestCount)
        }
    }

    var largestField = 0
    positionsWithClosestCount.filterNot { e -> listOf(nw, ne, sw, se).contains(e.key) }.forEach { _, u -> if (u > largestField) largestField = u }

    return largestField
}

private fun step(step: Pos, positionsWithClosestCount: MutableMap<Pos, Int>) {

    var closestPos: Pos? = null
    var tie = false

    for (entry in positionsWithClosestCount) {

        if (closestPos == null) {

            closestPos = entry.key
            continue
        }

        val distanceToPosition = getDistance(entry.key, step)
        val distanceToClosestPosition = getDistance(closestPos!!, step)

        if (distanceToPosition == distanceToClosestPosition ) {

            tie = true

        } else if (distanceToPosition < distanceToClosestPosition) {

            closestPos = entry.key
            tie = false
        }
    }

    if (!tie) {

        positionsWithClosestCount.compute(closestPos!!) { _, u ->  u!! + 1 }
    }
}


private fun getDistance(pos1: Pos, pos2: Pos): Int {

    return abs(pos1.x - pos2.x) + abs(pos1.y - pos2.y)
}