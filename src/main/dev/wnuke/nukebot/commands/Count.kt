@file:AutoWired

package dev.wnuke.nukebot.commands

import com.gitlab.kordlib.kordx.commands.annotation.AutoWired
import com.gitlab.kordlib.kordx.commands.annotation.ModuleName
import com.gitlab.kordlib.kordx.commands.argument.primitive.IntArgument
import com.gitlab.kordlib.kordx.commands.kord.module.command
import com.gitlab.kordlib.kordx.commands.model.command.invoke
import kotlinx.coroutines.*

var countThreads: HashSet<Job> = HashSet()

@ModuleName("count-command")
fun countStartCommand() = command("count") {
    invoke(IntArgument("start"), IntArgument("end")) { a, b ->
        countThreads.add(GlobalScope.launch {
            for (i in a..b) {
                channel.createMessage(i.toString())
                delay(1000)
            }
        })
    }
}

@ModuleName("countstop-command")
fun countStopCommand() = command("stopcount") {
    invoke {
        if (countThreads.isEmpty()) {
            respond("Not counting...")
            return@invoke
        }
        for (i in countThreads) {
            i.cancelAndJoin()
        }
        countThreads.clear()
        respond("Stopped counting...")
    }
}