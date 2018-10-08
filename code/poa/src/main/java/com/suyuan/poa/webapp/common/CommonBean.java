package com.suyuan.poa.webapp.common;

import lombok.Data;

/**
 * Bean共通父类。
 */
@Data
public class CommonBean {
    private Status status = Status.SUCCESS;
    private String message;

    public enum Status {
        SUCCESS,
        WARNING,
        ERROR
    }
}


