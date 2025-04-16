package com.example.myassssmentapplication.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myassssmentapplication.R
import com.example.myassssmentapplication.api.RetrofitClient
import com.example.myassssmentapplication.databinding.ActivityDashboardBinding
import com.example.myassssmentapplication.model.Artwork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private val viewModel: DashboardViewModel by viewModel()
    private lateinit var adapter: EntityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        val keypass = intent.getStringExtra("keypass")
        if (keypass != null) {
            // Capitalize first letter and add spaces before capitals
            val formattedTitle = keypass.split("(?<=.)(?=\\p{Upper})".toRegex())
                .joinToString(" ")
                .replaceFirstChar { it.uppercase() }
            supportActionBar?.title = formattedTitle
            
            viewModel.loadArtworks(keypass)
        } else {
            Toast.makeText(this, "Error: Missing keypass", Toast.LENGTH_SHORT).show()
            finish()
        }

        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        adapter = EntityAdapter { entity ->
            val intent = Intent(this, DetailsActivity::class.java).apply {
                putExtra("entity", HashMap(entity))
            }
            startActivity(intent)
        }
        binding.artworksRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.artworksRecyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.dashboardResult.observe(this) { result ->
            when (result) {
                is DashboardResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.errorTextView.visibility = View.GONE
                    adapter.submitList(result.entities)
                }
                is DashboardResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.errorTextView.visibility = View.VISIBLE
                    binding.errorTextView.text = result.message
                }
                is DashboardResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.errorTextView.visibility = View.GONE
                }
            }
        }
    }
} 