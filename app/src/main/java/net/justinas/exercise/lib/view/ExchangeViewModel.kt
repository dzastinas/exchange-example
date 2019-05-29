package net.justinas.exercise.lib.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import net.justinas.exercise.lib.domain.data.Currency
import net.justinas.exercise.lib.domain.data.CurrencyBalance
import net.justinas.exercise.lib.domain.data.ExchangeRequest
import net.justinas.exercise.lib.intent.DisplayException
import net.justinas.exercise.lib.intent.ExchangeInteractor
import net.justinas.exercise.lib.intent.GetBalanceInteractor
import net.justinas.exercise.lib.intent.ResetBalanceInteractor
import net.justinas.exercise.lib.utils.isZero
import net.justinas.minilist.util.LoadResult
import java.math.BigDecimal

class ExchangeViewModel(
    private val getBalanceInteractor: GetBalanceInteractor,
    private val exchangeInteractor: ExchangeInteractor,
    private val resetBalanceInteractor: ResetBalanceInteractor
) : ViewModel() {

    var result = MutableLiveData<LoadResult<String>>()
    val balance = MutableLiveData<MutableMap<Currency, CurrencyBalance>>()
    private val disposable = CompositeDisposable()

    val selectedFromPosition = MutableLiveData<Int>()
    val selectedToPosition = MutableLiveData<Int>()
    val from = MutableLiveData<String>()

    init {
        loadBalance()
    }

    private fun loadBalance() {
        disposable.add(
            getBalanceInteractor.execute(GetBalanceInteractor.Request())
                .subscribeBy(
                    onSuccess = {
                        balance.postValue(it)
                    },
                    onError = { result.postValue(LoadResult.Error(it)) })
        )
    }

    fun reset() {
        result.postValue(LoadResult.Loading)
        disposable.add(
            resetBalanceInteractor.execute(ResetBalanceInteractor.Request())
                .subscribeBy(
                    onSuccess = {
                        result.postValue(LoadResult.Success(""))
                        balance.postValue(it)
                    },
                    onError = { result.postValue(LoadResult.Error(it)) })
        )
    }

    fun exchange() {
        if (formValid()) {
            result.postValue(LoadResult.Loading)
            disposable.add(
                exchangeInteractor.execute(
                    ExchangeInteractor.Request(
                        ExchangeRequest(
                            from.value?.toBigDecimalOrNull() ?: BigDecimal.ZERO,
                            Currency.values()[selectedFromPosition.value ?: 0],
                            Currency.values()[selectedToPosition.value ?: 0]
                        )
                    )
                ).subscribeBy(
                    onSuccess = {
                        result.postValue(LoadResult.Success(it))
                        loadBalance()
                    },
                    onError = { result.postValue(LoadResult.Error(it)) })
            )
        }
    }

    private fun formValid(): Boolean {
        val validationMessage = getValidationMessage(selectedFromPosition.value, selectedToPosition.value, from.value)
        return when {
            validationMessage.isBlank() -> true
            else -> {
                result.postValue(LoadResult.Error(DisplayException(validationMessage)))
                false
            }
        }
    }

    private fun getValidationMessage(from: Int?, to: Int?, amount: String?): String {
        return when {
            from == null || from == to -> "Same currency"
            amount?.toBigDecimalOrNull() == null -> "Wrong number"
            amount.toBigDecimalOrNull()?.isZero() == true -> "Zero amount"
            else -> ""
        }
    }


    fun getBalance(map: MutableMap<Currency, CurrencyBalance>?, currency: Currency?): String =
        if (currency != null && map != null) {
            "${map[currency]?.balance?.toPlainString()} ${currency.name}"
        } else {
            "0 $currency"
        }

    fun getTotalFee(map: MutableMap<Currency, CurrencyBalance>?, currency: Currency?): String =
        if (currency != null && map != null) {
            "${map[currency]?.feeTotal?.toPlainString()} ${currency.name}"
        } else {
            "0 $currency"
        }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}