package net.justinas.exercise.lib.domain.data

import java.math.BigDecimal

data class ExchangeRequest(val fromAmount: BigDecimal, val from: Currency, val to: Currency)