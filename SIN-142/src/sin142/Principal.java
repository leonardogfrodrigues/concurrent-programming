/**
 * Universidade Federal de Viçosa campus Rio Paranaíba 
 * SIN 142 - Programação Concorrente Distribuída
 * Trabalho Prático
 * Prof. João Batista
 *
 * @author Leonardo Gabriel F. Rodrigues
 */

// Pacote utilizado
package sin142;

// Importação das bibliotecas necessárias
import java.util.Arrays;
import java.util.concurrent.Semaphore;



//Classe principal
public class Principal {

    //Constantes necessárias
    
    final static int TAMANHOVETOR = 1000000;    //Número de posições do vetor
    final static int VALORMAXIMO = 1000000;     //Valores que serão preenchidos aleatoriamente
    final static int QUANTIDADEITERACAO = 6;//Quantidade de Iterações


    /* Método estático para o Ordenamento Sequencial
     As iterações são determinadas de acordo com o valor colocado na constante QUANTIDADEITERACAO 
     */
    private static void metodoSequencial(){
        long somatorio = 0;
        double media;

        System.out.println("Ordenamento Sequencial");
        for (int i = 1; i <= QUANTIDADEITERACAO; i++) {
            int[] vetor = new int[TAMANHOVETOR];
            LGLibrary.povoar(vetor, VALORMAXIMO, i);

            long tempoInicial = System.currentTimeMillis();
            HeapSort vet = new HeapSort();
            vet.ordenar(vetor);
            
            //vet.salvarVetOrdenado(vetor, i);
            long tempoFinal = System.currentTimeMillis();

            System.out.println(vetor.length + " elementos ordenados em " + (tempoFinal - tempoInicial) / 1000.000 + " segundos.");
            somatorio += (tempoFinal - tempoInicial);
        }
        media = (somatorio / QUANTIDADEITERACAO);
        
        System.out.println("TEMPO MÉDIO: " + media / 1000 + " segundos.\n");
        
    }

    
    /* Método estático para o Ordenamento Concorrente
     * As threads são instanciadas e estas disputarão o processados para realizar
     * o ordenamento de modo sincronizado.
     * O método start() requisita os recursos do sistema necessários para rodar o thread e chama o seu método run(). 
     * O método join() faz a thread que chamá-lo aguardar a outra thread terminar.
     */
    private static void ordenaConcorrentemente(int[] vetor) {
        Semaphore semaforo = new Semaphore(1);

        Thread linhaA = new Thread(new ThreadOrdenaVetor(vetor, semaforo));
        Thread linhaB = new Thread(new ThreadOrdenaVetor(vetor, semaforo));
        Thread linhaC = new Thread(new ThreadOrdenaVetor(vetor, semaforo));

        linhaA.start();
        linhaB.start();
        linhaC.start();

        try {
            linhaA.join();
            linhaB.join();
            linhaC.join();
        } catch (InterruptedException ie) {
        }
    }

    /* Método estático para o Ordenamento Concorrente
     * As iterações são determinadas de acordo com o valor colocado na constante QUANTIDADEITERACAO
     * As threads são iniciadas e o semáforo determina quem acessa a região crítica
     */
    private static void metodoConcorrente() {
        long somatorio = 0;
        double media;

        System.out.println("Ordenamento Concorrente");
        for (int i = 1; i <= QUANTIDADEITERACAO; i++) {
            int[] vetor = new int[TAMANHOVETOR];
            LGLibrary.povoarConcorrente(vetor, VALORMAXIMO, i);

            long tempoInicial = System.currentTimeMillis();
            ordenaConcorrentemente(vetor);
            long tempoFinal = System.currentTimeMillis();

            somatorio += (tempoFinal - tempoInicial);           
        }
        media = (somatorio / QUANTIDADEITERACAO);
        System.out.println("TEMPO MÉDIO: " + media / 1000 + " segundos.\n");
    }

    /* Método estático para o Ordenamento Paralelo
     * As threads são instanciadas e estas disputarão o processador para realizar o ordenamento de modo sincronizado.
     * Nota-se que o vetor é dividido ao meio e 2 threads responsabilizam-se por realizar cada uma metade da ordenação.
     * Por fim, uma terceira thread responsabiliza-se por fazer o ordenamento final.
     * O método start() requisita os recursos do sistema necessários para rodar a thread e chama o seu método run()
     * onde está definido o que a thread deverá executar.  
     * O método join() faz a thread que chamá-lo aguardar a outra thread terminar.
     */
    private static void ordenaParalelamente(int[] vetor) {
        Semaphore semaforo = new Semaphore(1);

        int[] esquerda = Arrays.copyOfRange(vetor, 0, vetor.length / 2);
        int[] direita = Arrays.copyOfRange(vetor, vetor.length / 2, vetor.length);

        Thread linhaA = new Thread(new ThreadOrdenaVetor(esquerda, semaforo));
        Thread linhaB = new Thread(new ThreadOrdenaVetor(direita, semaforo));
        linhaA.start();
        linhaB.start();

        try {
            linhaA.join();
            linhaB.join();
        } catch (InterruptedException ie) {
        }

        System.arraycopy(esquerda, 0, vetor, 0, esquerda.length);
        System.arraycopy(direita, 0, vetor, esquerda.length, direita.length);

        Thread linhaC = new Thread(new ThreadOrdenaVetor(vetor, semaforo));
        linhaC.start();

        try {
            linhaC.join();
        } catch (InterruptedException ie) {
        }
    }

    /* Método estático para o Ordenamento Paralelo
     * As iterações são determinadas de acordo com o valor colocado na constante QUANTIDADEITERACAO
     * As threads são iniciadas e o semáforo determina quem acessa a região crítica
     */
    private static void metodoParalelo() {
        long somatorio = 0;
        double media;

        System.out.println("Ordenamento Paralelo");
        for (int i = 1; i <= QUANTIDADEITERACAO; i++) {
            int[] vetor = new int[TAMANHOVETOR];
            LGLibrary.povoar(vetor, VALORMAXIMO, i);

            long tempoInicial = System.currentTimeMillis();
            ordenaParalelamente(vetor);
            long tempoFinal = System.currentTimeMillis();

            somatorio += (tempoFinal - tempoInicial);
        }
        media = (somatorio / QUANTIDADEITERACAO);
        System.out.println("TEMPO MÉDIO: " + media / 1000 + " segundos.\n");
    }

    //Método Principal
    public static void main(String[] args) {
        //metodoSequencial();
        //metodoConcorrente();
        metodoParalelo();
    }
}
