package cn.fusionfish.core

import cn.fusionfish.core.command.Command
import cn.fusionfish.core.command.Invoke
import org.bukkit.command.CommandSender

/**
 * @author JeremyHu
 * @date 2022/12/9 23:46
 */
class TestCommand: Command("test") {

    @Invoke("s1 s2")
    fun i1(sender: CommandSender) {

    }

    @Invoke("s1 s2 s3")
    fun i2(sender: CommandSender) {

    }

    @Invoke("s1 s2 s3")
    fun i3(sender: CommandSender) {

    }

    @Invoke("s1 s4")
    fun i4(sender: CommandSender) {

    }
}