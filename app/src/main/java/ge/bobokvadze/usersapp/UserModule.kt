package ge.bobokvadze.usersapp

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import dagger.hilt.InstallIn
import javax.inject.Singleton
import com.squareup.moshi.Moshi
import java.util.concurrent.TimeUnit
import okhttp3.logging.HttpLoggingInterceptor
import dagger.hilt.components.SingletonComponent
import retrofit2.converter.moshi.MoshiConverterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

@Module
@InstallIn(SingletonComponent::class)
object UserModule {

    @Provides
    fun provideUsersRepository(usersService: UsersService): UsersRepository {
        return UsersRepository.Base(usersService)
    }

    @Provides
    fun provideCommunication(): Communication = Communication.Base()

    @Provides
    @Singleton
    fun provideSaveAndRead(): SaveStateAndRead = SaveStateAndRead.Base()
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
        .callTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .connectTimeout(15, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true).build()

    @Provides
    @Singleton
    fun provideRetrofitClient(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl("https://reqres.in/api/").addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
            )
        ).client(okHttpClient).build()


    @Provides
    @Singleton
    fun provideRetrofitCurrency(retrofitClient: Retrofit): UsersService =
        retrofitClient.create(UsersService::class.java)
}
