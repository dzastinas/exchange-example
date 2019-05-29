package net.justinas.exercise.lib.intent

import io.reactivex.Single
import net.justinas.exercise.lib.domain.BalanceRepository
import net.justinas.exercise.lib.domain.data.Currency
import net.justinas.exercise.lib.domain.data.CurrencyBalance
import net.justinas.minilist.util.InteractorSingle

class GetBalanceInteractor(private val userBalance: BalanceRepository) :
    InteractorSingle<MutableMap<Currency, CurrencyBalance>, GetBalanceInteractor.Request>() {

    override fun create(request: Request): Single<MutableMap<Currency, CurrencyBalance>> {
        return userBalance.loadBalance()
    }

    class Request : InteractorSingle.Request()
}