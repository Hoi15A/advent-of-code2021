fun main() {

    fun part1(input: String): Int {
        val data = parseBingoInput(input)
        val numsDrawn = data.first
        val boards = data.second

        var score = -100
        boardChecks@ for (num in numsDrawn) {
            for (board in boards) {
                board.markNum(num.toInt())
                if (board.checkWin()) {
                    val sum = board.getUnmarkedNumberSum()
                    score = sum * num.toInt()
                    break@boardChecks
                }
            }
        }

        return score
    }

    fun part2(input: String): Int {
        val data = parseBingoInput(input)
        val numsDrawn = data.first
        val boards = data.second

        // Triple<boardIndex, score, time>
        val winners = mutableListOf<Triple<Int, Int, Int>>()
        for ((time, num) in numsDrawn.withIndex()) {
            for ((bIndex, board) in boards.withIndex()) {
                board.markNum(num.toInt())
                if (board.checkWin()) {

                    if (winners.find { triple -> triple.first == bIndex } == null) {
                        val sum = board.getUnmarkedNumberSum()
                        val score = sum * num.toInt()
                        winners.add(Triple(bIndex, score, time))
                    }
                }
            }
        }

        winners.sortBy { triple -> triple.third }

        val winner = winners[winners.size - 1]
        return winner.second
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputRaw("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInputRaw("Day04")
    println(part1(input))
    println(part2(input))
}

/**
 * Parses the string input of advent of code bingo puzzle
 *
 * @param input Advent of code input
 * @return Pair containing list of draws and list of boards
 */
fun parseBingoInput(input: String): Pair<List<String>, List<Board>> {
    val tmp = input.split(Regex("\n\n"), 2)
    val numsDrawn = tmp[0].split(",")
    val boardStrings = tmp[1].split("\n\n")
    val boards = mutableListOf<Board>()

    for (str in boardStrings) {
        boards.add(Board(str))
    }

    return Pair(numsDrawn, boards)
}

class Board(initBoardState: String) {
    private val marked = -1
    /**
     * Stores bingo numbers, -1 if drawn
     */
    private var boardData = listOf(
        mutableListOf(0, 0, 0, 0, 0),
        mutableListOf(0, 0, 0, 0, 0),
        mutableListOf(0, 0, 0, 0, 0),
        mutableListOf(0, 0, 0, 0, 0),
        mutableListOf(0, 0, 0, 0, 0)
    )

    /**
     * Parse the board string
     */
    init {
        val rows = initBoardState.split("\n")
        for (i in rows.indices) {
            val row = rows[i]
            val nums = row.trim().split(Regex(" +"))
            for (j in nums.indices) {
                boardData[i][j] = nums[j].toInt()
            }
        }
    }

    fun markNum(number: Int) {
        for (i in 0..4) {
            for (j in 0..4) {
                if (boardData[i][j] == number) boardData[i][j] = marked
            }
        }
    }

    fun getUnmarkedNumberSum(): Int {
        var sum = 0
        for (row in boardData) {
            for (num in row) {
                if (num != marked) sum += num
            }
        }
        return sum
    }

    fun checkWin(): Boolean {
        return checkHorizontal() || checkVertical() /*|| checkDiagonal() "Diagonals don't count" ok boomer */
    }

    private fun checkHorizontal(): Boolean {
        for (row in boardData) {
            if (row[0] == marked && row[1] == marked && row[2] == marked && row[3] == marked && row[4] == marked) return true
        }
        return false
    }

    private fun checkVertical(): Boolean {
        for (i in 0..4) {
            var cellsMarked = 0
            for (row in boardData) {
                if (row[i] == marked) cellsMarked++
            }
            if (cellsMarked == 5) return true
        }
        return false
    }

    /**
     * Reading comprehension is a valuable skill to have, too bad I dont:
     * "(Diagonals don't count.)"
     */
    private fun checkDiagonal(): Boolean {
        var d1Count = 0
        var d2Count = 0
        for (i in 0..4) {
            if (boardData[i][i] == marked) d1Count++
            if (boardData[4 - i][4 - i] == marked) d2Count++
        }
        return d1Count == 5 || d2Count == 5
    }
}
