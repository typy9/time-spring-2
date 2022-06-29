package com.parpiiev.time.model;

public enum Status {
    CREATED("CREATED"),
    APPROVED("APPROVED"),
    DECLINED("DECLINED"),
    TOBEDELETED("TOBEDELETED");
    private final String value;
    Status(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }

}
