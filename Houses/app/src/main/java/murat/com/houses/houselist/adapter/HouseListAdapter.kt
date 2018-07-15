package murat.com.houses.houselist.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import murat.com.houses.R
import murat.com.houses.data.model.House

/**
 * Adapter that lists the houses
 */
class HouseListAdapter(private var mValues: List<House>, private val mListener: HouseItemListener) : RecyclerView.Adapter<HouseListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HouseListViewHolder {

        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.houselist_content, parent, false)

        return HouseListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mValues.size
    }


    override fun onBindViewHolder(holder: HouseListViewHolder, position: Int) {

        if (!mValues.isEmpty() && position < mValues.size) {
            val house = mValues[position]
            holder.setOnClickListener(mListener)
            holder.bind(house)
        }
    }

    /**
     * Updates the adapter data with the data from remote or local data source
     * @param houses
     */
    fun replaceData(houses: List<House>) {
        setList(houses)
        notifyDataSetChanged()
    }

    private fun setList(houses: List<House>) {
        mValues = houses
    }
}
