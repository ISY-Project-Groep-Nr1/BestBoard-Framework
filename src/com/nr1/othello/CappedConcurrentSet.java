package com.nr1.othello;

import java.util.Comparator;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.ToIntFunction;

public class CappedConcurrentSet<T> {
    private final ConcurrentSkipListSet<T> set;
    private final AtomicInteger size = new AtomicInteger(0);
    private final int capacity;
    private final AtomicInteger lowest = new AtomicInteger(Integer.MIN_VALUE);
    private final ToIntFunction<T> valueGetter;
    public CappedConcurrentSet(int capacity, ToIntFunction<T> valueGetter) {
        this.capacity = capacity;
        this.set = new ConcurrentSkipListSet<>(
                Comparator.comparingInt(valueGetter)
                        .thenComparingInt(System::identityHashCode)
        );
        this.valueGetter = valueGetter;
    }

    public void add(T item) {
        int value = valueGetter.applyAsInt(item);
        if (size.get() >= capacity && lowest.get() <= value) {
            return;
        }

        if (set.add(item)) {
            int currentSize = size.incrementAndGet();

            while (currentSize > capacity) {
                T last = set.pollFirst();
                if (last != null) {
                    int lastValue = valueGetter.applyAsInt(last);
                    if (lastValue < lowest.get()){
                        lowest.set(lastValue);
                    }
                    currentSize = size.decrementAndGet();
                } else {
                    break; // Set was cleared or modified elsewhere
                }
            }
        }
    }

    public Set<T> getWindow() {
        return set;
    }
    public int getSize(){
        return size.get();
    }
}