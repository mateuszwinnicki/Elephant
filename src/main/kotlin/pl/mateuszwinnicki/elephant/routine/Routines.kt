package pl.mateuszwinnicki.elephant.routine

import pl.mateuszwinnicki.elephant.clock.LocalGameTime

class Routines(routines: List<Routine>) {

    private val routinesSorted: List<Routine> = routines.sortedBy { it.timeRange.getClosedStart() }

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

    fun getPositionForTime(time: LocalGameTime): Routine {
        return routinesSorted.find { it.timeRange.inRange(time) } ?: throw Exception()
    }

}