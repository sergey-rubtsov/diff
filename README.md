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



