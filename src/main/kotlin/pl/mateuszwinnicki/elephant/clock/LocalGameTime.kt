package pl.mateuszwinnicki.elephant.clock

import java.time.LocalTime
import java.time.temporal.ChronoUnit

class LocalGameTime(localTime: LocalTime) : Comparable<LocalGameTime> {

    private val time = localTime.truncatedTo(ChronoUnit.MINUTES)

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

    fun plusMinutes(minutes: Int): LocalGameTime {
        return LocalGameTime(time.plusMinutes(minutes.toLong()))
    }

    fun plusHours(hours: Int): LocalGameTime {
        return LocalGameTime(time.plusHours(hours.toLong()))
    }

    override fun toString(): String {
        return "LocalGameTime(time=${time.hour}:${time.minute})"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LocalGameTime

        if (time != other.time) return false

        return true
    }

    override fun hashCode(): Int {
        return time?.hashCode() ?: 0
    }


}