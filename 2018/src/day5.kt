package demo

import java.io.File
import java.util.*
import kotlin.system.measureTimeMillis

fun main(args : Array<String>) {

    val row = File("input/day5").readText()


    val part1test = part1("dabAcCaCBAcCcaDA")
    if (part1test != 10) println("part1 test failed!, got $part1test, expected 10")
    println("part1: " + part1(row))

}

private fun part1(string: String): Int {


    var polymer = LinkedList(string.split("").subList(1, string.length + 1))
    var previousUnit = ""
    var removeNext = false

    val iterator = polymer.listIterator()
    while (iterator.hasNext()) {

        if (removeNext && iterator.hasPrevious()) {

            iterator.previous()
            iterator.remove()
            removeNext = false

            if (iterator.hasPrevious()) previousUnit = iterator.previous()

        } else {

            var unit = iterator.next()

            if (unit.equals(previousUnit, true) and (unit != previousUnit)) {

                iterator.remove()
                removeNext = true
            }

            previousUnit = unit
        }
    }

    return polymer.size
}