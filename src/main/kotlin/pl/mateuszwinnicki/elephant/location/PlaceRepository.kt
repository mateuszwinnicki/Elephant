package pl.mateuszwinnicki.elephant.location

class PlaceRepository {

    private val places = mutableMapOf<PlaceId, Place>()

    fun addPlaces(vararg places: Place) {
        this.places.putAll(
            places.map { p -> Pair(p.id, p) }
        )
    }

    fun findById(id: PlaceId): Place? {
        return places[id]
    }

}