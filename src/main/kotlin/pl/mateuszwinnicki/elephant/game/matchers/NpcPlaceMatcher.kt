package pl.mateuszwinnicki.elephant.game.matchers

import pl.mateuszwinnicki.elephant.location.PlaceId
import pl.mateuszwinnicki.elephant.npc.Npc

class NpcPlaceMatcher {

    private val npcsInPlace = mutableMapOf<PlaceId, MutableList<Npc>>()

    fun addNpcToPlace(npc: Npc, placeId: PlaceId) {
        npcsInPlace.getOrPut(placeId) { mutableListOf() }.add(npc)
    }

    fun clearPlacesFromNpcs(placeId: PlaceId) {
        npcsInPlace[placeId] = mutableListOf()
    }

    fun clearPlacesFromNpcs() {
        npcsInPlace.clear()
    }

    fun getNpcs(placeId: PlaceId): List<Npc> {
        return npcsInPlace.getOrPut(placeId) { mutableListOf() }
    }

}