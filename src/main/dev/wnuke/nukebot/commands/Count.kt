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
            var range = (a..b).toHashSet()
            if (a > b) range = b.downTo(a).toHashSet()
            for (i in range) {
                channel.createMessage(i.toString())
                delay(500)
            }
            if (countThreads.isNotEmpty()) countThreads.last().join()
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