package memory;

public class Memory {
    
    private Integer size;

    public Memory(Integer size){
        this.size = size;
    }

    public Error read(Integer address, Integer value){
        return Error.OK;
    }

    private Integer getSize(){
        return this.size;
    }

    public Error write(Integer address, Integer value){
        return Error.OK;
    }

}
