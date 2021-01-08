package pl.mateuszwinnicki.elephant.clock

class TimeRange(val closedStart: LocalGameTime, val openedEnd: LocalGameTime) {

    private val midnightBetween = closedStart > openedEnd

    fun inRange(time: LocalGameTime): Boolean {
        if (midnightBetween) {
            return (time >= closedStart && time > openedEnd) ||
                (time < closedStart && time < openedEnd)
        }
        return time >= closedStart && time < openedEnd
    }

    fun endsWithStartOf(other: TimeRange): Boolean {
        return openedEnd.compareTo(other.closedStart) == 0
    }

    override fun toString(): String {
        return "TimeRange(closedStart=$closedStart, openedEnd=$openedEnd)"
    }


}