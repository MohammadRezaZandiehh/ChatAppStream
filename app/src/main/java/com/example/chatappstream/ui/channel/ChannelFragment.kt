package com.example.chatappstream.ui.channel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.chatappstream.databinding.FragmentChannelBinding
import com.plcoding.streamchatapp.ui.BindingFragment
import dagger.hilt.android.AndroidEntryPoint
import io.getstream.chat.android.client.models.Filters
import io.getstream.chat.android.ui.channel.list.header.viewmodel.ChannelListHeaderViewModel
import io.getstream.chat.android.ui.channel.list.header.viewmodel.bindView
import io.getstream.chat.android.ui.channel.list.viewmodel.ChannelListViewModel
import io.getstream.chat.android.ui.channel.list.viewmodel.bindView
import io.getstream.chat.android.ui.channel.list.viewmodel.factory.ChannelListViewModelFactory

@AndroidEntryPoint                                                                                  //access to inject dependency --> in Android Component some Activity and Fragment
class ChannelFragment:BindingFragment<FragmentChannelBinding>() {

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentChannelBinding::inflate

    private val viewModel: ChannelViewModel by activityViewModels()                                 // when we clicked on createButton in dialogFragment , dialog is destroyed . but the viewModel actually not destroyed , because it's bound to the activity (min:8)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = viewModel.getUser()
        if(user == null) {
            findNavController().popBackStack()
            return
        }

        val factory = ChannelListViewModelFactory(
            filter = Filters.and(
                Filters.eq("type", "messaging"),                                      // we have nome another value for example 'live channel' , 'game channel' and set.. --> but here we want just "message channel" .
 //                Filters.eq("members", listOf())                                                  <-- for  example for have group chats:
            ),
            sort = ChannelListViewModel.DEFAULT_SORT,
            limit = 30
        )
        val channelListViewModel: ChannelListViewModel by viewModels { factory }

// viewModel for Header :
        val channelListHeaderViewModel: ChannelListHeaderViewModel by viewModels()
        channelListViewModel.bindView(binding.channelListView, viewLifecycleOwner)
        channelListHeaderViewModel.bindView(binding.channelListHeaderView, viewLifecycleOwner)

        binding.channelListHeaderView.setOnUserAvatarClickListener {
            viewModel.logOut()
            findNavController().popBackStack()
        }
    }
}