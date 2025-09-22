package no.nav.medlemskap.saga.domain

import java.time.LocalDate

data class Statsborgerskap(
    val landkode:String,
    val fom:LocalDate?,
    val tom:LocalDate?,
    val historisk: Boolean
)