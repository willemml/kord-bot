@file:AutoWired

package net.willemml.kordbot

import com.gitlab.kordlib.core.event.message.MessageCreateEvent
import com.gitlab.kordlib.core.on
import com.gitlab.kordlib.kordx.commands.annotation.AutoWired
import com.gitlab.kordlib.kordx.commands.kord.bot
import com.gitlab.kordlib.kordx.commands.kord.model.precondition.precondition
import com.gitlab.kordlib.kordx.commands.kord.model.prefix.kord
import com.gitlab.kordlib.kordx.commands.kord.model.prefix.mention
import com.gitlab.kordlib.kordx.commands.model.prefix.literal
import com.gitlab.kordlib.kordx.commands.model.prefix.or
import com.gitlab.kordlib.kordx.commands.model.prefix.prefix
import kapt.kotlin.generated.configure
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import java.io.File
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

val prefixes = prefix {
    kord { literal("n!") or mention() }
}

@Serializable
data class Lists(var banList: HashSet<Long>, var autoDelete: HashSet<Long>)

var lists = Lists(HashSet(), HashSet())

fun loadFiles() {
    val listsFile = File("lists")
    val json = Json(JsonConfiguration.Stable)
    if (listsFile.createNewFile()) {
        lists = json.parse(Lists.serializer(), listsFile.readText())
    }
}

fun preCommandChecks() = precondition {
    loadFiles()
    !lists.banList.contains(message.author!!.id.longValue) && !lists.autoDelete.contains(message.author!!.id.longValue) && message.author!!.id != kord.selfId
}

suspend fun main() = bot(System.getenv("BOT_TOKEN")) {
    this.configure()
    this.kord.on<MessageCreateEvent> {
        loadFiles()
        if (lists.autoDelete.contains(message.author!!.id.longValue)) message.delete()
        val timeStamp = DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss.SS")
                .withZone(ZoneOffset.UTC)
                .format(Instant.now())
        val messageToLog = "[${timeStamp}] [${message.getGuild().name}|${message.getChannel().data.name}] ${message.author!!.tag}: ${message.content}"
        println(messageToLog)
        File("messages.log").appendText(messageToLog + "\n")
    }
}
