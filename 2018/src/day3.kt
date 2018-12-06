package demo

import java.io.File

fun main(args : Array<String>) {

    val rows: List<String> = File("input/day3").readLines()

    part1(rows)
}

private fun part1(rows: List<String>) {

    data class Claim(val xStart: Int, val yStart: Int, val width: Int, val height: Int)
    data class SquareInch(val x: Int, val y: Int)

    var claimsPerSquareInch = mutableMapOf<SquareInch, Int>()

    rows.forEach {

        val regex = "#[0-9]+ @ ([0-9]+),([0-9]+): ([0-9]+)x([0-9]+)".toRegex()
        val matches = regex.find(it)?.groupValues?.drop(1)?.map { it.toInt() }

        if (matches != null) {

            val claim = Claim(matches[0], matches[1], matches[2], matches[3])

            for (x in claim.xStart until claim.xStart + claim.width) {
                for (y in claim.yStart until claim.yStart + claim.height) {

                    val squareInch = SquareInch(x, y)
                    claimsPerSquareInch[squareInch] = claimsPerSquareInch.getOrDefault(squareInch, 0) + 1
                }
            }
        }
    }

    println("part1 " + claimsPerSquareInch.filter { it.value >= 2 }.size)
}