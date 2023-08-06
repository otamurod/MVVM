package uz.otamurod.mvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.otamurod.mvvm.database.AppDatabase
import uz.otamurod.mvvm.database.entity.UserEntity
import uz.otamurod.mvvm.mapper.mapToUserEntity
import uz.otamurod.mvvm.networking.ApiService
import uz.otamurod.mvvm.repository.UserRepository
import uz.otamurod.mvvm.utils.NetworkHelper

class UserViewModel(
    appDatabase: AppDatabase,
    apiService: ApiService,
    private val networkHelper: NetworkHelper
) : ViewModel() {
    private val userRepository = UserRepository(apiService, appDatabase.userDao())
    private val usersFromDatabase = userRepository.getUsersFromDatabase()

    private val _stateFlow = MutableStateFlow<Resource<List<UserEntity>>>(Resource.Loading())
    val stateFlow = _stateFlow as StateFlow<Resource<List<UserEntity>>>

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                userRepository.getUsersFromApi()
                    .catch {
                        if (usersFromDatabase.isNotEmpty()) {
                            _stateFlow.emit(Resource.Success(usersFromDatabase))
                        } else {
                            _stateFlow.emit(Resource.Error(it))
                        }
                    }
                    .collectLatest {
                        val list = ArrayList<UserEntity>()
                        it.forEach {
                            list.add(it.mapToUserEntity(it))
                        }
                        _stateFlow.emit(Resource.Success(list))
                        userRepository.addUsersToDatabase(list)
                    }
            } else {
                if (usersFromDatabase.isNotEmpty()) {
                    _stateFlow.emit(Resource.Success(usersFromDatabase))
                } else {
                    _stateFlow.emit(Resource.Error(Throwable("Internet is Disconnected!")))
                }
            }
        }
    }
}