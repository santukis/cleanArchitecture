package com.santukis.cleanarchitecture.game.data.datasources

import android.content.Context
import android.content.SharedPreferences
import com.santukis.cleanarchitecture.BuildConfig
import com.santukis.cleanarchitecture.core.domain.model.Response
import com.santukis.cleanarchitecture.game.domain.model.GameHistory
import com.santukis.cleanarchitecture.game.domain.model.GameScore

class LocalGameDataSource(context: Context): GameDataSource {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("${BuildConfig.APPLICATION_ID}.game_stats", Context.MODE_PRIVATE)

    override suspend fun loadGameHistory(): Response<GameHistory> {
        val history = GameHistory(
            totalScore = getScore(GameScore.TotalScore::class.java),
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
        return score.getConstructor(Int::class.java, Int::class.java).newInstance(getCount(score), getSuccess(score))
    }

    override suspend fun addScore(score: GameScore) {
        var count = getCount(score::class.java)
        var success = getSuccess(score::class.java)
        sharedPreferences.edit().putInt("${score::class.java.name}_count", ++count).apply()
        sharedPreferences.edit().putInt("${score::class.java.name}_success", ++success).apply()
    }

    private fun <Score: GameScore> getCount(score: Class<Score>): Int =
        sharedPreferences.getInt("${score::class.java.name}_count", 0)

    private fun <Score: GameScore> getSuccess(score: Class<Score>): Int =
        sharedPreferences.getInt("${score::class.java.name}_success", 0)
}