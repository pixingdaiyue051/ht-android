package com.tequeno.bar.fragment;

@FunctionalInterface
public interface FragmentMsg {

    String call();

    default String call1() {
        return "call1 is invoked";
    }

    default String call2() {
        return "call2 is invoked";
    }

    default String call3() {
        return "call3 is invoked";
    }
}