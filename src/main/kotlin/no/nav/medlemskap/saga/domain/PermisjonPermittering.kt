package no.nav.medlemskap.saga.domain

data class PermisjonPermittering(
    val permisjonPermitteringId: String,
    val prosent: Double?,
    val varslingskode: String?
)