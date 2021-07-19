package com.example.tsurami

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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private val feelingViewModel: FeelingViewModel by viewModels {
        FeelingViewModelFactory(FeelingRepository())
    }

    private val arl: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult? ->
        Timber.d("ActivityResultCallback")
        Timber.d("$this")
        if (result?.resultCode == Activity.RESULT_OK) {
            result.data?.let { data: Intent ->
                Timber.d("RESULT_OK")
                Timber.d("[:data]:${data.hashCode()};")
                Timber.d("|[:key:val]")
                Timber.d("|:EXTRA_REPLY_DATETIME   :${data.getLongExtra(NewFeelingActivity.EXTRA_REPLY_DATETIME, 0)}")
                Timber.d("|:EXTRA_REPLY_LOCATION   :${data.getStringExtra(NewFeelingActivity.EXTRA_REPLY_LOCATION)}")
                Timber.d("|:EXTRA_REPLY_SANITY     :${data.getIntExtra(NewFeelingActivity.EXTRA_REPLY_SANITY, 0)}")
                Timber.d("|:EXTRA_REPLY_ACTIVENESS :${data.getIntExtra(NewFeelingActivity.EXTRA_REPLY_ACTIVENESS, 0)}")
                Timber.d("|:EXTRA_REPLY_DESCRIPTION:${data.getStringExtra(NewFeelingActivity.EXTRA_REPLY_DESCRIPTION)}")
                Timber.d(";")
            }
        } else {
            Timber.d("!RESULT_OK")
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate()")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = FeelingListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        feelingViewModel.allFeelings.observe(this) { feelings ->
            feelings.let { adapter.submitList(it) }
        }

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener() {
            Timber.d("fab onClick()")
            val intent = Intent(this@MainActivity, NewFeelingActivity::class.java)
            Timber.d("<Intent>[:hash:intent]:${intent.hashCode()}:${intent};")
            arl.launch(intent)
        }
    }
}