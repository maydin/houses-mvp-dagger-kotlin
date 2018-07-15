package murat.com.houses.houselist

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.BoundedMatcher
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.view.View
import murat.com.houses.ApplicationModule
import murat.com.houses.HouseApp
import murat.com.houses.R
import murat.com.houses.data.source.DaggerTestDataSourceComponent
import murat.com.houses.data.source.FakeRemoteDataSource
import murat.com.houses.houselist.adapter.HouseListViewHolder
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class HouseListScreenTest{

    @Rule
    @JvmField
    var housesActivityTestRule = object :
            ActivityTestRule<HouseListActivity>(HouseListActivity::class.java) {

        override fun beforeActivityLaunched() {
            setFakeDataSource()
            super.beforeActivityLaunched()
        }
    }

    private fun setFakeDataSource() {
        val application = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as HouseApp
        application.dataSourceComponent = DaggerTestDataSourceComponent.builder()
                .applicationModule(ApplicationModule(application))
                .build()
    }

    @Test
    fun showAllHousesTest() {
        onView(withId(R.id.house_list))
                .check(matches(atPosition(0, hasDescendant(withText(FakeRemoteDataSource.HOUSES[0].name)))))
        onView(withId(R.id.house_list))
                .check(matches(atPosition(1, hasDescendant(withText(FakeRemoteDataSource.HOUSES[1].name)))))

    }

    @Test
    fun clickAnItemShouldOpenDetailScreen(){
        onView(withId(R.id.house_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition<HouseListViewHolder>(0, click()))
        onView(withText(FakeRemoteDataSource.HOUSES[0].name)).check(matches(isDisplayed()))

    }

    fun atPosition(position: Int, itemMatcher: Matcher<View>): Matcher<View> {
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has item at position $position: ")
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(view: RecyclerView): Boolean {
                val viewHolder = view.findViewHolderForAdapterPosition(position)
                        ?: // has no item on such position
                        return false
                return itemMatcher.matches(viewHolder.itemView)
            }
        }
    }

}