# URL Shortener
## How to run this project:
##### a) Run as Docker image
Install Docker onto your computer. Pull the docker image with `docker pull samtrombone/url_shortener:beta`. Then run it on port 8080 with `docker run -p 8080:8080 samtrombone/url_shortener:beta`
##### b) Run after Git download
Clone or download the ZIP of the URLShortener project [from the Git repository](https://github.com/sam-o-floinn/URLShortener). The project can be run in a java IDE like IntelliJ, by selecting the *UrlShortenerApplication* class and running it.

## Table of Contents
1. Introduction Overview
2. Statistical Views
⋅⋅* Repository Information
⋅⋅* H2 database
⋅⋅* Docker statistics
3. File Runthrough
⋅⋅* Application and Entities
⋅⋅* Repositories
⋅⋅* Controllers
⋅⋅* Properties and SQL

4. Test Cases
⋅⋅* Tests for shortening the URL
⋅⋅* Tests for resolving codes to URLs given

---

## 1: Introduction
This is a server-side RESTful API that has two primary functions: create short codes for URLs, and use those same codes to redirect to those URLs. There is also information on dates created, last clicked, and the times clicked.

## 2: Statistical Views
#### a) Urls
Through the Spring repositories, there are two noticeable paths we can view from the default URL (e.g localhost:8080). 'urls' and 'codes', which are created from repositories.
"urls" will show all the short-URLs created, from the long URLs to their short URL code plus the times that a given link has been clicked. "codes" simply shows the codes created.
#### b) h2-console
Spring h2 Hibernate offers a database to store our info. This is persistent as long as the application has been run for. At the 'h2-console' end point, log in using the values as listed in *application.properties*. A web interface to a h2 database will let you run SQL queries to view any data on the repositories as desired.
#### c) Docker stats
In the default docker dashboard, you can inspect the file's CPU usage, memory usage, disk read/write and network I/O. Click on the project (should be port 8080), then click the *Stats* tab.

## 3: File Runthrough:
#### a) Applications and Entities:
There is only one application class, the *UrlShortenerApplication* class.
There are two entities. One to represent a code object *(CodeObject)*, another to represent a code-URL pair plus other relevant statistics *(UrlObject)*. I chose to represent these as entities not only to succinctly refer to them in the database, and throughout the project as autowired repositories, but also so I could customise the fields with potentially relevant data. For instance, the UrlObject does not just contain the *shortUrl* and the *longUrl* fields, it also contains a field to show how many times they've been clicked
#### b) Repositories
There's a repository each for CodeObject and UrlObject: *CodeRepository* and *UrlRepository*. The path to the URL where you can see their codes on the site is assigned in the argument to each one's `@RepositoryRestResource` notation. e.g CodeRepository's is 'codes', so it can be accessed via `localhost:port/codes`.
#### c) Controllers
This project needs essentially one controller for one directory.
There are some 'helper' functions here which do not respond to mappings or redirect. Conventionally I would put these in their own class separate from the routing logic. In past commercial projects of mine these would be helper classes: in Spring they are typically the Controller objects, as the Service routes.
This controller also contains mappings for business logic, as opposed to a Service class doing so. While I consider Service classes to be good practice for a full-stack Spring MVC project, a server-side API was relatively new to me. So given the scope of the project, I was concerned of such being superfluous. It is something I'd consider doing in a review of the project.
#### d) Properties and SQL
The application.properties file contains the configurations for the project's h2 database. The password, name, username and such can be adjusted here.
data.sql sets up the initial Url_Object table. It also initialises this table (and the automatically made Code_Object table) with a first dummy shortURL for www.google.com. This value is used for testing and is not configured to a variable.

## 4: Test Cases
Note that some tests will need the machine to be running in order to work correctly. 
To verify and demonstrate functionality, some simple test cases have been created. Two for the act of shortening a URL, two for the act of redirecting to one.
#### a) Shorten URL tests
"actualUrlReturns200" test gives a proper URL and should return a successful 200 HTTP status. 
"notUrlReturns500" runs the same procedure but with a string that is not a URL, and expects a ClientProtocolException.
#### b) Resolve URL tests
*badCodeReturns500* gives a non-existent URL which should cause an exception
*goodCodeReturns200* gives the code of the first database entry, created once the application is started up, and should return a 200 HTTP status number.