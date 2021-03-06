package pl.mateuszwinnicki.elephant.clock

import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.fail

internal class TimeRangeTest {

    @Test
    internal fun `time range should be start closed`() {
        val timeRange = TimeRange(LocalGameTime(1, 32), LocalGameTime(12, 54))

        assertTrue(timeRange.inRange(LocalGameTime(1, 32)))
    }

    @Test
    internal fun `time range should be end opened`() {
        val timeRange = TimeRange(LocalGameTime(2, 34), LocalGameTime(13, 22))

        assertFalse(timeRange.inRange(LocalGameTime(13, 22)))
    }

    @Test
    internal fun `time between should be in range`() {
        val timeRange = TimeRange(LocalGameTime(2, 34), LocalGameTime(13, 22))

        assertTrue(timeRange.inRange(LocalGameTime(5, 0)))
    }

    @Test
    internal fun `time range can be created between midnight`() {
        try {
            TimeRange(LocalGameTime(23, 0), LocalGameTime(2, 0))
        } catch (ex: Exception) {
            fail()
        }
    }

    @Test
    internal fun `time range between midnight can be properly searched before midnight`() {
        val timeRange = TimeRange(LocalGameTime(23, 0), LocalGameTime(2, 0))

        assertTrue(timeRange.inRange(LocalGameTime(1, 22)))
    }

    @Test
    internal fun `time range between midnight can be properly searched after midnight`() {
        val timeRange = TimeRange(LocalGameTime(23, 0), LocalGameTime(2, 0))

        assertTrue(timeRange.inRange(LocalGameTime(23, 30)))
    }

    @Test
    internal fun `time range between midnight can be properly searched after midnight with same start`() {
        val timeRange = TimeRange(LocalGameTime(23, 0), LocalGameTime(2, 0))

        assertTrue(timeRange.inRange(LocalGameTime(23, 0)))
    }

    @Test
    internal fun `time range between midnight can be properly searched after midnight with same end`() {
        val timeRange = TimeRange(LocalGameTime(23, 0), LocalGameTime(2, 0))

        assertFalse(timeRange.inRange(LocalGameTime(2, 0)))
    }

    @Test
    internal fun `time range between midnight searched outside range`() {
        val timeRange = TimeRange(LocalGameTime(23, 0), LocalGameTime(2, 0))

        assertFalse(timeRange.inRange(LocalGameTime(5, 21)))
    }

}