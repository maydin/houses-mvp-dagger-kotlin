package murat.com.houses.housedetail

import murat.com.houses.BasePresenter
import murat.com.houses.BaseView
import murat.com.houses.data.model.House

interface HouseDetailContract {

    interface View : BaseView<Presenter> {

        fun showHouse(house : House)

        fun houseNotFound()
    }

    interface Presenter : BasePresenter{

        fun showHouse()
    }
}