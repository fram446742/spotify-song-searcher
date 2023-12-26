/*--------------------1. User Code--------------------*/
//------> Packages and imports
package com.ucjc.compiled.generated;

import java_cup.runtime.*;
import java.util.LinkedList;
import com.ucjc.utils.*;

/*--------------------2. Options and declarations--------------------*/
%%
%{

//------> User code in java syntax

    public static LinkedList<TError> LexicalErrorTable = new LinkedList<TError>();

    // newline function declaracion
    private void newline() {
        yyline++;
        yycolumn = 1;
    }

    public LinkedList getTable(){
      return LexicalErrorTable;
    }

%}

//------> Directives
%public
%class Lexer
%cupsym Sym
%cup
%char
%column
%full
%ignorecase
%line


//------> Regular expressions
// Define the {NUM} macro
NUM = [0-9]+
NULL_TEXT = ""
JUMP = \r|\n|\r\n
WHITE_SPACE = {JUMP} | [\t\f]

//------> States
%state YYINITIAL, STR

%%
/*--------------------3. Lexical rules--------------------*/
//------> Symbols

//--------> Keywords
<YYINITIAL> SEARCH { return new Symbol(Sym.SEARCH, yyline, yycolumn, yytext()); }
<YYINITIAL> [Ss][Oo][Nn][Gg]_[Nn][Aa][Mm][Ee] { return new Symbol(Sym.SONG_NAME, yyline, yycolumn, yytext()); }
<YYINITIAL> [Aa][Rr][Tt][Ii][Ss][Tt] { return new Symbol(Sym.ARTIST, yyline, yycolumn, yytext()); }
<YYINITIAL> [Aa][Ll][Bb][Uu][Mm] { return new Symbol(Sym.ALBUM, yyline, yycolumn, yytext()); }
<YYINITIAL> [Mm][Ii][Ll][Ll][Ii][Oo][Nn]_[Ss][Tt][Rr][Ee][Aa][Mm][Ss] { return new Symbol(Sym.MILLION_STREAMS, yyline, yycolumn, yytext()); }
<YYINITIAL> [Rr][Ee][Ll][Ee][Aa][Ss][Ee]_[Dd][Aa][Tt][Ee] { return new Symbol(Sym.RELEASE_DATE, yyline, yycolumn, yytext()); }
<YYINITIAL> [Nn][Uu][Mm][Bb][Ee][Rr] { return new Symbol(Sym.NUMBER, yyline, yycolumn, yytext()); }

//--------> Numbers
<YYINITIAL> {NUM} { return new Symbol(Sym.NUM, yyline, yycolumn, yytext()); }

//--------> Strings
<YYINITIAL>"\"" {
  yytext(); // Discard the leading quote
  yybegin(STR);
}

<STR> "\"" {
    // The collection of the text between quotes ends
    String textInQuotes = yytext(); // Text in quotes is stored in a string
    System.out.println("Text in quotes: " + textInQuotes);
    // Return to the initial state
    yybegin(YYINITIAL);
    return new Symbol(Sym.STRING, textInQuotes); // Returns the content in quotes
}

<STR> [^\"\n\r]+ {
    // Collect characters inside quotes until a double quote, newline, or carriage return is encountered
}


<YYINITIAL> "\"\"" {
       // Handle double quotes (empty string)
        System.out.println("Lexical Error: Double quotes without content, line: " + yyline + ", column: " + yycolumn);
        TError data = new TError("", yyline, yycolumn, "Lexical Error", "Empty string is not allowed");
        LexicalErrorTable.add(data);
        
        // Vuelve al estado inicial
        yybegin(YYINITIAL);
}

//--------> Operators & Symbols
<YYINITIAL> "," { return new Symbol(Sym.COMMA, yyline, yycolumn, yytext()); }
<YYINITIAL> "=" { return new Symbol(Sym.EQUALS, yyline, yycolumn, yytext()); }
<YYINITIAL> ">" { return new Symbol(Sym.MORE_THAN, yyline, yycolumn, yytext()); }
<YYINITIAL> "<" { return new Symbol(Sym.LESS_THAN, yyline, yycolumn, yytext()); }
<YYINITIAL> "<=" { return new Symbol(Sym.LESS_THAN_EQUAL, yyline, yycolumn, yytext()); }
<YYINITIAL> ">=" { return new Symbol(Sym.MORE_THAN_EQUAL, yyline, yycolumn, yytext()); }

//--------> White Spaces
WHITE_SPACE { /* Ignore white spaces */ }
NULL_TEXT  { /* Ignore white spaces */ }
[ \t\r\n\f] {/* Ignore white spaces */}

//--------> Lexical Errors
.  {
    System.out.println("Lexical Error: " + yytext() + ", line: " + yyline + ", column: " + yycolumn);
    TError data = new TError(yytext(), yyline, yycolumn, "Lexical Error", "Symbol does not exist in the language");
    LexicalErrorTable.add(data);

    // Switch to a different state to ignore characters until the end of the line
    yybegin(YYINITIAL);
}

