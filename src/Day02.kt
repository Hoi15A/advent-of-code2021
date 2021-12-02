fun main() {

    fun part1(input: List<String>): Int {
        var horizontal = 0
        var depth = 0

        input.stream()
            .map { str -> str.split(' ') }
            .map { lst -> Pair(lst[0], lst[1]) }
            .forEach { pair ->
                when (pair.first) {
                    "forward" -> horizontal += pair.second.toInt()
                    "down" -> depth += pair.second.toInt()
                    "up" -> depth -= pair.second.toInt()
                }
            }

        return horizontal * depth
    }

    fun part2(input: List<String>): Int {
        var horizontal = 0
        var depth = 0
        var aim = 0

        input.stream()
            .map { str -> str.split(' ') }
            .map { lst -> Pair(lst[0], lst[1]) }
            .forEach { pair ->
                when (pair.first) {
                    "down" -> aim += pair.second.toInt()
                    "up" -> aim -= pair.second.toInt()
                    "forward" -> {
                        horizontal += pair.second.toInt()
                        depth += aim * pair.second.toInt()
                    }
                }
            }

        return horizontal * depth
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}



