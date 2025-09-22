package no.nav.medlemskap.saga.domain

import java.util.Date

data class Periode(val fom: String, val tom: String= Date().toString())