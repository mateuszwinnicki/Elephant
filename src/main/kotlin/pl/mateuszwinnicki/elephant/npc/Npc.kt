package pl.mateuszwinnicki.elephant.npc

import pl.mateuszwinnicki.elephant.routine.Routinable
import pl.mateuszwinnicki.elephant.routine.Routines

class Npc(val id: NpcId, private val routines: Routines) : Routinable {

    override fun routines(): Routines {
        return routines
    }


}