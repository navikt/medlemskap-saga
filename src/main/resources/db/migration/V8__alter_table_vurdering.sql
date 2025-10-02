ALTER TABLE vurdering_analyse
    RENAME COLUMN foersteDagForYtelse TO foerste_dag_for_ytelse,
    RENAME COLUMN startDatoForYtelse TO start_dato_for_ytelse,
    RENAME COLUMN nyeSpoersmaal TO nye_spoersmaal,
    RENAME COLUMN antallDagerMedSykmelding TO antall_dager_med_sykmelding,
    RENAME COLUMN arbeidUtenforNorge TO arbeid_utenfor_norge,
    RENAME COLUMN utfoertArbeidUtenforNorgeTag TO utfoert_arbeid_utenfor_norge,
    RENAME COLUMN oppholdUtenforEOSTag TO opphold_utenfor_eos,
    RENAME COLUMN oppholdUtenforNorgeTag TO opphold_utenfor_norge,
    RENAME COLUMN oppholdstillatelseOppgittTag TO oppholdstillatelse_oppgitt,
    RENAME COLUMN oppholdstillatelseUDIFom TO oppholdstillatelse_udi_fom,
    RENAME COLUMN oppholdstillatelseUDITom TO oppholdstillatelse_udi_tom,
    RENAME COLUMN oppholdstillatelseUDIType TO oppholdstillatelse_udi_type;