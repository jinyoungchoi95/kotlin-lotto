package lotto.domain

import lotto.argumentError

class LottoNumbers(
    private val numbers: List<LottoNumber>
) {
    init {
        check(numbers.size == LOTTO_NUMBER_SET) {
            argumentError("로또는 중복 없는 6자리 번호여야합니다.")
        }
    }

    fun matches(inputNumbers: LottoNumbers): Int {
        return numbers.count { number ->
            inputNumbers.numbers.contains(number)
        }
    }

    fun matches(inputNumber: LottoNumber): Boolean {
        return numbers.contains(inputNumber)
    }

    override fun toString(): String {
        return numbers.joinToString(", ") { number -> number.toString() }
    }

    companion object {
        private const val LOTTO_NUMBER_SET = 6
        private val LOTTO_NUMBERS = (1..45)

        fun from(): LottoNumbers {
            return LottoNumbers(
                LOTTO_NUMBERS.shuffled()
                    .take(6)
                    .sorted()
                    .map { number ->
                        LottoNumber(number)
                    }
            )
        }

        fun from(input: String): LottoNumbers {
            return LottoNumbers(
                input.split(",")
                    .map { split ->
                        split.trim().toInt()
                    }.sorted()
                    .map(::LottoNumber)
            )
        }
    }
}
