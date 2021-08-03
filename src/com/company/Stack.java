package com.company;

import java.util.ArrayList;
import java.util.Collections;

public class Stack {
    ArrayList<Integer> stackList;

    Stack() {
        stackList = new ArrayList<>();
    }


    void push(int value) {
        stackList.add(value);
    }


    int pop() {

        if (!isEmpty()) { // checks for an empty Stack
            int popValue = stackList.get(stackList.size() - 1);
            stackList.remove(stackList.size() - 1); // removes the poped element
            return popValue;
        } else {
            /*System.out.print("The stack is already empty  ");*/
            return -1;
        }
    }

    boolean isEmpty() {
        if (stackList.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }


    int peek() {
        return stackList.get(stackList.size() - 1);
    }

    public int size() {
        return stackList.size();
    }

    public int getMax() {
        if (isEmpty()) return 0;
        return Collections.max(stackList);
    }

    public int getMin() {
        if (isEmpty()) return 0;
        return Collections.min(stackList);
    }
}