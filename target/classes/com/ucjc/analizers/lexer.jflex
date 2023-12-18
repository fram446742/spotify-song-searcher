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
//--------> Números
<YYINITIAL> {NUM}+ { return new Symbol(Sym.NUM, yyline, yycolumn, yytext()); }

//--------> Cadenas de Texto
<YYINITIAL> {STRING_LITERAL} { return new Symbol(Sym.STRING, yyline, yycolumn, yytext()); }

//--------> Palabras Clave
<YYINITIAL> SEARCH { return new Symbol(Sym.SEARCH, yyline, yycolumn, yytext()); }
<YYINITIAL> [Ss][Oo][Nn][Gg]_[Nn][Aa][Mm][Ee] { return new Symbol(Sym.SONG_NAME, yyline, yycolumn, yytext()); }
<YYINITIAL> [Aa][Rr][Tt][Ii][Ss][Tt] { return new Symbol(Sym.ARTIST, yyline, yycolumn, yytext()); }
<YYINITIAL> [Aa][Ll][Bb][Uu][Mm] { return new Symbol(Sym.ALBUM, yyline, yycolumn, yytext()); }
<YYINITIAL> [Mm][Ii][Ll][Ll][Ii][Oo][Nn]_[Ss][Tt][Rr][Ee][Aa][Mm][Ss] { return new Symbol(Sym.MILLION_STREAMS, yyline, yycolumn, yytext()); }
<YYINITIAL> [Rr][Ee][Ll][Ee][Aa][Ss][Ee]_[Dd][Aa][Tt][Ee] { return new Symbol(Sym.RELEASE_DATE, yyline, yycolumn, yytext()); }
<YYINITIAL> [Nn][Uu][Mm][Bb][Ee][Rr] { return new Symbol(Sym.NUMBER, yyline, yycolumn, yytext()); }

//--------> Operadores y Símbolos
<YYINITIAL> , { return new Symbol(Sym.COMMA, yyline, yycolumn, yytext()); }
<YYINITIAL> "=" { return new Symbol(Sym.EQUALS, yyline, yycolumn, yytext()); }
<YYINITIAL> ">" { return new Symbol(Sym.MORE_THAN, yyline, yycolumn, yytext()); }
<YYINITIAL> "<" { return new Symbol(Sym.LESS_THAN, yyline, yycolumn, yytext()); }
<YYINITIAL> "<=" { return new Symbol(Sym.LESS_THAN_EQUAL, yyline, yycolumn, yytext()); }
<YYINITIAL> ">=" { return new Symbol(Sym.MORE_THAN_EQUAL, yyline, yycolumn, yytext()); }

//--------> Espacios en Blanco
<YYINITIAL> [\t\r\n\f]+ { /*Espacios en blanco, se ignoran*/ return new Symbol(Sym.WHITE_SPACE, yyline, yycolumn, yytext()); }

//--------> Ignorar Null Text
<YYINITIAL> NULL_TEXT { /*Ignorar null text*/ System.err.println("Error: Texto nulo encontrado"); }

//--------> Errores Lexicos
<YYINITIAL> . {
    System.out.println("Error lexico: " + yytext() + ", linea: " + yyline + ", columna: " + yycolumn);
    TError data = new TError(yytext(), yyline, yycolumn, "Error lexico", "Simbolo no existe en el lenguaje");
    TablaErroresLexicos.add(data);

    // Cambiar a un estado diferente para ignorar caracteres hasta el final de la línea
    yybegin(YYINITIAL);
}
