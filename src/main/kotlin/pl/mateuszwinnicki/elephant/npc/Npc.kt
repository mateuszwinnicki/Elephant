package pl.mateuszwinnicki.elephant.npc

import pl.mateuszwinnicki.elephant.routine.Routinable
import pl.mateuszwinnicki.elephant.routine.Routines

class Npc(private val routines: Routines) : Routinable {

    override fun routines(): Routines {
        return routines
    }

}