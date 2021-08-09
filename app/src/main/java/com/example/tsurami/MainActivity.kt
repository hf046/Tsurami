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
        Timber.d("\\[:ActivityResultCallback]")
//        Timber.d("\\:$this")
        Timber.d("\\:[:result?.resultCode]:${result?.resultCode};")
        if (result?.resultCode == Activity.RESULT_OK) {
            result.data?.let { data: Intent ->
                Timber.d("\\:")
                Timber.d("|[:key:val]")
                Timber.d("|:EXTRA_REPLY_DATETIME   :${data.getLongExtra(NewFeelingActivity.EXTRA_REPLY_DATETIME, 0)}")
                Timber.d("|:EXTRA_REPLY_LOCATION   :${data.getStringExtra(NewFeelingActivity.EXTRA_REPLY_LOCATION)}")
                Timber.d("|:EXTRA_REPLY_SANITY     :${data.getIntExtra(NewFeelingActivity.EXTRA_REPLY_SANITY, 0)}")
                Timber.d("|:EXTRA_REPLY_ACTIVENESS :${data.getIntExtra(NewFeelingActivity.EXTRA_REPLY_ACTIVENESS, 0)}")
                Timber.d("|:EXTRA_REPLY_DESCRIPTION:${data.getStringExtra(NewFeelingActivity.EXTRA_REPLY_DESCRIPTION)}")
                Timber.d(";")
            }
        } else {
            Timber.d("\\:<<result is empty>>")
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
//        Timber.d("\\:$this")
        Timber.d(";")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("\\[:onCreate]")
//        Timber.d("\\:$this")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.d("\\:<<setting recyclerView>>")
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = FeelingListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        Timber.d("\\:<<read from ViewModel>>")
        feelingViewModel.allFeelings.observe(this) { feelings ->
            Timber.d("\\[:Observer]")
//            Timber.d("\\:$this")
            Timber.d("\\:[:feelings]:{$feelings};")
            Timber.d("\\:<<submit feelings to the Recyclerview Adapter>>")
            feelings.let { adapter.submitList(it) }
//            adapter.submitList(feelings)
//            Timber.d("\\:$this")
            Timber.d(";")
        }

        Timber.d("\\:<<setting fab>>")
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            Timber.d("\\[:onClick]")
            Timber.d("\\:[:it]:$it;")
//            Timber.d("\\:$this")
            Timber.d("\\:<<make Intent>>")
            val intent = Intent(this@MainActivity, NewFeelingActivity::class.java)
            Timber.d("\\:[:intent]:$intent;")
            Timber.d("\\:arl.launch(intent)")
            arl.launch(intent)
//            Timber.d("\\:$this")
            Timber.d(";")
        }
//        Timber.d("\\:$this")
        Timber.d(";")
    }
}