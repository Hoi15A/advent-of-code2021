import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {

    data class Point(var x: Int, var y: Int)
    data class Line(val p1: Point, val p2: Point)

    fun parseLineData(data: String): Line {
        val regex = Regex("(?<x1>\\d+),(?<y1>\\d+) -> (?<x2>\\d+),(?<y2>\\d+)")
        val result = regex.matchEntire(data)
        assert(result!!.groups.size == 4)
        val x1 = result.groups["x1"]!!.value.toInt()
        val y1 = result.groups["y1"]!!.value.toInt()
        val x2 = result.groups["x2"]!!.value.toInt()
        val y2 = result.groups["y2"]!!.value.toInt()
        return Line(Point(x1, y1), Point(x2, y2))
    }

    fun lineToPoints(line: Line, countDiagonals: Boolean): List<Point> {
        val points = mutableListOf<Point>()

        if (line.p1.x == line.p2.x) { // Horizontal
            val start = min(line.p1.y, line.p2.y)
            val end   = max(line.p1.y, line.p2.y)

            for (i in start..end) {
                points.add(Point(line.p1.x, i))
            }
        } else if (line.p1.y == line.p2.y) { // Vertical
            val start = min(line.p1.x, line.p2.x)
            val end   = max(line.p1.x, line.p2.x)

            for (i in start..end) {
                points.add(Point(i, line.p1.y))
            }
        } else if (countDiagonals) {
            val start = line.p1
            val end = line.p2
            val xDir = if (start.x - end.x < 0) 1 else -1
            val yDir = if (start.y - end.y < 0) 1 else -1

            val curr = start
            points.add(curr.copy())
            for (step in 0 until abs(start.x - end.x)) {
                curr.x += xDir
                curr.y += yDir
                points.add(curr.copy())
            }
        }

        return points
    }

    fun part1(input: List<String>): Int {
        val board = mutableMapOf<Point, Int>()
        for (entry in input) {
            val line = parseLineData(entry)
            val points = lineToPoints(line, false)

            for (point in points) {
                board[point] = board.getOrDefault(point, 0) + 1
            }
        }

        return board.filter { entry -> entry.value >= 2 }.size
    }

    fun part2(input: List<String>): Int {
        val board = mutableMapOf<Point, Int>()
        for (entry in input) {
            val line = parseLineData(entry)
            val points = lineToPoints(line, true)

            for (point in points) {
                board[point] = board.getOrDefault(point, 0) + 1
            }
        }
        return board.filter { entry -> entry.value >= 2 }.size
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
