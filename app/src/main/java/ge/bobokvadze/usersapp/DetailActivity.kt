package ge.bobokvadze.usersapp

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import androidx.appcompat.app.AppCompatActivity
import ge.bobokvadze.usersapp.databinding.ActivityDetailBinding

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            tvName.text = viewModel.read().firstName
            tvEmail.text = viewModel.read().email
            tvSurname.text = viewModel.read().lastName
        }
    }
}