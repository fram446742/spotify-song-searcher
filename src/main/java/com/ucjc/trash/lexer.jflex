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

    public static LinkedList<TError> TablaErroresLexicos = new LinkedList<TError>();

    // newline function declaracion
    private void newline() {
        yyline++;
        yycolumn = 1;
    }

    // processString function declaracion
    private StringBuilder stringBuffer = new StringBuilder();

    private void processString() {
        stringBuffer.append(yytext());

}
%}

//------> Directivas
%public
%class Lexer
//%class SimpleSongLexer
%cupsym Sym
%cup
%char
%column
%full
%ignorecase
%line
STRING_LITERAL = \"([^\"\n]*(\\\"|\\\\.)*[^\"\n]*)\"

//------> Expresiones Regulares
// Define the {NUM} macro
// LENGUAGE = [a-zA-Z]
DIGIT = [0-9]
NUM = {DIGIT}+
NULL_TEXT = ""
//------> Estados
%state YYINITIAL

%%
/*--------------------3. Reglas lexicas--------------------*/
//------> Simbolos
<YYINITIAL> SEARCH { return new Symbol(Sym.SEARCH, yytext()); }
<YYINITIAL> {NUM}+ { return new Symbol(Sym.NUM, yytext()); }
<YYINITIAL> {STRING_LITERAL} { return new Symbol(Sym.STRING, yytext()); }
// <YYINITIAL> [a-zA-Z][a-zA-Z0-9]* { return new Symbol(Sym.WORD, yytext()); }

<YYINITIAL> [Ss][Oo][Nn][Gg]_[Nn][Aa][Mm][Ee] { return new Symbol(Sym.SONG_NAME, yytext()); }
<YYINITIAL> [Aa][Rr][Tt][Ii][Ss][Tt] { return new Symbol(Sym.ARTIST, yytext()); }
<YYINITIAL> [Aa][Ll][Bb][Uu][Mm] { return new Symbol(Sym.ALBUM, yytext()); }
<YYINITIAL> [Mm][Ii][Ll][Ll][Ii][Oo][Nn]_[Ss][Tt][Rr][Ee][Aa][Mm][Ss] { return new Symbol(Sym.MILLION_STREAMS, yytext()); }
<YYINITIAL> [Rr][Ee][Ll][Ee][Aa][Ss][Ee]_[Dd][Aa][Tt][Ee] { return new Symbol(Sym.RELEASE_DATE, yytext()); }
<YYINITIAL> [Nn][Uu][Mm][Bb][Ee][Rr] { return new Symbol(Sym.NUMBER, yytext()); }

<YYINITIAL> , { return new Symbol(Sym.COMMA, yytext()); }
<YYINITIAL> "/" { return new Symbol(Sym.SLASH, yytext()); }
<YYINITIAL> "=" { return new Symbol(Sym.EQUALS, yytext()); }
<YYINITIAL> ">" { return new Symbol(Sym.MORE_THAN, yytext()); }
<YYINITIAL> "<" { return new Symbol(Sym.LESS_THAN, yytext()); }
<YYINITIAL> "<=" { return new Symbol(Sym.LESS_THAN_EQUAL, yytext()); }
<YYINITIAL> ">=" { return new Symbol(Sym.MORE_THAN_EQUAL, yytext()); }
// <YYINITIAL> "(" { return new Symbol(Sym.PARI, yytext()); }
// <YYINITIAL> ")" { return new Symbol(Sym.PARD, yytext()); }
// <YYINITIAL>"AND" { return new Symbol(Sym.AND, yytext()); }
// <YYINITIAL>"OR"  { return new Symbol(Sym.OR, yytext()); }
// <YYINITIAL>"&&"  { return new Symbol(Sym.AND, yytext()); }
// <YYINITIAL>"||"  { return new Symbol(Sym.OR, yytext()); }

// <YYINITIAL> [\r\n]+  { /*Skip new line*/ ;}
<YYINITIAL> [\t\r\n\f]+ { /*Skip whitespace*/ return new Symbol(Sym.WHITE_SPACE, yytext()); }

<YYINITIAL> NULL_TEXT { /*Ignore null text*/ System.err.println("Error: Texto nulo encontrado"); }

//------> Espacios
[ \t\f ]             {/* Espacios en blanco, se ignoran */}


//------> Errores lexicos
. {
    System.out.println("Error lexico: " + yytext() + ", linea: " + yyline + ", columna: " + yycolumn);
    TError data = new TError(yytext(), yyline, yycolumn, "Error lexico", "Simbolo no existe en el lenguaje");
    TablaErroresLexicos.add(data);

    // Switch to a different state to ignore characters until the end of the line
    yybegin(YYINITIAL);
}
