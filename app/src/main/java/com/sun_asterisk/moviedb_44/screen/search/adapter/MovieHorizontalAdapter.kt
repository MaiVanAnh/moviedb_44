package com.sun_asterisk.moviedb_44.screen.search.adapter

import androidx.databinding.ViewDataBinding
import com.sun_asterisk.moviedb_44.R
import com.sun_asterisk.moviedb_44.data.model.Movie
import com.sun_asterisk.moviedb_44.databinding.ItemMovieHorizontalBinding
import com.sun_asterisk.moviedb_44.screen.base.BaseAdapter
import com.sun_asterisk.moviedb_44.screen.base.BaseViewHolder

class MovieHorizontalAdapter : BaseAdapter<Movie>() {
    override fun layout(row: Int): Int = R.layout.item_movie_horizontal

    override fun viewHolder(binding: ViewDataBinding): BaseViewHolder<Movie> {
        return MovieViewHolder(binding as ItemMovieHorizontalBinding)
    }

    companion object {
        class MovieViewHolder(private val itemMovieHorizontalBinding: ItemMovieHorizontalBinding) :
            BaseViewHolder<Movie>(itemMovieHorizontalBinding) {
            override fun bindData(data: Movie) {
                itemMovieHorizontalBinding.movie = data
                itemMovieHorizontalBinding.executePendingBindings()
            }
        }
    }
}
