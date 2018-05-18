package jp.mrtry.repositoryreader.view.repository

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.mugen.Mugen
import com.mugen.MugenCallbacks
import jp.mrtry.repositoryreader.R
import jp.mrtry.repositoryreader.databinding.ActivityMainBinding
import jp.mrtry.repositoryreader.util.SnackbarMessage
import jp.mrtry.repositoryreader.view.repository.adapter.RepositoryItemAdapter
import jp.mrtry.repositoryreader.viewModel.RepositoryViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // binding
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val repositoryViewModel = ViewModelProviders.of(this).get(RepositoryViewModel::class.java)
        binding.setLifecycleOwner(this)
        binding.viewModel = repositoryViewModel

        // toolbarのセット
        setSupportActionBar(toolbar)

        // ViewModelからSnackbarを表示するための準備
        repositoryViewModel.snackbarMessage.observe(this, SnackbarMessage.SnackbarObserver { resId ->
            val view = findViewById<View>(android.R.id.content)
            Snackbar.make(view, resId, Snackbar.LENGTH_LONG).show()
        })

        // RecyclerViewの準備
        val adapter = RepositoryItemAdapter()
        repositoryViewModel.repositories.value?.let {
            adapter.repositories = it
        }

        binding.repositoryList.apply {
            this.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            this.addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
            this.setHasFixedSize(true)
            this.adapter = adapter
        }

        // ViewModelで取得したデータをRecyclerViewに反映させる
        repositoryViewModel.repositories.observe(this, Observer {
            it?.let {
                adapter.repositories = it
                adapter.notifyDataSetChanged()
            }
        })

        repositoryViewModel.getRepositories()

        // 無限スクロールの実装
        val attacher = Mugen.with(binding.repositoryList, object : MugenCallbacks {
            override fun onLoadMore() {
                repositoryViewModel.getRepositories()
            }

            override fun isLoading(): Boolean {
                return repositoryViewModel.isLoading
            }

            override fun hasLoadedAllItems(): Boolean {
                return repositoryViewModel.isLoadFinished
            }
        }).start()
        attacher.loadMoreOffset = 10
    }
}
