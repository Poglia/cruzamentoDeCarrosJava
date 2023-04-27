 import java.util.concurrent.Semaphore;

public class Cruzamento {
    // Semáforos para controle das faixas de tráfego
    private Semaphore semaforoNS;
    private Semaphore semaforoLO;
    // Semáforo para controle do acesso ao cruzamento
    private Semaphore semaforoCruzamento;
    // Contador de carros
    private int contadorCarros;
    // Flag para indicar a direção atual
    private String direcaoAtual;

    public Cruzamento() {
        // Inicializa os semáforos com 1 permissão
        semaforoNS = new Semaphore(1);
        semaforoLO = new Semaphore(1);
        semaforoCruzamento = new Semaphore(1);
        contadorCarros = 1;
    }

    public void direcaoNS() throws InterruptedException {
        // Entra no semáforo do cruzamento
        semaforoCruzamento.acquire();

        // Entra no semáforo da direção NS
        semaforoNS.acquire();
         // Verifica se o semáforo correspondente à outra direção está aberto
         if(direcaoAtual != "NS") {
            System.out.println("\nSemaforo Norte VERDE.");
            System.out.println("Semaforo Leste VERMELHO.\n");
        }
      
        System.out.println("Carro [" + contadorCarros + "] Está indo na direção NORTE => SUL");
        Thread.sleep(500);
        semaforoNS.release();


        // Libera o semáforo do cruzamento
        semaforoCruzamento.release();
        contadorCarros++;
        // Atualiza a direção atual para "LO"
        direcaoAtual = "NS";
    }


    public void direcaoLO() throws InterruptedException {
        // Entra no semáforo do cruzamento
        semaforoCruzamento.acquire();

        // Entra no semáforo da direção EO
        semaforoLO.acquire();
        if(direcaoAtual != "LO"){
           
            System.out.println("\nSemaforo Leste VERDE.");
            System.out.println("Semaforo Norte VERMELHO.\n");
        }
        
        System.out.println("Carro [" + contadorCarros + "] Está indo na direção LESTE => OESTE");
        Thread.sleep(500);
        semaforoLO.release();

        // Libera o semáforo do cruzamento
        semaforoCruzamento.release();
        contadorCarros++;
        direcaoAtual = "LO";
    }
    
     

    public static void main(String[] args) {
        // Cria um novo objeto Cruzamento
        Cruzamento cruzamento = new Cruzamento();

        // Cria e inicia as threads para as direções NS e LO
        Thread threadNS = new Thread(new Runnable() {
            public void run() {
                while(cruzamento.contadorCarros < 50) {
                    try {
                        cruzamento.direcaoNS();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread threadLO = new Thread(new Runnable() {
            public void run() {
                while(cruzamento.contadorCarros < 50) {
                    try {
                        cruzamento.direcaoLO();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        threadNS.start();
        threadLO.start();
    }
}
