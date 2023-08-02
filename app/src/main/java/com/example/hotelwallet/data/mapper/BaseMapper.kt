package com.example.hotelwallet.data.mapper




interface BaseMapper<FROM, TO> {
    fun map(from: FROM): TO

    fun mapInverse(from: TO): FROM

    fun mapList(from: List<FROM>): List<TO> {
        return ArrayList<TO>().also { models ->
            from.forEach { item ->
                models.add(map(item))
            }
        }
    }

    fun mapListInverse(from: List<TO>): List<FROM> {
        return ArrayList<FROM>().also { models ->
            from.forEach { item ->
                models.add(mapInverse(item))
            }
        }
    }
}