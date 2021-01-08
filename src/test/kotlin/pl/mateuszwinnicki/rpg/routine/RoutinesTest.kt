package pl.mateuszwinnicki.rpg.routine

import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test
import pl.mateuszwinnicki.elephant.clock.LocalGameTime
import pl.mateuszwinnicki.elephant.clock.TimeRange
import pl.mateuszwinnicki.elephant.routine.Routine
import pl.mateuszwinnicki.elephant.routine.RoutineException
import pl.mateuszwinnicki.elephant.routine.RoutinePosition
import pl.mateuszwinnicki.elephant.routine.Routines

internal class RoutinesTest {

    @Test
    internal fun shouldThrowExceptionWhenRoutinesIsCreatedWithEmptyList() {
        try {
            Routines(listOf())
            fail()
        } catch (ex: RoutineException) {
            // fine
        }
    }

    @Test
    internal fun shouldThrowExceptionWhenRoutinesIsCreatedWithoutFullRoutines() {
        try {
            Routines(
                listOf(
                    Routine(TimeRange(LocalGameTime(0, 0), LocalGameTime(12, 0)), RoutinePosition()),
                    Routine(TimeRange(LocalGameTime(12, 0), LocalGameTime(14, 0)), RoutinePosition())
                )
            )
            fail()
        } catch (ex: RoutineException) {
            // fine
        }
    }

    @Test
    internal fun canBeCreatedWithFullRange() {
        try {
            Routines(
                listOf(
                    Routine(TimeRange(LocalGameTime(0, 0), LocalGameTime(12, 0)), RoutinePosition()),
                    Routine(TimeRange(LocalGameTime(12, 0), LocalGameTime(0, 0)), RoutinePosition())
                )
            )
        } catch (ex: RoutineException) {
            fail()
        }
    }

    @Test
    internal fun canBeCreatedWithFullRangeWithOneTimeRangeBetweenMidnight() {
        try {
            Routines(
                listOf(
                    Routine(TimeRange(LocalGameTime(3, 0), LocalGameTime(21, 0)), RoutinePosition()),
                    Routine(TimeRange(LocalGameTime(21, 0), LocalGameTime(22, 30)), RoutinePosition()),
                    Routine(TimeRange(LocalGameTime(22, 30), LocalGameTime(23, 55)), RoutinePosition()),
                    Routine(TimeRange(LocalGameTime(23, 55), LocalGameTime(3, 0)), RoutinePosition())
                )
            )
        } catch (ex: RoutineException) {
            fail()
        }
    }

    @Test
    internal fun canBeCreatedWithOneRoutineWhenItIsClosed() {
        try {
            Routines(
                listOf(
                    Routine(TimeRange(LocalGameTime(3, 0), LocalGameTime(3, 0)), RoutinePosition()),
                )
            )
        } catch (ex: RoutineException) {
            fail()
        }
    }

    @Test
    internal fun shouldThrowExceptionWhenCreatedWithOneNotClosedRoutine() {
        try {
            Routines(
                listOf(
                    Routine(TimeRange(LocalGameTime(3, 0), LocalGameTime(4, 0)), RoutinePosition()),
                )
            )
            fail()
        } catch (ex: RoutineException) {

        }
    }

}

