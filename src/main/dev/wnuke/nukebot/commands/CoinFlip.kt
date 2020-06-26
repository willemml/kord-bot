@file:AutoWired
package dev.wnuke.nukebot.commands

import com.gitlab.kordlib.kordx.commands.annotation.AutoWired
import com.gitlab.kordlib.kordx.commands.annotation.ModuleName
import com.gitlab.kordlib.kordx.commands.kord.module.command
import com.gitlab.kordlib.kordx.commands.model.command.invoke
import kotlin.random.Random

@ModuleName("coinflip-command")
fun coinFlipCommand() = command("flip") {
    invoke() {
        respond(when (Random.nextInt(0, 2)) {
            0 -> "heads"
            1 -> "tails"
            else -> "I lost the coin!"
        })
    }
}