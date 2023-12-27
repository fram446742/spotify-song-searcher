/*--------------------1. User Code--------------------*/
//------> Packages and imports
package com.ucjc.generated;

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

    public LinkedList clearTable(){
      LexicalErrorTable.clear();
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

//------> States
%state YYINITIAL

%%
/*--------------------3. Lexical rules--------------------*/
//------> Symbols
<YYINITIAL> {NUM} { return new Symbol(Sym.NUM, yyline, yycolumn, yytext()); }

//--------> Keywords
<YYINITIAL> [Ss][Ee][Aa][Rr][Cc][Hh] | [Bb][Uu][Ss][Cc][Aa][Rr] { return new Symbol(Sym.SEARCH, yyline, yycolumn, yytext()); }
<YYINITIAL> [Aa][Ll][Ll] | [Ee][Vv][Ee][Rr][Yy][Tt][Hh][Ii][Nn][Gg] | [Tt][Oo][Dd][Oo] | [Tt][Oo][Dd][Aa][Ss] { return new Symbol(Sym.ALL, yyline, yycolumn, yytext()); }
<YYINITIAL> [Ss][Oo][Nn][Gg]([ _])?[Nn][Aa][Mm][Ee] | [Ss][Oo][Nn][Gg] | [Nn][Aa][Mm][Ee] | [Tt][Ii][Tt][Ll][Ee] | [Tt][IiÍí][Tt][Uu][Ll][Oo] | [Nn][Oo][Mm][Bb][Rr][Ee] | [Cc][Aa][Nn][Cc][Ii][OoÓó][Nn] { return new Symbol(Sym.SONG_NAME, yyline, yycolumn, yytext()); }
<YYINITIAL> [Aa][Rr][Tt][Ii][Ss][Tt] | [Aa][Rr][Tt][Ii][Ss][Tt][Aa] { return new Symbol(Sym.ARTIST, yyline, yycolumn, yytext()); }
<YYINITIAL> [AaÁá][Ll][Bb][Uu][Mm] { return new Symbol(Sym.ALBUM, yyline, yycolumn, yytext()); }
<YYINITIAL> [Mm][Ii][Ll][Ll][Ii][Oo][Nn]([ _])?[Ss][Tt][Rr][Ee][Aa][Mm][Ss] | ([Mm][Ii][Ll][Ll][Oo][Nn][Ee][Ss])?([ _])?([Dd][Ee])?([ _])?[Rr][Ee][Pp][Rr][Oo][Dd][Uu][Cc][Cc][Ii][Oo][Nn][Ee][Ss] | ([Nn][UuÚú][Mm][Ee][Rr][Oo])?([ _])?([Dd][Ee])?([ _])?[Rr][Ee][Pp][Rr][Oo][Dd][Uu][Cc][Cc][Ii][Oo][Nn][Ee][Ss] { return new Symbol(Sym.MILLION_STREAMS, yyline, yycolumn, yytext()); }
<YYINITIAL> [Nn][Uu][Mm][Bb][Ee][Rr] | [Pp][Oo][Ss][Ii][Tt][Ii][Oo][Nn] | [Nn][UuÚú][Mm][Ee][Rr][Oo] | [Pp][Uu][Ee][Ss][Tt][Oo] | [Pp][Oo][Ss][Ii][Cc][Ii][OoÓó][Nn] { return new Symbol(Sym.NUMBER, yyline, yycolumn, yytext()); }

//--------> Text Strings
<YYINITIAL> \"[^\"]+\" { return new Symbol(Sym.STRING, yyline, yycolumn, yytext().substring(1, yytext().length() - 1)); }

//--------> Other text
<YYINITIAL> [A-Za-z]+ {/* Ignore other text*/}
<YYINITIAL> [\p{L}\p{M}\p{P}&&[^\"=><.]]+ {/* Ignore other text*/}

//--------> Operators & Symbols
<YYINITIAL> "=" | [Ee][Qq][Uu][Aa][Ll]([Ss])? | [Ii][Gg][Uu][Aa][Ll]([ _])?([Aa])? { return new Symbol(Sym.EQUALS, yyline, yycolumn, yytext()); }
<YYINITIAL> ">" | [Mm][Oo][Rr][Ee]([ _])?([Tt][Hh][Aa][Nn])? | [Mm][AaÁá][Ss]([ _])?([Dd][Ee])? | [Mm][AaÁá][Ss]([ _])?([Qq][Uu][Ee])? | [Mm][Aa][Yy][Oo][Rr]([Ee][Ss])?([ _])?([Qq][Uu][Ee])? | [Mm][Aa][Yy][Oo][Rr]([Ee][Ss])?([ _])?([Aa])? { return new Symbol(Sym.MORE_THAN, yyline, yycolumn, yytext()); }
<YYINITIAL> "<" | [Ll][Ee][Ss][Ss]([ _])?([Tt][Hh][Aa][Nn])? | [Mm][Ee][Nn][Oo][Ss]([ _])?([Dd][Ee])? | [Mm][Ee][Nn][Oo][Ss]([ _])?([Qq][Uu][Ee])? | [Mm][Ee][Nn][Oo][Rr]([Ee][Ss])?([ _])?([Qq][Uu][Ee])? | [Mm][Ee][Nn][Oo][Rr]([Ee][Ss])?([ _])?([Aa])? { return new Symbol(Sym.LESS_THAN, yyline, yycolumn, yytext()); }
<YYINITIAL> "<=" | [Ll][Ee][Ss][Ss]([ _])?([Tt][Hh][Aa][Nn])?([ _])?[Ee][Qq][Uu][Aa][Ll] | [Mm][Ee][Nn][Oo][SS]([ _])?[Ii][Gg][Uu][Aa][Ll]([ _])?([Qq][Uu][Ee])? | [Mm][Ee][Nn][Oo][Ss]([ _])?[Ii][Gg][Uu][Aa][Ll]([ _])?([Aa])? | [Mm][Ee][Nn][Oo][Rr]([Ee][Ss])?([ _])?[Ii][Gg][Uu][Aa][Ll]([Ee][Ss])?([ _])?([Qq][Uu][Ee])? | [Mm][Ee][Nn][Oo][Rr]([Ee][Ss])?([ _])?[Ii][Gg][Uu][Aa][Ll]([Ee][Ss])?([ _])?([Aa])? { return new Symbol(Sym.LESS_THAN_EQUAL, yyline, yycolumn, yytext()); }
<YYINITIAL> ">=" | [Mm][Oo][Rr][Ee]([ _])?([Tt][Hh][Aa][Nn])?([ _])?[Ee][Qq][Uu][Aa][Ll] | [Mm][AaÁá][Ss]([ _])?[Ii][Gg][Uu][Aa][Ll]([ _])?([Qq][Uu][Ee])? | [Mm][AaÁá][Ss]([ _])?[Ii][Gg][Uu][Aa][Ll]([ _])?([Aa])? | [Mm][Aa][Yy][Oo][Rr]([Ee][Ss])?([ _])?[Ii][Gg][Uu][Aa][Ll]([Ee][Ss])?([ _])?([Qq][Uu][Ee])? | [Mm][Aa][Yy][Oo][Rr]([Ee][Ss])?([ _])?[Ii][Gg][Uu][Aa][Ll]([Ee][Ss])?([ _])?([Aa])? { return new Symbol(Sym.MORE_THAN_EQUAL, yyline, yycolumn, yytext()); }

//--------> White Spaces and Ignored Text
[A-Za-z]+ { /* Ignore other text*/ }
[ \t\f]+ | \r?\n | \r { /* Ignore white spaces and line breaks */ }
[\p{L}\p{M}\p{P}&&[^\"=><.]]+ { /* Ignore other text*/ }

//--------> Lexical Errors
.  {
    System.out.println("Lexical Error: " + yytext() + ", line: " + yyline + ", column: " + yycolumn);
    TError data = new TError(yytext(), yyline, yycolumn, "Lexical Error", "Symbol does not exist in the language");
    LexicalErrorTable.add(data);

    // Switch to a different state to ignore characters until the end of the line
    yybegin(YYINITIAL);
}

