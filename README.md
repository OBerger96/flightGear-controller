# Advanced Software Development Project - milestone 5
**<p text align="center">[End of Semester Project](https://www.youtube.com/watch?v=SMqOTOVGjuQ&feature=youtu.be)<p>**
This is a project we developed during our ‘Advanced Software Development’ course in our 2nd year.<br/>
This project helped us to gain a higher level of knowledge in programming, with emphasis on design patterns and programming principles such as SOLID and GRASP, and finally developing our own JavaFX desktop application. <br/>
So, sit back, relax, and enjoy the flight!

---

## [Server](https://github.com/OBerger96/flightGear-server-side)
In this section we wrote a general server, which can be used over and over again in various projects.
In order for the server to be re-usable, there must be a seperation between the server's functionality and the rest of the code. 

Therefore, we defined the functionality of the server as an interface,
and each project can have different classes that will implement the same functionality in different ways.
Thus, the **Open / Close principle** has been applied.

Now the ```Server``` interface has a quite simple functionality:
* A method that receives a port for listening and its function will be to open the server and wait for clients.
* A method to close the server.

For this project we will use a class called ```MySerialServer``` that will be a type of ```Server```.

### ClientHandler
Imagine a situation in which the ```MySerialServer``` class would also define the client-server call protocol.
In different projects, there might be different conversations in different formats and with different expectations between the client and the server.
Therefore, we won't be able to use this class in other projects. 

To solve that issue, we had to separate the server mechanism implemented in ```MySerialServer``` from different forms of conversation with possible clients.
For that reason we created an interface called ```ClientHandler``` to determine the type of call with the client and its handling.
Now ```MySerialServer``` class can inject any desired implementation for ```ClientHandler```.


For example, for every implementation of a ```Server``` we can inject a call of inversion of strings or solving equations.
In the same way, if one day we would like to implement additional protocols then we will only need to add the implementation of ```ClientHandler``` without changing or copying again the code of the various implementations to the ```Server```.

In this method, we maintained both the **Single Responsibility** and **Open / Close** principles.

### Caching
The project also has a caching system,
for it might take a lot of time to calculate some solutions.
It would be redundant to calculate a solution for a problem that we already solved.
Instead, we can save solutions that were already calculated in an external file, or a database.
Upon receiving a new problem, we will first check the cache to see if we have already solved it.
If so, we will extract the solution from the disk instead of calculating it.

 
We created the ```CacheManager``` interface to manage the cache for us, with the following functionalities:
* Checks whether the solution already exists in the database.
* Extracts the data from the database (If a solution already exists).
* Saves the solution for the problem.

---

## UML
<p align="center">
  <img src="https://github.com/OBerger96/flightGear-controller/blob/master/UML/The%20server%20side.jpeg" width="800">
</p>

### Our Concerete Server
Given a graph, it could solve it using [A-star](https://en.wikipedia.org/wiki/A*_search_algorithm) algorithm (which is already implemented in this project based on [Dijkistra](https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm) algorithm using manhattan distances) or any other search algorithm.

<p align="center">
  <img src="https://github.com/OBerger96/flightGear-controller/blob/master/UML/Our%20Concrete%20Server.png" width="800">
</p>
In our concrete server, given a weighted graph, it will run the search algorithm, and as an output it will return the cheapest route to the target.

You can see that the Bridge Design Pattern was implemented, as we created a separation between the problem, and what solves the problem. That way we can solve various problems through different solutions.

The specific problem and solution in this project, is that when given a matrix the server will be able to solve it and return the quickest path from point A to point B using **A-star** algorithm as said before.

For example: lets assume we have this matrix:

|  |   |  |  |
| :---: | :---: | :---: | :---: |
| **115** | **83**  | 164 | 123 |
| 109 | **27**  | **30**  | **14**  |
| 156 | 175 | 189 | **6**   |
| 160 | 186 | 153 | **18**  |

If we set the start point to be 115 (0,0) and the end point to be 18 (3,3) then the path (the output) will be:

Right, Down, Right, Right, Down, Down.

---

## [Interpreter](https://github.com/OBerger96/flightGear-interpreter-lexer-parser) 
As stated at the beginning of the repository, the project is a GUI of a flight simulator by which you can control the plane and get information from it.

One of its features is running a script, which is basically a kind of custom programming language that can handle the plane.

As in the following example:

```scala
openDataServer 5400 10
connect 127.0.0.1 5402
var breaks = bind /controls/flight/speedbrake
var throttle = bind /controls/engines/engine/throttle
var heading = bind /instrumentation/heading-indicator/offset-deg
var airspeed = bind /instrumentation/airspeed-indicator/indicated-speed-kt
var roll = bind /instrumentation/attitude-indicator/indicated-roll-deg
var pitch = bind /instrumentation/attitude-indicator/internal-pitch-deg
var rudder = bind /controls/flight/rudder
var aileron = bind /controls/flight/aileron
var elevator = bind /controls/flight/elevator
var alt = bind /instrumentation/altimeter/indicated-altitude-ft
sleep 90000
breaks = 0
throttle = 1
var h0 = heading
while alt < 1000 {
	rudder = (h0 – heading)/20
	aileron = - roll / 70
	elevator = pitch / 50
	print alt
	sleep 250
}
print "done"

```
For this purpose, we wrote a code reader, an interpreter, which allows you to connect to the simulator, open a server, and run various commands that control the plane and sample its data.

In the text above, we see a while loop that will take place as long as the plane’s altitude is less than a 1000 meters, the loop content will give orders to the plane's acceleration and elevation.
In this part:
```scala
rudder = (h0 - heading)/180
```
We can see that arithmetic expressions are supported as well, and to interpret them we use [Dijkstra's Shunting Yard algorithm](https://en.wikipedia.org/wiki/Shunting-yard_algorithm).

### Command Patterns
<p align="center">
  <img src="https://github.com/OBerger96/flightGear-controller/blob/master/UML/Command%20Pattern.png?raw=true" width="600">
</p>
In this project there is an extensive use of commands, the plane needs to receive a lot of instructions in a short period of time in order to fly correctly. For that matter, the most suitable design pattern for the task is the Command Pattern. The Command Pattern implementation can be seen in our Parser - each command in the program is receiving its own Command Object.

It is important that all commands will implement the same interface, because we want them to have a common polymorphic denominator.

Another reason to use the Command Pattern is for when we need an assembly of commands at once. For example, we needed a command that holds other different commands inside of it. In that case, we combined the Command Pattern with Composite Pattern.

So if, for example, we take a look at the "loop" command or "if" command, then we can see that each contains a list of commands which in turn can be either a standard single command or a list of commands.

### Interpreter stages
<p align="center">
  <img src="https://github.com/OBerger96/flightGear-controller/blob/master/Images/script-reader.jpeg" width="800">
</p>
So this script-reader works in a very similar way to the interpreter of a real programming language.

The first stage that happens in the interpretation process is ``Lexer``.

The Lexer takes the string as it is, and converts it to a logical distribution according to commands and parameters that can run later on with a Scanner.

The next stage is the ``Parser`` stage, which begins converting the "array" created by the Lexer into commands and executes them.

However, since the script is only supposed to control the plane, we don't want that the interpreter will have to deal with connecting to server and running the simulator, in case there are syntactic errors or incorrent entries that might be discovered in the middle of the script.

So, before we start running the commands, we will make sure that a ``Pre-Parser`` will pass the initial scan on the script and check for Syntax errors, such as incorrect parameters or irrational values.

---

## MVVM Architecture
<p align="center">
  <img src="https://github.com/OBerger96/flightGear-controller/blob/master/UML/MVVM.jpeg" width="800">
</p>

In this project we chose to use the **MVVM architecture**.

We have the View layer that is responsible for the presentation, for example the 
input from the user. The View is also responsible for producing the graphics and has the code-
behind - for example, functions that are activated when we press a button, which are called
event-oriented programming.

* **Model** – Responsible for our business logic, such as algorithms and data access.
* **View Model** – It passes commands from the View to the Model, and its purpose is to
separate the View from the Model.
* **Data Binding** – We can wrap variables such as those in the View, and then when we change
something in the text, it will automatically changed in the ViewModel.

For the MVVM architecture to work, we'll have to wrap the different components together. 
This is done by the Observer Pattern, which binds the different components together, and notify them about changes that are made or needed to be made as required by the operator. 

---
## Design and Functionality of the App
<p align="center">
  <img src="https://github.com/OBerger96/flightGear-controller/blob/master/Images/screen.png?raw=true" width="800">
</p>

* **Connect** – The Connect button will open a popup window and by entering ip and port we will connect to the flight simulator. The app will connect to the simulator as a client, this will also enable the use of all the functionality of the application.

* **Load data** – The Load Data button will open a folder of CSV files that are used as maps. When a CSV file is loaded then a map of the uploaded data is displayed. The map will be displayed in colors based on the height of each point in the map, the lower the area the color will be red and the higher the area the color will be greener. We will sample from the simulator the exact location of the aircraft, as a result, an icon will be displayed in the same location on the map.

* **Calculate path** - The Calculate path button will open a popup window and by entering ip and port we will connect to a server that solves search problems (A server that I built on milestones 1-3). Clicking on any point on the map will set a destination for the aircraft, and the server will calculate the cheapest route to that point.

* **Load + Autopilot** – Loads the script with the airplane commands and thanks to the Interpreter that I built, the plane goes into autopilot mode and executes the commands in the script.

* **Manual** – The manual button will move the aircraft from autopilot mode to manual mode where the user controls the aircraft with the joystick.

---

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.<br/> See deployment for notes on how to deploy the project on a live system.

### Prerequisites
What things you need to install the software and how to install them.

### imports / technologies:
* JavaFX
* updated Java Development Kit (JDK)
* Flight Gear - open-source flight simulator

### Instructions
```scala
1. Download copy of the project
2. Open the Application directory
3. Edit the file location of Server.jar in the runServer.bat
4. Edit the file location of Client.jar in the runClient.bat
5. run runServer.bat
6. run runClient.bat
7. FlightGear.exe
```
---

## Built With
* [Eclipse](https://www.eclipse.org/downloads/packages/release/kepler/sr1/eclipse-ide-java-developers) - Java IDE
* [Scene Builder](https://gluonhq.com/products/scene-builder/)  - Scene Builder 8.5.0

---

## Authors
* **Noa Cohen** - [Profile on LinkedIn](https://www.linkedin.com/in/noalecohen1/)
* **Omer Berger** - [Profile on LinkedIn](https://www.linkedin.com/in/omerberger/)
