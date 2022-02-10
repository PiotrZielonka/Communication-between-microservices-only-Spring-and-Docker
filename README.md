# Communication between microservices only Spring + Jdbc + Docker

## Description

This task concerns communication between microservices. It shows how one microservice can connect with another. Features of Spring microservices can be better, for instance, Hystrix, Spring Config, etc but it is not the major purpose of this application. It is also additionally a good example of docker. The application works on the way below.

There are three microservices, Credit Customer Product and one database with three tables, Credit Customer Product. There are two relationships, the first OneToOne between Credit and Customer and the second also OneToOne between Customer and Product. 

When you use the method Post localhost:8080/credits and the JSON file below.

```json
{
   "creditName": "The cash loan",
   "customerDto": {
      "firstName": "Alice",
      "surname": "Nowak",
      "pesel": "12345678901"
   },
   "productDto": {
      "name": "Ecologic",
      "productValue": 12345
   }
}
```

In this example, the Credit microservice will save data "creditName": The cash loan to the table Credit. Next, the Customer microservice will save data "firstName": Alice, "surname": Nowak, "pesel": 12345678901 to the table Customer. Next, the Product microservice will save data "name": Ecologic, "productValue": 12345 to the Product table.

When you use the method GET at localhost:8080/credits you can get all credits. It works as follows. The Credit microservice will get data from Credit table next the Customer microservice will get data from the Customer table the Product microservice will get data from the product table. The example JSON file that you will get is below.

```json
[
  {
    "id":1,
    "creditName":"credit",
    "customerDto": {
      "id":1,
      "firstName":"Alice",
      "surname":"Nowak",
      "pesel":"12345678910",
      "creditId":1
    },
    "productDto": {
      "id":1,
      "name":"Alice",
      "productValue":12345,
      "creditId":1
    }
  },
  {
    "id":2,
    "creditName":"credit2",
    "customerDto": {
      "id":2,
      "firstName":"John",
      "surname":"Smith",
      "pesel":"01987654321",
      "creditId":2
    },
    "productDto": {
      "id":2,
      "name":"John",
      "productValue":54321,
      "creditId":2
    }
  }
]
```

## Development

1. Make sure Docker is run

2. After passing all these steps and after creating the database for testing that name is taskForTest you can use this command
```
mvnw clean install
```
3. You are not having the database for testing now that's why you have to use this command below. Use it in the folder Communication between microservices only Spring + Jdbc + Docker. It will cause automatic create images of docker for all microservices and the image for Java 11 this command generates also necessary files of dto Mapstruct.
```
mvnw clean install -DskipTests
```
4. Command below used in the folder Communication between microservices only Spring + Jdbc + Docker will run all microservices, the database PostrgeSQL and tool PgAdmin in docker environment on proper ports. You don't have to install anything on your local computer.
```
docker-compose up
```
5. Under port 5050 you have to configure the connection to the database in the tool PgAdmin that Docker launched in step three.
  * Login to PgAdmin email: pgadmin4@pgadmin.org password: admin
  * Servers > Create > Server
  * General > field Name any name
  * Connection > field Hostname/address our docker's port, for instance, 192.168.99.100 or localhost
  * Port 5432
  * Maintenance database postgres
  * Username postgres
  * Password slon
  * Password slon normally it is correct
  * Save

You should have the database that name is task with three tables Credit Customer and Product. If you don't have the tables, certainly they will be after doing all the above steps again. Then you will don't have to set the server of the database. You can also add them now necessary SQL queries are in microservice Credit in the catalog src/main/resources schema.sql. copy from there and paste it to PgAdmin the three tables will be created.

6. In order to create the credit in the database use the JSON file below. In Postman use the method Post correct web address is  localhost:8080/credits. If you do any error in data input will be throwing a validation exception.
```json
{
   "creditName": "The cash loan",
   "customerDto": {
      "firstName": "Alice",
      "surname": "Nowak",
      "pesel": "12345678901"
   },
   "productDto": {
      "name": "Ecologic",
      "productValue": 12345
   }
}
```
7. Use the method GET at localhost:8080/credits in order to get all credits that they saved in the database in step 6.
8. If in docker environment doesn't work correctly the methods GET and POST it is probably caused that docker doesn't accept the name localhost at the address of endpoints. In that case, you have to change the name localhost to your port of docker in the methods saveCustomerToCustomerMicroserviceThereIsCustomerTableLogic in microservice Credit in class CreditController and in class CreditServiceImpl in the methods getProductDtoByCreditIdFromProductMicroservice and getCustomerDtoByCreditIdFromCustomerMicroservice also in microservice Credit
