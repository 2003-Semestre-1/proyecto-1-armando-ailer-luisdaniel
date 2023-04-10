/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**  @author Ailer */
public class terminal extends JPanel  {
    
    private JTextField inputField;
    private JTextArea outputArea;
    private String lastInput;
    private boolean esperando = false;
    public terminal() {
        setLayout(new BorderLayout());
        
        setBorder(javax.swing.BorderFactory.createTitledBorder("Terminal"));
        inputField = new JTextField();
        inputField.setPreferredSize(new Dimension(800, 30));
        setColorInp(Color.BLACK, Color.WHITE);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFocusable(false);
        outputArea.append("> ");
        setColorOut(Color.BLACK, Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(outputArea);

        add(inputField, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.CENTER);

    }

    public void setEsperando(boolean b) {
        esperando = b;
    }

    public JTextField getInputField() {
        return inputField;
    }

    public void setLastInput(String lastInput) {
        this.lastInput = lastInput;
    }

    public JTextArea getOutputArea() {
        return outputArea;
    }
    
    public void setColorOut(Color backColor, Color foreColor){
        outputArea.setBackground(backColor);
        outputArea.setForeground(foreColor);
    }
    
    public void setColorInp(Color backColor, Color foreColor){
        inputField.setBackground(backColor);
        inputField.setForeground(foreColor);
    }
    
    public String getInput(){
        return lastInput;
    }
    
    public void setInput(String nuevo_string){
        lastInput = nuevo_string;
    }
    
    public void addToOuput(String newTetx){
        if (esperando){
            outputArea.append(newTetx);
        } else {
            outputArea.append(newTetx + "\n> ");
        }
    }
    public void cleanTerminal(){
        outputArea.setText("> ");
        lastInput = "";
    }
    
    public void activarField(){
        esperando = true;
        outputArea.setEnabled(true);
        inputField.setEnabled(true);
    }
}

