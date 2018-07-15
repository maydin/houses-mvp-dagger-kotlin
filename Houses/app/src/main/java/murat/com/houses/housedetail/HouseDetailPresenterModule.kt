package murat.com.houses.housedetail

import dagger.Module
import dagger.Provides

/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * [HouseListPresenter].
 */
@Module
internal class HouseDetailPresenterModule(private val mView: HouseDetailContract.View,private val houseUrl:String) {

    @Provides
    fun provideHouseUrl():String{
        return houseUrl
    }

    @Provides
    fun provideHouseContractView(): HouseDetailContract.View {
        return mView
    }

}