package com.example.hotelwallet.data.repository

import com.example.hotelwallet.data.mapper.FavoriteMapper
import com.example.hotelwallet.data.mapper.MenuMapper
import com.example.hotelwallet.data.mapper.OrderMapper
import com.example.hotelwallet.data.mapper.SubMenuMapper
import com.example.hotelwallet.data.source.local.dao.FavoriteDao
import com.example.hotelwallet.data.source.local.dao.OrderDao
import com.example.hotelwallet.data.source.local.dao.ProductDao
import com.example.hotelwallet.data.source.remote.Api
import com.example.hotelwallet.domain.model.Menu
import com.example.hotelwallet.domain.model.Order
import com.example.hotelwallet.domain.model.SubMenu
import com.example.hotelwallet.domain.repository.MenuRepository
import com.example.hotelwallet.utility.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MenuRepositoryImpl @Inject constructor(
    private val api: Api,
    private val productDao: ProductDao,
    private val favoriteDao: FavoriteDao,
    private val orderDao: OrderDao,
    private val menuMapper: MenuMapper,
    private val subMenuMapper: SubMenuMapper,
    private val favoriteMapper: FavoriteMapper,
    private val orderMapper: OrderMapper,
) : MenuRepository {

    override suspend fun getMenuByServiceId(serviceId: Int): Flow<Resource<List<Menu>>> = flow {
        try {
            emit(Resource.Loading)
            val categoriesResponse = menuMapper.mapList(
                api.getMenuByServiceId(serviceId).menu
            )
            emit(Resource.Success(categoriesResponse))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }

    override suspend fun getSubMenuByMenuId(menuId: Int): Flow<Resource<List<SubMenu>>> = flow {
        try {
            emit(Resource.Loading)
            coroutineScope {
                val categoriesResponse = async {
                    subMenuMapper.mapList(
                        api.getMenuItems("$menuId").plat
                    )
                }
                val favoriteResponse =
                    async(Dispatchers.IO) {
                        favoriteDao.getAllFavorites()?.let { favoriteMapper.mapList(it) }
                    }

                val subMenus = categoriesResponse.await()
                val favorites = favoriteResponse.await()

                favorites?.forEach { result ->
                    val subMenu = subMenus.find { it.id == result.id }
                    subMenu?.isFavorite = true
                }
                emit(Resource.Success(categoriesResponse.getCompleted()))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }

    override suspend fun insertProduct(product: SubMenu): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading)
            val addProductResponse = productDao.addProduct(subMenuMapper.mapInverse(product))
            emit(Resource.Success(addProductResponse))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }

    override suspend fun deleteProduct(product: SubMenu): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading)
            val deleteProductResponse = productDao.deleteProduct(subMenuMapper.mapInverse(product))
            emit(Resource.Success(deleteProductResponse))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }

    override suspend fun getCartProductList(): Flow<Resource<List<SubMenu>>> = flow {
        try {
            emit(Resource.Loading)
            val productsResponse =
                withContext(Dispatchers.IO) { subMenuMapper.mapList(productDao.getAllProducts()) }
            emit(Resource.Success(productsResponse))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }

    override suspend fun addProductToOrder(order: Order): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading)
            order.platList.map {
                withContext(Dispatchers.IO) {productDao.addProductToOrder(it.id)}
            }
            val responseOrder = orderDao.insertOrder(orderMapper.mapInverse(order))
            emit(Resource.Success(responseOrder))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}
