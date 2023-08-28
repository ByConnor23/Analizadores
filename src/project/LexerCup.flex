package project;

import java_cup.runtime.Symbol;

%%
%class LexerCup
%type java_cup.runtime.Symbol;
%cup
%full
%line
%char
%{  
    //Cadena analizada
    private Symbol symbol(int type, Object value){
        return new Symbol(type, yyline, yycolum ,value);
    }

    //Cadena no analizada
    private Symbol symbol(int type, Object value){
        return new Symbol(type, yyline, yycolum);
    }
%}
%%

