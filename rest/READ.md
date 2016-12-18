## cURL commands to test REST

###### Retrieve all book instances
```
curl -i -X GET http://localhost:8080/pa165/rest/bookinstance
```

###### Retrieve a book instance with ID 1
```
curl -i -X GET http://localhost:8080/pa165/rest/bookinstance/1
```

###### Deletes a book instance with ID 1
```
curl -i -X DELETE http://localhost:8080/pa165/rest/bookinstance/1
```

###### Creates new book instance
```
curl -X POST -i -H "Content-Type: application/json" --data
'{"bookState":"New", "bookAvailability":"AVAILABLE", "version":"1st Edition"}'
http://localhost:8080/pa165/rest/bookinstance/create
```

###### Changes the state of the book instance [ID:1] to **New State**
```
curl -X PUT -i -H "Content-Type: application/json" --data
'{"bookState":"New State"}'
http://localhost:8080/pa165/rest/bookinstance/1
```

###### Changes the availability of the book instance [ID:1] to **REMOVED**
```
curl -X PUT -i -H "Content-Type: application/json" --data
'"REMOVED"'
http://localhost:8080/pa165/rest/bookinstance/1
```
