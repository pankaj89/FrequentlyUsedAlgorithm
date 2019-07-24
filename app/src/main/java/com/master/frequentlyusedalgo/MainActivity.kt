package com.master.frequentlyusedalgo

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.master.frequentlyusedalgo.databinding.ItemBinding
import com.master.fualibrary.FrequentlyUsedAlgorithm
import com.simpleadapter.SimpleAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var adapterFrequentlyUsed: SimpleAdapter<String>
    lateinit var adapterMostlyUsed: SimpleAdapter<String>
    lateinit var adapterRecentlyUsed: SimpleAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val frequentlyUsed = FrequentlyUsedAlgorithm(this, "FOOD")
        arrayOf(btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8).forEach {
            it.setOnClickListener {
                frequentlyUsed.access((it as Button).text.toString())

                updateAdapter(frequentlyUsed)
            }
        }

        //-------frequently------
        adapterFrequentlyUsed =
            SimpleAdapter.with<String, ItemBinding>(R.layout.item) { adapterPosition, model, binding ->
                binding.txt.setText(model)
            }
        recyclerViewFrequentlyUsed.layoutManager = LinearLayoutManager(this)
        recyclerViewFrequentlyUsed.adapter = adapterFrequentlyUsed

        //-------mostly------
        adapterMostlyUsed =
            SimpleAdapter.with<String, ItemBinding>(R.layout.item) { adapterPosition, model, binding ->
                binding.txt.setText(model)
            }
        recyclerViewMostlyUsed.layoutManager = LinearLayoutManager(this)
        recyclerViewMostlyUsed.adapter = adapterMostlyUsed

        //-------recently------
        adapterRecentlyUsed =
            SimpleAdapter.with<String, ItemBinding>(R.layout.item) { adapterPosition, model, binding ->
                binding.txt.setText(model)
            }
        recyclerViewRecentlyUsed.layoutManager = LinearLayoutManager(this)
        recyclerViewRecentlyUsed.adapter = adapterRecentlyUsed

        updateAdapter(frequentlyUsed)
    }

    fun updateAdapter(frequentlyUsed: FrequentlyUsedAlgorithm) {
        val disposable=frequentlyUsed.getFrequentlyUsedList()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                Log.i("TAG", it.toString())
                adapterFrequentlyUsed.clear()
                adapterFrequentlyUsed.addAll(it)
                adapterFrequentlyUsed.notifyDataSetChanged()
            }, {
                Log.i("TAG", it.toString())
            })

        frequentlyUsed.getMostlyUsedList()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                Log.i("TAG", it.toString())
                adapterMostlyUsed.clear()
                adapterMostlyUsed.addAll(it)
                adapterMostlyUsed.notifyDataSetChanged()
            }, {
                Log.i("TAG", it.toString())
            })

        frequentlyUsed.getRecentlyUsedList()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                Log.i("TAG", it.toString())
                adapterRecentlyUsed.clear()
                adapterRecentlyUsed.addAll(it)
                adapterRecentlyUsed.notifyDataSetChanged()
            }, {
                Log.i("TAG", it.toString())
            })
    }
}
