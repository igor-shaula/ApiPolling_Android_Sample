package com.igor_shaula.api_polling

import android.app.Application
import android.os.StrictMode
import com.igor_shaula.api_polling.data_layer.data_sources.di.ContextModule
import com.igor_shaula.api_polling.data_layer.data_sources.di.DaggerContextComponent
import com.igor_shaula.api_polling.data_layer.data_sources.di.DaggerFakeApiComponent
import com.igor_shaula.api_polling.data_layer.data_sources.di.DaggerFakeDSComponent
import com.igor_shaula.api_polling.data_layer.data_sources.di.DaggerNetworkDSComponent
import com.igor_shaula.api_polling.data_layer.data_sources.di.DaggerRepositoryComponent
import com.igor_shaula.api_polling.data_layer.data_sources.di.DaggerRetrofitComponent
import com.igor_shaula.api_polling.data_layer.data_sources.di.RepositoryComponent
import timber.log.Timber

//val TIME_TO_SHOW_GOTO_FAKE_DIALOG = booleanPreferencesKey("timeToShowGoToFakeDialog")

//private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "localDataStore")

class ThisApp : Application() {

    override fun onCreate() {
        StrictMode.enableDefaults() // https://developer.android.com/reference/android/os/StrictMode.html
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        repositoryComponent = buildRepositoryComponent()
    }

    private fun buildRepositoryComponent(): RepositoryComponent {
//        val fakeApiComponent = DaggerFakeApiComponent.builder()
//            .fakeApiModule(FakeApiModule())
//            .build()
        val fakeDSComponent = DaggerFakeDSComponent.builder()
            .fakeApiComponent(DaggerFakeApiComponent.create())
            .build()
//        val retrofitComponent = DaggerRetrofitComponent.builder()
//            .retrofitModule(RetrofitModule())
//            .build()
        val networkDSComponent = DaggerNetworkDSComponent.builder()
            .retrofitComponent(DaggerRetrofitComponent.create())
            .build()
        val contextComponent = DaggerContextComponent.builder()
            .contextModule(ContextModule(this))
            .build()
        return DaggerRepositoryComponent.builder()
            .fakeDSComponent(fakeDSComponent)
            .networkDSComponent(networkDSComponent)
            .contextComponent(contextComponent)
//            .repositoryModule(RepositoryModule()) // not needed - works even without this line
            .build()
    }
//    fun readNeedFakeDialogFromLocalPrefs(): Flow<Boolean> =
//        dataStore.data.map { preferences ->
//            preferences[TIME_TO_SHOW_GOTO_FAKE_DIALOG] ?: false
//        }

//    suspend fun saveNeedFakeDialogToLocalPrefs(showFakeDataNextTime: Boolean) {
//        dataStore.edit { preferences ->
//            preferences[TIME_TO_SHOW_GOTO_FAKE_DIALOG] = showFakeDataNextTime
//        }
//    }

    enum class ActiveDataSource {
        NETWORK, FAKE
    }

    companion object {

        private lateinit var repositoryComponent: RepositoryComponent

        fun getRepositoryComponent(): RepositoryComponent = repositoryComponent
    }
}
