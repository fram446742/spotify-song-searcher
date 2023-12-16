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

//------> Expresiones Regulares
// Define the {NUM} macro
DIGIT = [0-9]
NUM = {DIGIT}+

//------> Estados
%state YYINITIAL, STRING

%%
/*--------------------3. Reglas lexicas--------------------*/
//------> Simbolos

<YYINITIAL> SEARCH { return new Symbol(Sym.SEARCH, yytext()); }
<YYINITIAL> {NUM}+ { return new Symbol(Sym.NUM, yytext()); }
<YYINITIAL> , { return new Symbol(Sym.COMMA, yytext()); }
<YYINITIAL> "/" { return new Symbol(Sym.SLASH, yytext()); }
<YYINITIAL> \n { newline(); }

<STRING> "," { yybegin(YYINITIAL); return new Symbol(Sym.STRING, yytext()); }
<STRING> \n { yybegin(YYINITIAL); newline(); }

<STRING> [^,\n] {
    System.out.println("Unassigned character in string: " + yytext() + ", linea: " + yyline + ", columna: " + yycolumn);
}

<YYINITIAL> [\+\-]([0-9]+)(M)? {
    int numMillionStreams = Integer.parseInt(yytext().substring(1));
    if (yytext().startsWith("+")) {
        System.out.println("Symbol: " + yytext() + " (more than " + numMillionStreams + " million streams)");
        return new Symbol(Sym.MORE_THAN, yycolumn, yyline, yytext());
    } else if (yytext().startsWith("-")) {
        System.out.println("Symbol: " + yytext() + " (less than or equal to " + numMillionStreams + " million streams)");
        return new Symbol(Sym.LESS_THAN_EQUAL, yycolumn, yyline, yytext());
    }
}

//------> Errores lexicos

. {
    System.out.println("Error lexico: " + yytext() + ", linea: " + yyline + ", columna: " + yycolumn);
    TError data = new TError(yytext(), yyline, yycolumn, "Error lexico", "Simbolo no existe en el lenguaje");
    TablaErroresLexicos.add(data);
}

