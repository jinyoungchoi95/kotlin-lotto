package lottery.domain

import java.math.BigDecimal
import java.math.RoundingMode

@JvmInline
value class Money(
    val value: BigDecimal,
) {
    init {
        require(value >= BigDecimal.ZERO) { "돈은 음수가 입력될 수 없다" }
    }

    fun times(count: Int): Money = Money(value.times(BigDecimal(count)))

    fun divide(money: Money): BigDecimal = value.divide(money.value, RoundingMode.DOWN)

    operator fun minus(money: Money) = Money(value.minus(money.value))
}
