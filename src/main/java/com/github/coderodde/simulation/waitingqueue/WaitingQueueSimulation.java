package com.github.coderodde.simulation.waitingqueue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class WaitingQueueSimulation {
    
    public static final class Person {
        private final String name;
        private final double waitingTime;
        
        Person(String name, double waitingTime) {
            this.name = 
                    Objects.requireNonNull(name, "The name is null.");
            
            this.waitingTime = checkWaitingTime(waitingTime);
        }
        
        public String getName() {
            return name;
        }
        
        public double getWaitingTime() {
            return waitingTime;
        }
        
        @Override
        public int hashCode() {
            return name.hashCode();
        }
        
        @Override
        public boolean equals(Object o) {
            return ((Person) o).name.equals(name);
        }
        
        @Override
        public String toString() {
            return "[" + name + ", " + waitingTime + "]";
        }
    }
    
    private final Set<Person> personSet = new HashSet<>();
    
    public void add(String name, double waitingTime) {
        personSet.add(new Person(name, waitingTime));
    }
    
    public List<Person> findMinimizingOrder() {
        List<Person> personList = new ArrayList<>(personSet);
        PermutationIterable<Person> iterable = 
                new PermutationIterable<>(personList);
        
        double minimumTotalWaitTime = Double.POSITIVE_INFINITY;
        List<Person> minimumTotalWaitTimeOrder = 
                new ArrayList<>(personList.size());
        
        for (List<Person> persons : iterable) {
            double tentativeTotalWaitTime = totalWaitTime(persons);
            
            if (minimumTotalWaitTime > tentativeTotalWaitTime) {
                minimumTotalWaitTime = tentativeTotalWaitTime;
                minimumTotalWaitTimeOrder.clear();
                minimumTotalWaitTimeOrder.addAll(persons);
            }
        }
        
        return minimumTotalWaitTimeOrder;
    }
    
    private static double totalWaitTime(List<Person> personList) {
        double totalWaitTime = 0.0;
        
        for (int i = 1; i < personList.size(); i++) {
            totalWaitTime += partialTotalWaitTime(personList, i);
        }
        
        return totalWaitTime;
    }
    
    private static double partialTotalWaitTime(List<Person> personList, int startIndex) {
        return personList.get(startIndex - 1).getWaitingTime() * 
              (personList.size() - startIndex);
    }

    private static double checkWaitingTime(double waitingTime) {
        if (waitingTime <= 0.0) {
            throw new IllegalArgumentException(
                    "Too small waiting time: " + waitingTime);
        }
        
        if (Double.isNaN(waitingTime)) {
            throw new IllegalArgumentException("Waiting time is NaN.");
        }
        
        if (Double.isInfinite(waitingTime)) {
            throw new IllegalArgumentException(
                    "Waiting time infinite in absolute value: " + waitingTime);
        }
        
        return waitingTime;
    }
}
