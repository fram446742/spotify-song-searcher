# Spotify song searcher

The purpose of this project is to demonstrate how to use lexical and syntactic analysis tools to create a simple query language for searching songs on Spotify. The project consists of the following components:

1. A JFlex scanner that tokenizes the input query string into lexical units such as keywords, operators, identifiers, literals, etc.
2. A CUP parser that parses the token stream and builds an abstract syntax tree (AST) representing the query.
3. A query evaluator that traverses the AST and executes the query on the SQL database, returning the matching songs as a result.

The project uses Java 11 or higher, JFlex 1.9.1 and CUP 0.11b. To run the project, follow these steps:

> Clone or download this repository to your local machine.
> Open a terminal and navigate to the root directory of the project.
> Run
>
> ```bash
> mvn clean install
> ```
> to compile the Java source files. Note: This assumes that the JFlex and CUP generated files are already present in the src directory. If not, you need to run the batch files located at `src/main/java/com/ucjc/utils` before compiling.
>
> Run
>
> ```bash
> java -jar target/bin/spotify-song-searcher-1.0-Beta.jar
> ```
> to execute the program.
>
> Execute the program to launch the Search Engine UI. Follow the instructions provided within the program to interact with the interface. The UI will provide clear on-screen instructions guiding you on how to use the search engine effectively. Pay attention to these instructions to make the most of the features available.
>
> If you want a more detailed insight of the syntaxis used please refer to:
>
> `src/main/java/com/ucjc/analizers/parser.cup`.
>
> Enjoy the results!
>
> ---
