package pl.mateuszwinnicki.elephant.clock

class MainClock(clock: LocalGameTime) {

    var clock = clock
        private set

    fun moveForward(hours: Int = 0, minutes: Int) {
        clock = clock.plusHours(hours).plusMinutes(minutes)
    }

}