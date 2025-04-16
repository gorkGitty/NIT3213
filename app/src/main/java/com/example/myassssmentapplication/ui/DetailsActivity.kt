package com.example.myassssmentapplication.ui

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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

    private fun displayEntityDetails(entity: Map<String, Any>) {
        // Clear any existing views
        binding.detailsContainer.removeAllViews()

        // Add each field as a separate TextView
        entity.forEach { (key, value) ->
            // Create a container for each field
            val fieldContainer = View.inflate(this, android.R.layout.simple_list_item_2, null)
            
            // Get the TextViews from the layout
            val fieldNameView = fieldContainer.findViewById<TextView>(android.R.id.text1)
            val fieldValueView = fieldContainer.findViewById<TextView>(android.R.id.text2)

            // Style the field name
            fieldNameView.apply {
                text = formatFieldName(key)
                textSize = 14f
                setTextColor(0xFF636E72.toInt()) // Gray color
                alpha = 0.8f
            }

            // Style the field value
            fieldValueView.apply {
                text = formatFieldValue(value)
                if (binding.detailsContainer.childCount == 0) {
                    // First field (title)
                    textSize = 28f
                    setTextColor(0xFF2D3436.toInt()) // Dark color
                    setTypeface(null, Typeface.BOLD)
                    setPadding(0, 8, 0, 24)
                } else if (binding.detailsContainer.childCount == 1) {
                    // Second field (subtitle)
                    textSize = 20f
                    setTextColor(0xFF2D3436.toInt()) // Dark color
                    alpha = 0.9f
                    setPadding(0, 4, 0, 16)
                } else {
                    // Other fields
                    textSize = 16f
                    setTextColor(0xFF2D3436.toInt()) // Dark color
                    setPadding(0, 4, 0, 4)
                }
            }

            binding.detailsContainer.addView(fieldContainer)

            // Add separator after each field except the last one
            if (key != entity.keys.last()) {
                View(this).apply {
                    layoutParams = android.widget.LinearLayout.LayoutParams(
                        android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                        1
                    )
                    setBackgroundColor(0xFFEEEEEE.toInt()) // Lighter separator color
                    alpha = 0.5f
                    binding.detailsContainer.addView(this)
                }
            }
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