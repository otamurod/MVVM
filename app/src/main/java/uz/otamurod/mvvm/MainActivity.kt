package uz.otamurod.mvvm

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.otamurod.mvvm.adapter.UserAdapter
import uz.otamurod.mvvm.database.AppDatabase
import uz.otamurod.mvvm.databinding.ActivityMainBinding
import uz.otamurod.mvvm.networking.ApiClient
import uz.otamurod.mvvm.utils.NetworkHelper
import uz.otamurod.mvvm.viewmodel.Resource
import uz.otamurod.mvvm.viewmodel.UserViewModel
import uz.otamurod.mvvm.viewmodel.UserViewModelFactory
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            UserViewModelFactory(
                AppDatabase.getInstance(this),
                ApiClient.apiService,
                NetworkHelper(this)
            )
        )[UserViewModel::class.java]

        launch {
            viewModel.stateFlow
                .collect {
                    when (it) {
                        is Resource.Error -> {
                            Toast.makeText(this@MainActivity, "${it.e.message}", Toast.LENGTH_SHORT)
                                .show()
                            Log.d("TAG", "onCreate: ${it.e.message}")
                        }
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            Log.d("TAG", "onCreate: ${it.data}")
                            userAdapter = UserAdapter(this@MainActivity, it.data)
                            binding.rv.adapter = userAdapter
                        }
                    }
                }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
}