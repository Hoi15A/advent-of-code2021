fun main() {

    fun part1(input: List<String>): Int {
        val data = mapToInts(input)
        var count = 0

        for (i in 1 until data.size) {
            val curr = data[i]
            val prev = data[i-1]
            if (curr > prev) count++
        }

        return count
    }

    fun part2(input: List<String>): Int {
        val data = mapToInts(input)
        var count = 0

        for (i in 3 until data.size) {
            val curr = data[i] + data[i - 1] + data[i - 2]
            val prev = data[i - 1] + data[i - 2] + data[i - 3]
            if (curr > prev) count++
        }

        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}


fun mapToInts(input: List<String>): List<Int> {
    return input.stream().map { str -> str.toInt() }.toList()
}
