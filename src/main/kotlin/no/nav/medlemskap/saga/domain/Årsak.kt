package no.nav.medlemskap.saga.domain

data class Årsak(val regelId: RegelId, val avklaring: String, val svar: Svar) {
}