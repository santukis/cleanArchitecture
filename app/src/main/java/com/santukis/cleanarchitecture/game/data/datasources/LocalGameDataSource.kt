package com.santukis.cleanarchitecture.game.data.datasources

import android.content.Context
import android.content.SharedPreferences
import androidx.sqlite.db.SimpleSQLiteQuery
import com.santukis.cleanarchitecture.BuildConfig
import com.santukis.cleanarchitecture.artwork.data.local.toQuestion
import com.santukis.cleanarchitecture.artwork.data.local.fromQuestionTypeToSqlQuery
import com.santukis.cleanarchitecture.core.data.local.AppDatabase
import com.santukis.cleanarchitecture.core.domain.model.Response
import com.santukis.cleanarchitecture.game.domain.model.GameHistory
import com.santukis.cleanarchitecture.game.domain.model.GameScore
import com.santukis.cleanarchitecture.game.domain.model.Question

class LocalGameDataSource(context: Context,
                          private val database: AppDatabase): GameDataSource {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("${BuildConfig.APPLICATION_ID}.game_stats", Context.MODE_PRIVATE)

    override suspend fun loadGameHistory(): Response<GameHistory> {
        val history = GameHistory(
            titleScore = getScore(GameScore.TitleScore::class.java),
            authorScore = getScore(GameScore.AuthorScore::class.java),
            datingScore = getScore(GameScore.DatingScore::class.java)
        )

        return when(history) {
            GameHistory.EMPTY -> Response.Error(Exception("Unable to load game history"))
            else -> Response.Success(history)
        }
    }

    private fun <Score: GameScore> getScore(score: Class<Score>): Score {
        return score.getConstructor(Int::class.java, Int::class.java).newInstance(getCount(score.simpleName), getSuccess(score.simpleName))
    }

    override suspend fun addScore(score: GameScore) {
        val count = getCount(score::class.java.simpleName)
        val success = getSuccess(score::class.java.simpleName)
        sharedPreferences.edit().putInt("${score::class.java.simpleName}_count", count + score.count).apply()
        sharedPreferences.edit().putInt("${score::class.java.simpleName}_success", success + score.success).apply()
    }

    private fun getCount(gameScoreName: String): Int =
        sharedPreferences.getInt("${gameScoreName}_count", 0)

    private fun getSuccess(gameScoreName: String): Int =
        sharedPreferences.getInt("${gameScoreName}_success", 0)

    override suspend fun loadQuestion(type: Int): Response<Question> =
        try {
            val items = database.artworkDao().loadQuestion(SimpleSQLiteQuery(fromQuestionTypeToSqlQuery(type)))

            when(val question = items?.toQuestion(type)) {
                null -> super.loadQuestion(type)
                else -> Response.Success(question)
            }

        } catch (exception: Exception) {
            super.loadQuestion(type)
        }
}