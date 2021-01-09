package pl.mateuszwinnicki.elephant.game

import org.junit.jupiter.api.Test
import pl.mateuszwinnicki.elephant.clock.LocalGameTime
import pl.mateuszwinnicki.elephant.clock.MainClock
import pl.mateuszwinnicki.elephant.clock.TimeRange
import pl.mateuszwinnicki.elephant.game.matchers.NpcPlaceMatcher
import pl.mateuszwinnicki.elephant.location.Place
import pl.mateuszwinnicki.elephant.location.PlaceId
import pl.mateuszwinnicki.elephant.location.PlaceRepository
import pl.mateuszwinnicki.elephant.npc.Npc
import pl.mateuszwinnicki.elephant.npc.NpcId
import pl.mateuszwinnicki.elephant.routine.Routine
import pl.mateuszwinnicki.elephant.routine.RoutinePosition
import pl.mateuszwinnicki.elephant.routine.Routines
import kotlin.test.assertEquals
import kotlin.test.fail

internal class GameTest {

    companion object {
        val CITY1_HOUSE1 = PlaceId("CITY1_HOUSE1")
        val CITY1_HOUSE2 = PlaceId("CITY1_HOUSE2")
        val CITY1_MARKET1 = PlaceId("CITY1_MARKET1")
        val CITY1_TAVERN1 = PlaceId("CITY1_TAVERN1")
        val NPC_CHAD1 = NpcId("NPC_CHAD1")
        val NPC_CHAD2 = NpcId("NPC_CHAD2")
    }

    @Test
    internal fun `moving time forward should move npcs between places`() {
        val placeRepository = PlaceRepository()
        placeRepository.addPlaces(
            Place(CITY1_HOUSE1),
            Place(CITY1_HOUSE2),
            Place(CITY1_MARKET1),
            Place(CITY1_TAVERN1),
        )
        val npcPlaceMatcher = NpcPlaceMatcher()
        val game = Game(MainClock(LocalGameTime(0, 0)), placeRepository, npcPlaceMatcher)

        game.registerNpcs(
            Npc(
                NPC_CHAD1,
                Routines(
                    listOf(
                        Routine(TimeRange(LocalGameTime(0, 0), LocalGameTime(8, 0)), RoutinePosition(CITY1_HOUSE1)),
                        Routine(TimeRange(LocalGameTime(8, 0), LocalGameTime(16, 0)), RoutinePosition(CITY1_MARKET1)),
                        Routine(TimeRange(LocalGameTime(16, 0), LocalGameTime(0, 0)), RoutinePosition(CITY1_TAVERN1))
                    )
                )
            ),
            Npc(
                NPC_CHAD2,
                Routines(
                    listOf(
                        Routine(TimeRange(LocalGameTime(0, 0), LocalGameTime(8, 0)), RoutinePosition(CITY1_HOUSE2)),
                        Routine(TimeRange(LocalGameTime(8, 0), LocalGameTime(16, 0)), RoutinePosition(CITY1_MARKET1)),
                        Routine(TimeRange(LocalGameTime(16, 0), LocalGameTime(0, 0)), RoutinePosition(CITY1_TAVERN1))
                    )
                )
            ),
        )

        game.passTime(hours = 9, minutes = 0)

        val npcs1 = game.getNpcsInPlaceForCurrentTime(CITY1_HOUSE2)
        val npcs2 = game.getNpcsInPlaceForCurrentTime(CITY1_MARKET1)
        val npcs3 = game.getNpcsInPlaceForCurrentTime(CITY1_TAVERN1)

        assertEquals(0, npcs1.size)
        assertEquals(2, npcs2.size)
        assertEquals(0, npcs3.size)
    }

    @Test
    internal fun `registering npcs should move them to valid position with main clock`() {
        val placeRepository = PlaceRepository()
        placeRepository.addPlaces(
            Place(CITY1_HOUSE1),
            Place(CITY1_HOUSE2),
            Place(CITY1_MARKET1),
            Place(CITY1_TAVERN1),
        )
        val npcPlaceMatcher = NpcPlaceMatcher()
        val game = Game(MainClock(LocalGameTime(17, 0)), placeRepository, npcPlaceMatcher)

        game.registerNpcs(
            Npc(
                NPC_CHAD1,
                Routines(
                    listOf(
                        Routine(TimeRange(LocalGameTime(0, 0), LocalGameTime(8, 0)), RoutinePosition(CITY1_HOUSE1)),
                        Routine(TimeRange(LocalGameTime(8, 0), LocalGameTime(16, 0)), RoutinePosition(CITY1_MARKET1)),
                        Routine(TimeRange(LocalGameTime(16, 0), LocalGameTime(0, 0)), RoutinePosition(CITY1_TAVERN1))
                    )
                )
            )
        )

        val npcs = game.getNpcsInPlaceForCurrentTime(CITY1_TAVERN1)

        assertEquals(1, npcs.size)
    }

    @Test
    internal fun `should throw exception when npc is registered second time`() {
        val placeRepository = PlaceRepository()
        placeRepository.addPlaces(
            Place(CITY1_HOUSE1),
            Place(CITY1_HOUSE2),
            Place(CITY1_MARKET1),
            Place(CITY1_TAVERN1),
        )
        val npcPlaceMatcher = NpcPlaceMatcher()
        val game = Game(MainClock(LocalGameTime(0, 0)), placeRepository, npcPlaceMatcher)

        val npc = Npc(
            NPC_CHAD1,
            Routines(
                listOf(
                    Routine(TimeRange(LocalGameTime(0, 0), LocalGameTime(0, 0)), RoutinePosition(CITY1_HOUSE1)),
                )
            )
        )
        game.registerNpcs(npc)

        try {
            game.registerNpcs(npc)
            fail()
        } catch (ex: NpcRegistrationException) {
            //fine
        }
    }

}