package ua.edu.ztu.student.zipz221_boyu.data.entity;

import androidx.annotation.IntRange;

public class CreditBalance {

    private int limit;
    private float balance;

    public CreditBalance(@IntRange(from = 0) int limit) {
        this.limit = limit;
        this.balance = limit;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public float getBalance() {
        return balance;
    }

    public float replenishBalance(float sum) {
        float it = balance + sum;
        balance = it > limit ? limit : it;
        return it > limit ? it - limit : 0;
    }
}
