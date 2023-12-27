
Java Version: Oracle OpenJDK Version 18.0.1

Additional Comments:

Project Title: Inventory Manager
--------------

Instructions
--------------
Use Gradle for JUnit testing and database dependencies

Uses local MySQL Database <br>
Connector: mysql-connector-java:8.0.30

Run the SQLScript to generate the databse in MySQL
Edit the user and password in ConnectDatabase.java file located in java/com/database directory

Recommend using IntelliJ to run code.<br>
On IntelliJ, run ManagerDriver.java to start application.


MySQL Schema<br>
--------------


Java Classes UML<br>
--------------
![Screenshot](imgs/InventoryManagerUML.png)

Features<br>
--------------
• Items: <br>
    &emsp;&emsp;&emsp;&emsp;-Add/Remove inventory of items <br>
    &emsp;&emsp;&emsp;&emsp;-Edit item sales prices <br>

• Sales Invoice: <br>
    &emsp;&emsp;&emsp;&emsp;-Able to input sales invoice information <br>
    &emsp;&emsp;&emsp;&emsp;-Can add multiple items to invoice or remove Items <br>
    &emsp;&emsp;&emsp;&emsp;-Generate total of item type with quantity and subtotals for items with quantity designator <br>
    &emsp;&emsp;&emsp;&emsp;-Link invoice to specific customer <br>

• Customer: <br>
    &emsp;&emsp;&emsp;&emsp;-Able to add customers <br>
    &emsp;&emsp;&emsp;&emsp;-Get total of customer purchases <br>


