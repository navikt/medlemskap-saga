# medlemskap-saga

Medlemskap Saga komponent som lagrer/oppdaterer status på medlemskap

## Avhengigheter

* Postgres database
    * Tabeller:
        - vurdering
        - vurdering_analyse
* Kafka

# API-er i medlemskap-saga

medlemskap-saga tilbyr 2 API-er.

1. SagaRoute - for lagring av vurderinger og vurderinger for analyse
2. AnalyseRoute - for henting av vurderinger og lagre til CSV-fil i Google Cloud Storage (GCS)
3. NullstillTestdataTestRoute - for sletting av testdata (kun tilgjengelig i dev-gcp)

## SagaRoute - Lagring av vurderinger

https://medlemskap-vurdering.intern.nav.no/findVureringerByFnr

### Eksempel på kall

Kallet er en POST mot url definert over

```
{
"fnr":"12345678912",
}
```

https://medlemskap-vurdering.intern.nav.no/vurdering

### Eksempel på kall

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

## AnalyseRoute - Generere uttrekksfil for vurderinger til analyse og lagre til GCS

| Endepunkt                                                                            | Konsument          | Miljø    |
|--------------------------------------------------------------------------------------|--------------------|----------|
| POST https://medlemskap-vurdering.intern.nav.no/analyse/hentUttrekk/{aarMaanedParam} | medlemskap-analyse | prod-gcp |
| POST https://medlemskap-vurdering.intern.dev.nav.no/analyse/hentUttrekk/{aarMaanedParam} | medlemskap-analyse | dev-gcp  |

### Formål
Formålet med API-et er å generere CSV-fil med vurderinger for gitt år og måned, og lagre denne filen i en spesifikk
Google Cloud Storage (GCS) bøtte for videre analyse. Filer vil være nedlastbare fra Buckets i Google Cloud etter
at de er opprettet.

I produksjon er det kun mulig å generere uttrekks-filer gjennom medlemskap-analyse komponenten for å sikre begrenset tilgang.
I test kan endepunktet kalles direkte for å generere fil, forutsatt at Bearer-tokenet har korrekt scope. medlemskap-analyse 
kan også benyttes i test ved å konfigurere en Trygdeetaten-bruker som brukes til innlogging.


### Eksempel på kall

curl -X POST  https://medlemskap-vurdering.intern.dev.nav.no/analyse/hentUttrekk/202510 \
-H "Authorization: Bearer DITT_TOKEN_HER"

## NullstillTestdataTestRoute - Sletting av testdata (kun dev-gcp)

| Endepunkt                                                                   | Miljø   |
|-----------------------------------------------------------------------------|---------|
| POST https://medlemskap-vurdering.intern.dev.nav.no/test/slett-vurdering    | dev-gcp |

### Formål
Endepunktet sletter alle vurderinger og analyse-rader for et gitt fødselsnummer fra begge tabellene (`vurdering` og `vurdering_analyse`). 
Kun tilgjengelig i dev-gcp for nullstilling av testdata.

### Eksempel på kall

Kallet er en POST mot url definert over

```
curl -X POST https://medlemskap-vurdering.intern.dev.nav.no/test/slett-vurdering \
  -H "Authorization: Bearer DITT_TOKEN_HER" \
  -H "Content-Type: application/json" \
  -d '{"fnr": "12345678912"}'
```

### Eksempel på respons

```json
{
  "fnr": "12345678912",
  "slettetVurderinger": 3,
  "slettetVurderingAnalyse": 3
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

DB_PASSWORD: <db_password>

DB_PORT: 5432

DB_USERNAME:medlemskap-saga

## start docker image for postgres

docker run --name lovme_postgres -e POSTGRES_USER=medlemskap-saga -e POSTGRES_PASSWORD=-<db_password> -e POSTGRES_DB=medlemskap -v postgres_data:/var/lib/postgresql/data -p 5432:5432 -d postgres:12

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


# Database
medlemskap-saga har en database som heter `medlemskap` som inneholder tabellene `vurdering`
og `vurdering_analyse`. Databasen er tilgjengelig i både dev-gcp og prod-gcp, og det benyttes Flyway for migrering av databasen.


## Steg 0: Få personlig tilgang (gjøres kun 1 gang ⚠️)
Gir brukeren din tilgang til databasen. Dette steget trengs kun å gjøres en gang. Dersom brukeren din
allerede har tilgang, kan du hoppe over dette steget og gå videre til steg 1.
```bash
nais postgres grant --team medlemskap --environment dev-gcp medlemskap-saga
```

## Steg 1: Velg riktig tilgangsnivå

Velg **én** av følgende basert på behov:

### Steg 1a – Lesetilgang (standard)
Brukes for feilsøking og innsikt i data:

```bash
nais postgres prepare --team medlemskap --environment dev-gcp medlemskap-saga
```

### Steg 1b – Skrivetilgang (kun ved behov ⚠️)

Brukes kun når det er nødvendig å endre data:
```bash
nais postgres prepare --team medlemskap --environment dev-gcp medlemskap-saga --all-privileges
```

## Steg 2: Koble til databasen
```bash
nais postgres proxy --team medlemskap --environment dev-gcp --reason "debugging issues" medlemskap-saga
```

## Steg 3: Rydd opp etter deg ⚠️
Når du er ferdig skal du fjerne tilgangsnivået du fikk tildelt i Steg 1.
```bash
nais postgres revoke --team medlemskap --environment dev-gcp medlemskap-saga
```

**Er du usikker på hvilken tilgang du har?**

Du kan sjekke hvilke rettigheter som din bruker har ved å kjøre følgende SQL-spørring i databasen:

```bash
SELECT
    has_table_privilege(current_user, 'vurdering', 'SELECT') AS can_select,
    has_table_privilege(current_user, 'vurdering', 'INSERT') AS can_insert,
    has_table_privilege(current_user, 'vurdering', 'UPDATE') AS can_update,
    has_table_privilege(current_user, 'vurdering', 'DELETE') AS can_delete;
```

**Teamets ansvar ved skrivetilgang ⚠️**

I teamet skal du ikke ha behov for å gi deg selv skrivetilgang til _prod-gcp_. Hvis du har behov for det, skal
teamet informeres og tilgangen skal loggføres i [adgangsoversikten](http://confluence.adeo.no/spaces/TLM/pages/800081767/Loggf%C3%B8ring+av+skrivetilgang+til+databaser+i+produksjon) med dato og tjenestelig formål.




