package net.justinas.minilist.util

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class InteractorSingle<T, R : InteractorSingle.Request> {

    protected abstract fun create(request: R): Single<T>

    fun execute(request: R): Single<T> {
        return create(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

    }

    abstract class Request
}