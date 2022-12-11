package cn.fusionfish.core

import cn.fusionfish.core.command.CommandManager
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author JeremyHu
 * @date 2022/12/2 21:23
 */
abstract class FissionPlugin: JavaPlugin() {

    abstract fun load()
    abstract fun disable()
    abstract fun enable()

    override fun onDisable() {

        CommandManager.unregisterAll()
        disable()
    }

    override fun onLoad() {


        load()
    }

    override fun onEnable() {

        enable()
    }

    fun extractRes() {

    }
}