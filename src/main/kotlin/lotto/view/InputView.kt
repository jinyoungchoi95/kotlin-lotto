package lotto.view

import java.math.BigDecimal

class InputView {

    fun inputBigDecimal(): BigDecimal {
        println("구입금액을 입력해 주세요.")
        return readln().toBigDecimal()
    }

    fun inputWinning(): List<Int> {
        println("지난 주 당첨 번호를 입력해 주세요.")
        return readln().split(DELIMITER)
            .map { it.toInt() }
    }

    companion object {
        private const val DELIMITER = ", "
    }
}
