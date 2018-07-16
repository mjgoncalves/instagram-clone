package com.example.marcelo.instagramclone.Utils;

public class StringManipulation {

    public static String expandUsername(String string){

        return string.replace(".", " ");
    }

    public static String condenseUsername(String string){
        return string.replace("", ".");
    }
}
