
GET http://localhost:7070/api/trips

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 10:43:57 GMT
Content-Type: application/json
Content-Length: 1090

[
{
"id": 1,
"startTime": [
2024,
11,
4,
11,
34,
39,
151065000
],
"endTime": [
2024,
11,
7,
11,
34,
39,
151065000
],
"longitude": 10.0,
"latitude": 20.0,
"name": "Beach Adventure",
"price": 299.99,
"category": "BEACH"
},
{
"id": 2,
"startTime": [
2024,
11,
4,
11,
34,
39,
151610000
],
"endTime": [
2024,
11,
9,
11,
34,
39,
151610000
],
"longitude": 30.0,
"latitude": 40.0,
"name": "City Tour",
"price": 199.99,
"category": "CITY"
},
{
"id": 3,
"startTime": [
2024,
11,
4,
11,
41,
28,
774319000
],
"endTime": [
2024,
11,
7,
11,
41,
28,
774319000
],
"longitude": 10.0,
"latitude": 20.0,
"name": "Beach Adventure",
"price": 299.99,
"category": "BEACH"
},
{
"id": 4,
"startTime": [
2024,
11,
4,
11,
41,
28,
774319000
],
"endTime": [
2024,
11,
9,
11,
41,
28,
774319000
],
"longitude": 30.0,
"latitude": 40.0,
"name": "City Tour",
"price": 199.99,
"category": "CITY"
},
{
"id": 5,
"startTime": [
2024,
11,
4,
11,
42,
53,
705361000
],
"endTime": [
2024,
11,
7,
11,
42,
53,
705361000
],
"longitude": 10.0,
"latitude": 20.0,
"name": "Beach Adventure",
"price": 299.99,
"category": "BEACH"
},
{
"id": 6,
"startTime": [
2024,
11,
4,
11,
42,
53,
705361000
],
"endTime": [
2024,
11,
9,
11,
42,
53,
705361000
],
"longitude": 30.0,
"latitude": 40.0,
"name": "City Tour",
"price": 199.99,
"category": "CITY"
}
]
Response file saved.
> 2024-11-04T114357.200.json

######

GET http://localhost:7070/api/trips/1

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 10:44:54 GMT
Content-Type: application/json
Content-Length: 184

{
"id": 1,
"startTime": [
2024,
11,
4,
11,
34,
39,
151065000
],
"endTime": [
2024,
11,
7,
11,
34,
39,
151065000
],
"longitude": 10.0,
"latitude": 20.0,
"name": "Beach Adventure",
"price": 299.99,
"category": "BEACH"
}
Response file saved.
> 2024-11-04T114454.200.json

Response code: 200 (OK); Time: 9ms (9 ms); Content length: 184 bytes (184 B)

######

POST http://localhost:7070/api/trips

HTTP/1.1 201 Created
Date: Mon, 04 Nov 2024 11:26:52 GMT
Content-Type: application/json
Content-Length: 168

{
"id": 7,
"startTime": [
2024,
12,
1,
10,
0
],
"endTime": [
2024,
12,
1,
12,
0
],
"longitude": 12.345678,
"latitude": 34.56789,
"name": "Exciting Adventure",
"price": 99.99,
"category": "FOREST"
}
Response file saved.
> 2024-11-04T122652.201.json
##########

PUT http://localhost:7070/api/trips/1

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 11:27:47 GMT
Content-Type: application/json
Content-Length: 165

{
"id": 1,
"startTime": [
2024,
12,
1,
9,
0
],
"endTime": [
2024,
12,
1,
11,
0
],
"longitude": 12.345678,
"latitude": 34.56789,
"name": "Updated Adventure",
"price": 89.99,
"category": "BEACH"
}
Response file saved.
> 2024-11-04T122747.200.json

Response code: 200 (OK); Time: 44ms (44 ms); Content length: 165 bytes (165 B)