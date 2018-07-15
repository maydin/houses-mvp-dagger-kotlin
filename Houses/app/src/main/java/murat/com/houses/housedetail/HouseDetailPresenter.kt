package murat.com.houses.housedetail

import murat.com.houses.data.source.DataRepository
import javax.inject.Inject

class HouseDetailPresenter @Inject
constructor(private val dataRepository: DataRepository, private val houseDetailView: HouseDetailContract.View,
            private val houseUrl:String)
    : HouseDetailContract.Presenter {

    init {
        houseDetailView.presenter = this
    }

    override fun start() {
        showHouse()
    }

    override fun showHouse() {
        val house = dataRepository.getHouse(houseUrl)
        if(house != null) {
            houseDetailView.showHouse(house)
        }
        else{
            houseDetailView.houseNotFound()
        }
    }
}