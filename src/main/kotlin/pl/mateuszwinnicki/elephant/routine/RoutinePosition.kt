package pl.mateuszwinnicki.elephant.routine

import pl.mateuszwinnicki.elephant.location.PlaceId

data class RoutinePosition(val placeId: PlaceId) {

    constructor() : this(PlaceId("none"))

}
