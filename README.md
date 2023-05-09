# medlemskap-saga
Medlemskap Saga komponent som lagrer/oppdaterer status på medlemskap


#Avhengigheter

* Database (Postgres)
* Kafka

# Rest endepunkter
https://medlemskap-vurdering.intern.nav.no/findVureringerByFnr
## Eksempel på kall

Kallet er en POST mot url definert over
```
{
"fnr":"12345678912",
}
```
https://medlemskap-vurdering.intern.nav.no/vurdering
## Eksempel på kall

Kallet er en POST mot url definert over
```
{
  "fnr" : "03118041401",
  "førsteDagForYtelse" : "2022-05-11",
  "periode" : {
    "fom" : "2022-05-11",
    "tom" : "2022-05-29"
  },
  "ytelse" : "SYKEPENGER"
}
```


# Testing
Tester bruker test containers for å verifisere lagring til database.


Det er ikke satt opp tester som tester kafka integrasjonen

# kjøring lokalt

komponenten kan kjøres lokalt, men vær klar over at det vil (potensielt) medføre at dokumenter kan være
lagret i lokal database (se egen seksjon for det) mens dokumenter vil bli lagret i JOARK.

Dette vil selvsagt kun skje dersom man leser fra Kafka på Aiven som også andre microtjenetser lytter på.


##sett opp miljø variable i windows eller lokalt på mac

DB_DATABASE:medlemskap

DB_HOST:127.0.0.1

DB_PASSWORD:-vRp4enzQSByToEPG-_iBhnF1EPqo7lHzUfXaAJIGTY

DB_PORT: 5432

DB_USERNAME:medlemskap-saga

## start docker image for postgres

docker run --name lovme_postgres -e POSTGRES_USER=medlemskap-saga -e POSTGRES_PASSWORD=-vRp4enzQSByToEPG-_iBhnF1EPqo7lHzUfXaAJIGTY -e POSTGRES_DB=medlemskap -v postgres_data:/var/lib/postgresql/data -p 5432:5432 -d postgres:12




#start kafka instans lokalt
lag følgende docker-compose.yaml fil

```

version: '2'
services:
zookeeper:
image: confluentinc/cp-zookeeper:latest
environment:
ZOOKEEPER_CLIENT_PORT: 2181
ZOOKEEPER_TICK_TIME: 2000
ports:
- 22181:2181

kafka:
image: confluentinc/cp-kafka:latest
depends_on:
- zookeeper
ports:
- 29092:29092
environment:
KAFKA_BROKER_ID: 1
KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka:9092,PLAINTEXT_HOST://localhost:29092
KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT,PLAINTEXT_HOST:PLAINTEXT
KAFKA_INTER_BROKER_LISTENER_NAME: PLAINTEXT
KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
```

kjør så kommando
```
docker-compose up -d 
```
Filen kan ligge hvor som helst

#sletting av docker volumer etter kjøring

merk! kommandor sletter alt ! : docker system prune -a --volumes


