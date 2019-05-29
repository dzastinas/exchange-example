package net.justinas.exercise.lib.domain

import io.reactivex.Single
import net.justinas.exercise.lib.domain.remote.ExchangeResponse
import java.math.BigDecimal

interface ExchangeRepository {

    fun exhange(fromAmount: BigDecimal, fromCurrency: String, toCurrency: String): Single<ExchangeResponse>
}
