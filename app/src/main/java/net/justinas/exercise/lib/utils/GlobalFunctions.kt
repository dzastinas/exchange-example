package net.justinas.exercise.lib.utils

import java.math.BigDecimal

fun BigDecimal.isZero() = compareTo(BigDecimal.ZERO) == 0