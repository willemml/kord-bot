@file:AutoWired
package net.willemml.kordbot.commands

import com.gitlab.kordlib.kordx.commands.annotation.AutoWired
import com.gitlab.kordlib.kordx.commands.annotation.ModuleName
import com.gitlab.kordlib.kordx.commands.kord.argument.UserArgument
import com.gitlab.kordlib.kordx.commands.kord.module.command
import com.gitlab.kordlib.kordx.commands.model.command.invoke
import net.willemml.kordbot.lists

@ModuleName("autodeladd-command")
fun autoDeleteAddCommand() = command("adeladd") {
    invoke(UserArgument) {
        if (author.id.longValue == 237237152495304704) {
            if (lists.autoDelete.contains(it.id.longValue)) {
                respond("${it.tag}'s messages are already being automatically deleted by this bot.")
            } else {
                lists.autoDelete.add(it.id.longValue)
                respond("${it.tag}'s messages are now being automatically deleted by this bot.")
            }
        } else {
            respond("You are not allowed to execute this command.")
        }
    }
}

@ModuleName("autodelrm-command")
fun autoDeleteRmCommand() = command("adelrm") {
    invoke(UserArgument) {
        if (author.id.longValue == 237237152495304704) {
            if (lists.autoDelete.contains(it.id.longValue)) {
                lists.autoDelete.remove(it.id.longValue)
                respond("${it.tag}'s messages are no longer being automatically deleted by this bot.")
            } else {
                respond("${it.tag}'s messages were not being automatically delete by this bot.")
            }
        } else {
            respond("You are not allowed to execute this command.")
        }
    }
}

@ModuleName("autodells-command")
fun autoDeleteListCommand() = command("adells") {
    invoke {
        respond(lists.autoDelete.joinToString("\n"))
    }
}