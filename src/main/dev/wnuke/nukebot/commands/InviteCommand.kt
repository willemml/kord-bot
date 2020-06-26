@file:AutoWired

package dev.wnuke.nukebot.commands

import com.gitlab.kordlib.kordx.commands.annotation.AutoWired
import com.gitlab.kordlib.kordx.commands.annotation.ModuleName
import com.gitlab.kordlib.kordx.commands.argument.primitive.IntArgument
import com.gitlab.kordlib.kordx.commands.kord.module.command
import com.gitlab.kordlib.kordx.commands.model.command.invoke

@ModuleName("invite-command")
fun inviteCommand() = command("invite") {
    invoke(IntArgument) {
       respond(when (it) {
           0 -> "https://discord.gg/uaV5gUm"
           1 -> "https://discord.gg/W63ZfZQ"
           2 -> "https://discord.gg/k2KaMz8"
           3 -> "https://discord.gg/qbQeVUd"
           4 -> "https://discord.gg/JrDN7tr"
           else -> "Nope."
       })
    }
}