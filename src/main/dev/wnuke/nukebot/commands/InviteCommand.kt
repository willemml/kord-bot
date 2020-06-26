@file:AutoWired

package dev.wnuke.nukebot.commands

import com.gitlab.kordlib.kordx.commands.annotation.AutoWired
import com.gitlab.kordlib.kordx.commands.annotation.ModuleName
import com.gitlab.kordlib.kordx.commands.argument.text.StringArgument
import com.gitlab.kordlib.kordx.commands.kord.module.command
import com.gitlab.kordlib.kordx.commands.model.command.invoke

@ModuleName("invite-command")
fun inviteCommand() = command("invite") {
    invoke {
        respond(this.kord.rest.channel.createInvite(message.channelId.value).toString())
    }
    invoke(StringArgument) {
        respond(this.kord.rest.invite.getInvite(it,false).toString())
    }
}