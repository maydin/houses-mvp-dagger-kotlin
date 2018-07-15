package murat.com.houses.houselist

import murat.com.houses.BasePresenter
import murat.com.houses.BaseView
import murat.com.houses.data.model.House

/**
 * This specifies the contract between the view and the presenter.
 */
class HouseListContract {

    interface View : BaseView<Presenter> {

        val isInternetAvailable: Boolean

        fun setLoadingIndicator(enable: Boolean)

        fun showHouses(houses: List<House>)

        fun showHouse(house: House)

        fun showLoadingHousesError()

        fun showNoHouses()
    }

    interface Presenter : BasePresenter {
        fun loadHouses(forceUpdate: Boolean, isInternetAvailable: Boolean)
    }
}
