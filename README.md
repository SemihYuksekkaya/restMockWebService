# RestMockService
RestMockService is a mock service which accepts almost all types of requests, and responds from files which is specified by the path. Explained in more detail below.

# Requirements
Tested with:
- Java 17 and 20
- Spring Boot 3.1.3
- Apache Maven 3.9.3

May work with lower versions, but not tested.

# How to Run
Build and Run the project as a regular Java project preferably with Intellij IDEA.
Should be up and running without any issue if the requirements are met accordingly.

# How to Use
Once the program is running, it will create a REST Mock Web Service at http://localhost:8080/ , This mock service will accept all requests with the type of GET, POST, PUT, DELETE, PATCH from any uri path.

Responses are specified in the responses folder, names of the response files should match (with some limitations) with the path of the request uri to be used. More details and examples are included below.

## Response Files
Response files should be put in responses folder.

Response files should have the same name with the request uri path with minor differences.
- Add type of the operation (CRUD) in front of the file name. 
#### Example:

- GET operation with the path ***`/demo`*** matches
***`getdemo.xml` (or json)***


The characters "**/**" and "**?**" can not be used in a Windows file name thus we have to name the files differently.
 
### 1. Replace "**/**" with "**_**" when naming the response file.

Examples:
- the path ***`/demo/path/`*** matches ***`demo_path.xml` (or json)***
- response file for the path: ***`service/foo/123`*** should be named as   ***`service_foo_123.xml`*** (or json)

### 2. Path queries are dealt with curly brackets "{ }"
Replace `"?query"` with `{query}`

Examples:
- the path ***`/path?id=123/`*** matches   ***`path{id=123}.xml`*** (or json)
- the path ***`/demo/service/foo?id=123/`*** matches   ***`demo_service_foo{id=123}.xml`*** (or json)


# Limitations
- This mock Web Service accepts any path but with some limitations.
The uri path cannot contain any of the following characters:

        [ ] % ^ | < > " * :

- Since we use altered names for the response file there may be a naming collision between some paths which are unlikely to be used in the same project e.g. 
    a xml response file of the path `/demo/path` is `demo_path.xml` which is same as the corresponding response file of the path `/demo_path`

- `%` is acceptable when using `%` to encode a character e.g. using ***`%7D`*** in the path instead of ***`}`***