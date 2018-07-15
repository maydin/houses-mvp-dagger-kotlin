package murat.com.houses.data.source

import murat.com.houses.data.model.House
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Concrete implementation to load houses from the data sources.
 */
@Singleton
class DataRepository @Inject
internal constructor(@param:Remote private val remoteDataSource: DataSource) : DataSource {

    private var cachedHouses: MutableMap<String, House>? = null
    /**
     * Marks the cache as invalid, to force an update the next time data is requested.
     */
    private var cacheIsDirty = false

    /**
     * Gets houses from remote data source
     * Note: [LoadHousesCallback.onDataNotAvailable] is fired if all data sources fail to
     * get the data.
     */
    override fun getHouses(callback: DataSource.LoadHousesCallback) {
        // Respond immediately with cache if available and not dirty
        if (cachedHouses != null && !cacheIsDirty) {
            callback.onHousesLoaded(ArrayList<House>(cachedHouses?.values))
            return
        }

        remoteDataSource.getHouses(object : DataSource.LoadHousesCallback {

            override fun onHousesLoaded(houses: List<House>) {
                refreshCache(houses)
                callback.onHousesLoaded(ArrayList(houses))
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }

            override fun onError() {
                callback.onError()
            }
        })
    }

    fun getHouse(url:String):House?{
        return cachedHouses?.get(url)
    }

    fun refreshCache(houses: List<House>) {
        if (cachedHouses == null) {
            cachedHouses = LinkedHashMap()
        }

        cachedHouses?.let {
            cachedHouses -> cachedHouses.clear()
            for (house in houses) {
                cachedHouses[house.url] = house
            }
        }

        cacheIsDirty = false
    }

    fun refreshHouses() {
        cacheIsDirty = true
    }
}
