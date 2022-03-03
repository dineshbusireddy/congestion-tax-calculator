# Congestion Tax Calculator

Welcome the Volvo Cars Congestion Tax Calculator assignment.

This repository contains a developer [assignment](ASSIGNMENT.md) used as a basis for candidate intervew and evaluation.

Clone this repository to get started. Due to a number of reasons, not least privacy, you will be asked to zip your solution and mail it in, instead of submitting a pull-request.

## Steps to verify Solution
**Command to build and up the server**

_Note_: This command by default uses Gothenburg tax rules(which will use application-GB.yml file) 
> mvn spring-boot:run

Or
**Command to build with Unit test cases and up server**
> mvn clean install && java -jar target/congestion-tax-calculator-1.0-SNAPSHOT.jar

**Commands to test for other city example: UK**
> mvn spring-boot:run -Dspring-boot.run.profiles=UK

- Unit testcase results exported as html file in the path **/source_files/Test_Results_CongestionTaxControllerTest.html**

  ![Alt text](https://github.com/dineshbusireddy/congestion-tax-calculator/blob/master/source_files/JUnits.PNG "JUnits")
- Server port is: **8585**
- api resource path: **/api/v1/congestion-tax/calculator**
  
  Ex: **http://localhost:8585/api/v1/congestion-tax/calculator**

  ![Alt text](https://github.com/dineshbusireddy/congestion-tax-calculator/blob/master/source_files/postman.PNG "Postman")  
- Documentation path(Swagger): **http://localhost:8585/swagger-ui.html**

  ![Alt text](https://github.com/dineshbusireddy/congestion-tax-calculator/blob/master/source_files/documentation.PNG "Swagger UI")
  
**Sample payloads:**

**_Request_**
````
{
  "vehicleType": "Car",
  "dates": [
   "2013-02-27T07:59:03.836Z",
   "2013-02-27T08:01:03.836Z",
   "2013-02-27T09:03:03.836Z",
   "2013-02-27T10:04:03.836Z",
   "2013-02-27T11:05:03.836Z",
   "2013-02-27T14:59:03.836Z",
   "2013-02-27T15:29:03.836Z",
   "2013-02-27T15:30:03.836Z",
   "2013-02-27T16:00:03.836Z",
   "2013-02-27T17:00:03.836Z",
   "2013-02-28T18:00:03.836Z"
  ]
}
````
**_Response_**
````
{
  "tax": 68,
  "taxWithCurrency": "68 SEK",
  "errors": null
}
````

