package com.sun_asterisk.moviedb_44.screen.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sun_asterisk.moviedb_44.R
import com.sun_asterisk.moviedb_44.data.repository.MovieRepository
import com.sun_asterisk.moviedb_44.data.source.local.MovieLocalDataSource
import com.sun_asterisk.moviedb_44.data.source.remote.MovieRemoteDataSource
import com.sun_asterisk.moviedb_44.databinding.ActivitySearchBinding
import com.sun_asterisk.moviedb_44.utils.EndlessRecyclerOnScrollListener
import com.sun_asterisk.moviedb_44.utils.hideKeyboard
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {
    lateinit var viewModel: SearchViewModel
    lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            SearchViewModel(MovieRepository.getInstance(MovieLocalDataSource(), MovieRemoteDataSource.getInstance()))
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        binding.viewModel = viewModel
        addEvent()
    }

    private fun addEvent() {
        binding.include.recyclerView.addOnScrollListener(object : EndlessRecyclerOnScrollListener() {
            override fun onLoadMore() {
                viewModel.loadMore()
            }
        })
        binding.edInput.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE && edInput.text.toString().trim().isNotEmpty()) {
                    hideKeyboard(this@SearchActivity)
                    viewModel.initData()
                    return true
                }
                return false
            }
        })
    }
}
