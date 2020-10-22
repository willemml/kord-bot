@file:AutoWired

package net.willemml.kordbot.commands

import com.gitlab.kordlib.kordx.commands.annotation.AutoWired
import com.gitlab.kordlib.kordx.commands.annotation.ModuleName
import com.gitlab.kordlib.kordx.commands.kord.module.command
import com.gitlab.kordlib.kordx.commands.model.command.invoke

@ModuleName("userlist-command")
fun userListCommand() = command("userlist") {
    invoke {

        val members = message.getGuild().memberCount?.let { kord.rest.guild.getGuildMembers(message.getGuild().id.value, null, it) }!!.toList()
        val memberList = members.joinToString(separator = "\n") { "${it.user!!.username}#${it.user!!.discriminator}" }
        respond(memberList)
    }
}