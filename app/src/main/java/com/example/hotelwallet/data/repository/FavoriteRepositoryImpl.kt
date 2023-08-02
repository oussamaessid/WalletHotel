package com.example.hotelwallet.data.repository

import com.example.hotelwallet.data.mapper.FavoriteMapper
import com.example.hotelwallet.data.source.local.dao.FavoriteDao
import com.example.hotelwallet.domain.model.SubMenu
import com.example.hotelwallet.domain.repository.FavoriteRepository
import com.example.hotelwallet.utility.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao,
    private val favoriteMapper: FavoriteMapper,
) : FavoriteRepository {
    override suspend fun insertProduct(product: SubMenu): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading)
            val addProductResponse = favoriteDao.addProduct(favoriteMapper.mapInverse(product))
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
            val deleteProductResponse =
                favoriteDao.deleteProduct(favoriteMapper.mapInverse(product))
            emit(Resource.Success(deleteProductResponse))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }

    override suspend fun getFavoriteProductList(): Flow<Resource<List<SubMenu>?>> = flow {
        try {
            emit(Resource.Loading)
            val productsResponse =
                withContext(Dispatchers.IO) { favoriteDao.getAllFavorites()?.let {favoriteMapper.mapList(it)} }
            emit(Resource.Success(productsResponse))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}