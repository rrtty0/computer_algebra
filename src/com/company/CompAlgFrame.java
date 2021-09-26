package com.company;

import javax.swing.*;
import java.awt.*;

public class CompAlgFrame extends JFrame
{
    private static final int DEFAULT_WIDTH = 820;
    private static final int DEFAULT_HEIGHT = 480;

    private JPanel naturalPanel;
    private JPanel integerPanel;
    private JPanel fractionPanel;
    private JPanel complexPanel;
    private JPanel polynomialPanel;

    public CompAlgFrame()
    {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        setLocation(screenSize.width/2 - DEFAULT_WIDTH/2, screenSize.height/2 - DEFAULT_HEIGHT*2/3);

        JPanel panel = new JPanel();
        JTabbedPane tabbedPane = new JTabbedPane();
        Font font = new Font("Arial", Font.PLAIN, 18);
        tabbedPane.setFont(font);
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

        //naturalPanel = new NaturalPanel();
        //integerPanel = new IntegerPanel();
        //fractionPanel = new FractionPanel();
        //complexPanel = new JPanel();
        //polynomialPanel = new JPanel();



        tabbedPane.addTab("Natural", new NaturalPanel());
        tabbedPane.addTab("Integer", new IntegerPanel());
        tabbedPane.addTab("Fraction", new FractionPanel());
        tabbedPane.addTab("Complex", new ComplexPanel());
        tabbedPane.addTab("Polynomial", new PolynomialPanel());



        panel.add(tabbedPane, BorderLayout.CENTER);
        add(panel);
        pack();
    }


}
