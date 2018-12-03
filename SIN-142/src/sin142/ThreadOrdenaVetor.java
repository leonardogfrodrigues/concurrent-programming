/**
 * Universidade Federal de Viçosa campus Rio Paranaíba 
 * SIN 142 - Programação Concorrente Distribuída
 * Trabalho Prático
 * Prof. João Batista
 *
 * @author Leonardo Gabriel F. Rodrigues
 */

//Pacote utilizado
package sin142;

//Importação das bibliotecas necessárias
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;


//Classe ThreadOrdenaVetor é utilizada pelas threads para a realização da ordenação de maneira sincronizada 
public class ThreadOrdenaVetor implements Runnable {

    //Vetor de Inteiros
    public int[] vetor;
    //Semáforo
    private Semaphore semaforo;

    //Construtor Completo
    public ThreadOrdenaVetor(int[] vetor, Semaphore semaforo) {
        this.vetor = vetor;
        this.semaforo = semaforo;
    }

    //Construtor Padrão
    public ThreadOrdenaVetor() {
    }

    /* Método da thread para fazer o ordenamento de maneira sincronizada, ou seja,
     através do semáforo determina-se qual thread poderá acessar a seção crítica */
    @Override
    public void run() {
        //Variável do tipo long que armazena o tempo inicial
        long tempoInicial = System.currentTimeMillis();

        try {
            //Método para se requisitar o acesso ao semáforo
            semaforo.acquire();
            HeapSort vet = new HeapSort();
            vet.ordenar(vetor);
            
            for (int i = 1; i <= 6; i++) { 
                vet.salvarVetOrdenadoConcorrente(vetor, i);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(ThreadOrdenaVetor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //Método para liberar o recurso do semáforo
            semaforo.release();
        }
 
        //Variável do tipo long que armazena o tempo final
        //Por fim exibe-se o tempo de ordenação dos elementos do vetor em segundos
        long tempoFinal = System.currentTimeMillis();
        System.out.println(vetor.length + " elementos ordenados em " + (tempoFinal - tempoInicial)/1000.000 + " segundos.");
    }
}
