package net.justinas.exercise.lib.domain.remote;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path

interface ExchangeApi {

    @GET("currency/commercial/exchange/{fromAmount}-{fromCurrency}/{toCurrency}/latest")
    fun getExchange(
        @Path("fromAmount") fromAmount: String,
        @Path("fromCurrency") fromCurrency: String,
        @Path("toCurrency") toCurrency: String
    ): Single<ExchangeResponse>
}
