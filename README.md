
# ToDoApp

ToDo application with both backend and frontend implementations.



**Backend:** Spring Boot with JWT Auth mechanism

**Frontend:** Vue.js



### Folder Structure


    .
    ├── todoserver                  # Java Spring Boot project                     
    ├── todoapp                     # Vue.js project (Vue3)
    ├── docker-compose.yml          # Docker Compose
    └── README.md  


## Installation

To install **TodoApp**, follow these steps:

* Download projects


* Go `./todoserver` folder
```bash
  cd todoserver
```

* Build `todoserver` project and copy jar to `release` folder 
```bash
  # Linux and macOS
  
  ./mvnw clean package -DskipTests
  
  cp target/todo-server-0.0.1.jar release

  -----------------------------------------------------------------------------

  # Windows  
  
  ./mvnw.cmd clean package -DskipTests
  
  copy target/todo-server-0.0.1.jar release
```
* Go back to `.` folder
```bash
  cd ..
```
* Run Docker compose
```bash
  docker-compose up --build -d
```



#### API Documentation (Swagger)

```bash
  http://localhost:8000/swagger-api
```

#### Vue.js application

```bash

  http://localhost:8080/

```
