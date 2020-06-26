package dev.wnuke.nukebot

import com.gitlab.kordlib.common.entity.Snowflake
import com.gitlab.kordlib.core.Kord
import com.gitlab.kordlib.core.event.message.MessageCreateEvent
import com.gitlab.kordlib.core.on
import kotlinx.coroutines.delay
import java.io.File
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import kotlin.random.Random

suspend fun main(token: Array<String>) {
    val kord = Kord(token[0])
    val banList: ArrayList<Long> = ArrayList()
    val autoDelList: ArrayList<Long> = ArrayList()

    kord.on<MessageCreateEvent> {
        val timeStamp = DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss.SS")
                .withZone(ZoneOffset.UTC)
                .format(Instant.now())
        val messageToLog = "[${timeStamp}] [${message.getGuild().name}|${message.getChannel().data.name}] ${message.author!!.tag}: ${message.content}"
        println(messageToLog)
        File("messages.log").appendText(messageToLog + "\n")

        when {
            autoDelList.contains(message.author!!.id.longValue) -> {
                println("Deleted message from ${message.author!!.tag}")
                message.delete()
                return@on
            }
            banList.contains(message.author?.data?.id) && message.content.startsWith("n!") -> {
                message.channel.createMessage("${message.author!!.mention} You are banned from using this bot please do not use it or it will ping you.")
                return@on
            }
            message.content.startsWith("n!help") -> {
                message.channel.createMessage("""
                    NUKE Bot commands list:```
                    n!count <Integer>: counts from 1 to the specified number
                    n!echo <String>: sends the specified String
                    n!rps: Rock Paper Scissors
                    n!flip: flips a coin (heads/tails)
                    n!ban <user>: bans a user from using the bot (bot owner only)
                    n!unban <user>: allows a banned user to use the bot (bot owner only)
                    n!autodel <user>: toggles automatic deletion of a user's future messages (bot owner only)
                    n!inv <code>: prepends https://discord.gg/ to a piece of text
                    n!userlist: lists all users in the server
                    n!usercount: tells how many users are in the server```
                """.trimIndent())
            }
            message.content.startsWith("n!echo ") -> {
                message.channel.createMessage(message.content.removePrefix("n!echo "))
            }
            message.content.startsWith("n!count ") -> {
                val noPrefix = message.content.removePrefix("n!count ")
                if (noPrefix.toIntOrNull() != null) {
                    for (i in 1..noPrefix.toInt()) {
                        delay(500)
                        message.channel.createMessage(i.toString())
                    }
                } else {
                    return@on
                }
            }
            message.content.startsWith("n!rps") -> {
                when (Random.nextInt(0, 3)) {
                    0 -> message.channel.createMessage("rock")
                    1 -> message.channel.createMessage("paper")
                    2 -> message.channel.createMessage("scissors")
                }
            }
            message.content.startsWith("n!flip") -> {
                when (Random.nextInt(0, 2)) {
                    0 -> message.channel.createMessage("heads")
                    1 -> message.channel.createMessage("tails")
                }
            }
            message.content.startsWith("n!ban") && message.author!!.id.longValue == 237237152495304704 -> {
                if (message.data.mentions.isNotEmpty()) {
                    for (id in message.data.mentions) {
                        if (banList.contains(id)) {
                            message.channel.createMessage("${kord.getUser(Snowflake(id))!!.tag} is already banned from using NUKE Bot.")
                        } else {
                            banList.add(id)
                            message.channel.createMessage("Banned ${kord.getUser(Snowflake(id))!!.tag} from using NUKE Bot.")
                        }
                    }
                }
            }
            message.content.startsWith("n!unban") && message.author!!.id.longValue == 237237152495304704 -> {
                if (message.data.mentions.isNotEmpty()) {
                    for (id in message.data.mentions) {
                        if (banList.contains(id)) {
                            banList.remove(id)
                            message.channel.createMessage("Unbanned ${kord.getUser(Snowflake(id))!!.tag} from using NUKE Bot.")
                        } else {
                            message.channel.createMessage("${kord.getUser(Snowflake(id))!!.tag} is already allowed to use NUKE Bot.")
                        }
                    }
                }
            }
            message.content.startsWith("n!autodel") && message.author!!.id.longValue == 237237152495304704 -> {
                if (message.data.mentions.isNotEmpty()) {
                    for (id in message.data.mentions) {
                        if (autoDelList.contains(id)) {
                            autoDelList.remove(id)
                            message.channel.createMessage("Stopped auto-deleting all messages from ${kord.getUser(Snowflake(id))!!.tag}.")
                        } else {
                            autoDelList.add(id)
                            message.channel.createMessage("Auto-deleting all messages from ${kord.getUser(Snowflake(id))!!.tag}.")
                        }
                    }
                }
            }
            message.content.startsWith("n!inv") -> {
                message.channel.createMessage(message.getGuild().getInvite(message.content.removePrefix("n!inv ")).toString())
            }
            message.content.startsWith("n!userlist") -> {
                val members = message.getGuild().memberCount?.let { kord.rest.guild.getGuildMembers(message.getGuild().id.value, null, it) }!!.toList()
                val memberList = members.joinToString(separator = "\n") { "${it.user!!.username}#${it.user!!.discriminator}" }
                message.channel.createMessage(memberList)
            }
            message.content.startsWith("n!usercount") -> {
                message.channel.createMessage("This server has ${message.getGuild().memberCount} members.")
            }
            else -> return@on
        }
    }
    kord.login()
}
