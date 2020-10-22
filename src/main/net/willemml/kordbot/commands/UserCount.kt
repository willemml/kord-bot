@file:AutoWired

package net.willemml.kordbot.commands

import com.gitlab.kordlib.kordx.commands.annotation.AutoWired
import com.gitlab.kordlib.kordx.commands.annotation.ModuleName
import com.gitlab.kordlib.kordx.commands.kord.module.command
import com.gitlab.kordlib.kordx.commands.model.command.invoke

@ModuleName("usercount-command")
fun userCountCommand() = command("usercount") {
    invoke {
        respond("This server has ${message.getGuild().memberCount} members.")
    }
}