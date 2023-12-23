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

    public String strg;

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
DIGIT = [0-9]
NULL_TEXT = ""
JUMP = \r|\n|\r\n
WHITE_SPACE = {JUMP} | [\t\f]

//------> States
%state YYINITIAL

%%
/*--------------------3. Lexical rules--------------------*/
//------> Symbols
<YYINITIAL> {NUM} { return new Symbol(Sym.NUM, yyline, yycolumn, yytext()); }
<YYINITIAL> {NUM}\.{DIGIT}{1,3} { return new Symbol(Sym.DECIMAL, yyline, yycolumn, yytext()); }

//--------> Keywords
<YYINITIAL> [Ss][Ee][Aa][Rr][Cc][Hh] { return new Symbol(Sym.SEARCH, yyline, yycolumn, yytext()); }
<YYINITIAL> [Aa][Ll][Ll] { return new Symbol(Sym.ALL, yyline, yycolumn, yytext()); }
<YYINITIAL> [Ss][Oo][Nn][Gg]_[Nn][Aa][Mm][Ee] { return new Symbol(Sym.SONG_NAME, yyline, yycolumn, yytext()); }
<YYINITIAL> [Aa][Rr][Tt][Ii][Ss][Tt] { return new Symbol(Sym.ARTIST, yyline, yycolumn, yytext()); }
<YYINITIAL> [Aa][Ll][Bb][Uu][Mm] { return new Symbol(Sym.ALBUM, yyline, yycolumn, yytext()); }
<YYINITIAL> [Mm][Ii][Ll][Ll][Ii][Oo][Nn]_[Ss][Tt][Rr][Ee][Aa][Mm][Ss] { return new Symbol(Sym.MILLION_STREAMS, yyline, yycolumn, yytext()); }
<YYINITIAL> [Rr][Ee][Ll][Ee][Aa][Ss][Ee]_[Dd][Aa][Tt][Ee] { return new Symbol(Sym.RELEASE_DATE, yyline, yycolumn, yytext()); }
<YYINITIAL> [Nn][Uu][Mm][Bb][Ee][Rr] { return new Symbol(Sym.NUMBER, yyline, yycolumn, yytext()); }

//--------> Text Strings
<YYINITIAL> \"[^\"]+\" { return new Symbol(Sym.STRING, yyline, yycolumn, yytext().substring(1, yytext().length() - 1)); }

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

