package com.example.homeworkers2.order;

import java.util.Comparator;

public class OrdersComporator implements Comparator<OrderData> {


    @Override
    public int compare(OrderData o1, OrderData o2) {

        int priority1 = getPriotity(o1);
        int priority2 = getPriotity(o2);

        return Integer.compare(priority1, priority2);
    }

    private int getPriotity(OrderData o){
        int priority = -1;

        if( o.getIsActive()){
            priority = 0;
        }

        if(!o.getIsActive()){
            priority = 1;
        }

        return priority;
    }
}
