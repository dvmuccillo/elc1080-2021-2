import runner.CPU;
import io.DevicesController;
import mem.Error;
import mem.Memory;

public class App {

    /**
     * O tamanho da memória.
     */
    private static Integer memorySize = 20;

    public static void main(String[] args) throws Exception {
        
        /**
         * Programa para executar na nossa CPU.
         */
        final Integer[] program = { 
            2, 0, 7, 2, 10, 5, 17,    //  0      x=0; l=10
            8, 20, 1,                 //  7 ali: print x
            9, 8, 11, 17, 18, 7,      // 10      if ++x != l goto ali
            1,                        // 16      FIM
            0                         // 17 aqui tá o "l"
        };

        /**
         * Variáveis que representam o computador.
         */

        final Memory memory = new Memory(memorySize);
        final DevicesController devicesController = new DevicesController();
        final CPU cpu = new CPU();

        /**
         * Copia o programa para a memória.
         */
        for(int i = 0; i < program.length; i++){
            if(memory.write(i, program[i]) != Error.OK){
                System.out.println("Erro de memoria, endereco " + i);
                System.exit(1);
            }
        }

        /**
         * Para debug dos valores em memória.
         */
        //memory.debug();

        /**
         * Inicializa a CPU com as variáveis criadas.
         */
        cpu.setDevicesController(devicesController);
        cpu.setMemory(memory);
        cpu.setState(0,0,0,0);

        /**
         * Executa uma instrução por vez até parar
         */
        while(true){
            cpu.printState();

            runner.Error error = cpu.run();

            if(runner.Error.OK != error){
                System.out.println("Erro na execução: " + error);
                cpu.printState();
                break;
            }
        }
    }
}
