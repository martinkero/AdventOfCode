package demo

import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.min

fun main(args : Array<String>) {

    val rows: List<String> = File("input/day4").readLines()

    part1(rows)
}

enum class EVENT_TYPE {
    SHIFT_STARTED,
    ASLEEP,
    WAKEUP
}

private fun part1(rows: List<String>) {

    data class Event(val date: LocalDateTime, val eventType: EVENT_TYPE, val guardID: Int)

    var events = mutableListOf<Event>()
    val regex = "\\[(.*)\\] (\\D+|\\D+(\\d+)\\D+)\$".toRegex()

    rows.forEach {

        val matches = regex.find(it)?.groupValues

        if (matches != null) {

            val dateString = matches[1]
            val eventString = matches[2]
            val date = LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
            val eventType = when(eventString) {
                "falls asleep" -> EVENT_TYPE.ASLEEP
                "wakes up" -> EVENT_TYPE.WAKEUP
                else -> EVENT_TYPE.SHIFT_STARTED
            }

            val guardID = if (matches[3] == "") 0 else matches[3].toInt()

            events.add(Event(date, eventType, guardID))
        }
    }

    events.sortBy { it.date }

    val minuteFormatter = DateTimeFormatter.ofPattern("mm")
    var minutesSleptPerGuard = mutableMapOf<Int, MutableMap<Int, Int>>()
    var guardWhoSleptMost = 0
    var mostMinutesSlept = 0
    var currentGuardID = 0
    var minuteGuardFellAsleep = 0

    events.forEach {

        val currentMinute = it.date.format(minuteFormatter).toInt()

        when (it.eventType) {
            EVENT_TYPE.SHIFT_STARTED -> currentGuardID = it.guardID
            EVENT_TYPE.ASLEEP -> minuteGuardFellAsleep = currentMinute
            EVENT_TYPE.WAKEUP -> {

                var minutesSlept = minutesSleptPerGuard.getOrDefault(currentGuardID, mutableMapOf())

                for (minute in minuteGuardFellAsleep..currentMinute) {

                    minutesSlept[minute] = minutesSlept.getOrDefault(minute, 0) + 1
                }

                minutesSleptPerGuard[currentGuardID] = minutesSlept

                val totalMinutesSlept = minutesSlept.map{ it.value }.sum()

                if (totalMinutesSlept > mostMinutesSlept) {

                    mostMinutesSlept = totalMinutesSlept
                    guardWhoSleptMost = currentGuardID
                }
            }
        }
    }

    var mostCommonlySleptMinute = 0
    var timesSlept = 0
    minutesSleptPerGuard[guardWhoSleptMost]?.forEach {

        if (it.value > timesSlept) {

            timesSlept = it.value
            mostCommonlySleptMinute = it.key
        }
    }

    println("part1: " + guardWhoSleptMost * mostCommonlySleptMinute)
}