@file:AutoWired

package net.willemml.kordbot.commands

import com.gitlab.kordlib.common.entity.Snowflake
import com.gitlab.kordlib.core.Kord
import com.gitlab.kordlib.core.event.message.MessageCreateEvent
import com.gitlab.kordlib.core.on
import com.gitlab.kordlib.kordx.commands.annotation.AutoWired
import com.gitlab.kordlib.kordx.commands.annotation.ModuleName
import com.gitlab.kordlib.kordx.commands.kord.module.command
import com.gitlab.kordlib.kordx.commands.model.command.invoke
import com.gitlab.kordlib.kordx.emoji.Emojis
import com.gitlab.kordlib.kordx.emoji.toReaction
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import java.io.File


val countGames: HashMap<Snowflake, Job> = HashMap()

@ModuleName("countgame-command")
fun countGameCommand() = command("cg") {
    invoke {
        File("cg-highscore").createNewFile()
        newCountGame(channel.id, kord)
        val highScore: Int = if (File("cg-highscore").readText().toIntOrNull() != null) File("cg-highscore").readText().toInt() else 0
        respond("Counting game has started in this channel, try to beat the current highscore of $highScore")
    }
}

@ModuleName("countgamestop-command")
fun countGameStopCommand() = command("cgstop") {
    invoke {
        if (countGames[channel.id] != null) {
            countGames[channel.id]!!.cancelAndJoin()
            countGames.remove(channel.id)
            respond("Counting game has been stopped.")
        } else {
            respond("No counting game in this channel.")
        }
    }
}

fun newCountGame(channel: Snowflake, kord: Kord) {
    countGames[channel] = GlobalScope.launch {
        var currentCount: Int = 0
        var lastUserToCount: Snowflake? = null
        kord.on<MessageCreateEvent> {
            if (message.content.toIntOrNull() == null || message.channel.id != channel || countGames[channel] == null) return@on
            if (message.author!!.id != lastUserToCount) {
                if (currentCount + 1 == message.content.toInt()) {
                    currentCount++
                    message.addReaction(Emojis.whiteCheckMark.toReaction())
                    lastUserToCount = message.author!!.id
                    val highScore: Int = if (File("cg-highscore").readText().toIntOrNull() != null) File("cg-highscore").readText().toInt() else 0
                    if (currentCount > highScore) {
                        message.channel.createMessage("You have beat the current highscore with $currentCount points.")
                        File("cg-highscore").writeText(currentCount.toString())
                    }
                    if (currentCount.toString().last() == '0') message.channel.createMessage("Yay, you have reached $currentCount, the highest score is $highScore.")
                } else {
                    message.addReaction(Emojis.x.toReaction())
                    currentCount = 0
                    lastUserToCount = null
                    message.channel.createMessage("Wrong number, resetting to 0.")
                }
            } else {
                message.addReaction(Emojis.x.toReaction())
                currentCount = 0
                lastUserToCount = null
                message.channel.createMessage("You cannot count twice, resetting to 0.")
            }
        }
    }
}