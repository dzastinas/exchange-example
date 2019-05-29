package net.justinas.exercise.lib.domain

import io.reactivex.Single
import net.justinas.exercise.lib.domain.data.Currency
import net.justinas.exercise.lib.domain.data.CurrencyBalance
import net.justinas.exercise.lib.domain.data.Exchange

interface BalanceRepository {

    fun loadBalance(): Single<MutableMap<Currency, CurrencyBalance>>
    fun resetBalance(): Single<MutableMap<Currency, CurrencyBalance>>
    fun mutateBalance(calculatedBalance: CurrencyBalance, exchange: Exchange): Single<Boolean>
}
