package no.nav.medlemskap.saga.domain

enum class RegelId(val identifikator: String, val avklaring: String, val neiBegrunnelse: String = "", val jaBegrunnelse: String = "", val uavklartBegrunnelse: String = "")
