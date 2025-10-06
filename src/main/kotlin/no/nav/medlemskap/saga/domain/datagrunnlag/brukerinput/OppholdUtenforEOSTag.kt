package no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput

data class OppholdUtenforEOSTag(
    val oppholdUtenforEØS: Boolean,
    val oppholdUtenforEØSLand: String,
    val oppholdUtenforEØSFom: String,
    val oppholdUtenforEØSTom: String,
    val oppholdUtenforEØSAntallPerioder: Int,
    val oppholdUtenforEØSGrunn: String,
) {
    companion object {
        fun fra(oppholdUtenforEØS: OppholdUtenforEOS?): OppholdUtenforEOSTag? {
            if (oppholdUtenforEØS == null) return null

            return OppholdUtenforEOSTag(
                oppholdUtenforEØS.oppholdUtenforEØSOppgitt(),
                oppholdUtenforEØS.ppholdUtenforEØSFørsteOppgitteLand(),
                oppholdUtenforEØS.oppholdUtenforEØSFørsteOppgitteFom(),
                oppholdUtenforEØS.oppholdUtenforEØSFørsteOppgitteTom(),
                oppholdUtenforEØS.oppholdUtenforEØSAntallPerioderOppgitt(),
                oppholdUtenforEØS.oppholdUtenforEØSFørsteOppgitteGrunn()
            )
        }
    }
}
