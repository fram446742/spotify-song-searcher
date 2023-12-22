/*--------------------1. Codigo Usuario--------------------*/
//------> Paquetes e importaciones
package com.ucjc.compiled.generated;

import java_cup.runtime.*;
import java.util.LinkedList;
import com.ucjc.utils.*;

/*--------------------2. Opciones y declaraciones--------------------*/
%%
%{

//------> Codigo de usuario en sintaxis java

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

//------> Directivas
%public
%class Lexer
%cupsym Sym
%cup
%char
%column
%full
%ignorecase
%line


//------> Expresiones Regulares
// Define the {NUM} macro
NUM = [0-9]+
NULL_TEXT = ""
JUMP = \r|\n|\r\n
WHITE_SPACE = {JUMP} | [\t\f]

//------> Estados
%state YYINITIAL, STRING

%%
/*--------------------3. Reglas lexicas--------------------*/
//------> Simbolos
//--------> Números
<YYINITIAL> {NUM} { return new Symbol(Sym.NUM, yyline, yycolumn, yytext()); }

//--------> Cadenas de Texto
<YYINITIAL>\" {
  yybegin(STRING);
}

<STRING>[^\n]* {
  // You can manage the characters inside the string here
}

<STRING>\" {
  yybegin(YYINITIAL);
  return new Symbol(sym.STRING, yytext().substring(1, yytext().length() - 1)); // Devuelve el contenido entre comillas
}

//--------> Keywords
<YYINITIAL> SEARCH { return new Symbol(Sym.SEARCH, yyline, yycolumn, yytext()); }
<YYINITIAL> [Ss][Oo][Nn][Gg]_[Nn][Aa][Mm][Ee] { return new Symbol(Sym.SONG_NAME, yyline, yycolumn, yytext()); }
<YYINITIAL> [Aa][Rr][Tt][Ii][Ss][Tt] { return new Symbol(Sym.ARTIST, yyline, yycolumn, yytext()); }
<YYINITIAL> [Aa][Ll][Bb][Uu][Mm] { return new Symbol(Sym.ALBUM, yyline, yycolumn, yytext()); }
<YYINITIAL> [Mm][Ii][Ll][Ll][Ii][Oo][Nn]_[Ss][Tt][Rr][Ee][Aa][Mm][Ss] { return new Symbol(Sym.MILLION_STREAMS, yyline, yycolumn, yytext()); }
<YYINITIAL> [Rr][Ee][Ll][Ee][Aa][Ss][Ee]_[Dd][Aa][Tt][Ee] { return new Symbol(Sym.RELEASE_DATE, yyline, yycolumn, yytext()); }
<YYINITIAL> [Nn][Uu][Mm][Bb][Ee][Rr] { return new Symbol(Sym.NUMBER, yyline, yycolumn, yytext()); }

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

