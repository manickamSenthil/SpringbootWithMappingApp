# SpringbootWithMappingApp

There are two roles
admin (ALL rights)
user (ALL rights except to update the address)
admin credentials 
user name : admin
password : admin

user credentials
user name : user
password : user

H2 database: http://localhost:8090/h2-console/
 JDBC URL : jdbc:h2:mem:tcs
 username : tcs
 password :tcstest

GET API:
getting person details : http://localhost:8090/crudapp/persons 
getting single person details : http://localhost:8090/crudapp/persons/{personid}
getting person details using anyName (FirstName or/and Lastname): 
http://localhost:8090/crudapp/persons/anyname?fName=senthil&lName=manickam 

getting single pet details : http://localhost:8090/crudapp/pets/{petid}
getting pet details: http://localhost:8090/crudapp/pets


POST API:
inserting new person : http://localhost:8090/crudapp/persons 
{
        "firstName": "senthil",
        "lastName": "manickam",
        "dob": "12/12/1993",
        "address": "madurai"
}

inserting new pet : http://localhost:8090/crudapp/pets
{ "petName": "cat", "petAge": "2" }



PUT API:
update the person details : http://localhost:8090/crudapp/persons/{personid} 
{ "firstName": "senthil", "lastName": "manickam", "dob": "12/12/1995" }

update the pet : http://localhost:8090/crudapp/pets/{petid}
 { "petName": "Tom", "petAge": "9" }

 
Linking pet to existing person: http://localhost:8090/crudapp/pets/{petId}/persons/{personId}

 
DELETE API :
http://localhost:8090/crudapp/persons/{personid} 
http://localhost:8090/crudapp/pets/{petid}
