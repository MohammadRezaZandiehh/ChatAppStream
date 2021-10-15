package com.example.chatappstream.di

import android.content.Context
import com.example.chatappstream.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.getstream.chat.android.client.ChatClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideChatClient(@ApplicationContext context: Context) =                                   ژا//Note : for use String that comes from String.xml , we must have context ., so we get from  " @ApplicationContext context: Context "

        ChatClient.Builder(context.getString(R.string.api_key), context).build()
}