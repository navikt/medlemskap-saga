package no.nav.medlemskap.saga.domain

data class OppholdUtenforNorge(
    val id: String,
    val sporsmalstekst: String?,
    val svar:Boolean,
    val oppholdUtenforNorge:List<Opphold>
)