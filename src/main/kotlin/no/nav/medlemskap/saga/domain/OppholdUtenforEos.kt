package no.nav.medlemskap.saga.domain

data class OppholdUtenforEos(
    val id: String,
    val sporsmalstekst: String?,
    val svar:Boolean,
    val oppholdUtenforEOS:List<Opphold>
)