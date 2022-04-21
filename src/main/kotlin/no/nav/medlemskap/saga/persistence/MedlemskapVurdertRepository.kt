package no.nav.medlemskap.saga.persistence

import kotliquery.Row
import java.util.*
import javax.sql.DataSource
import kotliquery.queryOf
import kotliquery.sessionOf
import kotliquery.using

interface MedlemskapVurdertRepository {
    fun finnVurdering(soknadId: String): List<VurderingDao>
    fun lagreVurdering(id: String, eventDate: Date, json: String)
    fun finnVurderingMedFnr(fnr: String): List<VurderingDao>
}

class PostgresMedlemskapVurdertRepository(val dataSource: DataSource) : MedlemskapVurdertRepository {
    val INSERT_VURDERING = "INSERT INTO vurdering(soknadId, date, json) VALUES(?, ?, (to_json(?::json)))"
    val SELECT_ALL = "select * from vurdering"
    val FIND_BY_SOKNAD_ID = "select * from vurdering where soknadId = ?"
    val FIND_BY_FNR = "select * from vurdering where json->'datagrunnlag'->>'fnr' =?"

    override fun finnVurdering(soknadId: String): List<VurderingDao> {

        return using(sessionOf(dataSource)) {
                it.run(queryOf(FIND_BY_SOKNAD_ID, soknadId).map(toVurderingDao).asList)
        }
    }
    override fun finnVurderingMedFnr(fnr: String): List<VurderingDao> {

        return using(sessionOf(dataSource)) {
            it.run(queryOf(FIND_BY_FNR, fnr).map(toVurderingDao).asList)
        }
    }

        fun hentVurderinger(): List<VurderingDao> {

            return using(sessionOf(dataSource)) { session ->
                session.transaction {
                    it.run(queryOf(SELECT_ALL).map(toVurderingDao).asList)
                }

            }
        }

    override fun lagreVurdering(id: String, eventDate: Date, json: String) {
        using(sessionOf(dataSource)) { session ->
            session.transaction {
                it.run(queryOf(INSERT_VURDERING, id, eventDate, json).asExecute)
            }

        }
    }


    fun using(datasource: DataSource): PostgresMedlemskapVurdertRepository {
        return PostgresMedlemskapVurdertRepository(datasource)
    }

    val toVurderingDao: (Row) -> VurderingDao = { row ->
        VurderingDao(
            row.int("id").toString(),
            row.string("soknadId"),
            row.sqlDate("date"),
            row.string("json")
        )
    }
}