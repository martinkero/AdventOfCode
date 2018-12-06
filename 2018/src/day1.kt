package demo

import java.io.File

fun main(args : Array<String>) {

    val rows: List<String> = File("input/day1").readLines()

    part1(rows)
    part2(rows)
}


private fun part1(rows: List<String>) {

    var sum = rows.map { it.toInt() }.sum()

    println("part1: " + sum)
}

private fun part2(rows: List<String>) {

    var sum = 0
    var foundEntries = mutableSetOf(sum)

    while (true) {

        rows.forEach {

            sum += it.toInt();

            if (foundEntries.contains(sum)) {

                println("part2: $sum")
                return

            } else {

                foundEntries.add(sum)
            }
        }
    }
}