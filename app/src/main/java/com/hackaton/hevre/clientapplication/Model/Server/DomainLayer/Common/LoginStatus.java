package com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.Common;

/**
 * Created by אביחי on 23/10/2016.
 */
public enum LoginStatus {
    SUCCESS(1),
    INVALID_CREDENTIALS(2),
    NAME_ALREADY_EXISTS(3),
    NAME_DOESNT_EXIST(4),
    MAIL_ALREADY_EXIST(5),
    FACEBOOK_LOGIN(6),
    ERROR(7);

    private final int value;

    private LoginStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
