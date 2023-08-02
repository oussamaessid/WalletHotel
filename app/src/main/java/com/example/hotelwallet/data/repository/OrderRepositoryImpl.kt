package com.example.hotelwallet.data.repository

import com.example.hotelwallet.data.mapper.OrderMapper
import com.example.hotelwallet.data.source.local.dao.OrderDao
import com.example.hotelwallet.domain.model.Order
import com.example.hotelwallet.domain.repository.OrderRepository
import com.example.hotelwallet.utility.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderDao: OrderDao,
    private val orderMapper: OrderMapper,
) : OrderRepository {

    override suspend fun getOrderList(): Flow<Resource<List<Order>?>> = flow {
        try {
            emit(Resource.Loading)
            val orderResponse =
                withContext(Dispatchers.IO) {
                    orderDao.getAllOrder().let { orderMapper.mapList(it) }
                }
            emit(Resource.Success(orderResponse))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}