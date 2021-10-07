package no.nav.medlemskap.saga.persistence

import kotliquery.Row
import java.util.*
import javax.sql.DataSource
import kotliquery.queryOf
import kotliquery.sessionOf
import kotliquery.using
import java.sql.ResultSet

interface  MedlemskapVurdertRepository {
    fun finnVurdering(soknadId:String):String
    fun lagreVurdering(id:String,eventDate:Date,json:String)
}

 class PostgresMedlemskapVurdertRepository (val dataSource: DataSource):MedlemskapVurdertRepository{
     val INSERT_VURDERING = "INSERT INTO vurdering(soknadId, date, json) VALUES(?, ?, (to_json(?::json)))"
     val SELECT_ALL = "select * from vurdering"

    override fun finnVurdering(soknadId: String): String {
        throw NotImplementedError()
        TODO("Not yet implemented")
    }

    override fun lagreVurdering(id: String, eventDate: Date, json: String) {

        using(sessionOf(dataSource)) {session -> session.transaction{
            it.run(queryOf(INSERT_VURDERING,id,eventDate,json).asExecute) }

        }
    }
      fun hentVurderinger() :List<VurderingDao> {

         return  using(sessionOf(dataSource)) {session -> session.transaction{
              it.run(queryOf(SELECT_ALL).map(toVurderingDao).asList) }

          }
         }
     }

     fun using(datasource: DataSource): PostgresMedlemskapVurdertRepository{
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
data class VurderingDao(val id: String,val soknadId: String,val date: Date,val json: String)