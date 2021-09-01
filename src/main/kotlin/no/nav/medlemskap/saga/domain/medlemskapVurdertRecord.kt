package no.nav.medlemskap.saga.domain

data class medlemskapVurdertRecord(val partition:Int,val offset:Long,val value : String, val key:String,val topic:String,val json  :String)


