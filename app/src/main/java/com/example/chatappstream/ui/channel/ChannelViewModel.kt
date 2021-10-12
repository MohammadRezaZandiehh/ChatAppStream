package com.example.chatappstream.ui.channel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.models.User
import javax.inject.Inject

@HiltViewModel
class ChannelViewModel @Inject constructor(
    private var client: ChatClient
) : ViewModel() {

    fun logOut() {
        client.disconnect()
    }

    fun getUser(): User? {                                                                          //access to current user
        return client.getCurrentUser()
    }
}