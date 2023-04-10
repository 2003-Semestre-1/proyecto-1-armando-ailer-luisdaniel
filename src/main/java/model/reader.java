package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/* @author Ailer Alvarado - Armando Arce - Daniel Rojas*/
public class reader {
    private static List<String>[] memoria = new ArrayList[256];
    enum Operation {
        LOAD,
        STORE,
        MOV,
        SUB,
        ADD,
        INC,
        DEC,
        SWAP,
        INT,
        CMP,
        PUSH,
        POP,
        JMP,
        JE,
        JNE,
        PARAM
    }

    enum Memory {
        AX,
        BX,
        CX,
        DX
    }
    enum Interruption {
        INT_20H("20H"),
        INT_10H("10H"),
        INT_09H("09H"),
        INT_21H("21H"),
        INT_3CH("3CH"),
        INT_3DH("3DH"),
        INT_4DH("4DH"),
        INT_40H("40H"),
        INT_41H("41H");

        private final String value;

        private Interruption(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
        /**
     * Busca el índice de la primera ocurrencia de una lista vacía en un arreglo 
     * de listas de cadenas, a partir del índice 'first_i'.
     * 
     * @param check_memory el arreglo de listas de cadenas en el que se va a buscar
     * @param first_i el índice a partir del cual se debe empezar a buscar
     * @return el índice de la primera ocurrencia de la lista vacía, 
     * o -1 si no se encuentra
     */
    public static int buscarIndice(List<String>[] check_memory, int first_i) {
        for (int i = first_i; i < check_memory.length; i++) {
            if (check_memory[i].isEmpty()){
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Busca el índice de un segmento vacío de tamaño 'n' en un arreglo de listas de cadenas.
     * El método comienza la búsqueda a partir del índice 10 y si encuentra un segmento vacío 
     * de tamaño 'n' lo retorna. Si no encuentra un segmento vacío de tamaño 'n', retorna -1.
     * 
     * @param check_memory el arreglo de listas de cadenas en el que se realiza la búsqueda
     * @param n el tamaño del segmento vacío que se busca
     * @return el índice del inicio del segmento vacío, o -1 si no se encuentra
     */
    public static int buscarSegmentoVacio(List<String>[] check_memory, int n) {
        int first_i = 10;
        while (first_i < check_memory.length) {
            int indice = buscarIndice(check_memory, first_i);
            if (indice == -1 || indice + n > check_memory.length) {
                break;
            }
            boolean segmentoVacio = true;
            for (int i = indice + 1; i < indice + n; i++) {
                if (!check_memory[i].isEmpty()) {
                    segmentoVacio = false;
                    break;
                }
            }
            if (segmentoVacio) {
                return indice;
            }
            first_i = indice + 1;
        }
        return -1;
    }

    /**
     * Busca el índice de un segmento vacío de tamaño 'n' en un arreglo de cadenas,
     * de forma aleatoria. El método comienza la búsqueda a partir del índice 10,
     * y si encuentra un segmento vacío lo retorna. Si no encuentra un segmento 
     * vacío de tamaño 'n', retorna -1.
     * 
     * @param check_memory el arreglo de cadenas en el que se realiza la búsqueda
     * @param n el tamaño del segmento vacío que se busca
     * @return el índice del inicio del segmento vacío, o -1 si no se encuentra
     */
    public static int buscarSegmentoVacioRandom(List<String>[] check_memory, int n, int first_i) {
        List<Integer> indicesSeleccionados = new ArrayList<>();
        Random rand = new Random();
        while (first_i < check_memory.length) {
            int indice = rand.nextInt(check_memory.length - n - 10) + 10;
            while (indicesSeleccionados.contains(indice)) {
                indice = rand.nextInt(check_memory.length - n);
            }
            indicesSeleccionados.add(indice);

            boolean segmentoVacio = true;
            for (int i = indice; i < indice + n; i++) {
                if (!check_memory[i].isEmpty()) {
                    segmentoVacio = false;
                    break;
                }
            }
            if (segmentoVacio) {
                return indice;
            }
            first_i = indice + 1;
            
            if (check_memory.length - n - n == indicesSeleccionados.size()) { // verifica si se ya no hay memoria disponible o un segmento posible
                return -1;
            }
        }
        return -1;
    }
    
    /**
     * Verifica si una cadena de caracteres representa un número entero.
     * @param dato_a_verificar La cadena a verificar.
     * @return true si la cadena representa un número entero, false de lo contrario.
     */
    public static boolean esEntero(String dato_a_verificar) {
        try {
            int numero = Integer.parseInt(dato_a_verificar);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Valida y analiza una línea de entrada.
     *
     * @param line la línea de entrada a validar y analizar
     * @return una lista de tokens válidos si la línea es válida, de lo contrario una lista vacía
     */
    public static List validarLinea(String line) {
        line = line.toUpperCase();
        String[] lista = line.split("\\s*,\\s*|\\s+");
        try {
            List<String> tokenList = new ArrayList<>();
            Operation op = Operation.valueOf(lista[0]);
            if ("INT".equals(lista[0])){
                Interruption intToken = Interruption.valueOf("INT_" + lista[1]);
                tokenList.add(0, op.toString());
                tokenList.add(1, lista[1]);
            } else if("PARAM".equals(lista[0])){
                if (lista.length <=4){
                    for (int i = 1; i < lista.length; i++){
                        int verificarEntero = Integer.valueOf(lista[i]);
                    }
                    tokenList.add(0, op.toString());
                    for (int i = 1; i < lista.length; i++){
                        tokenList.add(i, lista[i]);
                    }
                } else{
                    throw new RuntimeException("El número de parametros exceden el limite.");
                }
            }else if (Arrays.asList("JMP", "JE", "JNE").contains(lista[0])) {
                int numero = Integer.parseInt(lista[1]);
                tokenList.add(0, op.toString());
                tokenList.add(1, lista[1]);
            }  else if (lista.length > 1) {
                String tokenMemory = lista[1];
                Memory mem = Memory.valueOf(tokenMemory);
                tokenList.add(0, op.toString());
                tokenList.add(1, mem.toString());
                String value;
                switch (op) {
                    case CMP:
                        value = lista[2];
                        mem = Memory.valueOf(value);
                        tokenList.add(2, value);
                        break;
                    case MOV:
                        value = lista[2];
                        if (!esEntero(value)){
                            mem = Memory.valueOf(value);
                        };
                        tokenList.add(2, value);
                        break;
                    case SWAP:
                        mem = Memory.valueOf(lista[2]);
                        tokenList.add(2, lista[2]);
                        break;
                    case INC:
                        mem = Memory.valueOf(lista[1]);
                        break;
                    case DEC:
                        mem = Memory.valueOf(lista[1]);
                        break;
                }
            } else {
                tokenList.add(0, op.toString());
            }
            return tokenList;
        } catch (Exception ex) {
            //throw new RuntimeException(ex);
            System.out.println(ex);
        }
        return new ArrayList<>();
    }

    /**
     * Asigna un bloque de memoria consecutiva a partir de un índice aleatorio a un
     * conjunto de líneas. El número de líneas en el bloque está determinado por el
     * tamaño de la lista de líneas de entrada. Si no se puede encontrar un bloque 
     * de memoria lo suficientemente grande, se imprime un mensaje de error.
     * 
     * @param lineas la lista de líneas a asignar a la memoria
     */
    public static void asignarMemoria(List<List<String>> lineas){
        int n = lineas.size(); // buscar segmentos de n lineas vacías consecutivas
        int indice = buscarSegmentoVacioRandom(memoria, n, 0); // busca un indice posible de forma random a partir del indice 10
        if (indice == -1) {
            System.out.println("Memoria insuficiente.");
        } else {
            System.out.println("Memoria asginada.");
            int counter = 0;
            for (List<String> temp_linea : lineas) {
                memoria[indice + counter] = temp_linea;
                counter++;
            }
        }
    }
    
    /**
     * Lee un archivo de texto en la ruta especificada y 
     * almacena los tokens de las líneas válidas en la memoria.
     * 
     * @param rutaArchivo la ruta del archivo a leer
     * @param memory_ la memoria previamente cargada
     * @param limpear define si la memoria debe ser limpeada
     * @return la memoria con el nuevo estado
     */
    public static List<String>[] leerArchivo(String rutaArchivo, List<String>[] memory_, boolean limpear){
        boolean flag_error = false;
        String error_message = "";
        Integer numero_linea = 0;
        File archivo = new File(rutaArchivo); // Crea una instancia de File con la ruta del archivo
        List<List<String>> tokenList = new ArrayList<>(); // lista donde se almacenan temporalmente las lineas del *.asm
        if (limpear){
            memoria = memory_.clone();
            for (int i = 0; i < memoria.length; i++) { 
                memoria[i] = new ArrayList<>(); 
            } // Limpeamos la memoria
        } else{
            memoria = memory_.clone();
        }
        try { 
            Scanner scanner = new Scanner(archivo); // Crea una instancia de Scanner para leer el archivo
            while (scanner.hasNextLine()) { // Recorre el archivo línea por línea e imprime cada línea
                numero_linea += 1;
                String linea = scanner.nextLine();
                List<String> temp_tokenList = validarLinea(linea);
                if (!temp_tokenList.isEmpty()){
                    tokenList.add(temp_tokenList);
                } else{
                    flag_error = true;
                    error_message = String.format("Error en linea %d, \"%s\" no es una instrucción valida.", numero_linea, linea);
                    break;
                }
            }
            scanner.close(); // Cierra el scanner para liberar los recursos
        } catch (FileNotFoundException e) {
            error_message = "El archivo no existe o no se puede leer.";
            System.out.println(error_message);
        }
        if (!flag_error){
            asignarMemoria(tokenList);
        } else {
            //JOptionPane.showMessageDialog(null, error_message,"Error al leer el archivo", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(error_message);
        }  
        return memoria;
    }
    
    public static void main(String[] args) {}
}
