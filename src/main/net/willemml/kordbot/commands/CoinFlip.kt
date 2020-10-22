@file:AutoWired
package net.willemml.kordbot.commands

import com.gitlab.kordlib.kordx.commands.annotation.AutoWired
import com.gitlab.kordlib.kordx.commands.annotation.ModuleName
import com.gitlab.kordlib.kordx.commands.kord.module.command
import com.gitlab.kordlib.kordx.commands.model.command.invoke
import java.util.concurrent.ThreadLocalRandom

@ModuleName("coinflip-command")
fun coinFlipCommand() = command("flip") {
    invoke() {
        respond(when (ThreadLocalRandom.current().nextBoolean()) {
            true -> "heads"
            false -> "tails"
        })
    }
}