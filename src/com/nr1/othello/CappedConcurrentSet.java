package com.nr1.othello;

import java.util.Comparator;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.ToIntFunction;

public class CappedConcurrentSet<T> {
    private final ConcurrentSkipListSet<T> set;
    private final int capacity;
    private final AtomicInteger highest = new AtomicInteger(Integer.MIN_VALUE);
    private final ToIntFunction<T> valueGetter;
    public CappedConcurrentSet(int capacity, ToIntFunction<T> valueGetter) {
        this.capacity = capacity;
        this.set = new ConcurrentSkipListSet<>(Comparator.comparingInt(valueGetter));
        this.valueGetter = valueGetter;
    }

    public void add(T item) {
        int value = valueGetter.applyAsInt(item);
        if (set.size() >= capacity && highest.get() > value) {
            System.out.println("we are full");
            return;
        }

        set.add(item);
        if (highest.get() < value){
            highest.set(value);
        }
        while (set.size() > capacity) {
            set.pollLast();
        }
    }

    public Set<T> getWindow() {
        return set;
    }
}