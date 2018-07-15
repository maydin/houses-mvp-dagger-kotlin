package murat.com.houses.housedetail

import dagger.Component
import murat.com.houses.data.source.DataSourceComponent
import murat.com.houses.utils.FragmentScoped

/**
 * This is a Dagger component. Refer to [murat.com.houses.HouseApp] for the list of Dagger components
 * used in this application.
 * <P>
</P> */
@FragmentScoped
@Component(dependencies = [(DataSourceComponent::class)], modules = [(HouseDetailPresenterModule::class)])
interface HouseDetailComponent {

    fun inject(activity: HouseDetailActivity)
}
