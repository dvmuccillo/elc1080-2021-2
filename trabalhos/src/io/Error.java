package io;

public enum Error {
    OK(0),
    INVALID_DEVICE(1),
    UNSUPPORTED_OPERATION(2);

    private final Integer value;

    private Error(Integer value) {
        this.value = value;
    }

    public Integer getValue(){
        return this.value;
    }

}
