package no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput

data class OppholdUtenforEOSTag(
    val oppholdUtenforEØS: Boolean,
    val oppholdUtenforEØSLand: String,
    val oppholdUtenforEØSFom: String,
    val oppholdUtenforEØSTom: String,
    val oppholdUtenforEØSAntallPerioder: Int,
    val oppholdUtenforEØSGrunn: String,
) {
    constructor(oppholdUtenforEØS: OppholdUtenforEOS) : this(
        oppholdUtenforEØS.oppholdUtenforEØSOppgitt(),
        oppholdUtenforEØS.ppholdUtenforEØSFørsteOppgitteLand(),
        oppholdUtenforEØS.oppholdUtenforEØSFørsteOppgitteFom(),
        oppholdUtenforEØS.oppholdUtenforEØSFørsteOppgitteTom(),
        oppholdUtenforEØS.oppholdUtenforEØSAntallPerioderOppgitt(),
        oppholdUtenforEØS.oppholdUtenforEØSFørsteOppgitteGrunn()
    )
}
