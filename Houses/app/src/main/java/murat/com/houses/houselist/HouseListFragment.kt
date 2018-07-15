package murat.com.houses.houselist

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import murat.com.houses.R
import murat.com.houses.data.model.House
import murat.com.houses.housedetail.HouseDetailActivity
import murat.com.houses.houselist.adapter.HouseItemListener
import murat.com.houses.houselist.adapter.HouseListAdapter
import java.util.*

/**
 * A Fragment representing a list of Houses.
 * The Fragment presents a list of items, which when touched,
 * lead to a detail screen which is displaying
 */
class HouseListFragment : Fragment(), HouseListContract.View {

    private lateinit var listAdapter: HouseListAdapter

    override lateinit var presenter: HouseListContract.Presenter

    private lateinit var noHousesView: View

    private lateinit var noHousesTextView: TextView

    private lateinit var housesView: LinearLayout

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override val isInternetAvailable: Boolean
        get() {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }

    /**
     * Listener for clicks on houses in the list.
     */
    private var mItemListener: HouseItemListener = object : HouseItemListener {


        override fun onHouseClick(clickedHouse: House) {
            showHouse(clickedHouse)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listAdapter = HouseListAdapter(ArrayList(0), mItemListener)
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.houselist_fragment, container, false)

        // Set up houses view
        val listView = root.findViewById<View>(R.id.house_list) as RecyclerView
        listView.adapter = listAdapter
        listView.layoutManager = LinearLayoutManager(this.context)
        housesView = root.findViewById<View>(R.id.housesAll) as LinearLayout

        // Set up  no houses view
        noHousesView = root.findViewById(R.id.noHouses)
        noHousesTextView = root.findViewById<View>(R.id.noHousesText) as TextView

        // Set up progress indicator
        swipeRefreshLayout = root.findViewById<View>(R.id.refresh_layout) as SwipeRefreshLayout

        swipeRefreshLayout.setOnRefreshListener { presenter.loadHouses(true, isInternetAvailable) }

        setHasOptionsMenu(true)

        return root
    }

    override fun setLoadingIndicator(enable: Boolean) {

        if (view == null) {
            return
        }
        // Make sure setRefreshing() is called after the layout is done with everything else.
        swipeRefreshLayout.post { swipeRefreshLayout.isRefreshing = enable }
    }

    override fun showHouses(houses: List<House>) {
        listAdapter.replaceData(houses)

        housesView.visibility = View.VISIBLE
        noHousesView.visibility = View.GONE
    }


    override fun showNoHouses() {
        housesView.visibility = View.GONE
        noHousesView.visibility = View.VISIBLE
        noHousesTextView.visibility = View.VISIBLE
        noHousesTextView.text = getString(R.string.no_houses_all)
    }

    override fun showHouse(house: House) {
        val intent = Intent(context, HouseDetailActivity::class.java).apply {
            putExtra(HouseDetailActivity.EXTRA_HOUSE_URL, house.url)
        }
        startActivity(intent)

    }

    override fun showLoadingHousesError() {
        showMessage(getString(R.string.loading_houses_error))
    }

    private fun showMessage(message: String) {
        Snackbar.make(housesView, message, Snackbar.LENGTH_LONG).show()
    }

    companion object {

        fun newInstance(): HouseListFragment {
            return HouseListFragment()
        }
    }

}// Requires empty public constructor
