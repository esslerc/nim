# Nim Game

The Nim Game Server provides endpoints to play the Nim game. 
Players can check the game status and make moves using HTTP requests. 
Below are the instructions on how to interact with the server endpoints:

## 1. Get Game Status

Endpoint: GET /api/nim/status

To check the current status of the game, send a GET request to the above endpoint. 
The server will respond with the current state of the game, including the number of stones in each row.

Example Request (using cURL):
`curl -X GET http://localhost:8080/api/nim/status`

## 2. Reset Game State

Endpoint: GET /api/nim/reset

To reset the current game state, send a GET request to the above endpoint.

Example Request (using cURL):
`curl -X GET http://localhost:8080/api/nim/reset`

## 3. Make a Move

Endpoint: POST /api/nim/move

To make a move in the game, send a POST request to the above endpoint.
Include two POST Body parameters as JSON in your request:

row: The index of the row from which you want to remove stones.
stonesToTake: The number of stones you want to remove from the specified row.

The server will process your move and respond with the updated game state.

Example Request (using cURL):

` curl -X POST -H "Content-Type: application/json" -d '{"row": 1, "stonesToTake": 2}' http://localhost:8080/api/nim/move `

In the above example, the player is removing 2 stones from the first row.

## 4. Misère Mode
In Nim, Misère Mode is a variation of the traditional Nim game where the objective of the game is reversed. 
Instead of the usual goal of taking the last stone(s) and winning, in Misère Mode, the player who is forced to take the last stone(s) loses the game.

The rules in Misère Mode are essentially the same as in the regular Nim game, where players take turns removing stones from rows. 
However, the winning condition is different. In this mode, players strategically play to ensure their opponents are left with the final move. 
This adds a unique layer of complexity to the game, as players must not only focus on their moves but also anticipate and manipulate their opponent's moves to force them into a losing position.

You can simply activate the Misère Mode by setting `strategy=misere` in the `application.properties`

## Build and Run
You can either create and run the project with your own gradle version or with the gradlew wrapper which comes with this project.
In both cases, please open a terminal in the root directory of the project. The execute one of the following options:

- gradlew 
Use cli and go into the root project folder, then execute:
``./gradlew bootJar``

- own gradle installation
``gradle bootJar``

After the build process is complete, you can find the JAR executable file in the `build/libs` directory of the project.
You can then use this JAR file to run the NIM app.

``java -jar build/libs/nim-0.0.1-alpha.jar``

## Now you are ready to play Nim using the Nim Game Server! Enjoy your game!
