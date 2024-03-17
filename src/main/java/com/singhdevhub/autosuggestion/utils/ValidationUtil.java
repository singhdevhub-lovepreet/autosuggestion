package com.singhdevhub.autosuggestion.utils;

import java.util.Stack;

public class ValidationUtil
{

    public static boolean validParentheses(String code){
        Stack<Character> paranthesesStack = new Stack<>();
        for(int i = 0; i<code.length(); i++){
            if(code.charAt(i) == '('){
                paranthesesStack.push('(');
            }else if(code.charAt(i)=='{'){
                paranthesesStack.push('{');
            }else if(code.charAt(i)=='<'){
                paranthesesStack.push('<');
            }else if(code.charAt(i)==')' && paranthesesStack.pop()!='('){
                return false;
            }else if(code.charAt(i)=='}' && paranthesesStack.pop()!='{'){
                return false;
            }else if(code.charAt(i)=='>' && (paranthesesStack.pop()!='<')){
                return false;
            }
        }
        return paranthesesStack.isEmpty();
    }

}
