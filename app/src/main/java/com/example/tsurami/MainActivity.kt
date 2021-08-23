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
import com.example.tsurami.dao.service.AddFeelingDao
import com.google.android.material.floatingactionbutton.FloatingActionButton
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private val feelingViewModel: FeelingViewModel by viewModels {
        FeelingViewModelFactory(FeelingRepository())
    }

    private val arl: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult? ->
        Timber.d("\\[:result callback]")
        Timber.d("\\:check result status")
        if (result?.resultCode == Activity.RESULT_OK) {
            Timber.d("\\:get input data from Intent")
            result.data?.let { data: Intent ->
                val feeling = data.getSerializableExtra(NewFeelingActivity.EXTRA_REPLY_FEELING)
                val location = data.getSerializableExtra(NewFeelingActivity.EXTRA_REPLY_LOCATION)
                val comment = data.getSerializableExtra(NewFeelingActivity.EXTRA_REPLY_COMMENT)
                Timber.d("\\:")
                Timber.d("|[:key:val]")
                Timber.d("|:EXTRA_REPLY_FEELING  :$feeling")
                Timber.d("|:EXTRA_REPLY_LOCATION :$location")
                Timber.d("|:EXTRA_REPLY_COMMENT :$comment")
                Timber.d(";")
                Timber.d("\\:save to DB")
//                TODO("add Feeling to DB")
//                TODO("add Location to DB")
//                TODO("add Comment to DB")
            }
        } else {
            Timber.d("\\:fail to get result")
            Timber.d("\\:show fail msg")
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
        Timber.d("\\:result callback end\n;")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("\\[:onCreate]")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.d("\\:get elements from layout")
        Timber.d("\\:(recyclerView, adapter)")
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = FeelingListAdapter()

        Timber.d("\\:setup recyclerView")
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        Timber.d("\\:setup the view-model's livedata observer")
        feelingViewModel.allFeelings.observe(this) { feelings ->
            Timber.d("\\[:observe]")
            Timber.d("\\:[:feelings]:{$feelings};")
            Timber.d("\\:submit feelings to the Recyclerview Adapter")
            feelings.let { adapter.submitList(it) }
//            adapter.submitList(feelings)
            Timber.d("\\:observe end\n;")
        }

        Timber.d("\\:get element from layout")
        Timber.d("\\:(FloatingActionButton)")
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        Timber.d("\\:setting fab")
        fab.setOnClickListener {
            Timber.d("\\[:onClick]")
            Timber.d("\\:[:it]:$it;")
//            Timber.d("\\:$this")
            Timber.d("\\:make Intent")
            val intent = Intent(this@MainActivity, NewFeelingActivity::class.java)
            Timber.d("\\:arl.launch(intent)")
            arl.launch(intent)
//            Timber.d("\\:$this")
            Timber.d("\\:onClick end\n;")
        }
//        Timber.d("\\:$this")
        Timber.d("\\:onCreate end\n;")
    }
}