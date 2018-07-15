package murat.com.houses.data.source

import murat.com.houses.data.model.House

/**
 * Main entry point for accessing house data.
 */
interface DataSource {

    interface LoadHousesCallback {

        fun onHousesLoaded(houses: List<House>)

        fun onDataNotAvailable()

        fun onError()
    }

    fun getHouses(callback: LoadHousesCallback)
}
