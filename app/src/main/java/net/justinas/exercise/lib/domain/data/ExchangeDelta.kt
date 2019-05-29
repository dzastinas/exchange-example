package net.justinas.exercise.lib.domain.data

import java.math.BigDecimal

data class ExchangeDelta(val count: Int, val balance: BigDecimal, val currency: Currency)