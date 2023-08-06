package uz.otamurod.mvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.otamurod.mvvm.database.AppDatabase
import uz.otamurod.mvvm.networking.ApiService
import uz.otamurod.mvvm.utils.NetworkHelper

class UserViewModelFactory(
    private val appDatabase: AppDatabase,
    private val apiService: ApiService,
    private val networkHelper: NetworkHelper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(appDatabase, apiService, networkHelper) as T
        }
        return throw Exception("Error Creating UserViewModel!")
    }
}