package memory;

public class Memory {
    
    private Integer[] storage;

    public Memory(Integer size){
        this.storage = new Integer[size];
    }

    /**
     * Destrói um objeto de memória.
     * Ao menos isso é o que essa gambiarra espera, pois já que o java
     * não nos deixa destruir objetos (ou deixa?) a solução é atribuir
     * null a referência e esperar que o GC colete o objeto antigo.
     * 
     * @param memory
     */
    public static void destroy(Memory memory){
        memory = null;
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