# Quarkus Warehouse

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```
or 
```shell script
mvn quarkus:dev 
```

## Packaging and running the application

The application can be packaged using:
```shell script
./mvn clean install
```

The application is now runnable using `java -jar target/quarkus-warehouse-runner.jar`.

# RESTful API:
There are 5 type of RESTful api in Warehouse App:

* ``GET`` : `/article/list`: Return list of articles
* ``GET`` : `/product/list`: Return list of products
* ``PUT`` : `/product/sell/{id}`: Remove(Sell) a product and update the article accordingly
    * http `200`: In case of success sell operation
    * http `400`: In case of fail sell operation, will return array of errors object

* ``POST`` : `/importer/inventory`: Import articles from an inventory json file. the sample file can be found from 
    ``src/main/resources/META-INF/resources/sample/inventory.json`` 
    * http `200`: In case of success import of all articles
    * http `400`: In case of fail import even for one article,it will return array of errors object for each article object that is mentioned in the file.
* ``POST`` : `/importer/products`: Import product from a product json file. the sample file can be found from 
    ``src/main/resources/META-INF/resources/sample/products.json`` 
    * http `200`: In case of success import of all articles
    * http `400`: In case of fail import even for one article,it will return array of errors object for each article object that is mentioned in the file.
 
## Errors Object:
In case of processing the service with errors,the application will generate an array of errors objects 
that format is followed by the following object: 
```
[
  {
    "code": string,
    "description": string,
    "param": string,
  }
]
```

###Error Object Example:
---

```
[
    {
      "code": "INVALID_VALUE",
      "param": "name",
      "description": "Name is required"
    },
    {
      "code": "INVALID_VALUE",
      "param": "stock",
      "description": "stock must be only digits"
    }
]
``` 


