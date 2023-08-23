package mx.edu.uts.movietrailerstv.ui.detail

import android.app.Activity
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.leanback.widget.AbstractDetailsDescriptionPresenter
import androidx.leanback.widget.Action
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.FullWidthDetailsOverviewRowPresenter
import androidx.leanback.widget.FullWidthDetailsOverviewSharedElementHelper
import mx.edu.uts.movietrailerstv.ui.detail.DetailActivity
import mx.edu.uts.movietrailerstv.R
import mx.edu.uts.movietrailerstv.domain.Movie


class FullWidthDetailsMovieRowPresenter(private val activity: Activity) :
    FullWidthDetailsOverviewRowPresenter(DetailsDescriptionPresenter()) {

    private enum class Options(@StringRes val stringRes: Int) {
        WATCH_TRAILER(R.string.watch_trailer),
        FAVORITE(R.string.favorite);
    }

    init {
        val sharedElementHelper = FullWidthDetailsOverviewSharedElementHelper()
        sharedElementHelper.setSharedElementEnterTransition(
            activity, DetailActivity.SHARED_ELEMENT_NAME
        )
        setListener(sharedElementHelper)
        isParticipatingEntranceTransition = true
    }

    fun buildActions(): ArrayObjectAdapter {

        setOnActionClickedListener { action ->
            val option = Options.values().first { it.ordinal == action.id.toInt() }
            Toast.makeText(activity, option.stringRes, Toast.LENGTH_SHORT).show()
        }

        return ArrayObjectAdapter().apply {
            Options.values().forEach { option ->
                add(Action(option.ordinal.toLong(), activity.getString(option.stringRes)))
            }
        }
    }
}

private class DetailsDescriptionPresenter : AbstractDetailsDescriptionPresenter() {

    override fun onBindDescription(vh: ViewHolder, item: Any) {
        val movie = item as Movie
        vh.title.text = movie.title
        vh.subtitle.text = movie.releaseDate
        vh.body.text = movie.summary
    }

}