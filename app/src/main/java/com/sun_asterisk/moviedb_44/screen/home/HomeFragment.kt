package com.sun_asterisk.moviedb_44.screen.home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sun_asterisk.moviedb_44.R
import com.sun_asterisk.moviedb_44.data.repository.MovieRepository
import com.sun_asterisk.moviedb_44.data.source.local.MovieLocalDataSource
import com.sun_asterisk.moviedb_44.data.source.remote.MovieRemoteDataSource
import com.sun_asterisk.moviedb_44.databinding.FragmentHomeBinding
import com.sun_asterisk.moviedb_44.screen.base.BaseFragment
import com.sun_asterisk.moviedb_44.screen.search.SearchActivity
import com.sun_asterisk.moviedb_44.utils.CategoryAnnotation
import kotlinx.android.synthetic.main.category_recycler.view.*
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_home, container, false)
        viewModel =
            HomeViewModel(MovieRepository.getInstance(MovieLocalDataSource(),
                MovieRemoteDataSource.getInstance()))
        binding.viewModel = viewModel
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(Color.WHITE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }

    private fun initView() {
        popularRecyclerView.tvCategory.text = CategoryAnnotation.POPULAR
        upComingRecyclerView.tvCategory.text = CategoryAnnotation.UPCOMING
        playNowRecyclerView.tvCategory.text = CategoryAnnotation.NOW_PLAYING
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.option_menu_home, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.option_menu_search) {
            gotoSearchView()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun gotoSearchView() {
    }

    override fun onStop() {
        super.onStop()
        viewModel.onStop()
    }
}
