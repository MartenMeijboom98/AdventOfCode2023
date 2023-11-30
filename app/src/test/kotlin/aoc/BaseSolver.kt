package aoc

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

private const val BASE_URL = "https://adventofcode.com/"
private const val TWENTY_TWO_COOKIE =
    "session=53616c7465645f5f18e61604c5117705464018e3a3a5db833f6678151b02b1368cff79c9cfe067e0bb1d7356fb9c5d085481839d6cc225816fb8ad96dcfbecd4"
private const val TWENTY_THREE_COOKIE =
    "session=53616c7465645f5f18e61604c5117705464018e3a3a5db833f6678151b02b1368cff79c9cfe067e0bb1d7356fb9c5d085481839d6cc225816fb8ad96dcfbecd4"

abstract class BaseSolver(
    year: Int = 2023,
    day: Int
) {

    private val cookies = mapOf(2022 to TWENTY_TWO_COOKIE, 2023 to TWENTY_THREE_COOKIE)

    val input: List<String>

    init {
        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder()
            .uri(URI.create(buildUrl(year, day)))
            .header("cookie", cookies[year])
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofLines())

        input = response.body().toList()
    }

    fun <T> List<T>.split(predicate: (T) -> Boolean): List<List<T>> {
        val idx = this.indexOfFirst(predicate)
        return if (idx == -1) {
            listOf(this)
        } else {
            return listOf(this.take(idx)) + this.drop(idx + 1).split(predicate)
        }
    }

    private fun buildUrl(year: Int, day: Int): String {
        return "$BASE_URL$year/day/$day/input"
    }

}