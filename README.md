# Backend dev technical test - JSR

We want to offer a new feature to our customers showing similar products to the one they are currently seeing. To do this we agreed with our front-end applications to create a new REST API operation that will provide them the product detail of the similar products for a given one. [Here](./similarProducts.yaml) is the contract we agreed.

We already have an endpoint that provides the product Ids similar for a given one. We also have another endpoint that returns the product detail by product Id. [Here](./existingApis.yaml) is the documentation of the existing APIs.

![Diagram](./assets/diagram.jpg "Diagram")


## Setting up the enviroment

**1.** First of all, you must download this git repository into your computer.

**2.** After you have downloaded the repository, you have to enter the folder "PruebaNivelMCA/api", where the developed application is located.

**3.** We have to download the dependencies and build the project:
```
    mvn clean install
```
We can also test the project is working properly running the simulation component and the java application before creating the Docker image(remember to change the "mock.urls" property when running locally in application.properties to "http://localhost:3001"):
```
  docker-compose up -d simulado

  java -jar target/api-1.0.0.jar
```
After validating, make sure you stop the java application, and delete the container "simulado":
```
  docker-compose down simulado
```

**4.** Now we will create the Docker image of the Java application, by running the following commands (returning the value of "mock.urls" to the previous "simulado"):
  ```
     docker build --tag api-products .
  ```
**5.** In order to validate the creation of the image, we will run the following command:
  ```
     docker images

    REPOSITORY                 TAG        IMAGE ID       CREATED             SIZE
    api-products               latest     333577453143   About an hour ago   559MB

  ```
**6.** After creating and validating the creation of the image, we will create the containers of the project:
  ```
  docker-compose up -d simulado influxdb grafana api
  ```

Now the application is operative and you can try the endpoint **localhost:5000/product/{productId}/similar**, where "productId" is the product you want to search for similar products. For further information, you can access http://localhost:5000/swagger-ui/index.html.




