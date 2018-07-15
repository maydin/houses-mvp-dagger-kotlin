package murat.com.houses.data.source

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * This is used by Dagger to inject the required arguments into the [DataRepository].
 */
@Module
class TestDataRepositoryModule {

    @Provides
    @Singleton
    @Remote
    fun provideRemoteDataSource(): DataSource {
        return FakeRemoteDataSource()
    }
}
