%%
%class SpotifyLexer
%public
%unicode
%cup

%{
import java.io.StringReader;
import main.Tag;
import main.Token;

// Método para manejar errores
private Token createErrorToken(String value) {
    System.err.println("Error: Caracter no reconocido: " + value);
    return new Token(Tag.ERROR, value);
}


%}

DIGIT = [0-9]
ID = [a-zA-Z_][a-zA-Z0-9_]*

%%
{DIGIT}+              { return new Token(Tag.NUMBER, yytext()); }
"SELECT"             { return new Token(Tag.SELECT, yytext()); }
"FROM"               { return new Token(Tag.FROM, yytext()); }
"WHERE"              { return new Token(Tag.WHERE, yytext()); }
"AND"                { return new Token(Tag.AND, yytext()); }
"OR"                 { return new Token(Tag.OR, yytext()); }
"ORDER"              { return new Token(Tag.ORDER, yytext()); }
"BY"                 { return new Token(Tag.BY, yytext()); }

"NUMBER"             { return new Token(Tag.NUMBER, yytext()); }
"SONG_NAME"          { return new Token(Tag.SONG_NAME, yytext()); }
"ARTIST"             { return new Token(Tag.ARTIST, yytext()); }
"ALBUM"              { return new Token(Tag.ALBUM, yytext()); }
"MILLION_STREAMS"    { return new Token(Tag.MILLION_STREAMS, yytext()); }
"RELEASE_DATE"       { return new Token(Tag.RELEASE_DATE, yytext()); }

\"([^\"\n])*\"       { return new Token(Tag.STRING, yytext()); }

"//" .* [\r\n]       { /* Ignorar comentarios de una línea */ }
"/*" ([^*]|\*+[^*/])* "*"* "/" { /* Ignorar comentarios multilínea */ }

"<"                 { return new Token(Tag.LESS_THAN, yytext()); }
">"                 { return new Token(Tag.GREATER_THAN, yytext()); }
"<="                { return new Token(Tag.LESS_THAN_OR_EQUAL, yytext()); }
">="                { return new Token(Tag.GREATER_THAN_OR_EQUAL, yytext()); }

";"                 { return new Token(Tag.SEMICOLON, yytext()); }

{ID}                 { return new Token(Tag.ID, yytext()); }

[ \t\n\r]            // Ignorar espacios en blanco y saltos de línea
.                    { return createErrorToken(yytext()); }
