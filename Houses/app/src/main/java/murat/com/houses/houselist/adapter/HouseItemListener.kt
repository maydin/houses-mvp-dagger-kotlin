package murat.com.houses.houselist.adapter

import murat.com.houses.data.model.House

/**
 * Listener for Fragment and list interaction
 */
interface HouseItemListener {

    fun onHouseClick(clickedHouse: House)
}
