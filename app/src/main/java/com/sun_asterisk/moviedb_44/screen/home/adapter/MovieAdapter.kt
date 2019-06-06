package com.sun_asterisk.moviedb_44.screen.home.adapter

import androidx.databinding.ViewDataBinding
import com.sun_asterisk.moviedb_44.R
import com.sun_asterisk.moviedb_44.data.model.Movie
import com.sun_asterisk.moviedb_44.databinding.ItemMovieBinding
import com.sun_asterisk.moviedb_44.screen.base.BaseAdapter
import com.sun_asterisk.moviedb_44.screen.base.BaseViewHolder

class MovieAdapter : BaseAdapter<Movie>() {

    override fun layout(row: Int): Int = R.layout.item_movie

    override fun viewHolder(binding: ViewDataBinding): BaseViewHolder<Movie> = MovieAdapterViewHolder(binding as ItemMovieBinding)

    companion object {

        class MovieAdapterViewHolder(private val itemMovieBinding: ItemMovieBinding) :
            BaseViewHolder<Movie>(itemMovieBinding) {

            override fun bindData(data: Movie) {
                itemMovieBinding.movie = data
                itemMovieBinding.executePendingBindings()
            }
        }
    }
}
