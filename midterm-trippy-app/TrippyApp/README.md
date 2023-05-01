# Project overview

# Files and where to find them
1. File for building application database can be found in -> src/main/resources/CreateTrippyAppDb.sql
2. File with postman request collection can be found in -> src/main/resources/Requests.postman_collection_TrippyApp


# Starting the project
1. Clone the repository dobrin-rashkov/midterm-trippy-app/TrippyApp/
2. Run `mvn clean install` command
3. Open pgAdmin -> Query tool -> Open CreateTrippyAppDb.sql file -> Select all lines and run.

# Functionalities
### Implemented

1. Users

       1.1 Adding users

                                Request: POST http://localhost:8085/users
                                 Body raw (json)
                                   json
                                   {
                                   "username": "ExampleUsername",
                                   "firstName": "ExampleFirstName",
                                   "lastName": "ExampleLastName",
                                   "city": "ExampleCity"
                                   }

       1.2 Updating users 
        
                                 Request: PUT http://localhost:8085/users/1
                                  Body raw (json)
                                   json
                                   {
                                   "username": "ExampleUsername",
                                   "firstName": "ExampleFirstName",
                                   "lastName": "ExampleLastName",
                                   "city": "ExampleCity"
                                   }

                                  Request: PUT http://localhost:8085/users/1?returnOld=true
                                  Body raw (json)
                                   json
                                   {
                                   "username": "ExampleUsername",
                                   "firstName": "ExampleFirstName",
                                   "lastName": "ExampleLastName",
                                   "city": "ExampleCity"
                                   }

       1.3 Get All users 
                                 Request: GET http://localhost:8085/users
                                  
       1.4 Get user by id
                                 Request: GET http://localhost:8085/users/{id}
                
       1.5 Get user by username
                                 Request: GET http://localhost:8085/users?username={username}

       1.6 Get user by email
                                 Request: GET http://localhost:8085/users?email={email}

       1.7 Get businesses by user city

                                 Request: GET http://localhost:8085/users/1/businesses
      
2. Business:

       2.1 Adding business 
                             Request: POST  http://localhost:8085/businesses
                               Body raw (json)
                               json
                               {
                               "name": "ExampleName",
                               "city": "ExampleCity",
                               "businessType": "BAR",       -> Enum currently supporting ["BAR","HOTEL","RESTAURANT"]
                               "address": "ExampleAddress",
                               "email": "ExampleEmail",
                               "phone": "ExamplePhone",     ->  Currently supports format +359 or 0 - 883 - 11 22 33
                               "website": "example@website.com"

       2.2 Updating business

                             Request: PUT http://localhost:8085/businesses/{id}
                                Body raw (json)
                                json
                                {
                                "name": "ExampleName",
                                "city": "ExampleCity",
                                "businessType": "BAR",       -> Enum currently supporting ["BAR","HOTEL","RESTAURANT"]
                                "address": "123 Main St",
                                "email": "ExampleEmail",
                                "phone": "ExamplePhone",     ->  Currently supports format +359 or 0 - 883 - 11 22 33
                                "website": "example@website.com"
                                  } 

                             Request: PUT http://localhost:8085/businesses/{id}?returnOld=true
                                Body raw (json)
                                json
                                {
                                "name": "ExampleName",
                                "city": "ExampleCity",
                                "businessType": "BAR",       -> Enum currently supporting ["BAR","HOTEL","RESTAURANT"]
                                "address": "123 Main St",
                                "email": "ExampleEmail",
                                "phone": "ExamplePhone",     ->  Currently supports format +359 or 0 - 883 - 11 22 33
                                "website": "example@website.com"
                                  }
   
       2.3 Get business by id 
                             Request: GET http://localhost:8085/businesses/{id}
 
       2.4 Get business by type 
                             Request: GET http://localhost:8085/businesses?type={type} -> Enum currently supporting ["BAR","HOTEL","RESTAURANT"]

       2.5 Get business by email 
                             Request: GET http://localhost:8085/businesses?email={email}

       2.6 Get business by name 
                             Request: GET http://localhost:8085/businesses?name={name}

       2.7 Get business by name and city 
                             Request: GET http://localhost:8085/businesses?name={name}&city={city}

       2.8 Get business by average review rate lower than 
                             Request: GET http://localhost:8085/businesses?rate={rate}&query=lowerThan

       2.9 Get business by average review rate bigger than 
                             Request: GET http://localhost:8085/businesses?rate={rate}&query=biggerThan

       2.10 Get reviews by business id 
                             Request: GET http://localhost:8085/businesses/{business_id}/reviews
       
3. Reviews
       
       3.1 Add review 
                             Request: POST http://localhost:8085/reviews?username={username}   
                              Body raw (json)
                                json
                                {
                                "rating": "1",             -> Allowed values from 1 to 5
                                "text" : "Awful first try",
                                "businessId": 1
                                }

       3.2 Update review 
                             Request: PUT http://localhost:8085/reviews/{review_id}?username={username}
                              Body raw (json)
                                json
                                {
                                "rating": "1",             -> Allowed values from 1 to 5
                                "text" : "Awful first try",
                                "businessId": 1
                                }

                              Request: PUT http://localhost:8085/reviews/{review_id}?username={username}&returnOld=true
                              Body raw (json)
                                json
                                {
                                "rating": "1",             -> Allowed values from 1 to 5
                                "text" : "Awful first try",
                                "businessId": 1
                                }

       3.3 Delete review 
                             Request: DELETE http://localhost:8085/reviews/{id}?username={username}&returnOld=true

       3.4 Get review by id 
                             Request: GET http://localhost:8085/reviews/{id}

       3.5 Get all reviews
                             Request: GET http://localhost:8085/reviews
                              
### Not implemented
1. Testing is around 70% 
2. 
3.
