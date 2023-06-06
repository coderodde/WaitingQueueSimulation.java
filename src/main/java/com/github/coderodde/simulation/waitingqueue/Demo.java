package com.github.coderodde.simulation.waitingqueue;

import com.github.coderodde.simulation.waitingqueue.WaitingQueueSimulation.Person;
import java.util.List;

public class Demo {

    public static void main(String[] args) {
        WaitingQueueSimulation wqs = new WaitingQueueSimulation();
        
        wqs.add("Alice", 2.0);
        wqs.add("Bob", 3.0);
        wqs.add("Jack", 1.0);
        
        List<Person> persons = wqs.findMinimizingOrder();
        System.out.println(persons);
    }
}
