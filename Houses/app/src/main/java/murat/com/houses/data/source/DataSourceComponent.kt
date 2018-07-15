package murat.com.houses.data.source

import dagger.Component
import murat.com.houses.ApplicationModule
import javax.inject.Singleton

/**
 * This is a Dagger component. Refer to [murat.com.houses.HouseApp] for the list of Dagger components
 * used in this application.
 * <P>
</P> */
@Singleton
@Component(modules = [(DataRepositoryModule::class), (ApplicationModule::class)])
interface DataSourceComponent {

    val dataRepository: DataRepository
}
