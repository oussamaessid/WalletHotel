package com.example.hotelwallet.data.mapper

import com.example.hotelwallet.data.model.UserDto
import com.example.hotelwallet.domain.model.User
import javax.inject.Inject

class UserMapper @Inject constructor() :
    BaseMapper<UserDto, User> {
    override fun map(from: UserDto): User {
        return User(
            id = from.id,
            lastName = from.lastName,
            firstName = from.firstName,
            photo = from.photo,
            email = from.email,
            role = from.role,
            status = from.status,
            balance = from.balance,
            hotelId = from.hotelId,
            serviceId = from.serviceId,
            createdAt = from.createdAt,
            updatedAt = from.updatedAt
        )
    }

    override fun mapInverse(from: User): UserDto {
        return UserDto(
            id = from.id,
            lastName = from.lastName,
            firstName = from.firstName,
            photo = from.photo,
            email = from.email,
            role = from.role,
            status = from.status,
            balance = from.balance,
            hotelId = from.hotelId,
            serviceId = from.serviceId,
            createdAt = from.createdAt,
            updatedAt = from.updatedAt
        )
    }
}