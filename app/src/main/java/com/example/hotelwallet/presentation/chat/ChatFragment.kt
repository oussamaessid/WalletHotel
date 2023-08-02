package com.example.hotelwallet.presentation.chat

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.hotelwallet.R
import com.example.hotelwallet.databinding.FragmentChatBinding
import com.example.hotelwallet.domain.model.ChatMessage
import com.example.hotelwallet.domain.model.ToolbarConfiguration
import com.example.hotelwallet.presentation.misc.BaseFragment
import com.example.hotelwallet.utility.Resource

/**
 * A simple [Fragment] subclass.
 * Use the [ChatFragment] factory method to
 * create an instance of this fragment.
 */
class ChatFragment : BaseFragment<FragmentChatBinding>(
    FragmentChatBinding::inflate,
    toolbarConfiguration = ToolbarConfiguration(
        visibility = View.VISIBLE,
        btnBackVisibility = View.VISIBLE,
        title = R.string.txt_title_chat
    )
) {
    private val chatViewModel by activityViewModels<ChatViewModel>()

    private lateinit var chatAdapter: ChatMessageAdapter
    private var chatList = mutableListOf<ChatMessage>()
    private lateinit var message : String
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        chatList.add(ChatMessage(desc = "success", ques = "Hello Question", res = "Hello response", time = "2023-06-16 21:21"))
//        chatAdapter = ChatMessageAdapter(chatList)

        binding.imgSendMsg.setOnClickListener {
            message = binding.editNewMessage.text.toString()
            chatViewModel.getChatMessage(message)
            binding.editNewMessage.text.clear()
        }

        observeChat()
        chatAdapter = ChatMessageAdapter(chatList)
        binding.recyclerViewChat.setHasFixedSize(true)
        binding.recyclerViewChat.isNestedScrollingEnabled = false
        binding.recyclerViewChat.adapter = chatAdapter
        setBottomNavigation(false)

    }

    private fun observeChat() {
        lifecycleScope.launchWhenStarted {
            chatViewModel.stateChat.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> {
                        setLoading(true)
                    }
                    is Resource.Success -> {
                        it.data.apply {

                            chatList.add(this)
                            chatAdapter.notifyDataSetChanged()

                        }
                        setLoading(false)
                    }
                    is Resource.Error -> {
                        setLoading(false)
                    }
                }
            }
        }
    }

}