package com.example.unsplashapicompose.ui

import androidx.lifecycle.ViewModel
import com.example.unsplashapicompose.managers.ConnectionManager
import javax.inject.Inject

abstract class BaseViewModel<T> : ViewModel() {

    @Inject
    protected lateinit var connectionManager: ConnectionManager

    abstract fun handleEvent(event: T)

}