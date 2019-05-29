package net.justinas.exercise.lib.domain.data

import net.justinas.exercise.lib.intent.DisplayException
import net.justinas.exercise.lib.utils.isZero
import java.math.BigDecimal

data class CurrencyBalance(val currency: Currency, val balance: BigDecimal, val count: Int, val feeTotal: BigDecimal, val lastFee: BigDecimal = BigDecimal.ZERO) {

    fun makeExchangeIfPossible(request: ExchangeRequest): CurrencyBalance {
        val futureBalance = balance - request.fromAmount
        return if (futureBalance >= BigDecimal.ZERO) {
            copy(balance = futureBalance, count = count + 1)
        } else {
            throw DisplayException("Not enough balance")
        }
    }

    fun apply7PercentFee(fromAmount: BigDecimal): CurrencyBalance {
        if (balance.isZero()) {
            throw DisplayException("Not enough balance")
        }
        val fee = fromAmount.multiply(BigDecimal(7)).divide(BigDecimal(100))
        val futureBalance = balance - fee
        return if (futureBalance > BigDecimal.ZERO) {
            copy(balance = futureBalance, feeTotal = feeTotal + fee, lastFee = fee)
        } else {
            throw DisplayException("Not enough balance")
        }
    }
}
