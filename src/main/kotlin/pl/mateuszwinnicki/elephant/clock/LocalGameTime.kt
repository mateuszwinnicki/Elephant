package pl.mateuszwinnicki.elephant.clock

import java.time.LocalTime
import java.time.temporal.ChronoUnit

class LocalGameTime(localTime: LocalTime) : Comparable<LocalGameTime> {

    private val time: LocalTime = localTime.truncatedTo(ChronoUnit.MINUTES)

    constructor(hour: Int, minute: Int) : this(LocalTime.of(hour, minute))

    override fun compareTo(other: LocalGameTime): Int {
        return this.time.compareTo(other.time)
    }

    fun isAfter(other: LocalGameTime): Boolean {
        return time.isAfter(other.time)
    }

    fun isBefore(other: LocalGameTime): Boolean {
        return time.isBefore(other.time)
    }

    override fun toString(): String {
        return "LocalGameTime(time=${time.hour}:${time.minute})"
    }

}