package mx.com.disoftware.blogapp.core

import java.util.concurrent.TimeUnit

private const val SECOND_MILLIS = 1
private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
private const val HOURS_MILLIS = 60 * MINUTE_MILLIS
private const val DAY_MILLIS = 24 * HOURS_MILLIS

object TimeUtils {
    fun getTimeAgo(time: Int): String {

        val now = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())

        if (time > now || time <= 0){
            return "in the future"
        }

        val diff = now - time

        return when {
            diff < MINUTE_MILLIS -> "Just now"
            diff < 2 * MINUTE_MILLIS -> "a minute go"
            diff < 60 * MINUTE_MILLIS -> "${diff / MINUTE_MILLIS} minutes ago"
            diff < 2 * HOURS_MILLIS -> "an hour go"
            diff < 24 * HOURS_MILLIS -> "${diff / HOURS_MILLIS} hours ago"
            diff < 48 * HOURS_MILLIS -> "yesterday"
            else -> "${diff / DAY_MILLIS} days ago"
        }

    }
}