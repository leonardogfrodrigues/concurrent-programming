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
import java.util.Random;

import java.io.FileWriter;
import java.util.ArrayList;

//Classe que funcionará como uma "biblioteca" contendo funções personalizadas que serão utilizadas 
public class LGLibrary {

    //Método estático para preencher o vetor com valores aleatórios
    public static void povoar(int[] vetor, int valorMaximo, int seed) {
        ArrayList<Integer> numeros = new ArrayList<Integer>();
        
        Random gerador = new Random(seed);
        for (int i = 0; i < vetor.length; i++) {
            vetor[i] = gerador.nextInt(valorMaximo);
            numeros.add(vetor[i]);
        }
        
        
        //Put arrayList contents in file - need check rewrite policies (replace, new ones...)
        try {
            FileWriter writer = new FileWriter("vetor-semOrdenar.txt",true);
            
            writer.write ("\r\n" + "Vetor " + seed);
            for (Integer num : numeros) {
                //writer.write("\r\n" + numeros);
                writer.write("\r\n" + String.valueOf(num)+ " ");
            }
            writer.close();
        }
        catch(Exception e){
            System.out.println("Erro!");
        }
        
    }
    
    //Método estático para preencher o vetor com valores aleatórios
    public static void povoarConcorrente(int[] vetor, int valorMaximo, int seed) {
        ArrayList<Integer> numeros = new ArrayList<Integer>();
        
        Random gerador = new Random(seed);
        for (int i = 0; i < vetor.length; i++) {
            vetor[i] = gerador.nextInt(valorMaximo);
            numeros.add(vetor[i]);
        }
        
        
        //Put arrayList contents in file - need check rewrite policies (replace, new ones...)
        try {
            FileWriter writer = new FileWriter("vetor-semOrdenar-Concorrente.txt",true);
            
            writer.write ("\r\n" + "Vetor " + seed);
            for (Integer num : numeros) {
                //writer.write("\r\n" + numeros);
                writer.write("\r\n" + String.valueOf(num)+ " ");
            }
            writer.close();
        }
        catch(Exception e){
            System.out.println("Erro!");
        }
        
    }
    
    /*Metodo estático para ordenar o vetor utilizando Bubble Sort.
    * Bubble Sort consiste em comparar dados armazenados em um vetor de tamanho qualquer, 
    * comparando cada elemento de uma posição com o próximo elemento do vetor*/
    
    public static void ordenar(int[] vetor) {
        int vetorTamanho = vetor.length;
        int auxiliar;
        for (int i = 0; i < vetorTamanho; i++) {
            for (int j = 1; j < (vetorTamanho - i); j++) {
                if (vetor[j - 1] > vetor[j]) {
                    auxiliar = vetor[j - 1];
                    vetor[j - 1] = vetor[j];
                    vetor[j] = auxiliar;
                }
            }
        }
    }
}


