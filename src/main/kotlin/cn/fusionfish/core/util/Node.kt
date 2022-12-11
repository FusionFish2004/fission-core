package cn.fusionfish.core.util

/**
 * @author JeremyHu
 * @date 2022/12/9 22:32
 */
interface Node {
    fun children(): MutableMap<String, out Node>
    fun value(): Any

    fun tree(): LinkedHashMap<String, Int> {
        val map = linkedMapOf<String, Int>()
        tree(this, 1, map)
        return map
    }

    private fun tree(node: Node, level: Int, map: LinkedHashMap<String, Int>) {
        val sortedMap = node.children().toSortedMap()
        sortedMap.forEach { (name, node) ->
            map[name] = level
            if (node.children().isNotEmpty()) {
                tree(node, level+1, map)
            }
        }
    }
}

interface MutableNode: Node{
    fun value(value: Any)
}