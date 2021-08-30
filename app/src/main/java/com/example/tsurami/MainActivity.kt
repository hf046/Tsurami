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
import com.example.tsurami.db.entity.Comment
import com.example.tsurami.db.entity.Feeling
import com.example.tsurami.db.entity.Location
import com.example.tsurami.db.entity.Test
import com.example.tsurami.viewmodel.TestViewModel
import com.example.tsurami.viewmodel.TestViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import timber.log.Timber

class MainActivity : AppCompatActivity() {
//    private val tsuramiViewModel: TsuramiViewModel by viewModels {
//        TsuramiViewModelFactory((application as TsuramiApplication).repository)
//    }
    private val testViewModel: TestViewModel by viewModels {
        TestViewModelFactory((application as TestApplication).repository)
    }

    private val arl: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult? ->
        Timber.d("\\[:result callback]")
        Timber.d("\\:check result status")
        if (result?.resultCode == Activity.RESULT_OK) {
            Timber.d("\\:get input data from Intent")
            result.data?.let { data: Intent ->
                var feeling = data.getSerializableExtra(NewFeelingActivity.EXTRA_REPLY_FEELING) as Feeling
                val location = data.getSerializableExtra(NewFeelingActivity.EXTRA_REPLY_LOCATION) as Location?
                val comment = data.getSerializableExtra(NewFeelingActivity.EXTRA_REPLY_COMMENT) as Comment?
                val test = data.getSerializableExtra(NewFeelingActivity.EXTRA_REPLY_TEST) as Test?
                Timber.d("\\:")
                Timber.d("|[:key:val]")
                Timber.d("|:EXTRA_REPLY_FEELING  :$feeling")
                Timber.d("|:EXTRA_REPLY_LOCATION :$location")
                Timber.d("|:EXTRA_REPLY_COMMENT :$comment")
                Timber.d("|:EXTRA_REPLY_TEST :$test")
                Timber.d(";")
                Timber.d("\\:save to DB")
//                TODO("add Feeling on DB")
                Timber.d("\\:[:feeling]:$feeling;")
//                tsuramiViewModel.insert(FeelingDatum(feeling, location, comment))
//                val feelingDatum = FeelingDatum(feeling, location, comment)
//                Timber.d("\\:[:feelingDatum]:$feelingDatum;")
//                feeling.location_id = null
//                tsuramiViewModel.insert(feeling)
//                TODO("add Location on DB")
//                TODO("add Comment on DB")
//                TODO("add Test on DB")
                if (test != null) testViewModel.insert(test)
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
        Timber.d("\\:set R layout")
        setContentView(R.layout.activity_main)

        Timber.d("\\:get recyclerview")
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        Timber.d("\\:create list adapter")
//        val adapter = FeelingDatumListAdapter()
        val adapter = TestListAdapter()

        Timber.d("\\:setup recyclerView")
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        Timber.d("\\:setup the view-model's livedata observer")
//        tsuramiViewModel.allFeelingDatum.observe(this) { feelingData ->
//            Timber.d("\\[:observe]")
//            Timber.d("\\:[:feelingData]:$feelingData;")
//            Timber.d("\\:submit feelings to the Recyclerview Adapter")
//            feelingData.let { adapter.submitList(it) }
//            Timber.d("\\:observe end\n;")
//        }
        testViewModel.allTests.observe(this) { tests ->
            tests.let { adapter.submitList(it) }
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