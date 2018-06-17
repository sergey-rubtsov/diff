# Scalable Web

This service provides 2 http endpoints that accepts JSON base64 encoded binary data on both endpoints:

**{host}/v1/diff/{ID}/left** 

and 

**{host}/v1/diff/{ID}/right**

The provided data is diff-ed and the results is available on a third endpoint:

**{host}/v1/diff/{ID}**

The results provided the following info in JSON format:
- If equal return that
- If not of equal size just return that
- If of same size provide insight in where the diffs are, actual diffs are not needed. 
So mainly offsets + length in the data

## Compilation

_Project contains Lombok annotations!_

To compile it in your IDE you need to install Lombok plugin and enable annotation processing.

Clone this project and import it as gradle project.
After all dependencies are downloaded tests could be run.
Test coverage is about 96%

## Run

This is a Spring Boot application. 
You can run it with class DiffApplication.

## How to check

Run application.

Make POST request 

**http://localhost:8083/v1/diff/42/left** with body 0123A56789A
it should return status code 200

And POST request 

**http://localhost:8083/v1/diff/42/right** with body 0123456789A
it should return status code 200 too

The GET request 

**http://localhost:8083/v1/diff/42** will return:

``
{
    "size": 11,
    "offset": 4
}
``

If you make POST requests with different body size, the response will be next:

``
{
    "status": "UNEQUAL_SIZE"
}
``

If you make POST requests with equals body, the response will be next:

``
{
    "status": "EQUAL_SIZE"
}
``

The empty body in POST request will return error 406 "Not Acceptable"

If you try to GET and did not make any POST, you will receive error 404 "Not Found"

If you try to GET and did only one POST, you will receive error 422 "Unprocessable Entity"

