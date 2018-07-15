package murat.com.houses.data.source

import dagger.Binds
import dagger.Module
import murat.com.houses.data.source.remote.RemoteDataSource
import javax.inject.Singleton

/**
 * This is used by Dagger to inject the required arguments into the [DataRepository].
 */
@Module
abstract class DataRepositoryModule {

    @Singleton
    @Binds
    @Remote
    internal abstract fun provideRemoteDataSource(dataSource: RemoteDataSource): DataSource
}
