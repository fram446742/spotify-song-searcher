#Spotify song searcher#

The purpose of this project is to demonstrate how to use lexical and syntactic analysis tools to create a simple query language for searching songs on Spotify. The project consists of the following components:

A Spotify API wrapper that connects to the Spotify Web API and retrieves song information from a given playlist ID.
A JFlex scanner that tokenizes the input query string into lexical units such as keywords, operators, identifiers, literals, etc.
A CUP parser that parses the token stream and builds an abstract syntax tree (AST) representing the query.
A query evaluator that traverses the AST and executes the query on the SQL database, returning the matching songs as a result.
The project uses Java 11 or higher, JFlex 1.9.1 and CUP 0.11b. To run the project, follow these steps:

Clone or download this repository to your local machine.
Open a terminal and navigate to the root directory of the project.
Run `javac src/*.java` to compile the Java source files. Note: This assumes that the JFlex and CUP generated files are already present in the src directory. If not, you need to run the batch files located at `src/main/java/com/ucjc/generators` before compiling.
Run `java target/main/java/com/ucjc/App /path/to/your/input.txt` to execute the program. This will read the playlist ID and the query from the input.txt file. You can edit the input.txt file to change the playlist ID and the query according to your preferences. The query should follow the grammar defined in `src/main/java/com/ucjc/analizer/parser.cup`.
Enjoy the results!
