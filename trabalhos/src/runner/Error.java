package runner;

public enum Error {
    OK(0),
    STOPED(1),
    INVALID_INSTRUCTION(2);

    private final Integer value;

    private Error(Integer value) {
        this.value = value;
    }

    public Integer getValue(){
        return this.value;
    }
}
