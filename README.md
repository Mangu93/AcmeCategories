# AcmeCategories

This microservice is used to both receive and return information about products and their categories, storing it into a MongoDb collection

## Development

To start your application in the dev profile, run:

    ./gradlew

Before that, don't forget to run the MongoDb container described in `src/main/docker/docker-compose.yml` with

    docker-compose up -d
