# Revolut
Simple Money Transaction API

## Short intro
Application uses Spark Java framework, H2 in memory database, Flyway for data migration, Hibernate ORM.

Unit tests done using Spock framework.

## Usage
Application runs on standard port - 4567  

API endpoints:
```
 /api/v1/transfer
```

Sample request:
```
{
    "remitter": {
        "id": 1,
        "accountId": "7469651245"
    },
    "beneficiary": {
        "name": "Sharon Rowe",
        "accountId": "3876505146"
    },
    "amount": 100,
    "details": "To do some business"
}
```

## Author
> Laurynas Kačinauskas