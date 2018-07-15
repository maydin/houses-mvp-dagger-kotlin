package murat.com.houses.housedetail

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import murat.com.houses.R
import murat.com.houses.data.model.House

/**
 * A placeholder fragment containing a simple view.
 */
class HouseDetailFragment : Fragment(),HouseDetailContract.View {

    private lateinit var nameTextView: TextView
    private lateinit var regionTextView: TextView
    private lateinit var coatofarmsTextView: TextView
    private lateinit var foundedTextView: TextView

    override lateinit var presenter: HouseDetailContract.Presenter

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.house_detail_fragment, container, false)
        setHasOptionsMenu(true)
        with(root) {
            nameTextView = findViewById(R.id.info_text_name)
            regionTextView = findViewById(R.id.info_text_region)
            coatofarmsTextView = findViewById(R.id.info_text_coatofarms)
            foundedTextView = findViewById(R.id.info_text_founded)
        }

        return root
    }

    override fun showHouse(house: House) {
        with(house){
            nameTextView.setText(name)
            regionTextView.setText(region)
            coatofarmsTextView.setText(coatOfArms)
            foundedTextView.setText(founded)
        }
    }

    override fun houseNotFound() {
        Snackbar.make(nameTextView,getString(R.string.house_not_found),Snackbar.LENGTH_LONG)
    }

    companion object {

        fun newInstance() = HouseDetailFragment()
    }
}
