package pl.mateuszwinnicki.elephant.routine

import pl.mateuszwinnicki.elephant.clock.LocalGameTime

class Routines(routines: List<Routine>) {

    private val routinesSorted = routines.sortedBy { it.timeRange.closedStart }

    init {
        if (routinesSorted.isEmpty()) {
            throw RoutineException("Routines are empty")
        }
        routinesSorted.zipWithNext { a, b ->
            if (!a.timeRange.endsWithStartOf(b.timeRange)) {
                throw RoutineException("Routines are not connected with each other. There is a missing routine in between $a and $b")
            }
        }
        if (!routinesSorted.last().timeRange.endsWithStartOf(routinesSorted.first().timeRange)) {
            throw RoutineException("Routines are not connected with each other. There is a missing routine in between ${routinesSorted.first()} and ${routinesSorted.last()}")
        }
    }

    fun getPositionForTime(time: LocalGameTime): RoutinePosition {
        return routinesSorted.find { it.timeRange.inRange(time) }?.position ?: throw IllegalStateException("There must be exactly one routine available among routines: $routinesSorted for requested time: $time")
    }

}