package lottery.domain.lottery

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class LotteryNumberTest : FunSpec({

    context("init") {
        test("로또 번호가 1~45 이외의 수가 저장되려 하는 경우 예외가 발생한다.") {
            forAll(row(0), row(46)) { input ->
                val exception = shouldThrowExactly<IllegalArgumentException> { LotteryNumber(value = input) }
                exception.message shouldBe "로또 번호는 1~45의 수만 입력 가능하다."
            }
        }
    }

    context("from") {
        test("문자 입력시 예외가 발생한다") {
            val exception = shouldThrowExactly<IllegalArgumentException> { LotteryNumber.from(value = "a") }
            exception.message shouldBe "숫자만 입력 가능하다"
        }

        test("숫자문자를 입력받아 생성한다") {
            val actual = LotteryNumber.from(value = "1")
            actual shouldBe LotteryNumber(value = 1)
        }
    }
}) {
    companion object {
        val LOTTERY_NUMBER_1 = LotteryNumber(value = 1)
        val LOTTERY_NUMBER_2 = LotteryNumber(value = 2)
        val LOTTERY_NUMBER_3 = LotteryNumber(value = 3)
        val LOTTERY_NUMBER_4 = LotteryNumber(value = 4)
        val LOTTERY_NUMBER_5 = LotteryNumber(value = 5)
        val LOTTERY_NUMBER_6 = LotteryNumber(value = 6)
        val LOTTERY_NUMBER_7 = LotteryNumber(value = 7)
        val LOTTERY_NUMBER_8 = LotteryNumber(value = 8)
        val LOTTERY_NUMBER_9 = LotteryNumber(value = 9)
        val LOTTERY_NUMBER_10 = LotteryNumber(value = 10)
    }
}
