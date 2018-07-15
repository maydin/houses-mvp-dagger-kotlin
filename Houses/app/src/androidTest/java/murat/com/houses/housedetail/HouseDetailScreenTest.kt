package murat.com.houses.housedetail

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import murat.com.houses.ApplicationModule
import murat.com.houses.HouseApp
import murat.com.houses.data.source.DaggerTestDataSourceComponent
import murat.com.houses.data.source.FakeRemoteDataSource
import murat.com.houses.houselist.HouseListActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class HouseDetailScreenTest{

    @Rule
    @JvmField
    var housesActivityTestRule = object :
            ActivityTestRule<HouseListActivity>(HouseListActivity::class.java,false,false) {

        override fun beforeActivityLaunched() {
            val application = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as HouseApp
            application.dataSourceComponent = DaggerTestDataSourceComponent.builder()
                    .applicationModule(ApplicationModule(application))
                    .build()
            super.beforeActivityLaunched()
        }
    }

    @Test
    fun detailScreenShouldDisplayHouseDetails(){
        val intent = Intent().apply {
            putExtra(HouseDetailActivity.EXTRA_HOUSE_URL,FakeRemoteDataSource.HOUSES[0].url )
        }
        housesActivityTestRule.launchActivity(intent)

        Espresso.onView(ViewMatchers.withText(FakeRemoteDataSource.HOUSES[0].name)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}