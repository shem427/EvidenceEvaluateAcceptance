package com.suyuan.poa.webapp.common;

import lombok.Data;

@Data
public class CommonBean {
    private Status status = Status.SUCESS;
    private String message;
}

enum Status {
    SUCESS,
    WARNING,
    ERROR
}