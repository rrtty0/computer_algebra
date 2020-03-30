package com.company;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class IntegerPanel extends JPanel
{
    //размеры JTextArea
    public static final int TEXT_ROWS = 7;
    public static final int TEXT_COLUMNS = 20;

    //дефолтный цвет кнопки(то есть цвет кнопки, если она сейчас не нажата)
    private Color defaultButtonBackgroundColor;

    //дефолтная рамка у текстовых полей ввода
    Border defaultBorder;

    //рамка текстовых полей ввода: "ошибка ввода"
    Border errorBorder;

    //Метки возле текстовых полей ввода
    private JLabel labelNum1;
    private JLabel labelNum2;
    private JLabel labelNaturalNum;

    //текстовые поля ввода
    private JTextField num1;
    private JTextField num2;
    private JTextField naturalNum;

    //индикатор вывода подсказки ввода и сама подсказка ввода для полей num1, num2 и intNum
    boolean num1HintIsVisible;
    final String num1HintString = "Example: 123456789";
    boolean num2HintIsVisible;
    final String num2HintString = "Example: -645738291";
    boolean naturalNumHintIsVisible;
    final String naturalNumHintString = "Example: 909090";

    //индикатор корректного ввода(здесь пустая строка тоже корректно!!!)
    boolean num1CorrectInput;
    boolean num2CorrectInput;
    boolean naturalNumCorrectInput;

    //кнопки для выбора действия
    private JButton butABS_Z_N;
    private JButton butPOZ_Z_D;
    private JButton butMUL_ZM_Z;
    private JButton butTRANS_N_Z;
    private JButton butTRANS_Z_N;
    private JButton butADD_ZZ_Z;
    private JButton butSUB_ZZ_Z;
    private JButton butMUL_ZZ_Z;
    private JButton butDIV_ZZ_Z;
    private JButton butMOD_ZZ_Z;


    //кнопка, которая была последней нажата
    private JButton lastPressedButton;

    //метка возле поля вывода результата(JTextArea)
    private JLabel labelResult;

    //поле вывода результата
    private JTextArea resultArea;

    //м-во, хранящее название операций, в которых участвует поле num2
    private Set<String> withNum2 = new HashSet<>();




    public IntegerPanel()
    {
        //установка диспетчера компоновки
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);

        //ввод элеменов в множество
        withNum2.add("ADD_ZZ_Z");
        withNum2.add("SUB_ZZ_Z");
        withNum2.add("MUL_ZZ_Z");
        withNum2.add("DIV_ZZ_Z");
        withNum2.add("MOD_ZZ_Z");

        //дефолтный шрифт для меток
        Font fontForLabel = new Font("Arial", Font.BOLD, 15);

        //создание меток и установка им дефолтного шрифта
        labelNum1 = new JLabel("Первое целое число:");
        labelNum1.setFont(fontForLabel);
        labelNum2 = new JLabel("Второе целое число:");
        labelNum2.setFont(fontForLabel);
        labelNaturalNum = new JLabel("Натуральное число:");
        labelNaturalNum.setFont(fontForLabel);

        //дефолтный шрифт для текстовых полей
        Font fontforTextField = new Font("Arial", Font.PLAIN, 16);

        //создание и установка свойств полю num1
        num1 = new JTextField();
        num1.setPreferredSize(new Dimension(220, 30));
        num1.setFont(fontforTextField);
        num1.addFocusListener(new Num1FocusListener());
        num1.setText("" + num1HintString);
        num1.setForeground(Color.GRAY);
        num1HintIsVisible = true;
        num1.getDocument().addDocumentListener(new MyDocumentListener());

        //определение дефолтного цвета фона кнопки
        defaultButtonBackgroundColor = num1.getBackground();
        //определение дефолтной рамки для текстовых полей ввода
        defaultBorder = num1.getBorder();

        //создание и установка свойств полю num2
        num2 = new JTextField();
        num2.setPreferredSize(new Dimension(220, 30));
        num2.setFont(fontforTextField);
        num2.addFocusListener(new Num2FocusListener());
        num2.setText("" + num2HintString);
        num2.setForeground(Color.GRAY);
        num2HintIsVisible = true;
        num2.getDocument().addDocumentListener(new MyDocumentListener());

        //создание и установка свойств полю intNum
        naturalNum = new JTextField();
        naturalNum.setPreferredSize(new Dimension(220, 30));
        naturalNum.setFont(fontforTextField);
        naturalNum.addFocusListener(new IntNumFocusListener());
        naturalNum.setText("" + naturalNumHintString);
        naturalNum.setForeground(Color.GRAY);
        naturalNumHintIsVisible = true;
        naturalNum.getDocument().addDocumentListener(new MyDocumentListener());

        //определение рамки текстовых полей ввода вида: "ошибка ввода"
        errorBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.RED);

        //инициализация флагов корректного ввода
        num1CorrectInput = true;
        num2CorrectInput = true;
        naturalNumCorrectInput = true;

        //создание кнопок
        butABS_Z_N = new JButton(new ButtonAction("ABS_Z_N"));
        butABS_Z_N.setPreferredSize(new Dimension(115, 27));

        butPOZ_Z_D = new JButton(new ButtonAction("POZ_Z_D"));
        butPOZ_Z_D.setPreferredSize(new Dimension(115, 27));

        butMUL_ZM_Z = new JButton(new ButtonAction("MUL_ZM_Z"));
        butMUL_ZM_Z .setPreferredSize(new Dimension(115, 27));

        butTRANS_N_Z = new JButton(new ButtonAction("TRANS_N_Z"));
        butTRANS_N_Z.setPreferredSize(new Dimension(115, 27));

        butTRANS_Z_N = new JButton(new ButtonAction("TRANS_Z_N"));
        butTRANS_Z_N .setPreferredSize(new Dimension(115, 27));

        butADD_ZZ_Z = new JButton(new ButtonAction("ADD_ZZ_Z"));
        butADD_ZZ_Z.setPreferredSize(new Dimension(115, 27));

        butSUB_ZZ_Z = new JButton(new ButtonAction("SUB_ZZ_Z"));
        butSUB_ZZ_Z.setPreferredSize(new Dimension(115, 27));

        butMUL_ZZ_Z = new JButton(new ButtonAction("MUL_ZZ_Z"));
        butMUL_ZZ_Z.setPreferredSize(new Dimension(115, 27));

        butDIV_ZZ_Z = new JButton(new ButtonAction("DIV_ZZ_Z"));
        butDIV_ZZ_Z.setPreferredSize(new Dimension(115, 27));

        butMOD_ZZ_Z = new JButton(new ButtonAction("MOD_ZZ_Z"));
        butMOD_ZZ_Z.setPreferredSize(new Dimension(115, 27));

        //создание метки возле поля "результат" и установка ей дефолтного шрифта
        labelResult = new JLabel("Результат:");
        labelResult.setFont(fontForLabel);

        //создание поля вывода результата, установка шрифта, установка запрета редактирования поля вручную
        resultArea = new JTextArea(TEXT_ROWS, TEXT_COLUMNS);
        resultArea.setFont(fontforTextField);
        resultArea.setEditable(false);

        //вывод компонентов на экран
        add(labelNum1, new GBC(0,0).setWeight(100, 0).setInsets(30, 0, 0, 45));
        add(num1, new GBC(0,1).setWeight(100, 0).setInsets(0, 17, 15, 45));
        add(labelNum2, new GBC(0,2).setWeight(100, 0).setInsets(15, 0, 0, 45));
        add(num2, new GBC(0,3).setWeight(100, 0).setInsets(0, 17, 15, 45));
        add(labelNaturalNum, new GBC(0,4).setWeight(100, 0).setInsets(15, 0, 0, 45));
        add(naturalNum, new GBC(0,5).setWeight(100, 0).setInsets(0, 17, 15, 45));
        add(butABS_Z_N, new GBC(1, 0).setWeight(100, 0).setInsets(30, 4, 7, 4));
        add(butPOZ_Z_D, new GBC(2, 0).setWeight(100, 0).setInsets(30, 4, 7, 4));
        add(butMUL_ZM_Z, new GBC(3, 0).setWeight(100, 0).setInsets(30, 4, 7, 4));
        add(butTRANS_N_Z, new GBC(4, 0).setWeight(100, 0).setInsets(30, 4, 7, 4));
        add(butTRANS_Z_N, new GBC(1, 1).setWeight(100, 0).setInsets(7, 4, 7, 4));
        add(butADD_ZZ_Z, new GBC(2, 1).setWeight(100, 0).setInsets(7, 4, 7, 4));
        add(butSUB_ZZ_Z, new GBC(3, 1).setWeight(100, 0).setInsets(7, 4, 7, 4));
        add(butMUL_ZZ_Z, new GBC(4, 1).setWeight(100, 0).setInsets(7, 4, 7, 4));
        add(butDIV_ZZ_Z, new GBC(1, 2).setWeight(100, 0).setInsets(7, 4, 7, 4));
        add(butMOD_ZZ_Z, new GBC(2, 2).setWeight(100, 0).setInsets(7, 4, 7, 4));
        add(labelResult, new GBC(0, 6).setWeight(100, 0).setInsets(15, 4, 4, 4).setFill(GBC.EAST));
        add(new JScrollPane(resultArea), new GBC(0, 7, 5, 1).setWeight(100, 0).setFill(GBC.BOTH));
    }

    //слушатель фокуса ввода текстовых полей
    private class Num1FocusListener implements FocusListener
    {
        @Override
        public void focusGained(FocusEvent e)
        {
            if(num1HintIsVisible)
            {
                num1HintIsVisible = false;
                num1.setText("");
                num1.setForeground(Color.BLACK);
            }
            else
                num1HintIsVisible = false;
        }

        @Override
        public void focusLost(FocusEvent e)
        {
            if(num1.getText().isEmpty())
            {
                num1HintIsVisible = true;
                num1.setForeground(Color.GRAY);
                num1.setText("" + num1HintString);
            }
        }
    }

    private class Num2FocusListener implements FocusListener
    {
        @Override
        public void focusGained(FocusEvent e)
        {
            if(num2HintIsVisible)
            {
                num2HintIsVisible = false;
                num2.setText("");
                num2.setForeground(Color.BLACK);
            }
            else
                num2HintIsVisible = false;
        }

        @Override
        public void focusLost(FocusEvent e)
        {
            if(num2.getText().isEmpty())
            {
                num2HintIsVisible = true;
                num2.setForeground(Color.GRAY);
                num2.setText("" + num2HintString);
            }
        }
    }

    private class IntNumFocusListener implements FocusListener
    {
        @Override
        public void focusGained(FocusEvent e)
        {
            if(naturalNumHintIsVisible)
            {
                naturalNumHintIsVisible = false;
                naturalNum.setText("");
                naturalNum.setForeground(Color.BLACK);
            }
            else
                naturalNumHintIsVisible = false;
        }

        @Override
        public void focusLost(FocusEvent e)
        {
            if(naturalNum.getText().isEmpty())
            {
                naturalNumHintIsVisible = true;
                naturalNum.setForeground(Color.GRAY);
                naturalNum.setText("" + naturalNumHintString);
            }
        }
    }

    //слушатель изменения текста в текстовом поле
    private class MyDocumentListener implements DocumentListener
    {
        @Override
        public void insertUpdate(DocumentEvent e)
        {
            checkTextFields();
            getResult();
        }

        @Override
        public void removeUpdate(DocumentEvent e)
        {
            checkTextFields();
            getResult();
        }

        @Override
        public void changedUpdate(DocumentEvent e)
        {
            checkTextFields();
            getResult();
        }
    }

    private class ButtonAction extends AbstractAction
    {

        //установка короткой подсказки к каждой кнопке
        public ButtonAction(String name)
        {
            putValue(Action.NAME, name);
            switch (name)
            {
                case "ABS_Z_N":
                    putValue(Action.SHORT_DESCRIPTION, "МОДУЛЬ ЦЕЛОГО ЧИСЛА, РЕЗУЛЬТАТ - НАТУРАЛЬНОЕ");
                    break;
                case "POZ_Z_D":
                    putValue(Action.SHORT_DESCRIPTION, "ПОЛУЧЕНИЕ ЗНАКА ЧИСЛА: 0 - ЧИСЛО РАВНО НУЛЮ, 1 - ПОЛОЖИТЕЛЬНОЕ, 2 - ОТРИЦАТЕЛЬНОЕ");
                    break;
                case "MUL_ZM_Z":
                    putValue(Action.SHORT_DESCRIPTION, "СМЕНА ЗНАКА ЦЕЛОГО ЧИСЛА");
                    break;
                case "TRANS_N_Z":
                    putValue(Action.SHORT_DESCRIPTION, "КОНВЕРТАЦИЯ НАТУРАЛЬНОГО ЧИСЛА В ЦЕЛОЕ");
                    break;
                case "TRANS_Z_N":
                    putValue(Action.SHORT_DESCRIPTION, "КОНВЕРТАЦИЯ ЦЕЛОГО ЧИСЛА В НАТУРАЛЬНОЕ");
                    break;
                case "ADD_ZZ_Z":
                    putValue(Action.SHORT_DESCRIPTION, "СЛОЖЕНИЕ ДВУХ ЦЕЛЫХ ЧИСЕЛ");
                    break;
                case "SUB_ZZ_Z":
                    putValue(Action.SHORT_DESCRIPTION, "РАЗНОСТЬ ДВУХ ЦЕЛЫХ ЧИСЕЛ");
                    break;
                case "MUL_ZZ_Z":
                    putValue(Action.SHORT_DESCRIPTION, "ПРОИЗВЕДЕНИЕ ДВУХ ЦЕЛЫХ ЧИСЕЛ");
                    break;
                case "DIV_ZZ_Z":
                    putValue(Action.SHORT_DESCRIPTION, "ЧАСТНОЕ ОТ ДЕЛЕНИЯ ДВУХ ЦЕЛЫХ ЧИСЕЛ");
                    break;
                case "MOD_ZZ_Z":
                    putValue(Action.SHORT_DESCRIPTION, "ОСТАТОК ОТ ДЕЛЕНИЯ ДВУХ ЦЕЛЫХ ЧИСЕЛ");
                    break;
            }
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(lastPressedButton != null)
            {
                lastPressedButton.setBackground(defaultButtonBackgroundColor);
                num1.setEnabled(true);
                num2.setEnabled(true);
                naturalNum.setEnabled(true);
            }
            if(lastPressedButton != e.getSource())
            {
                lastPressedButton = (JButton) e.getSource();
                lastPressedButton.setBackground(Color.CYAN);
                String command = lastPressedButton.getActionCommand();
                if(withNum2.contains(command))
                    naturalNum.setEnabled(false);
                else
                {
                    num2.setEnabled(false);
                    if(command.equals("TRANS_N_Z"))
                        num1.setEnabled(false);
                    else
                        naturalNum.setEnabled(false);
                }

            }
            else
                lastPressedButton = null;
            getResult();
        }
    }

    //получение и вывод результата
    private void getResult()
    {
        if (lastPressedButton != null)
        {
            String command = lastPressedButton.getActionCommand();
            switch (command)
            {
                case "ABS_Z_N":
                    if (!num1HintIsVisible && num1CorrectInput && !num1.getText().isEmpty())
                        resultArea.setText("" + Integer.ABS_Z_N(Integer.parseInteger(num1.getText())));
                    else
                        resultArea.setText("");
                    break;

                case "POZ_Z_D":
                    if(!num1HintIsVisible && num1CorrectInput && !num1.getText().isEmpty())
                        resultArea.setText("" + Integer.POZ_Z_D(Integer.parseInteger(num1.getText())));
                    else
                        resultArea.setText("");
                    break;

                case "MUL_ZM_Z":
                    if(!num1HintIsVisible && num1CorrectInput && !num1.getText().isEmpty())
                        resultArea.setText("" + Integer.MUL_ZM_Z(Integer.parseInteger(num1.getText())));
                    else
                        resultArea.setText("");
                    break;

                case "TRANS_N_Z":
                    if(!naturalNumHintIsVisible && naturalNumCorrectInput && !naturalNum.getText().isEmpty())
                        resultArea.setText("" + Integer.TRANS_N_Z(Natural.parseNatural(naturalNum.getText())));
                    else
                        resultArea.setText("");
                    break;

                case "TRANS_Z_N":
                    if(!num1HintIsVisible && num1CorrectInput && !num1.getText().isEmpty())
                        resultArea.setText("" + Integer.TRANS_Z_N(Integer.parseInteger(num1.getText())));
                    else
                        resultArea.setText("");
                    break;

                case "ADD_ZZ_Z":
                    if(!num1HintIsVisible && !num2HintIsVisible
                            && num1CorrectInput && num2CorrectInput
                            && !num1.getText().isEmpty() && !num2.getText().isEmpty())
                        resultArea.setText("" + Integer.ADD_ZZ_Z(Integer.parseInteger(num1.getText()), Integer.parseInteger(num2.getText())));
                    else
                        resultArea.setText("");
                    break;

                case "SUB_ZZ_Z":
                    if(!num1HintIsVisible && !num2HintIsVisible
                            && num1CorrectInput && num2CorrectInput
                            && !num1.getText().isEmpty() && !num2.getText().isEmpty())
                        resultArea.setText("" + Integer.SUB_ZZ_Z(Integer.parseInteger(num1.getText()), Integer.parseInteger(num2.getText())));
                    else
                        resultArea.setText("");
                    break;

                case "MUL_ZZ_Z":
                    if(!num1HintIsVisible && !num2HintIsVisible
                            && num1CorrectInput && num2CorrectInput
                            && !num1.getText().isEmpty() && !num2.getText().isEmpty())
                        resultArea.setText("" + Integer.MUL_ZZ_Z(Integer.parseInteger(num1.getText()), Integer.parseInteger(num2.getText())));
                    else
                        resultArea.setText("");
                    break;

                case "DIV_ZZ_Z":
                    if(!num1HintIsVisible && !num2HintIsVisible
                            && num1CorrectInput && num2CorrectInput
                            && !num1.getText().isEmpty() && !num2.getText().isEmpty())
                    {
                        Integer result = Integer.DIV_ZZ_Z(Integer.parseInteger(num1.getText()), Integer.parseInteger(num2.getText()));
                        if(result != null)
                            resultArea.setText("" + result);
                        else
                            resultArea.setText("Делитель должен быть отличен от нуля!");
                    }
                    else
                        resultArea.setText("");
                    break;

                case "MOD_ZZ_Z":
                    if(!num1HintIsVisible && !num2HintIsVisible
                            && num1CorrectInput && num2CorrectInput
                            && !num1.getText().isEmpty() && !num2.getText().isEmpty())
                    {
                        Integer result = Integer.MOD_ZZ_Z(Integer.parseInteger(num1.getText()), Integer.parseInteger(num2.getText()));
                        if(result != null)
                            resultArea.setText("" + result);
                        else
                            resultArea.setText("Делитель должен быть отличен от нуля!");
                    }
                    else
                        resultArea.setText("");
                    break;
            }
        }
        else
            resultArea.setText("");
    }

    //проверка корректности ввода в текстовых полях
    private void checkTextFields()
    {
        Pattern p1 = Pattern.compile("^((-)?\\d+)?$");
        Matcher m;

        if(!num1HintIsVisible)
        {
            m = p1.matcher(num1.getText());
            if(m.matches())
            {
                if(!num1CorrectInput)
                {
                    num1.setBorder(defaultBorder);
                    num1CorrectInput = true;
                }
            }
            else
                if(num1CorrectInput)
                {
                    num1.setBorder(errorBorder);
                    num1CorrectInput = false;
                }
        }

        if(!num2HintIsVisible)
        {
            m = p1.matcher(num2.getText());
            if(m.matches())
            {
                if(!num2CorrectInput)
                {
                    num2.setBorder(defaultBorder);
                    num2CorrectInput = true;
                }
            }
            else
                if(num2CorrectInput)
                {
                    num2.setBorder(errorBorder);
                    num2CorrectInput = false;
                }
        }

        if(!naturalNumHintIsVisible)
        {
            m = p1.matcher(naturalNum.getText());
            if(m.matches())
            {
                if(!naturalNumCorrectInput)
                {
                    naturalNum.setBorder(defaultBorder);
                    naturalNumCorrectInput = true;
                }
            }
            else
                if(naturalNumCorrectInput)
                {
                    naturalNum.setBorder(errorBorder);
                    naturalNumCorrectInput = false;
                }
        }
    }
}

