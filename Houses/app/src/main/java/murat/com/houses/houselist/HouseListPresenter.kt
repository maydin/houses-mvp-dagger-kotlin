package murat.com.houses.houselist

import murat.com.houses.data.model.House
import murat.com.houses.data.source.DataRepository
import murat.com.houses.data.source.DataSource
import javax.inject.Inject

/**
 * Listens to user actions from the UI ([HouseListFragment]), retrieves the data and updates the
 * UI as required.
 *
 *
 */
class HouseListPresenter @Inject
constructor(private val dataRepository: DataRepository, private val houseView: HouseListContract.View) : HouseListContract.Presenter {

    init {
        houseView.presenter = this
    }
    private var firstLoad = true

    override fun start() {
        loadHouses(false, houseView.isInternetAvailable)
    }

    override fun loadHouses(forceUpdate: Boolean, isInternetAvailable: Boolean) {
        fetchHouses(isInternetAvailable && (forceUpdate || firstLoad))
        firstLoad = false
    }

    /**
     * @param forceUpdate   Pass in true to refresh the data in the [DataSource]
     */
    private fun fetchHouses(forceUpdate: Boolean) {

        houseView.setLoadingIndicator(true)

        if (forceUpdate) {
            dataRepository.refreshHouses()
        }

        dataRepository.getHouses(object : DataSource.LoadHousesCallback {
            override fun onHousesLoaded(houses: List<House>) {

                houseView.setLoadingIndicator(false)
                processHouses(houses)
            }

            override fun onDataNotAvailable() {
                houseView.setLoadingIndicator(false)
                houseView.showNoHouses()
            }

            override fun onError() {
                houseView.setLoadingIndicator(false)
                houseView.showLoadingHousesError()
            }
        })
    }

    /**
     * Display empty state view or list view depending on the data
     * @param houses
     */
    private fun processHouses(houses: List<House>) {
        if (houses.isEmpty()) {
            // Show a message indicating there are no houses
            houseView.showNoHouses()
        } else {
            // Show the list of houses
            houseView.showHouses(houses)
        }
    }

}
