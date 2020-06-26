@file:AutoWired
package dev.wnuke.nukebot.commands

import com.gitlab.kordlib.kordx.commands.annotation.AutoWired
import com.gitlab.kordlib.kordx.commands.annotation.ModuleName
import com.gitlab.kordlib.kordx.commands.kord.module.command
import com.gitlab.kordlib.kordx.commands.model.command.invoke
import java.util.concurrent.ThreadLocalRandom

@ModuleName("rps-command")
fun rpsCommand() = command("rps") {
    invoke() {
        respond(when (ThreadLocalRandom.current().nextInt(0, 3)) {
            0 -> "rock"
            1 -> "paper"
            2 -> "scissors"
            else -> "I win!"
        })
    }
}