package com.appsvit.productlist_app.di

import android.app.Application
import androidx.room.Room
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import com.appsvit.productlist_app.data.local.ProductDatabase
import com.appsvit.productlist_app.data.remote.ProductService
import com.appsvit.productlist_app.data.repository.ProductRepository
import com.appsvit.productlist_app.data.repository.impl.ProductRepositoryImpl
import com.appsvit.productlist_app.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)  // Set connection timeout
            .writeTimeout(30, TimeUnit.SECONDS)    // Set write timeout
            .readTimeout(30, TimeUnit.SECONDS)     // Set read timeout
            .build()
    }

    @Provides
    @Singleton
    fun providesProductApi(okHttpClient: OkHttpClient): ProductService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient) // Use the custom OkHttpClient
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductService::class.java)
    }

    @Provides
    @Singleton
    fun providesProductDatabase(app: Application): ProductDatabase {
        return Room.databaseBuilder(
            app.applicationContext,
            ProductDatabase::class.java,
            Constants.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providesProductRepository(
        productService: ProductService,
        db: ProductDatabase
    ): ProductRepository =
        ProductRepositoryImpl(productService, db)
}