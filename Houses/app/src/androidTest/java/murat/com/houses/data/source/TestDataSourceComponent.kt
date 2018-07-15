package murat.com.houses.data.source

import dagger.Component
import murat.com.houses.ApplicationModule
import javax.inject.Singleton

@Singleton
@Component(modules = [(TestDataRepositoryModule::class), (ApplicationModule::class)])
interface TestDataSourceComponent : DataSourceComponent