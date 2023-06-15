package lottery.domain.lottery

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import lottery.domain.Money
import lottery.domain.Rank
import lottery.domain.lottery.LotteryTest.Companion.LOTTERY_1_6
import lottery.domain.lottery.LotteryTest.Companion.LOTTERY_3_8
import lottery.domain.lottery.LotteryTest.Companion.LOTTERY_4_9
import java.math.BigDecimal

class LotteriesTest : FunSpec({

    context("compareWinningLottery") {
        test("보유한 모든 로또의 결과를 확인할 수 있다") {
            val lotteries = Lotteries(values = mutableListOf(LOTTERY_1_6, LOTTERY_1_6, LOTTERY_3_8, LOTTERY_4_9))
            val winningLottery = LOTTERY_1_6

            val actual = lotteries.compareWinningLottery(winningLottery = winningLottery)

            actual[Rank.FIRST] shouldBe 2
            actual[Rank.SECOND] shouldBe 0
            actual[Rank.THIRD] shouldBe 1
            actual[Rank.FOURTH] shouldBe 1
        }
    }

    context("cost") {
        test("구매한 로또의 금액을 반환할 수 있다.") {
            val lotteries = Lotteries(values = mutableListOf(LOTTERY_1_6, LOTTERY_1_6, LOTTERY_3_8, LOTTERY_4_9))
            val actual = lotteries.cost()

            actual shouldBe Money(value = BigDecimal(4_000))
        }
    }
})
