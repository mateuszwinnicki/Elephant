package pl.mateuszwinnicki.elephant.game

import pl.mateuszwinnicki.elephant.clock.LocalGameTime
import pl.mateuszwinnicki.elephant.clock.MainClock
import pl.mateuszwinnicki.elephant.game.matchers.NpcPlaceMatcher
import pl.mateuszwinnicki.elephant.location.PlaceId
import pl.mateuszwinnicki.elephant.location.PlaceRepository
import pl.mateuszwinnicki.elephant.npc.Npc
import pl.mateuszwinnicki.elephant.npc.NpcId

class Game(
    private val mainClock: MainClock = MainClock(LocalGameTime(0, 0)),
    private val placeRepository: PlaceRepository,
    private val npcPlaceMatcher: NpcPlaceMatcher = NpcPlaceMatcher()
) {

    companion object {
        //TODO: 1 minute can be too small, TBD
        private const val STANDARD_MINUTES_WINDOW = 1
    }

    private val registeredNpcs = mutableMapOf<NpcId, Npc>()

    fun registerNpcs(vararg npcs: Npc) {
        npcs.forEach { npc ->
            if (registeredNpcs.containsKey(npc.id)) {
                throw NpcRegistrationException("Npc with id ${npc.id} was already registered")
            }
            registeredNpcs[npc.id] = npc
            moveNpcToPositionWithCurrentClock(npc)
        }
    }

    fun passTime(hours: Int = 0, minutes: Int = 0) {
        val gameTicks = (hours * 60 + minutes) / STANDARD_MINUTES_WINDOW // FIXME: this will work only if const will be 1 (and for division that wont return modulo)
        for (i in 1..gameTicks) {
            processGameTick()
        }
    }

    private fun processGameTick() {
        moveClockForward()
        moveNpcsWithRoutines()
    }

    //TODO: remove npcPlaceMatcher, NPC should "move" between positions itself, not by some external force
    private fun moveNpcsWithRoutines() {
        npcPlaceMatcher.clearPlacesFromNpcs()
        registeredNpcs.values.forEach(this@Game::moveNpcToPositionWithCurrentClock)
    }

    private fun moveNpcToPositionWithCurrentClock(npc: Npc) {
        val positionForTime = npc.routines().getPositionForTime(mainClock.clock)
        npcPlaceMatcher.addNpcToPlace(npc, positionForTime.placeId)
    }

    private fun moveClockForward() {
        mainClock.moveForward(minutes = STANDARD_MINUTES_WINDOW)
    }

    fun getNpcsInPlaceForCurrentTime(placeId: PlaceId): List<Npc> {
        return npcPlaceMatcher.getNpcs(placeId)
    }


}