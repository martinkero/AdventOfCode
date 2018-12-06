package demo

import java.io.File

fun main(args : Array<String>) {

    val rows: List<String> = File("input/day2").readLines()

    part1(rows)
}


private fun part1(rows: List<String>) {

    var twos = 0
    var threes = 0

    rows.forEach {

        var twoFound = false
        var threeFound = false
        var row = it

        while(row.isNotEmpty()) {

            val char = row[0]
            val count = row.filter { c -> c == char}.length
            row = row.filter { c -> c != char}

            if (count == 2 && !twoFound) {
                twos++
                twoFound = true
            }
            if (count == 3 && !threeFound) {
                threes++
                threeFound = true
            }
            if (twoFound && threeFound) break
        }
    }

    print("part1: " + twos * threes)
}

private fun part2(rows: List<String>) {

}