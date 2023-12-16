/*--------------------1. Codigo Usuario--------------------*/
//------> Paquetes e importaciones
package com.ucjc.analizers;

import java_cup.runtime.*;
import java.util.LinkedList;

/*--------------------2. Opciones y declaraciones--------------------*/

%%
%{
    //------> Codigo de usuario en sintaxis java
    public static LinkedList<TError> TablaErroresLexicos = new LinkedList<TError>();

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
//%unicode

//------> Expresiones regulares

number = [0-9]+

//------> Estados
// %state STRING
// %state COMENTARIO

%%

/*--------------------3. Reglas lexicas--------------------*/

//------> Simbolos

<YYINITIAL> [\+\-]([0-9]+)(M)?   {
    int numMillionStreams = Integer.parseInt(yytext.substring(1, yytext.length() - 1));
    if (yytext.startsWith("+")) {
        System.out.println("Symbol: " + yytext + " (more than " + numMillionStreams + " million streams)");
        return new Symbol(Sym.more_than, yycolumn, yyline, yytext());
    } else if (yytext.startsWith("-")) {
        System.out.println("Symbol: " + yytext + " (less than or equal to " + numMillionStreams + " million streams)");
        return new Symbol(Sym.less_than_equal, yycolumn, yyline, yytext());
    }
}

<YYINITIAL> \/       {
    System.out.println("Symbol: / (search for multiple songs)");
    return new Symbol(Sym.slash, yycolumn, yyline, yytext());
}

<YYINITIAL> ,        {
    System.out.println("Symbol: , (search for multiple songs)");
    return new Symbol(Sym.comma, yycolumn, yyline, yytext());
}

// <YYINITIAL> "+"         {System.out.println("Symbol: " + yytext + " plus"); return new Symbol(Sym.plus, yycolumn, yyline, yytext()); }
// <YYINITIAL> "-"         {System.out.println("Symbol: " + yytext + " minus"); return new Symbol(Sym.minus, yycolumn, yyline, yytext()); }
// <YYINITIAL> "*"         {System.out.println("Symbol: " + yytext + " star"); return new Symbol(Sym.star, yycolumn, yyline, yytext()); }
// <YYINITIAL> "/"         {System.out.println("Symbol: " + yytext + " slash"); return new Symbol(Sym.slash, yycolumn, yyline, yytext()); }
// <YYINITIAL> "("         {System.out.println("Symbol: " + yytext + " parI"); return new Symbol(Sym.parI, yycolumn, yyline, yytext()); }
// <YYINITIAL> ")"         {System.out.println("Symbol: " + yytext + " parD"); return new Symbol(Sym.parD, yycolumn, yyline, yytext()); }

//------> Simbolos expresiones regulares

<YYINITIAL> (number)         {System.out.println("Symbol: " + yytext + " number"); return new Symbol(Sym.num, yycolumn, yyline, yytext()); }

//------> Espacios

[ \t\r\n\f ]                {/* Espacios en blanco, se ignoran*/}

//------> Errores lexicos

.                           {System.out.println("Error lexico: " + yytext() + ", linea: " + yyline + ", columna: " + yycolumn);
                             TError data = new TError(yytext, yyline, yycolumn, "Error lexico", "Simbolo no existe en el lenguaje");
                             TablaErroresLexicos.add(data); }
