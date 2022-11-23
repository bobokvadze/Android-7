package ge.bobokvadze.usersapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import ge.bobokvadze.usersapp.model.Data
import dagger.hilt.android.AndroidEntryPoint
import androidx.appcompat.app.AppCompatActivity
import ge.bobokvadze.usersapp.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), Listeners {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = UsersAdapter(this)

        viewModel.collect {
            it.handleView(binding, adapter)
        }
    }

    override fun onClick(item: Data) {
        viewModel.save(item)
        val intent = Intent(this, DetailActivity::class.java)
        startActivity(intent)
    }
}