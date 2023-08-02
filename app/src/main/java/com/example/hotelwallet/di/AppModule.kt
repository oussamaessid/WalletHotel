package com.example.hotelwallet.di


import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Room
import com.example.hotelwallet.data.mapper.*
import com.example.hotelwallet.data.repository.*
import com.example.hotelwallet.data.source.local.AppDataStoreManager
import com.example.hotelwallet.data.source.local.HotelWalletDatabase
import com.example.hotelwallet.data.source.local.dao.OrderDao
import com.example.hotelwallet.data.source.local.dao.FavoriteDao
import com.example.hotelwallet.data.source.local.dao.ProductDao
import com.example.hotelwallet.data.source.remote.Api
import com.example.hotelwallet.data.source.remote.HotelApi
import com.example.hotelwallet.domain.repository.*
import com.example.hotelwallet.utility.BASE_URL
import com.example.hotelwallet.utility.BASE_URL1
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideHoteloApi(): HotelApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(HotelApi::class.java)
    }

    @Singleton
    @Provides
    fun provideApi(): Api {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL1)
            .build()
            .create(Api::class.java)
    }

    @SuppressLint("RestrictedApi")
    @Provides
    @Singleton
    internal fun provideDatabaseApp(@ApplicationContext application: Context): HotelWalletDatabase {
        return Room.databaseBuilder(
            application,
            HotelWalletDatabase::class.java,
            HotelWalletDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideProductDao(appDatabase: HotelWalletDatabase): ProductDao {
        return appDatabase.productDao()
    }

    @Provides
    fun provideFavoriteDao(appDatabase: HotelWalletDatabase): FavoriteDao {
        return appDatabase.favoriteDao()
    }

    @Provides
    fun provideOrderDao(appDatabase: HotelWalletDatabase): OrderDao {
        return appDatabase.orderDao()
    }

    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext appContext: Context): AppDataStore =
        AppDataStoreManager(appContext)

    @Singleton
    @Provides
    fun provideMenuRepository(
        api: Api,
        menuMapper: MenuMapper,
        subMenuMapper: SubMenuMapper,
        favoriteMapper: FavoriteMapper,
        orderMapper: OrderMapper,
        productDao: ProductDao,
        favoriteDao: FavoriteDao,
        orderDao: OrderDao,
    ): MenuRepository =
        MenuRepositoryImpl(
            api = api,
            menuMapper = menuMapper,
            productDao = productDao,
            favoriteDao = favoriteDao,
            orderDao = orderDao,
            subMenuMapper = subMenuMapper,
            favoriteMapper = favoriteMapper,
            orderMapper = orderMapper,
        )

    @Singleton
    @Provides
    fun provideFavoriteRepository(
        favoriteMapper: FavoriteMapper,
        favoriteDao: FavoriteDao
    ): FavoriteRepository =
        FavoriteRepositoryImpl(favoriteDao = favoriteDao, favoriteMapper = favoriteMapper)

    @Singleton
    @Provides
    fun provideOrderRepository(
        orderMapper: OrderMapper,
        orderDao: OrderDao
    ): OrderRepository =
        OrderRepositoryImpl(orderDao = orderDao, orderMapper = orderMapper)

    @Singleton
    @Provides
    fun provideServiceRepository(
        api: Api,
        serviceMapper: ServiceMapper,
    ): ServiceRepository =
        ServicesRepositoryImpl(api = api, serviceMapper = serviceMapper)

    @Singleton
    @Provides
    fun provideGymRepository(
        api: Api,
        planMapper: PlanMapper,
        imageMapper: ImageMapper
    ): GymRepository =
        GymRepositoryImpl(api = api, planMapper = planMapper, imageMapper = imageMapper)

    @Singleton
    @Provides
    fun provideLoginRepository(
        api: Api,
        userMapper: UserMapper,
        messageMapper: MessageMapper,
        appDataStore: AppDataStore
    ): AuthenticationRepository =
        AuthenticationRepositoryImpl(
            api = api,
            userMapper = userMapper,
            messageMapper = messageMapper,
            dataStore = appDataStore
        )

    @Singleton
    @Provides
    fun provideProfileRepository(
        api: Api,
        userMapper: UserMapper,
        appDataStore: AppDataStore
    ): ProfileRepository =
        ProfileRepositoryImpl(api = api, userMapper = userMapper, dataStore = appDataStore)

    @Singleton
    @Provides
    fun provideLanguageRepository(
        appDataStore: AppDataStore
    ): LanguageRepository =
        LanguageRepositoryImpl(dataStore = appDataStore)

    @Singleton
    @Provides
    fun provideChatRepository(
        api: HotelApi,
        chatMapper: ChatMapper
        ): ChatRepository =
        ChatRepositoryImpl(api = api, chatMapper = chatMapper)
}