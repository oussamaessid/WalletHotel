package com.example.hotelwallet.data.mapper

import com.example.hotelwallet.data.model.ChatMessageDto
import com.example.hotelwallet.domain.model.ChatMessage
import javax.inject.Inject

class ChatMapper @Inject constructor() :
    BaseMapper<ChatMessageDto, ChatMessage> {
    override fun map(from: ChatMessageDto): ChatMessage {
        return ChatMessage(
            desc = from.desc,
            ques = from.ques,
            res = from.res,
            time = from.time
        )
    }

    override fun mapInverse(from: ChatMessage): ChatMessageDto {
        return ChatMessageDto(
            desc = from.desc,
            ques = from.ques,
            res = from.res,
            time = from.time
        )
    }

}