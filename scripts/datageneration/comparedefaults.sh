#!/bin/bash

listOfSchemas="BankAccount
BookTown
Cloc
CoffeeOrders
CustomerOrder
DellStore
Employee
Examination
Flights
FrenchTowns
Inventory
Iso3166
JWhoisServer
NistDML181
NistDML182
NistDML183
NistWeather
NistXTS748
NistXTS749
Person
Products
RiskIt
StudentResidence
UnixUsage
Usda"

for schema in $listOfSchemas 
do
	java -cp build:lib/* paper.datagenerationjv.CompareDefaults parsedcasestudy.$schema amplifiedConstraintCACWithNullAndUniqueColumnCACCoverage random
done