# vacancy-explorer
[![license](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

Job search can help you expand your knowledge about required qualities, technologies and opportunies even without attending any interview.

Current job search UX (on sites I check) looks like simplistic text search.

This project should provide backend API that can be used to experiment and build job-specific search features and custom UX.

## Scope of project

This is backend API project. With HTTP as protocol.

CRUD operations for vacancy descriptions.

Search operation with parameters to filter results.

NO site-specific adapters, crawlers, integrations.

## Build & test instuctions

```
make build
```

```
make test
```

## Run & use instructions

```
make run
```

To add vacancy:

```
curl localhost:8080/vacancies \
     --request POST \
     --header 'Content-type: application/json' \
     --data '{"description":"Simple description"}'
```

Open [corresponding URL](http://localhost:8080/vacancies/1) in your browser to see vacancy data.


To delete vacancy by id:

```
curl localhost:8080/vacancies/1 \
     --request DELETE
```


Ctrl-c to stop application.