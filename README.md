# Description of the solution
The solution was developed according to the requirements, in Java with SpringBoot. All the requested points were met, including:
- An endpoint for requests for new records that receives a JSON payload with the values of the title, description and base64File
  - The creationDate is added when mapping the DTO (Data Transfer Object) for file creation to the FileEntity type item that will be stored in the database.
  - The id is automatically generated when inserting the file into the database.
- An endpoint to retrieve files by id, which receives the id of the record to be retrieved as a parameter in the url.
- An endpoint to retrieve all files that implements pagination, additionally other functionalities were added such as filters and data sorting, as explained in more detail in the tests section.
- A test clase for executing tests in the application by requesting the endpoints with example data.
- The complete logic for wrapping both database an application in docker containers.

> A basic script was also provided to facilitate the execution of the application.

## Technical considerations
For the solution, some additional aspects were worked on, which are presented below:
- Exception handling: validations were added to prevent exceptions from occurring during the execution of the application. A class was also added to handle exceptions in a customized way. Logs were added to the application to have more detail when there are errors.
- Programming standards: several standards are met within the application, from basic ones such as nomenclature, to some structural patterns, such as the use of packages and specific classes for each functionality. Design patterns are also handled, such as the use of DTOs (Data Transfer Object) for receiving requests. Data mapping classes are handled to move from one type of object to another. Customized specifications are handled for the application of dynamic filters in the data query.
- Security issues: The handling of environment variables for the definition of sensitive data such as users and passwords is considered. This applies both in the application configuration (.yml properties files) and in dockerization in the compose.yml file, in order to avoid the exposure of sensitive data.

# Running the application
### Creating the necessary files
To run the application, you must provide a .env file, to define the required parameters such as:

```text
POSTGRES_HOST=postgres
POSTGRES_PORT=5432
POSTGRES_USER=myuser
POSTGRES_PASSWORD=mypassword
POSTGRES_DB=mydatabase
```
These variables will be used to configure compose.yml file for both postgres and application services.

> The .env file must be created at project's root directory and provide your own values

### Starting dockerized services
Once the .env file is created, you can run the run.sh file, which contains a basic script to compile the project and to start the postgres and application services in the docker containers.

# Testing the application
Once started the services, you can use Postman for testing the application.
### Inserting data
To insert some registers to the database, you must make a POST request like this:
```http request
POST /api/files HTTP/1.1
Host: localhost:80
Content-Type: application/json
Content-Length: 146

{
    "title": "Example title for file",
    "description": "Example description for file",
    "base64File": "VGhpcyBpcyBhbiBleGFtcGxlIGZpbGUu"
}
```
and pass the data as JSON like this:

```json
{
    "title": "Example title for file",
    "description": "Example description for file",
    "base64File": "VGhpcyBpcyBhbiBleGFtcGxlIGZpbGUu"
}
```
As an example, I provide you a cURL request:
```shell
curl --location 'http://localhost:80/api/files' \
--header 'Content-Type: application/json' \
--data '{
    "title": "Example title for file",
    "description": "Example description for file",
    "base64File": "VGhpcyBpcyBhbiBleGFtcGxlIGZpbGUu"
}'
```
If all goes well, you should be greeted with the ID of the created file, which you can use to retrieve the file record from the database.

## Retrieving data
### Requesting a single file
To request a single file,  you must make a GET request like this:
```http request
GET /api/files/1 HTTP/1.1
Host: localhost:8080
```
Where you must pass the id as path variable, in this case 1. As an example I provide you a cURL request:
```shell
curl --location 'http://localhost/api/files/1'
```
### Requesting multiple files
To request multiple files, you must make a POST request, passing many parameters for managing the query like this:
```http request
POST /api/files/all HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Content-Length: 263

{
    "page": 0,
    "size": 10,
    "filters":{
        "from": "2024-08-27 09-13-00",
        "to": "2024-08-28 21-13-00",
        "title": "example",
        "description": "example"
    },
    "sort": {
        "field": "title",
        "order": "asc"
    }
}
```
> Data will be retrieved by pages, so you will need to provide the page value and page size (total number of items to be retrieved). If you don't, the default values will be set, 0 for page and 10 for size.

#### Applying filters
You can apply filters by passing the object filters, with these options:

- **from**: that indicates the date since you want to retrieve the data, you must provide this value in the format "yyyy-MM-dd HH:mm:ss" i.e. "2024-08-27 09:13:00", otherwise, it will produce an exception.
- **to**: that indicates the date until you want to retrieve the data, you must provide this value in the format "yyyy-MM-dd HH:mm:ss" i.e. "2024-08-27 09:13:00", otherwise, it will produce an exception.
- **title**: here you can write a text which will be used for matching the files that contains in the title the provided text, it's case-insensitive.
- **description**: here you can write a text which will be used for matching the files that contains in the description the provided text, it's case-insensitive.

> If you don't provide the filters object, no filters will be applied.

#### Ordering data
You can also order the data by passing the object sort, with these values:

- **field**: Here you can pass any field from the file (id, title, description, creationDate, base64File) that will be used to apply the sorting.
- **order**: here you can specify the ordering direction (asc, desc)

> If you don't provide the sort object, a default descending ordering by creationDate will be applied.

As an example I provide you a cURL request:
```shell
curl --location 'http://localhost:8080/api/files/all' \
--header 'Content-Type: application/json' \
--data '{
    "page": 0,
    "size": 10,
    "filters":{
        "title": "example"
    },
    "sort": {
        "field": "title",
        "order": "asc"
    }
}'
```