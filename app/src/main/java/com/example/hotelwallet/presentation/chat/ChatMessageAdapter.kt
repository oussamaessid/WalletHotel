package com.example.hotelwallet.presentation.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelwallet.R
import com.example.hotelwallet.databinding.RowItemChatBinding
import com.example.hotelwallet.domain.model.ChatMessage

class ChatMessageAdapter(
    private val messageList: List<ChatMessage>
) : RecyclerView.Adapter<ChatMessageAdapter.ChatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = RowItemChatBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        with(holder) {
            with(messageList[position]) {
                binding.txtUserNameYour.text = itemView.context.getString(R.string.txt_you)
                binding.txtDateYour.text = time
                binding.txtMsgYour.text = ques

                binding.txtUserNameResponse.text = itemView.context.getString(R.string.txt_hotel)
                binding.txtDateResponse.text = time
                binding.txtMsgResponse.text = res
            }
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    inner class ChatViewHolder(val binding: RowItemChatBinding) :
        RecyclerView.ViewHolder(binding.root)
}