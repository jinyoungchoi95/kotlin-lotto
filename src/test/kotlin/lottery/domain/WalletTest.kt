package lottery.domain

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import lottery.domain.lottery.Lotteries
import lottery.domain.lottery.LotteryNumberTest.Companion.LOTTERY_NUMBER_7
import lottery.domain.lottery.LotteryTest.Companion.LOTTERY_1_6
import lottery.domain.lottery.LotteryTest.Companion.LOTTERY_2_7
import lottery.domain.lottery.LotteryTest.Companion.LOTTERY_4_9
import lottery.domain.lottery.WinningLottery
import lottery.domain.lottery.generator.RandomLotteryGenerator
import lottery.domain.rank.Rank
import java.math.BigDecimal

class WalletTest : FunSpec({

    context("init") {
        test("wallet 생성 시 로또를 구매할 돈이 없는 경우 예외가 발생한다") {
            val exception = shouldThrowExactly<IllegalArgumentException> { Wallet(money = Money(BigDecimal(0))) }
            exception.message shouldBe "로또를 사기엔 부족한 금액이다"
        }

        test("wallet 생성 시 로또 금액으로 나누어떨어지지 않는 경우 예외가 발생한다") {
            val exception =
                shouldThrowExactly<IllegalArgumentException> { Wallet(money = Money(BigDecimal(1_200))) }
            exception.message shouldBe "로또 금액으로 나누어떨어지지 않는 금액이다"
        }

        test("wallet을 정상적으로 생성한다") {
            shouldNotThrow<IllegalArgumentException> { Wallet(money = Money(BigDecimal(2_000))) }
        }
    }

    context("purchaseManualLotteries") {
        test("수동 로또를 구매할 돈이 없는 경우 예외가 발생한다.") {
            val givenManualLotteries = listOf(
                listOf("1", "2", "3", "4", "5", "6"),
                listOf("2", "3", "4", "5", "6", "7"),
            )
            val wallet = Wallet(money = Money(BigDecimal(1_000)))

            val exception =
                shouldThrowExactly<IllegalArgumentException> { wallet.purchaseManualLotteries(givenManualLotteries) }
            exception.message shouldBe "수동 로또를 사기엔 부족한 금액이다"
        }

        test("수동 로또를 구매할 수 있다") {
            val givenManualLotteries = listOf(
                listOf("1", "2", "3", "4", "5", "6"),
                listOf("2", "3", "4", "5", "6", "7"),
            )
            val wallet = Wallet(money = Money(BigDecimal(2_500)))

            val actual = wallet.purchaseManualLotteries(givenManualLotteries)
            wallet.money shouldBe Money(value = BigDecimal(500))
            actual shouldHaveSize 2
        }
    }

    context("purchaseLottery") {
        test("로또를 구매한다") {
            val wallet = Wallet(money = Money(BigDecimal(3_500)))
            val actual = wallet.purchaseLotteries(RandomLotteryGenerator)

            wallet.money shouldBe Money(value = BigDecimal(500))
            actual shouldHaveSize 3
        }
    }

    context("calculateLotteryResult") {
        test("당첨 로또를 받아 로또의 당첨 결과를 반환할 수 있다") {
            val wallet = Wallet(
                money = Money(BigDecimal(1_000)),
                manualLotteries = Lotteries(
                    values = mutableListOf(
                        LOTTERY_1_6,
                        LOTTERY_2_7,
                    ),
                ),
                randomLotteries = Lotteries(
                    values = mutableListOf(
                        LOTTERY_1_6,
                        LOTTERY_4_9,
                    ),
                ),
            )
            val winningLottery = WinningLottery(lottery = LOTTERY_1_6, bonusNumber = LOTTERY_NUMBER_7)

            val actual = wallet.calculateLotteryResult(winLottery = winningLottery)
            actual.statistics[Rank.FIRST] shouldBe 2
            actual.statistics[Rank.SECOND] shouldBe 1
            actual.statistics[Rank.THIRD] shouldBe 0
            actual.statistics[Rank.FOURTH] shouldBe 0
            actual.statistics[Rank.FIFTH] shouldBe 1
            actual.lottoYield.setScale(2) shouldBe BigDecimal(1007501.25)
        }
    }
})
