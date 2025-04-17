package com.example.myassssmentapplication.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.dashboard_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupRecyclerView() {
        adapter = EntityAdapter { entity ->
            val intent = Intent(this, DetailsActivity::class.java).apply {
                putExtra("entity", HashMap(entity))
            }
            startActivity(intent)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.dashboardResult.observe(this) { result ->
            when (result) {
                is DashboardResult.Success -> {
                    adapter.submitList(result.entities)
                }
                is DashboardResult.Error -> {
                    Toast.makeText(this, result.message, Toast.LENGTH_LONG).show()
                }
                is DashboardResult.Loading -> {
                    // Loading state is handled by the ViewModel
                }
            }
        }
    }
} 