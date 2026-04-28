package com.example.alkewallet.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alkewallet.data.api.UserDao
import com.example.alkewallet.data.network.UserEntity
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userDao: UserDao
) : ViewModel() {

    private val _user = MutableLiveData<UserEntity>()
    val user: LiveData<UserEntity> get() = _user

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun saveUser(userEntity: UserEntity) {
        viewModelScope.launch {
            try {
                userDao.insertUser(userEntity)
            } catch (e: Exception) {
                _errorMessage.value = "No fue posible guardar el perfil"
            }
        }
    }

    fun loadUser(id: Int) {
        viewModelScope.launch {
            try {
                val result = userDao.getUserById(id)

                if (result != null) {
                    _user.value = result
                } else {
                    _errorMessage.value = "No se encontró el perfil del usuario"
                }
            } catch (e: Exception) {
                _errorMessage.value = "No fue posible cargar el perfil"
            }
        }
    }
}