package net.justinas.exercise.lib

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.Single
import net.justinas.exercise.lib.domain.data.Currency
import net.justinas.exercise.lib.domain.data.CurrencyBalance
import net.justinas.exercise.lib.intent.ExchangeInteractor
import net.justinas.exercise.lib.intent.GetBalanceInteractor
import net.justinas.exercise.lib.intent.ResetBalanceInteractor
import net.justinas.exercise.lib.util.any
import net.justinas.exercise.lib.view.ExchangeViewModel
import net.justinas.minilist.util.LoadResult
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.math.BigDecimal

class ShowLoadBalanceTest {

    @Mock
    private lateinit var balanceInteractor: GetBalanceInteractor
    @Mock
    private lateinit var exchangeInteractor: ExchangeInteractor
    @Mock
    private lateinit var resetInteractor: ResetBalanceInteractor

    private lateinit var viewModel: ExchangeViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    @Throws(Exception::class)
    fun `Give init block Should load balance`() {
        // Given
        val euroBalance = CurrencyBalance(Currency.EUR, BigDecimal(2000.00), 0, BigDecimal.ZERO)
        val dollarBalance = CurrencyBalance(Currency.USD, BigDecimal(0), 0, BigDecimal.ZERO)
        val japanBalance = CurrencyBalance(Currency.JPY, BigDecimal(0), 0, BigDecimal.ZERO)
        val allWallets2 =
            mapOf(Currency.EUR to euroBalance, Currency.USD to dollarBalance, Currency.JPY to japanBalance)

        `when`(balanceInteractor.execute(any())).thenReturn(Single.just(allWallets2.toMutableMap()))

        //When
        viewModel = ExchangeViewModel(balanceInteractor, exchangeInteractor, resetInteractor)

        // Should
        viewModel.balance.observeForever {
            if (it is LoadResult.Success<*>) {
                val result = it.data as MutableMap<Currency, CurrencyBalance>
                MatcherAssert.assertThat(result, `is`(allWallets2))
            }

        }
        Mockito.verify(balanceInteractor).execute(any())
    }
}