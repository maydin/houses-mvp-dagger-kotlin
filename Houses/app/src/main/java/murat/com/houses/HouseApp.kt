package murat.com.houses

import android.app.Application
import android.support.annotation.VisibleForTesting
import murat.com.houses.data.source.DaggerDataSourceComponent
import murat.com.houses.data.source.DataSourceComponent


/**
 * The application is made of 3 Dagger components, as follows:<BR></BR>
 * [DataSourceComponent]: the data (it encapsulates server data)<BR></BR>
 * { HouseListComponent}: showing the list of houses<BR></BR>
 * { HouseDetailComponent}: showing the detail of a house<BR></BR>
 */
class HouseApp : Application() {

    @set:VisibleForTesting
    var dataSourceComponent: DataSourceComponent? = null

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            //Stetho.initializeWithDefaults(this);//Stetho can be added in the future for debugging
        }
        dataSourceComponent = DaggerDataSourceComponent.builder()
                .applicationModule(ApplicationModule(applicationContext))
                .build()
    }
}
