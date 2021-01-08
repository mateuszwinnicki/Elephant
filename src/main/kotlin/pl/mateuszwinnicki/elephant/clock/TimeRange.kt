package pl.mateuszwinnicki.elephant.clock

class TimeRange(private val closedStart: LocalGameTime, private val openedEnd: LocalGameTime) {

    private val midnightBetween: Boolean = closedStart > openedEnd

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

    fun getClosedStart(): LocalGameTime {
        return closedStart;
    }

    override fun toString(): String {
        return "TimeRange(closedStart=$closedStart, openedEnd=$openedEnd)"
    }


}