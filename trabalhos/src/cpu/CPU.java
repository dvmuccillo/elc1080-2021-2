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
    private int stopInfo;

    private Memory memory;

    private DevicesController devicesController;

    public void setDevicesController(DevicesController devicesController){
        this.devicesController = devicesController;
    }

    public void setMemory(Memory memory){
        this.memory = memory;
    }

}
