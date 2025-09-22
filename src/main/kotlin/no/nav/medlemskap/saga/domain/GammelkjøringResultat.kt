package no.nav.medlemskap.saga.domain

data class GammelkjøringResultat (
    val regelId:String,
    val svar:Svar,
    val begrunnelse:String,
    val avklaring:String,
    val årsaker:List<Årsak> =listOf(),
)