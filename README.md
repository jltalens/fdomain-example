### Functional domain modelling example

This is just me learning some domain modelling the functional way with:

* [http4s|
* fs2
* Free monads with cats

For now it just exposes and endpoint with a canned response.

# Running it
```
# sbt run
```

# API

```
# curl http://localhost:8888/api/domain-example/101

{"userId":{"id":"user 123"},"status":"Created","timestamp":"2018-03-31T12:07:12.29","externalAccountId":321}
```

I'll add more endpoints, interactions with other bounded contexts and tests as I go along.