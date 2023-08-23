package mx.edu.uts.movietrailerstv.data.repository

import mx.edu.uts.movietrailerstv.data.server.RemoteConnection
import mx.edu.uts.movietrailerstv.data.server.toDomain
import mx.edu.uts.movietrailerstv.domain.Category
import mx.edu.uts.movietrailerstv.domain.Movie


class MoviesRepository(private val apiKey: String) {

    suspend fun getCategories(): Map<Category, List<Movie>> {
        return Category.values().associateWith { category ->
            RemoteConnection
                .service
                .listPopularMovies(apiKey, category.id)
                .results.map { it.toDomain() }
        }
    }
}