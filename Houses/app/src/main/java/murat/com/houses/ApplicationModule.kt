package murat.com.houses

import android.content.Context

import dagger.Module
import dagger.Provides

/**
 * This is a Dagger module. We use this to pass in the Context dependency to the
 * [murat.com.houses.data.source.DataSourceComponent].
 */
@Module
open class ApplicationModule(private val mContext: Context) {

    @Provides
    internal fun provideContext(): Context {
        return mContext
    }
}