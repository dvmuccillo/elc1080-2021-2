package cpu;

import io.DevicesController;
import memory.Memory;

public class CPU {
    
    /**
     * Contador de programa.
     * Registrador que contém o endereço da memória onde se encontra a próxima instrução a ser executada.
     */
    private int PC;

    /**
     * Acumulador.
     * Registrador que contém um dado usado de forma implícita em várias instruções.
     */
    private int A;

    /**
     * Registrador auxiliar.
     */
    private int X;

    /**
     * Modo da CPU.
     * Registrador que contém o modo de execução da CPU, pode ser:
     * - normal - (valor 0) a CPU está apta a executar a próxima instrução;
     * - parada - (valor diferente de 0) a CPU não pode executar a próxima instrução.
     *            O código identifica o motivo (execução suspensa por ter executado uma instrução de parada,
     *            ou erro causado na execução da última instrução).
     */
    private int mode;

    /**
     * Complemento de Parada.
     * Registrador que contém informação complementar sobre o motivo de não estar em modo normal.
     */
    private String stopInfo;

    private Memory memory;

    private DevicesController devicesController;

    /**
     * Retorna o estado da CPU.
     * 
     * @return O estado da CPU.
     */
    public Integer getMode(){
        return this.mode;
    }

    /**
     * Imprime o estado da CPU.
     */
    public void printMode(){
        StringBuilder stringBuilder = new StringBuilder("Estado da CPU: ").append(this.mode);

        if(0 == this.mode){
            stringBuilder.append(" (OK)");
        } else {
            stringBuilder.append(" - Erro: ").append(this.stopInfo);
        } 

        System.out.println(stringBuilder.toString());
    }

    public void setDevicesController(DevicesController devicesController){
        this.devicesController = devicesController;
    }

    /**
     * Define o estado da CPU.
     * 
     * @param mode O estado da CPU.
     */
    public void setMode(Integer mode){
        this.mode = mode;
    }

    public void setMemory(Memory memory){
        this.memory = memory;
    }

}
