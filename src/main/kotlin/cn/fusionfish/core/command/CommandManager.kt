package cn.fusionfish.core.command

import cn.fusionfish.core.FissionPlugin
import org.bukkit.Bukkit
import kotlin.reflect.full.declaredFunctions

/**
 * @author JeremyHu
 * @date 2022/12/2 22:16
 */
object CommandManager {

    val commands = mutableMapOf<String, Command>()

    /**
     * 更新指令
     */
    fun update() {
        val server = Bukkit.getServer()
        //反射调用syncCommands
        val clazz = server::class
        clazz.declaredFunctions
            .first { it.name == "syncCommands" }
            .call()
    }

    /**
     * 注册指令
     */
    fun register(plugin: FissionPlugin, node: Command) {
        val commandMap = Bukkit.getServer().commandMap
        commandMap.register(plugin.name, node)
        commands[node.name] = node

        //实例化子命令

    }

    /**
     * 卸载所有指令
     */
    fun unregisterAll() {
        val commandMap = Bukkit.getServer().commandMap
        commandMap.knownCommands
            .values
            .removeIf { commands.containsValue(it) }
        commands.forEach { (_, c) ->  c.unregister(commandMap)}
        commands.clear()
    }
}