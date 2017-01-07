This is a file, that describes, how to test REST api with curl.
There are curl commands for each Controller and each method in this controller.

BookInstanceController
    getBookInstances: curl -i -X GET http://localhost:8080/pa165/rest/bookinstance
    getBookInstance: curl -i -X GET http://localhost:8080/pa165/rest/bookinstance/1
    deleteBookInstance: curl -i -X DELETE http://localhost:8080/pa165/rest/bookinstance/4
                        not all id work, because of foreign keys
    createBookInstance: curl -X POST -i -H "Content-Type: application/json" --data '{"bookState":"Brand new Book instance created by curl and REST :)","version":"2nd Edition","bookAvailability":"BORROWED","bookId": "1"}' http://localhost:8080/pa165/rest/bookinstance/create
    changeBookState: curl -X PUT -i -H "Content-Type: application/json" --data '{"id" : "1", "bookState" : "changed state by curl"}' http://localhost:8080/pa165/rest/bookinstance/1/newstate
    changeBookAvailability: curl -X PUT -i -H "Content-Type: application/json" --data '"AVAILABLE"' http://localhost:8080/pa165/rest/bookinstance/1/newavailability
DepartmentController
    getDepartments: curl -i -X GET http://localhost:8080/pa165/rest/department
    addDepartment: curl -X POST -i -H "Content-Type: application/json" --data '{"name":"Department created with curl and REST","shortName":"DPT"}' http://localhost:8080/pa165/rest/department/create
    getById: curl -i -X GET http://localhost:8080/pa165/rest/department/1
LoanController
    findById: curl -i -X GET http://localhost:8080/pa165/rest/loan/1
    createLoan: curl -X POST -i -H "Content-Type: application/json" --data '{"dateFrom":"2018-12-15 00:00","returnDate":"null","loanLength":"30","loanState":"restloan","user":{"id":"3","name":"Simon White","login":"simon","passwordHash":"1000:25c1c3c6afe7446c55fd0119c64eee0daad4c63a19e7dc9f:e5d5dad3f4df0aff73506573863c00d2ff9fde41494afce4","address":"California, USA","totalFines":"0.00","userType":"CUSTOMER"},"bookInstance":{"id":5,"bookState":"New, not already borrowed","version":"2nd Modified Edition","bookAvailability":"AVAILABLE","book":{"id":1,"name":"Technological Advacements on Farms","isbn":"577571257","description":"Short description of robotic advacements on local farms.","author":"Joe Backhand","pages":100,"department":{"id":1,"name":"Agriculture, Robots and Tennis","shortName":"ART"}}}}' http://localhost:8080/pa165/rest/loan/create
    recalculateFines: curl -i -X GET http://localhost:8080/pa165/rest/loan/recalculateFines
MainController
    getResources: curl -i -X GET http://localhost:8080/pa165/rest/
UserController
    getUsers: curl -i -X GET http://localhost:8080/pa165/rest/user
    getUserById: curl -i -X GET http://localhost:8080/pa165/rest/user/1
    createUser: curl -X POST -i -H "Content-Type: application/json" --data '{"name" : "Peter Green", "login" : "xgreen", "passwordHash" : "admin", "address" : "Ulica 3 Mesto", "totalFines" : "12.14", "userType" : "EMPLOYEE"}' http://localhost:8080/pa165/rest/user/create