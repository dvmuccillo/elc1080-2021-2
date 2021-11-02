package runner;

import java.util.concurrent.atomic.AtomicInteger;

import io.DevicesController;
import mem.Memory;
import runner.Error;

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
    private AtomicInteger A;

    /**
     * Registrador temporário para leitura do valor A1 (mem[PC+1]).
     */
    private AtomicInteger A1;

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
    private int mode; //TODO arrumar isso aqui pra usar o ENUM em vez da gambiarra atual

    /**
     * Complemento de Parada.
     * Registrador que contém informação complementar sobre o motivo de não estar em modo normal.
     */
    private String stopInfo;

    private Memory memory;

    private DevicesController devicesController;

    public CPU(){
        this.A = new AtomicInteger();
        this.A1 = new AtomicInteger();
    }

    /**
     * Busca o valor A1 (PC+1) na memória.
     * @return O retorno da operação de leitura;
     */
    private mem.Error readA1(){
        final AtomicInteger value = new AtomicInteger();
        return this.memory.read(this.PC + 1, value);
    }

    /**
     * Imprime o estado da CPU.
     */
    public void printState(){
        StringBuilder stringBuilder = new StringBuilder("Estado da CPU: ").append(this.mode);

        if(0 == this.mode){
            stringBuilder.append(" (OK)");
        } else {
            stringBuilder.append(" - Erro: ").append(this.stopInfo);
        } 

        System.out.println(stringBuilder.toString());
        System.out.println("PC: " + this.PC);
        System.out.println("A: " + this.A);
        System.out.println("X: " + this.X);
    }

    /**
     * Executa uma instrução da memória.
     * 
     * @return
     */
    public Error run(){
        final AtomicInteger instruction = new AtomicInteger();
        final AtomicInteger aux = new AtomicInteger();

        this.memory.read(this.PC, instruction);


        /**
         * Verificar como melhorar isso aqui depois,
         * por hora switch gigante resolve.
         */
        switch(instruction.get()){
            
            //NOP
            case 0:
                //do nothing
                break;

            //PARE
            case 1:
                this.stop("Instrução de parada executada");
                
                break;

            //CARGI
            case 2:
                if(this.readA1() == mem.Error.OK){
                    this.A.set(this.A1.get());;
                }else {
                    this.stop("Erro durante acesso a memoria!");
                }

                break;

            //CARGM
            case 3:
                if(
                    this.readA1() != mem.Error.OK ||
                    this.memory.read(this.A1.get(), this.A) != mem.Error.OK
                ){
                    this.stop("Erro durante acesso a memoria!");
                }
                
                break;

            //CARGX
            case 4:
                if(
                    this.readA1() != mem.Error.OK ||
                    this.memory.read(this.A1.get() + this.X, this.A) != mem.Error.OK
                ){
                    this.stop("Erro durante acesso a memoria!");
                }
                
                break;

            //ARMM
            case 5:
                if(
                    this.readA1() != mem.Error.OK ||
                    this.memory.write(this.A1.get(), this.A.get()) != mem.Error.OK
                ){
                    this.stop("Erro durante acesso a memoria!");
                }

                break;

            //ARMX
            case 6:
                if(
                    this.readA1() != mem.Error.OK ||
                    this.memory.write(this.A1.get() + this.X, this.A.get()) != mem.Error.OK
                ){
                    this.stop("Erro durante acesso a memoria!");
                }

                break;

            //MXAX
            case 7:
                this.X = this.A.get();

                break;

            //MVXA
            case 8:
                this.A.set(this.X);

                break;

            //INCX
            case 9:
                this.X++;

                break;

            //SOMA
            case 10:
                if(
                    this.readA1() != mem.Error.OK ||
                    this.memory.read(this.A1.get(), aux) != mem.Error.OK
                ){
                    this.stop("Erro durante acesso a memoria!");
                } else {
                    this.A.set(this.A.get() + aux.get());
                } 

                break;

            //SUB
            case 11:
                if(
                    this.readA1() != mem.Error.OK ||
                    this.memory.read(this.A1.get(), aux) != mem.Error.OK
                ){
                    this.stop("Erro durante acesso a memoria!");
                } else {
                    this.A.set(this.A.get() - aux.get());
                } 

                break;

            //MULT
            case 12:
                if(
                    this.readA1() != mem.Error.OK ||
                    this.memory.read(this.A1.get(), aux) != mem.Error.OK
                ){
                    this.stop("Erro durante acesso a memoria!");
                } else {
                    this.A.set(this.A.get() * aux.get());
                } 

                break;

            //DIV
            case 13:
                if(
                    this.readA1() != mem.Error.OK ||
                    this.memory.read(this.A1.get(), aux) != mem.Error.OK
                ){
                    this.stop("Erro durante acesso a memoria!");
                } else {
                    this.A.set(this.A.get() / aux.get());
                } 

                break;

            //RESTO
            case 14:
                if(
                    this.readA1() != mem.Error.OK ||
                    this.memory.read(this.A1.get(), aux) != mem.Error.OK
                ){
                    this.stop("Erro durante acesso a memoria!");
                } else {
                    this.A.set(this.A.get() % aux.get());
                } 

                break;

            //NEG
            case 15:
                this.A.set(this.A.get() * -1);

                break;

            //DESV
            case 16:
                if(this.readA1() != mem.Error.OK){
                    this.stop("Erro durante acesso a memoria!");
                } else {
                    this.PC = this.A1.get();
                }

                break;

            //DESVZ
            case 17:
                if(0 == this.A.get()){
                    if(this.readA1() != mem.Error.OK){
                        this.stop("Erro durante acesso a memoria!");
                    } else {
                        this.PC = this.A1.get();
                    }
                }

                break;

            //DESVNZ
            case 18:
                if(0 != this.A.get()){
                    if(this.readA1() != mem.Error.OK){
                        this.stop("Erro durante acesso a memoria!");
                    } else {
                        this.PC = this.A1.get();
                    }
                }

                break;

            //LE
            case 19:
                //TODO

                break;

            //ESCR
            case 20:
                //TODO

                break;

            //Instrução desconhecida
            default:
                this.stop(Error.INVALID_INSTRUCTION.toString());
        }

        this.PC++;

        return Error.OK;
    }

    public void setDevicesController(DevicesController devicesController){
        this.devicesController = devicesController;
    }

    /**
     * Define o estado da CPU.
     * 
     * @param mode O estado da CPU.
     */
    public void setState(Integer mode, Integer PC, Integer A, Integer X){
        this.mode = mode;
        this.PC = PC;
        this.A.set(A);
        this.X = X;
    }

    public void setMemory(Memory memory){
        this.memory = memory;
    }

    private void stop(String stopInfo){
        this.mode = Error.STOPED.getValue();
        this.stopInfo = stopInfo;
    }

}
