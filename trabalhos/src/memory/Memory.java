package memory;

public class Memory {
    
    private Integer[] storage;

    public Memory(Integer size){
        this.storage = new Integer[size];
    }

    private Boolean isValidAddress(Integer address){
        return address < this.getSize();
    }

    public Error read(Integer address, Integer value){
        if(!this.isValidAddress(address))
            return Error.INVALID_ADDRESS;

        value = this.storage[address];

        return Error.OK;
    }

    public Integer getSize() {
        return this.storage.length;
    }

    public Error write(Integer address, Integer value){
        if(!this.isValidAddress(address))
            return Error.INVALID_ADDRESS;

        this.storage[address] = value;

        return Error.OK;
    }

}
