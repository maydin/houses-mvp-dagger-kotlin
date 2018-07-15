package murat.com.houses.houselist

import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import murat.com.houses.HouseApp
import murat.com.houses.R
import murat.com.houses.utils.ActivityUtils.replaceFragmentInActivity
import javax.inject.Inject

class HouseListActivity : AppCompatActivity() {

    @Inject
    lateinit var houseListPresenter: HouseListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.houselist_activity)

        var houseListFragment: Fragment = supportFragmentManager
                .findFragmentById(R.id.contentFrame) as HouseListFragment? ?:
        HouseListFragment.newInstance()

        // Create the presenter
        DaggerHouseListComponent.builder()
                .dataSourceComponent((application as HouseApp).dataSourceComponent)
                .houseListPresenterModule(HouseListPresenterModule(houseListFragment as HouseListFragment)).build()
                .inject(this)

        replaceFragmentInActivity(houseListFragment, R.id.contentFrame)
    }

    @VisibleForTesting
    fun setComponent(houseListComponent: DaggerHouseListComponent){
        houseListComponent.inject(this)
    }
}
