@file:AutoWired

package dev.wnuke.nukebot.commands

import com.gitlab.kordlib.common.entity.Snowflake
import com.gitlab.kordlib.kordx.commands.annotation.AutoWired
import com.gitlab.kordlib.kordx.commands.annotation.ModuleName
import com.gitlab.kordlib.kordx.commands.argument.primitive.IntArgument
import com.gitlab.kordlib.kordx.commands.kord.module.command
import com.gitlab.kordlib.kordx.commands.model.command.invoke
import com.gitlab.kordlib.kordx.emoji.Emojis
import com.gitlab.kordlib.kordx.emoji.toReaction

var lastUserToCount: Snowflake? = null
var currentCount: Int = 0
var highScore: Int = currentCount

@ModuleName("countgame-command")
fun countGameCommand() = command("cg") {
    invoke(IntArgument) {
        if (message.author!!.id != lastUserToCount) {
            if (currentCount + 1 == it) {
                currentCount++
                message.addReaction(Emojis.whiteCheckMark.toReaction())
                lastUserToCount = message.author!!.id
                if (currentCount > highScore) highScore = currentCount
                if (currentCount.toString().last() == '0') respond("Yay, you have reached $currentCount, the highest score is $highScore.")
            } else {
                message.addReaction(Emojis.x.toReaction())
                currentCount = 0
                lastUserToCount = null
                respond("Wrong number, resetting to 0.")
            }
        } else {
            message.addReaction(Emojis.x.toReaction())
            currentCount = 0
            lastUserToCount = null
            respond("You cannot count twice, resetting to 0.")
        }
    }
}