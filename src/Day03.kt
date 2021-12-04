fun main() {

    fun part1(input: List<String>): Int {
        var gamma = ""

        for (i in 0 until input[0].length) {
            var ones = 0
            for (str in input) {
                if ('1' == str[i]) ones++
            }

            gamma += if (ones > input.size / 2) {
                "1"
            } else {
                "0"
            }
        }

        val gammaRate = gamma.toInt(2)
        val epsilonRate = gammaRate xor "1".repeat(input[0].length).toInt(2)

        return gammaRate * epsilonRate
    }

    fun part2(input: List<String>): Int {
        fun getRating(data: List<String>, isOxygen: Boolean): Int {
            var rating = ""
            var nums = data.toMutableList()
            for (i in 0 until input[0].length) {
                val zeros = mutableListOf<String>()
                val ones = mutableListOf<String>()

                for (bits in nums) {
                    if (bits[i] == '0')  {
                        zeros.add(bits)
                    } else {
                        ones.add(bits)
                    }
                }

                nums = if (isOxygen) {
                    if (zeros.size > ones.size) { zeros } else { ones }
                } else {
                    if (zeros.size > ones.size) { ones } else { zeros }
                }


                if (nums.size == 1) {
                    rating = nums[0]
                    break
                }
            }
            return rating.toInt(2)
        }

        val oxygen = getRating(input, true)
        val co2 = getRating(input, false)

        return oxygen * co2
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
