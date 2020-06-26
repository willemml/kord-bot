@file:AutoWired

package dev.wnuke.nukebot

import com.gitlab.kordlib.core.event.message.MessageCreateEvent
import com.gitlab.kordlib.kordx.commands.annotation.AutoWired
import com.gitlab.kordlib.kordx.commands.kord.bot
import com.gitlab.kordlib.kordx.commands.kord.model.prefix.kord
import com.gitlab.kordlib.kordx.commands.kord.model.prefix.mention
import com.gitlab.kordlib.kordx.commands.model.prefix.literal
import com.gitlab.kordlib.kordx.commands.model.prefix.or
import com.gitlab.kordlib.kordx.commands.model.prefix.prefix
import com.gitlab.kordlib.core.on
import kapt.kotlin.generated.configure
import java.io.File
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

val prefixes = prefix {
    kord { literal("n!") or mention() }
}

suspend fun main() = bot(System.getenv("BOT_TOKEN")) {
    this.configure()
    this.kord.on<MessageCreateEvent> {
        val timeStamp = DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss.SS")
                .withZone(ZoneOffset.UTC)
                .format(Instant.now())
        val messageToLog = "[${timeStamp}] [${message.getGuild().name}|${message.getChannel().data.name}] ${message.author!!.tag}: ${message.content}"
        println(messageToLog)
        File("messages.log").appendText(messageToLog + "\n")
    }
}
