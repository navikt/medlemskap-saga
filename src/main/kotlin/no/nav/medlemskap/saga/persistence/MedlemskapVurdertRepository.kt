package no.nav.medlemskap.saga.persistence

import java.util.*
import javax.sql.DataSource
import kotliquery.Row
import kotliquery.queryOf
import kotliquery.sessionOf
import kotliquery.using

interface  MedlemskapVurdertRepository {
    fun finnVurdering(id:String):String
    fun lagreVurdering(id:String,eventDate:Date,json:String)
}

 class PostgressMedlemskapVurdertRepository (val dataSource: DataSource):MedlemskapVurdertRepository{
     val INSERT_VURDERING = "INSERT INTO vurdering(id, date, json) VALUES(?, ?, (to_json(?::json)))"

    override fun finnVurdering(id: String): String {
        throw NotImplementedError()
        TODO("Not yet implemented")
    }

    override fun lagreVurdering(id: String, eventDate: Date, json: String) {

        using(sessionOf(dataSource)) {session -> session.transaction{
            it.run(queryOf(INSERT_VURDERING,id,eventDate,json).asExecute) }

        }
    }

     fun using(datasource: DataSource): PostgressMedlemskapVurdertRepository{
         return PostgressMedlemskapVurdertRepository(datasource)
     }

}