package murat.com.houses.houselist.adapter

import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View

import murat.com.houses.R
import murat.com.houses.data.model.House

/**
 * View Holder to display House items
 */
class HouseListViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

    private var listener: HouseItemListener? = null
    private lateinit var mItem: House

    private var cardViewHouse: CardView
    private var nameTextView: AppCompatTextView
    private var regionTextView: AppCompatTextView

    init {

        view.setOnClickListener(this)

        cardViewHouse = view.findViewById(R.id.card_view_house)
        nameTextView = view.findViewById(R.id.info_text_name)
        regionTextView = view.findViewById(R.id.info_text_region)
    }

    fun bind(house: House) {
        mItem = house
        nameTextView.text = house.name
        regionTextView.text = house.region
    }


    fun setOnClickListener(listener: HouseItemListener) {
        this.listener = listener
    }

    override fun onClick(v: View) {
        listener?.onHouseClick(mItem)
    }
}