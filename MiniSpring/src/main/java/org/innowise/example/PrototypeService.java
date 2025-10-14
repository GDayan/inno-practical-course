package org.innowise.example;

import org.innowise.annotation.Component;
import org.innowise.annotation.Scope;

@Component
@Scope("prototype")
public class PrototypeService {
    private static int instanceCount = 0;
    private final int instanceNumber;

    public PrototypeService(){
        instanceNumber = ++instanceCount;
        System.out.println("A PrototypeService instance has been created №: " + instanceNumber);
    }

    public String getInfo(){
        return "PrototypeService instance №: " + instanceNumber;
    }
}
