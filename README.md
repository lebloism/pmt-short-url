# PMT Short url Provider
***
A little short url provider

# Technical choices

## Spring Boot
Imposed.<br/> 
I know Spring but not Spring Boot, so I started with a tutorial, and then adapt the code for the exercise.

## Google App Engine
I didn't know GAE, but wanted to test it.<br/> 
I would have use a flexible environment, as recommended for Spring Boot applications, but got a timeout when deploying. In order to keep moving, I changed to standard environment.

## Database
MongoDB would have been a good choice, as there are only simple objects to store, without relations. But I don't have any experienced with it, so prefered not to try (as I already had Spring Boot And GAE to get to know).<br/>
I tried MySQL but got problems when deploying to GAE.<br/>
So finally, I took H2 : it wouldn't be appropriate for a real application, as data are only stored in memory, but it's OK for the context of the exercise.

## ORM
For a simple CRUD application, Hibernate is great. I let it create the schema, as it's very simple.<br/>
For a more complex data model, I would prefer create the schema through migrations (with flyway for example), and write SQL requests myself, with binding to entities by MyBatis for example.

# Implementation choices

## Model
I kept a technical id, in order to be more flexible. But the shortUrl field could (should ?) have been the id.

## Webservices
In addition to asked webservices, I added : 
* a webservice to retrieve all short url objects. Useful for testing the application
* a webservice to follow a short url (go to to the associated long url)

A Postman collection with the webservices is provided in code source (PMT_short_url_provider.postman_collection.json)

# Not implemented
We could add :
* webservices security
* possibility to choose between different short url generation strategies
    * not using confusing characters as O/0, I/l
    * easy-to-memorize (alternation of consonants and vowels)
* metrics
* a better error handling (with nice error messages)
* a documentation with Swagger
* unit tests (especially if different generation strategies) - integration tests were a way to go quickier, but for maintenance, unit tests would be more appropriate

Of course, this short url provider would be useful only if hosted on a very short domain name !!
