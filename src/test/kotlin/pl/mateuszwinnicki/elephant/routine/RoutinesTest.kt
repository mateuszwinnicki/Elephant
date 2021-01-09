package pl.mateuszwinnicki.elephant.routine

import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test
import pl.mateuszwinnicki.elephant.clock.LocalGameTime
import pl.mateuszwinnicki.elephant.clock.TimeRange
import pl.mateuszwinnicki.elephant.location.PlaceId
import kotlin.test.assertEquals

internal class RoutinesTest {

    @Test
    internal fun `should throw exception when routines is created with empty list`() {
        try {
            Routines(listOf())
            fail()
        } catch (ex: RoutineException) {
            // fine
        }
    }

    @Test
    internal fun `should throw exception when routines is created without full routines`() {
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
    internal fun `can be created with full range`() {
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
    internal fun `can be created with full range with one time range between midnight`() {
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
    internal fun `can be created with one routine when it is closed`() {
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
    internal fun `should throw exception when created with one not closed routine`() {
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

    @Test
    internal fun `routines can be created with only one routine`() {
        try {
            Routines(
                listOf(
                    Routine(TimeRange(LocalGameTime(12, 0), LocalGameTime(12, 0)), RoutinePosition()),
                )
            )
        } catch (ex: RoutineException) {
            fail()
        }
    }

    @Test
    internal fun `find routine when only one is available`() {
        val placeId = PlaceId("CITY1_MARKET")
        val routines = Routines(
            listOf(
                Routine(TimeRange(LocalGameTime(12, 0), LocalGameTime(12, 0)), RoutinePosition(placeId)),
            )
        )

        val positionForTime = routines.getPositionForTime(LocalGameTime(11, 30));

        assertEquals(placeId, positionForTime.placeId)
    }

    @Test
    internal fun `find routine when only one is available and searched by starting time`() {
        val placeId = PlaceId("CITY1_MARKET")
        val routines = Routines(
            listOf(
                Routine(TimeRange(LocalGameTime(12, 0), LocalGameTime(12, 0)), RoutinePosition(placeId)),
            )
        )
        val positionForTime = routines.getPositionForTime(LocalGameTime(12, 0));

        assertEquals(placeId, positionForTime.placeId)
    }

}

