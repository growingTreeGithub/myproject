## Warehouse Management System

I have developed a warehouse management system that include basic warehouse information module, stock management module, permission management module.
This system can help users to record procurement request, inbound and outbound order for building materials in construction site.
It can also used to keep track of the inventory left in the warehouse.

## Environment
* Windows 10
* MySQL 8.0.19
* JDK 13.0.1
* Intellij IDEA 2019.2
* Maven 3.6.3
* Apache tomcat 9.0.31

## Key Tools used
|name|description|reference web site|
|----|-----------|-----------------|
|Spring Framework|container|https://spring.io/projects/spring-framework|
|Spring MVC|MVC framework|https://docs.spring.io/spring-framework/reference/web/webmvc.html|
|Java|coding language|https://www.java.com/zh-TW/|
|MyBatis|persistence framework|https://mybatis.org/mybatis-3/index.html|
|Maven|project management tool|https://maven.apache.org/|
|html|markup language|https://developer.mozilla.org/en-US/docs/Learn/Getting_started_with_the_web/HTML_basics|
|css|style sheet language|https://developer.mozilla.org/en-US/docs/Learn/Getting_started_with_the_web/CSS_basics|
|jquery|fast, small, and feature-rich JavaScript library|https://jquery.com/|
|jquery UI|plugins for user interface interaction|https://jqueryui.com/|
|jquery validation|plugins makes clientside form validation easy|https://jqueryvalidation.org/|

## Reminder
* Please be aware whether there are any databases named warehouse and the tables' names that appear in the warehouse project sql which may affect the exisitng table data
in your database as **there is deletion of table data in the sql script when you run the script**.
* Please amend the jdbc.username and jdbc.password in projects/warehouse/src/main/resources/main.properties file to be your database username and password. Otherwise, you cannot connect to database for interaction.
* the username and password for logining into the warehouse management system is admin and 1234. For simplicity of testing, the password of all the employee inside the system are all set to be 1234. You can also use other employee to login into the system with password 1234. Different employees have different permissions in the system.

## Deployment guide
1. Environment preparation(Install MySQL 8.0.19, jdk 13.0.1, Maven 3.6.3, Intellij IDEA 2019, apache tomcat 9.0.31 etc) and start related services. Use the default configuration and default port.
2. Clone the above project source code locally and open it. It is recommended to use Intellij IDEA to open the project and compile locally.
3. Amend the jdbc.username and jdbc.password in projects/warehouse/src/main/resources/main.properties file to be your database username and password. Otherwise, you cannot connect to database for interaction.
4. Initialize the corresponding database script warehouse project sql.sql in projects folder.
5. Compile, deploy and after the project deploys in tomcat server successfully, enter http://localhost:8080/login.jsp in broswer to have testing. Use admin as username and 1234 as password and click login button to login the system.
