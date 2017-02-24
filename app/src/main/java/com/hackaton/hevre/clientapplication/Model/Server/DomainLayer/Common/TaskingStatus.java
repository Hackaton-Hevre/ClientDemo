package com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.Common;

/**
 * Created by אביחי on 23/10/2016.
 */
public enum TaskingStatus {
    SUCCESS(1),
    TASK_ALREADY_EXISTS(2),
    ILLEGAL_TASK(3);

    private final int value;

    private TaskingStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
