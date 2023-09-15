package com.example.lbgapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.example.lbgapplication.databinding.ActivityMainBinding
import com.example.lbgapplication.ui.CatsDataModel


class MainActivity : AppCompatActivity(), CatsListAdapter.CatDetailCallBack {

    private lateinit var binding: ActivityMainBinding
    private lateinit var catsListAdapter: CatsListAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViewModel()
        setUpRecyclerView()
        viewModel.getCatsData()

        with(viewModel) {
            catsData.observe(this@MainActivity) {
                catsListAdapter.submitList(it)
            }
            isLoading.observe(this@MainActivity) {
                if (it) {
                    binding.mainProgressBar.visibility = View.VISIBLE
                } else {
                    binding.mainProgressBar.visibility = View.GONE
                }
            }


            error.observe(this@MainActivity) {
                Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUpRecyclerView() = binding.rvCats.apply {
        catsListAdapter = CatsListAdapter(this@MainActivity, this@MainActivity)
      /*  val lManager = StaggeredGridLayoutManager(2, VERTICAL)
        lManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE;*/

        adapter = catsListAdapter
        layoutManager = GridLayoutManager(this@MainActivity,2)

    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun callForCatDetails(cat: CatsDataModel) {
        startActivity(Intent(this, FullViewActivity::class.java).apply { putExtra("URL", cat.url) })

    }
}