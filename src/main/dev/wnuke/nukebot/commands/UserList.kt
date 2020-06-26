@file:AutoWired

package dev.wnuke.nukebot.commands

import com.gitlab.kordlib.kordx.commands.annotation.AutoWired
import com.gitlab.kordlib.kordx.commands.annotation.ModuleName
import com.gitlab.kordlib.kordx.commands.kord.module.command
import com.gitlab.kordlib.kordx.commands.model.command.invoke

@ModuleName("userlist-command")
fun userListCommand() = command("userlist") {
    invoke {
        respond(message.getGuild().memberCount?.let { kord.rest.guild.getGuildMembers(message.getGuild().id.value, null, it) }!!.toList().joinToString(separator = "\n") { "${it.user!!.username}#${it.user!!.discriminator}" })
    }
}