package net.justinas.exercise.lib.intent

import io.reactivex.Single
import net.justinas.exercise.lib.domain.BalanceRepository
import net.justinas.exercise.lib.domain.ExchangeRepository
import net.justinas.exercise.lib.domain.data.Currency
import net.justinas.exercise.lib.domain.data.CurrencyBalance
import net.justinas.exercise.lib.domain.data.Exchange
import net.justinas.exercise.lib.domain.data.ExchangeRequest
import net.justinas.exercise.lib.utils.isZero
import net.justinas.minilist.util.InteractorSingle
import java.math.BigDecimal

class ExchangeInteractor(private val exchange: ExchangeRepository, private val userBalance: BalanceRepository) :
    InteractorSingle<String, ExchangeInteractor.Request>() {

    override fun create(request: Request): Single<String> {
        return exchange.exhange(request.exchange.fromAmount, request.exchange.from.name, request.exchange.to.name)
            .flatMap { exchangeResponse ->
                userBalance.loadBalance()
                    .flatMap { currentBalance ->
                        val currencyFromBalance = currentBalance[request.exchange.from]!!
                        val calculateExchange = calculateExchange(request.exchange, currencyFromBalance)
                        if (calculateExchange.count != currencyFromBalance.count) {
                            userBalance.mutateBalance(
                                calculateExchange,
                                Exchange(
                                    BigDecimal(exchangeResponse.amount),
                                    Currency.valueOf(exchangeResponse.currency)
                                )
                            ).flatMap { result ->
                                if (result) {
                                    Single.just(
                                        createMessage(
                                            request.exchange.fromAmount,
                                            exchangeResponse.amount,
                                            currencyFromBalance.lastFee,
                                            request.exchange.from,
                                            exchangeResponse.currency
                                        )
                                    )
                                } else {
                                    Single.just("")
                                }
                            }
                        } else {
                            Single.just("")
                        }
                    }
            }
    }

    private fun createMessage(
        from: BigDecimal,
        to: String,
        fee: BigDecimal,
        currencyTo: Currency,
        currencyFrom: String
    ) =
        """Converted $from $currencyFrom from to $to $currencyTo. Fee - ${if (fee.isZero()) "Free" else fee.toPlainString() + currencyFrom}"""

    private fun calculateExchange(request: ExchangeRequest, balance: CurrencyBalance): CurrencyBalance {
        return when (balance.count) {
            in 0..4 -> balance.makeExchangeIfPossible(request)
            else -> balance.apply7PercentFee(request.fromAmount).makeExchangeIfPossible(request)
        }
    }

    data class Request(val exchange: ExchangeRequest) : InteractorSingle.Request()
}