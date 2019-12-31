package com.wbb.controller.other;

public class Test {

    public static void main(String[] args) {
        System.out.println(isValid());

    }

    public static boolean isValid(){

        notValid();

        return false;
    }

    public static void notValid(){
        return;
    }
}
