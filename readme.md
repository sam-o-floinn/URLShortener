# URL Shortener
## How to run this project:
##### a) Run as Docker image
Install Docker onto your computer. Pull the docker image with `docker pull samtrombone/urlshortener:dev`. Then run it on port 8080 with `docker run -p 8080:8080 samtrombone/url_shortener:dev`
##### b) Run after Git download
Clone or download the ZIP of the URLShortener project [from the Git repository](https://github.com/sam-o-floinn/URLShortener). The project can be run in a java IDE like IntelliJ, by selecting the *UrlShortenerApplication* class and running it.

## Table of Contents
### 1. Introduction Overview
### 2. Statistical Views
##### a) Repository Information
##### b) H2 database
##### c) Docker statistics
### 3. File Runthrough
##### a) Application and Entities
##### b) Repositories
##### c) Controllers
##### d) Services
##### e) Properties and SQL
### 4. Test Cases
##### a) Tests for shortening the URL
##### b) Tests for resolving codes to URLs given

---

## 1: Introduction
This is a server-side RESTful API that has two primary functions: create short codes for URLs, and use those same codes to redirect to those URLs. There is also information on dates created, last clicked, and the times clicked.

## 2: Statistical Views
#### a) Urls
Thanks to a Spring repository, we can view from the default URL, *urls* (e.g `localhost:8080/urls`). This lists all the URL mappnig objects in the repo, from the long URLs to their short URL code. It also has extra statistics like the times that a given link has been clicked.
#### b) h2-console
Spring h2 Hibernate offers a database to store our info. This is persistent as long as the application has been run for. At the 'h2-console' end point, log in using the values as listed in *application.properties*. A web interface to a h2 database will let you run SQL queries to view any data on the repositories as desired.
#### c) Docker stats
In the default docker dashboard, you can inspect the file's CPU usage, memory usage, disk read/write and network I/O. Click on the project (should be port 8080), then click the *Stats* tab.

## 3: File Runthrough:
#### a) Applications and Entities:
There is only one application class, the *UrlShortenerApplication* class.
This uses an entity *(UrlObject)* to represent a shortURL-longURL pair plus other relevant statistics. I chose to represent these as entities not only to succinctly refer to them in the database, and throughout the project as autowired repositories, but also so I could customise the fields with potentially relevant data. For instance, the UrlObject does not just contain the *shortUrl* and the *longUrl* fields, it also contains a field to show how many times they've been clicked, the dates it was created and last clicked.
#### b) Repositories
There's a repository for UrlObject: *UrlRepository*. The path to the URL where you can see its data on a web browser is in the `@RepositoryRestResource` notation. UrlRepository's is 'urls', so it can be accessed via `localhost:port/urls`.
#### c) Controllers
This project needs essentially one controller for one directory. There are two primary functions, which simply call the Service object's functions of the same name. Hence this controller maps these methods to the Service.
#### d) Services
The two primary functions here are `routeShortUrl()` and `goToUrl()`. The former takes a normal URL param and makes a Url Mapping of it (it can also take an optional `shortURL` param if the user wants the link to have a specific shortUrl). The latter takes a short URL param, finds a matching object in the UrlRepository, and redirects to the matching URL.
There are some 'helper' functions here to make the Service code more maintainable and readable. `httpCheck(url)` checks if the given string starts with 'http://', to rectify errors that emerge when the link lacks this prefix (e.g *www.google.com*). `validateShortUrl(shortUrl)` checks if the given shortUrl by the user is valid and not matched to a UrlMapping object in the repository already. `getRandomString()` generates a random alphanumeric 6-digit string to use as our shortUrl.

#### e) Properties and SQL
The application.properties file contains the configurations for the project's h2 database. The password, name, username and such can be adjusted here.
data.sql sets up the initial Url_Object table. It also initialises this table (and the automatically made Url_Object table) with a first dummy shortURL for the long URL *www.google.com*, for the sake of testing.

## 4: Test Cases
Note that some tests will need the machine to be running in order to work correctly. 
To verify and demonstrate functionality, some simple test cases have been created. Two for the act of shortening a URL, two for the act of redirecting to one.
#### a) Shorten URL tests
`shortenUrl_ActualUrl_ReturnsTrue`" test gives a proper URL and should assert it maps correctly.
`shortenUrl_NotUrl_HttpHostConnectException` runs the same procedure but with a string that is not a correct URL, and expects a HttpHostConnectionException.
#### b) Resolve URL tests
`goToUrl_BadShortUrl_Returns500` gives a short URL which does not exist in our UrlRepository, and should cause an exception.
`goToUrl_GoodShortUrl_Returns200` gives a short URL that does exist in the repository, as initialised in *data.sql*, and should return a 200 HTTP status number.