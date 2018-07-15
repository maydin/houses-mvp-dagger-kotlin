package murat.com.houses.houselist

import dagger.Module
import dagger.Provides

/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * [HouseListPresenter].
 */
@Module
open class HouseListPresenterModule(private val mView: HouseListContract.View) {

    @Provides
    fun provideHouseContractView(): HouseListContract.View {
        return mView
    }

}