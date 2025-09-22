package no.nav.medlemskap.saga.domain

import java.time.LocalDate

data class PdlOppholdsTilatelse(
    val type:String,
    val oppholdFra:LocalDate?,
    val oppholdTil:LocalDate?
)