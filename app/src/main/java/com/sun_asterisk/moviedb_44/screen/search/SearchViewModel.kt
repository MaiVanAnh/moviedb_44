package com.sun_asterisk.moviedb_44.screen.search

import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.sun_asterisk.moviedb_44.data.repository.MovieRepository
import com.sun_asterisk.moviedb_44.screen.base.BaseViewModel
import com.sun_asterisk.moviedb_44.screen.search.adapter.MovieHorizontalAdapter
import com.sun_asterisk.moviedb_44.utils.Constant
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchViewModel(private val movieRepository: MovieRepository) : BaseViewModel() {
    private var page = Constant.PAGE_DEFAULT
    private var oldSearchContent: String = "OLD VALUE"
    private var newSearchContent: String = "NEW VALUE"
    val titleObservable: ObservableBoolean = ObservableBoolean()
    val centerProgressBarObservable: ObservableBoolean = ObservableBoolean()
    val bottomProgressBarObservable: ObservableBoolean = ObservableBoolean()
    val announceObservable: ObservableBoolean = ObservableBoolean()
    val recyclerViewObservable: ObservableBoolean = ObservableBoolean()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    val movieAdapter = ObservableField<MovieHorizontalAdapter>()
    val watcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            newSearchContent = s.toString()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            announceObservable.set(false)
        }
    }

    init {
        movieAdapter.set(MovieHorizontalAdapter())
        setupOriginal()
    }

    private fun setupOriginal() {
        titleObservable.set(true)
        centerProgressBarObservable.set(false)
        announceObservable.set(false)
        recyclerViewObservable.set(false)
    }

    fun initData() {
        announceObservable.set(false)
        centerProgressBarObservable.set(true)
        page = Constant.PAGE_DEFAULT
        if (newSearchContent != oldSearchContent) {
            Handler().postDelayed({ getData() }, Constant.TIME_LOADING)
        } else {
            recyclerViewObservable.set(true)
            announceObservable.set(false)
            centerProgressBarObservable.set(false)
        }
    }

    fun loadMore() {
        bottomProgressBarObservable.set(true)
        if ((movieAdapter.get()!!.itemCount % Constant.AMOUNT_ITEM_IN_PER_PAGE) == 0) {
            page++
            Handler().postDelayed({ getData() }, Constant.TIME_LOADING)
        } else {
            bottomProgressBarObservable.set(false)
        }
    }

    private fun getData() {
        compositeDisposable.add(
            movieRepository.searchMovie(newSearchContent, page, false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { movies ->
                        run {
                            if (movies.isEmpty()) {
                                if (page == Constant.PAGE_DEFAULT) {
                                    announceObservable.set(true)
                                    recyclerViewObservable.set(false)
                                }
                            } else {
                                if (page == Constant.PAGE_DEFAULT) {
                                    movieAdapter.get()!!.clearList()
                                    oldSearchContent = newSearchContent
                                }
                                movieAdapter.get()!!.addItems(movies)
                                titleObservable.set(false)
                                recyclerViewObservable.set(true)
                            }
                            centerProgressBarObservable.set(false)
                            bottomProgressBarObservable.set(false)
                        }
                    },
                    { throwable ->
                        run {
                            throwable.localizedMessage
                            centerProgressBarObservable.set(false)
                            bottomProgressBarObservable.set(false)
                        }
                    })
        )
    }

    override fun onStart() {
    }

    override fun onStop() {
        compositeDisposable.clear()
    }
}
