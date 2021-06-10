package com.example.tsurami

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private val repository: FeelingRepository = FeelingRepository()
    private val feelingViewModel: FeelingViewModel by viewModels {
        FeelingViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("test", "onCreate()")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = FeelingListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        feelingViewModel.allFeelings.observe(this) { feelings ->
            feelings.let { adapter.submitList(it) }
        }

        val arl: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult? ->
            Log.d("test", "arl ActivityResult")
            if (result?.resultCode == Activity.RESULT_OK) {
                result.data?.let { data: Intent ->
                    Log.d("test", "RESULT_OK")
                    Log.d("test", "[${data.hashCode()}] ${result.data}")
                    Log.d("test", "${data.getStringExtra(NewFeelingActivity.EXTRA_REPLY_DATETIME)}")
                    Log.d("test", "${data.getStringExtra(NewFeelingActivity.EXTRA_REPLY_LOCATION)}")
                    Log.d("test", "${data.getStringExtra(NewFeelingActivity.EXTRA_REPLY_SANITY)}")
                    Log.d("test", "${data.getStringExtra(NewFeelingActivity.EXTRA_REPLY_ACTIVENESS)}")
                    Log.d("test", "${data.getStringExtra(NewFeelingActivity.EXTRA_REPLY_DESCRIPTION)}")
                }
            } else {
                Log.d("test", "!RESULT_OK")
                Toast.makeText(
                    applicationContext,
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener() {
            Log.d("test", "fab onClick()")
            val intent = Intent(this@MainActivity, NewFeelingActivity::class.java)
            Log.d("test", "[${intent.hashCode()}] ${intent}")
            arl.launch(intent)
        }
    }
}