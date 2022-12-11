package cn.fusionfish.core.command

import cn.fusionfish.core.util.Node
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import kotlin.reflect.KFunction
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.jvm.isAccessible

/**
 * @author JeremyHu
 * @date 2022/12/9 13:33
 */

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Invoke(val commandString: String = "")

interface CommandNode : Node {
    override fun children(): MutableMap<String, CommandNode>
}

open class Command(name: String) : Command(name), CommandNode {

    private val children = mutableMapOf<String, CommandNode>()

    init {
        buildCommandTree()
    }

    constructor(name: String, aliases: Collection<String>) : this(name) {
        this.aliases.addAll(aliases)
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>?): Boolean {
        TODO("Not yet implemented")
    }

    override fun children(): MutableMap<String, CommandNode> {
        return children
    }

    override fun value(): String {
        return name
    }

    /**
     * 建立指令树
     */
    private fun buildCommandTree() {
        this::class.declaredFunctions
            .filter { it.hasAnnotation<Invoke>() }
            .filter { it.parameters[0].type is CommandSender }
            .forEach {
                it.isAccessible = true
                val annotation = it.annotations.first { a -> a is Invoke } as Invoke
                val cmdStr = annotation.commandString
                val split = cmdStr.split(' ')

                //创建树
                var node: CommandNode = this
                for (cmd in split) {
                    node.children().putIfAbsent(cmd, SubCommand(cmd))
                    node = node.children()[cmd]!!
                }

                val subCommand = node as SubCommand
                subCommand.action = it
            }

    }
}

class SubCommand(private val name: String): CommandNode {

    private val children = mutableMapOf<String, CommandNode>()
    var action: KFunction<*>? = null

    override fun children(): MutableMap<String, CommandNode> {
        return children
    }

    override fun value(): String {
        return name
    }

    fun invoke(params: List<Parameter<Any>>) {
        if (action == null) {
            return
        }

        val values = params.map { it.value }
        action!!.call(values)
    }

}