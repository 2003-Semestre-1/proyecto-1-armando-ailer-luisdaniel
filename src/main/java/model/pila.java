/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/* @author Ailer Alvarado - Armando Arce - Daniel Rojas*/
public class pila {

    private int[] elementos = new int[10];
    private int top = -1;
    private int max = 10; 

    public pila() {
    }

    public pila(int max){
        this.max = max;
        elementos = new int[max];
    }
    public void push(int elemento) {
        if (top == max-1) {
            throw new RuntimeException("Error: Pila llena");
        }
        top++;
        elementos[top] = elemento;
    }

    public int pop() {
        if (top == -1) {
            System.out.println("Pila vacía");
            return -1;
        }
        int elemento = elementos[top];
        top--;
        return elemento;
    }
    
    public void imprimirValores() {
        if (top == -1) {
            System.out.println("Pila vacía");
            return;
        }
        System.out.print("Estado pila:   ");
        for (int i = top; i >= 0; i--) {
            System.out.print(elementos[i] + " ");
        }
        System.out.println("");
    }
}

