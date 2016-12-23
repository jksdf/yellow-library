## cURL commands to test BookInstance REST Controller

###### Retrieve all book instances
```
curl -i -X GET http://localhost:8080/pa165/rest/bookinstance
```

###### Retrieve all removed book instances
```
curl -i -X GET http://localhost:8080/pa165/rest/bookinstance?filter=removed
```

###### Retrieve all available book instances of book with ID:1
```
curl -i -X GET http://localhost:8080/pa165/rest/bookinstance?filter=available&bid=1
```

###### Retrieve a book instance with ID 1
```
curl -i -X GET http://localhost:8080/pa165/rest/bookinstance/4
```

###### Attempts to retrieve a non-existent book instance
```
curl -i -X GET http://localhost:8080/pa165/rest/bookinstance/999
```

###### Deletes a book instance with ID 1
```
curl -i -X DELETE http://localhost:8080/pa165/rest/bookinstance/1
```

###### Attempts to deletes a non-existent book instance
```
curl -i -X DELETE http://localhost:8080/pa165/rest/bookinstance/999
```

###### Creates new book instance
```
curl -X POST -i -H "Content-Type: application/json" --data
'{"bookState":"New", "bookAvailability":"AVAILABLE", "version":"1st Edition", "bookId":2}'
http://localhost:8080/pa165/rest/bookinstance/create
```

###### Attempts to create already present book instance
```
curl -X POST -i -H "Content-Type: application/json" --data
'{"id":3,"bookState":"Mustaches drawn on portraits","version":"1st Edition","bookAvailability":"AVAILABLE","book":{"id":2,"name":"How to play tennis with wheat straws","isbn":"2992901818","description":"Brief overview on how to make tennic racket using only wheat straws.","author":"Joe Backhand","pages":20,"department":{"id":1,"name":"Agriculture, Robots and Tennis","shortName":"ART"}}}'
http://localhost:8080/pa165/rest/bookinstance/create
```

###### Changes the state of the book instance [ID:1] to **New State**
```
curl -X PUT -i -H "Content-Type: application/json" --data
'{"bookState":"New State"}'
http://localhost:8080/pa165/rest/bookinstance/1
```

###### Attempts to changes the state of the book instance [ID:1] to **NS** (too short)
```
curl -X PUT -i -H "Content-Type: application/json" --data
'{"bookState":"NS"}'
http://localhost:8080/pa165/rest/bookinstance/1
```

###### Changes the availability of the book instance [ID:1] to **REMOVED**
```
curl -X PUT -i -H "Content-Type: application/json" --data
'"REMOVED"'
http://localhost:8080/pa165/rest/bookinstance/1
```

###### Changes the Book of the book instance [ID:1]
```
curl -X PUT -i -H "Content-Type: application/json" --data
'{"id":"3"}'
http:""localhost:8080/pa165/rest/bookinstance/1/newbook
```

###### Attempts to change the Book of the book instance [ID:1] to non-existent book
```
curl -X PUT -i -H "Content-Type: application/json" --data
'{"id":"999"}'
http:""localhost:8080/pa165/rest/bookinstance/1/newbook
```
