package com.example.tsurami.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tsurami.FeelingDatumListAdapter
import com.example.tsurami.R
import com.example.tsurami.TsuramiApplication
import com.example.tsurami.db.datum.FeelingDatum
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.tsurami.viewmodel.*

class MainActivity : AppCompatActivity() {
    private val feelingDatumViewModel: FeelingDatumViewModel by viewModels {
        FeelingDatumViewModelFactory((application as TsuramiApplication).repository)
    }

    private val arl: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult? ->
        if (result?.resultCode == Activity.RESULT_OK) {
            result.data?.let { data: Intent ->
                val feelingDatum = data.getSerializableExtra(NewFeelingActivity.EXTRA_REPLY_FEELING_DATUM) as FeelingDatum
                feelingDatumViewModel.save(feelingDatum)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = FeelingDatumListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        feelingDatumViewModel.allFeelingData.observe(this) { words ->
            words.let { adapter.submitList(it) }
        }

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewFeelingActivity::class.java)
            arl.launch(intent)
        }
    }
}