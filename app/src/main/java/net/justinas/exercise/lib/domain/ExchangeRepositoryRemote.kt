package net.justinas.exercise.lib.domain

import io.reactivex.Single
import net.justinas.exercise.lib.domain.remote.ExchangeApi
import net.justinas.exercise.lib.domain.remote.ExchangeResponse
import java.math.BigDecimal

class ExchangeRepositoryRemote(private val api: ExchangeApi) : ExchangeRepository {

    override fun exhange(fromAmount: BigDecimal, fromCurrency: String, toCurrency: String): Single<ExchangeResponse> {
        return api.getExchange(fromAmount.toPlainString(), fromCurrency, toCurrency)
    }
}