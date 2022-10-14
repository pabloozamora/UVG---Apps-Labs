package com.zamora.lab13.di

import android.content.Context
import androidx.room.Room
import com.zamora.lab13.datasource.api.RickAndMortyAPI
import com.zamora.lab13.datasource.localsource.CharacterDao
import com.zamora.lab13.datasource.localsource.Database
import com.zamora.lab13.datasource.model.Character
import com.zamora.lab13.datasource.repository.CharacterRepository
import com.zamora.lab13.datasource.repository.CharacterRepositoryImpl
import com.zamora.lab13.datasource.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor() : HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return logging
    }

    @Provides
    @Singleton
    fun provideHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun providepi(client: OkHttpClient): RickAndMortyAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.RICK_AND_MORTY_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(RickAndMortyAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Context): Database{
        return Room.databaseBuilder(
            context,
            Database::class.java,
            "Database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCharacterDao(database: Database): CharacterDao{
        return database.characterDao()
    }

    @Provides
    @Singleton
    fun provideCharacterRepository(api: RickAndMortyAPI, dao: CharacterDao): CharacterRepository {
        return CharacterRepositoryImpl(
            api = api,
            characterDao = dao)
        )
    }
}