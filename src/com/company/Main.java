package com.company;


import javax.swing.*;
import java.awt.*;


public class Main {

    public static void main(String[] args) {
        // write your code here



        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new CompAlgFrame();
                frame.setTitle("Computer Algebra");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                frame.setResizable(false);
            }
        });

    }
}