package com.silas.gpsworksapp.data.mapper

import com.silas.gpsworksapp.data.response.DetailResponse
import com.silas.gpsworksapp.data.response.Work

interface ConvertResponseToWorkMapper {

    fun convertResponse(detailResponse: DetailResponse): Work

    class Impl : ConvertResponseToWorkMapper {
        override fun convertResponse(detailResponse: DetailResponse): Work {
            return Work(
                actors = detailResponse.actors,
                awards = detailResponse.awards,
                boxOffice = detailResponse.boxOffice,
                country = detailResponse.country,
                dVD = detailResponse.dVD,
                director = detailResponse.director,
                genre = detailResponse.genre,
                imdbID = detailResponse.imdbID,
                imdbRating = detailResponse.imdbRating,
                imdbVotes = detailResponse.imdbVotes,
                language = detailResponse.language,
                metascore = detailResponse.metascore,
                plot = detailResponse.plot,
                poster = detailResponse.poster,
                production = detailResponse.production,
                rated = detailResponse.rated,
                released = detailResponse.released,
                response = detailResponse.response,
                runtime = detailResponse.runtime,
                title = detailResponse.title,
                type = detailResponse.type,
                website = detailResponse.website,
                writer = detailResponse.writer,
                year = detailResponse.year,
            )
        }
    }
}