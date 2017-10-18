# The Security Project

## About

Describe your project here.

## Prerequisites

- Java 1.8+
- Maven 3.3+
- Node.js, Grunt and Bower
- spring boot 1.4.0
- Mybatis 1.1.1 持久层数据访问框架
- Swagger 2.2.2 方便rest api测试
- json-lib 2.4
- Mysql
- JUnit

## 运行方法

### 开发环境
``mvn spring-boot:run -Dspring.profiles.active=dev``

或

``java -Dspring.profiles.active=dev -jar target/*.jar``

### 测试环境
``mvn spring-boot:run -Dspring.profiles.active=test``

或

``java -Dspring.profiles.active=test -jar target/*.jar``

### 线上环境
``mvn spring-boot:run -Dspring.profiles.active=prod``

或

``java -Dspring.profiles.active=prod -jar target/*.jar``

### 运行命令简化版
``mvn spring-boot:run``

## 打包命令
``mvn clean package``

## 更新pom依赖关系:
执行maven命令mvn dependency:tree

## License

This software is licensed under the [BSD License][BSD]. For more information, read the file [LICENSE](LICENSE).

[BSD]: https://opensource.org/licenses/BSD-3-Clause
