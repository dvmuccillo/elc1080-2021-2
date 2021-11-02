package mem;

public enum Error {
    
    OK(0),
    INVALID_ADDRESS(1);

    private final Integer value;

    private Error(Integer value) {
        this.value = value;
    }

    public Integer getValue(){
        return this.value;
    }

}
