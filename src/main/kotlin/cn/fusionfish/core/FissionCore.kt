package cn.fusionfish.core

import org.bukkit.plugin.java.JavaPlugin

/**
 * @author JeremyHu
 * @date 2022/12/2 15:06
 */
object FissionCore: JavaPlugin() {
    override fun onEnable() {
        val testCommand = TestCommand()
        val tree = testCommand.tree()
        logger.info(tree.toString())
    }
}