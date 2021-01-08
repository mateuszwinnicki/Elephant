package pl.mateuszwinnicki.elephant

import pl.mateuszwinnicki.elephant.clock.LocalGameTime
import pl.mateuszwinnicki.elephant.clock.MainClock
import pl.mateuszwinnicki.elephant.routine.Routinable

class Game {

    val mainClock: MainClock = MainClock(LocalGameTime(0, 0))
    val routinables: MutableList<Routinable> = mutableListOf()

    fun addRoutinable(routinable: Routinable) {
        routinables.add(routinable)
    }


}