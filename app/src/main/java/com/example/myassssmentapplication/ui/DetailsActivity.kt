package com.example.myassssmentapplication.ui

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myassssmentapplication.R
import com.example.myassssmentapplication.databinding.ActivityDetailsBinding
import kotlin.math.roundToInt

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        @Suppress("UNCHECKED_CAST")
        val entity = intent.getSerializableExtra("entity") as? HashMap<String, Any>
        if (entity != null) {
            displayEntityDetails(entity)
        } else {
            finish()
        }
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

    private fun displayEntityDetails(entity: Map<String, Any>) {
        // Clear any existing views
        binding.detailsContainer.removeAllViews()
        binding.additionalInfo.text = ""

        // Convert to LinkedHashMap to preserve order
        val orderedEntity = if (entity is LinkedHashMap) {
            entity
        } else {
            LinkedHashMap(entity)
        }

        // Get the first two fields (excluding description)
        val firstTwoFields = orderedEntity.entries
            .filter { it.key != "description" }
            .take(2)
            .toList()

        // Get remaining fields (excluding description)
        val remainingFields = orderedEntity.entries
            .filter { it.key != "description" }
            .drop(2)

        // Get description separately
        val description = orderedEntity["description"]

        // Display first two fields in the main card
        firstTwoFields.forEach { (key, value) ->
            addFieldToContainer(binding.detailsContainer, key, value, true)
        }

        // Add a divider after the first two fields
        if (firstTwoFields.isNotEmpty()) {
            addDivider(binding.detailsContainer)
        }

        // Display remaining fields in the main card
        remainingFields.forEach { (key, value) ->
            addFieldToContainer(binding.detailsContainer, key, value, false)
        }

        // Display description in the additional information section
        if (description != null) {
            binding.additionalInfo.text = description.toString()
        }
    }

    private fun addFieldToContainer(container: LinearLayout, key: String, value: Any, isMainField: Boolean) {
        // Create a container for the field
        val fieldContainer = View.inflate(this, android.R.layout.simple_list_item_2, null)
        
        // Get the TextViews from the layout
        val fieldNameView = fieldContainer.findViewById<TextView>(android.R.id.text1)
        val fieldValueView = fieldContainer.findViewById<TextView>(android.R.id.text2)

        // Style the field name
        fieldNameView.apply {
            text = formatFieldName(key)
            textSize = 14f
            setTextColor(0xFF718096.toInt()) // Gray color
            alpha = 0.8f
        }

        // Style the field value
        fieldValueView.apply {
            text = formatFieldValue(value)
            if (isMainField) {
                // First two fields styling
                textSize = if (container.childCount == 0) 28f else 20f
                setTextColor(0xFF2D3748.toInt()) // Dark color
                if (container.childCount == 0) {
                    setTypeface(null, Typeface.BOLD)
                    setPadding(0, 8, 0, 24)
                } else {
                    alpha = 0.9f
                    setPadding(0, 4, 0, 16)
                }
            } else {
                // Other fields styling
                textSize = 16f
                setTextColor(0xFF2D3748.toInt()) // Dark color
                setPadding(0, 4, 0, 4)
            }
        }

        container.addView(fieldContainer)
    }

    private fun addDivider(container: LinearLayout) {
        View(this).apply {
            layoutParams = android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                1
            )
            setBackgroundColor(0xFFE2E8F0.toInt()) // Lighter separator color
            alpha = 0.5f
            container.addView(this)
        }
    }

    private fun formatFieldName(key: String): String {
        // Convert camelCase to Title Case with spaces
        return key.split("(?=\\p{Upper})".toRegex())
            .joinToString(" ")
            .uppercase() // Make field names uppercase for modern look
    }

    private fun formatFieldValue(value: Any): String {
        return when (value) {
            is Double -> value.roundToInt().toString()
            is Float -> value.roundToInt().toString()
            else -> value.toString()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
} 