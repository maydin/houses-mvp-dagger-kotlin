package murat.com.houses.housedetail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import murat.com.houses.HouseApp
import murat.com.houses.R
import murat.com.houses.utils.ActivityUtils.replaceFragmentInActivity
import murat.com.houses.utils.ActivityUtils.setupActionBar
import javax.inject.Inject

class HouseDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var houseDetailPresenter: HouseDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.houselist_activity)

        // Set up the toolbar.
        setupActionBar(R.id.toolbar) {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        // Get the requested house url
        val houseUrl = intent.getStringExtra(EXTRA_HOUSE_URL)

        val houseDetailFragment = supportFragmentManager
                .findFragmentById(R.id.contentFrame) as HouseDetailFragment? ?:
        HouseDetailFragment.newInstance().also {
            replaceFragmentInActivity(it, R.id.contentFrame)
        }

        // Create the presenter
        DaggerHouseDetailComponent.builder()
                .dataSourceComponent((application as HouseApp).dataSourceComponent)
                .houseDetailPresenterModule(HouseDetailPresenterModule(houseDetailFragment,houseUrl)).build()
                .inject(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_HOUSE_URL = "HOUSE_URL"
    }

}
