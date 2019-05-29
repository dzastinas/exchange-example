package net.justinas.exercise.lib.domain

import io.reactivex.Single
import net.justinas.exercise.lib.domain.data.Currency
import net.justinas.exercise.lib.domain.data.Currency.*
import net.justinas.exercise.lib.domain.data.CurrencyBalance
import net.justinas.exercise.lib.domain.data.Exchange
import java.math.BigDecimal

class UserRepositoryLocal : BalanceRepository {

    private val euroBalance = CurrencyBalance(EUR, BigDecimal(1000.00), 0, BigDecimal.ZERO)
    private val dollarBalance = CurrencyBalance(USD, BigDecimal(0), 0, BigDecimal.ZERO)
    private val japanBalance = CurrencyBalance(JPY, BigDecimal(0), 0, BigDecimal.ZERO)

    private var allWallets = mutableMapOf(EUR to euroBalance, USD to dollarBalance, JPY to japanBalance)

    override fun loadBalance(): Single<MutableMap<Currency, CurrencyBalance>> {
        return Single.just(allWallets)
    }

    override fun mutateBalance(
        calculatedBalance: CurrencyBalance,
        exchange: Exchange
    ): Single<Boolean> {
        return Single.fromCallable { mutate(calculatedBalance, exchange) }
    }

    fun mutate(
        calculatedBalance: CurrencyBalance,
        exchange: Exchange
    ): Boolean {
        allWallets[calculatedBalance.currency] = calculatedBalance
        val currencyBalance = allWallets[exchange.currency]!!
        allWallets[exchange.currency] = currencyBalance.copy(balance = currencyBalance.balance + exchange.amount)
        return true
    }

    override fun resetBalance(): Single<MutableMap<Currency, CurrencyBalance>> {
        allWallets = mutableMapOf(EUR to euroBalance, USD to dollarBalance, JPY to japanBalance)
        return Single.just(allWallets)
    }
}