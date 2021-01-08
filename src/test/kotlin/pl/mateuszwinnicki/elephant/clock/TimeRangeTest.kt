package pl.mateuszwinnicki.elephant.clock

import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.fail

internal class TimeRangeTest {

    @Test
    internal fun timeRangeShouldBeStartClosed() {
        val timeRange = TimeRange(LocalGameTime(1, 32), LocalGameTime(12, 54))

        assertTrue(timeRange.inRange(LocalGameTime(1, 32)))
    }

    @Test
    internal fun timeRangeShouldBeEndOpened() {
        val timeRange = TimeRange(LocalGameTime(2, 34), LocalGameTime(13, 22))

        assertFalse(timeRange.inRange(LocalGameTime(13, 22)))
    }

    @Test
    internal fun timeBetweenShouldBeInRange() {
        val timeRange = TimeRange(LocalGameTime(2, 34), LocalGameTime(13, 22))

        assertTrue(timeRange.inRange(LocalGameTime(5, 0)))
    }

    @Test
    internal fun timeRangeCanBeCreatedBetweenMidnight() {
        try {
            TimeRange(LocalGameTime(23, 0), LocalGameTime(2, 0))
        } catch (ex: Exception) {
            fail()
        }
    }

    @Test
    internal fun timeRangeBetweenMidnightCanBeProperlySearchedBeforeMidnight() {
        val timeRange = TimeRange(LocalGameTime(23, 0), LocalGameTime(2, 0))

        assertTrue(timeRange.inRange(LocalGameTime(1, 22)))
    }

    @Test
    internal fun timeRangeBetweenMidnightCanBeProperlySearchedAfterMidnight() {
        val timeRange = TimeRange(LocalGameTime(23, 0), LocalGameTime(2, 0))

        assertTrue(timeRange.inRange(LocalGameTime(23, 30)))
    }

    @Test
    internal fun timeRangeBetweenMidnightCanBeProperlySearchedAfterMidnightWithSameStart() {
        val timeRange = TimeRange(LocalGameTime(23, 0), LocalGameTime(2, 0))

        assertTrue(timeRange.inRange(LocalGameTime(23, 0)))
    }

    @Test
    internal fun timeRangeBetweenMidnightCanBeProperlySearchedAfterMidnightWithSameEnd() {
        val timeRange = TimeRange(LocalGameTime(23, 0), LocalGameTime(2, 0))

        assertFalse(timeRange.inRange(LocalGameTime(2, 0)))
    }

    @Test
    internal fun timeRangeBetweenMidnightSearchedOutsideRange() {
        val timeRange = TimeRange(LocalGameTime(23, 0), LocalGameTime(2, 0))

        assertFalse(timeRange.inRange(LocalGameTime(5, 21)))
    }

}