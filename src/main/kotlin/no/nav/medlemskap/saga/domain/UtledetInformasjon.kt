package no.nav.medlemskap.saga.domain

class UtledetInformasjon(
    val informasjon: Informasjon,
    val kilde: List<String> = listOf()
)