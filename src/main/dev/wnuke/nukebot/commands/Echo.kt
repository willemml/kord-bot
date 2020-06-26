@file:AutoWired
package dev.wnuke.nukebot.commands

import com.gitlab.kordlib.kordx.commands.annotation.AutoWired
import com.gitlab.kordlib.kordx.commands.annotation.ModuleName
import com.gitlab.kordlib.kordx.commands.argument.text.StringArgument
import com.gitlab.kordlib.kordx.commands.kord.module.command
import com.gitlab.kordlib.kordx.commands.model.command.invoke

@ModuleName("echo-command")
fun echoCommand() = command("echo") {
    invoke(StringArgument) {
        respond(it)
    }
}

