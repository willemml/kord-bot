@file:AutoWired
package dev.wnuke.nukebot.commands

import com.gitlab.kordlib.kordx.commands.annotation.AutoWired
import com.gitlab.kordlib.kordx.commands.annotation.ModuleName
import com.gitlab.kordlib.kordx.commands.kord.argument.UserArgument
import com.gitlab.kordlib.kordx.commands.kord.module.command
import com.gitlab.kordlib.kordx.commands.model.command.invoke
import dev.wnuke.nukebot.lists

@ModuleName("botban-command")
fun botBanCommand() = command("bban") {
    invoke(UserArgument) {
        if (author.id.longValue == 237237152495304704) {
            if (lists.banList.contains(it.id.longValue)) {
                respond("${it.tag} is already banned from using this bot.")
            } else {
                lists.banList.add(it.id.longValue)
                respond("${it.tag} is now banned from using this bot.")
            }
        } else {
            respond("You are not allowed to execute this command.")
        }
    }
}

@ModuleName("botunban-command")
fun botUnBanCommand() = command("unbban") {
    invoke(UserArgument) {
        if (author.id.longValue == 237237152495304704) {
            if (lists.banList.contains(it.id.longValue)) {
                lists.banList.remove(it.id.longValue)
                respond("${it.tag} is now allowed to use this bot.")
            } else {
                respond("${it.tag} is already allowed to use this bot.")
            }
        } else {
            respond("You are not allowed to execute this command.")
        }
    }
}

@ModuleName("botbanls-command")
fun botBanListCommand() = command("bbls") {
    invoke {
        respond(lists.banList.joinToString("\n"))
    }
}