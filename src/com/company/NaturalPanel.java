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


public class NaturalPanel extends JPanel
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
    private JLabel labelIntNum;

    //текстовые поля ввода
    private JTextField num1;
    private JTextField num2;
    private JTextField intNum;

    //индикатор вывода подсказки ввода и сама подсказка ввода для полей num1, num2 и intNum
    boolean num1HintIsVisible;
    final String num1HintString = "Example: 3906";
    boolean num2HintIsVisible;
    final String num2HintString = "Example: 89034077";
    boolean intNumHintIsVisible;
    final String intNumHintString = "Example: 2";

    //индикатор корректного ввода(здесь пустая строка тоже корректно!!!)
    boolean num1CorrectInput;
    boolean num2CorrectInput;
    boolean intNumCorrectInput;

    //кнопки для выбора действия
    private JButton butCOM_NN_D;
    private JButton butNZER_N_B;
    private JButton butADD_1N_N;
    private JButton butADD_NN_N;
    private JButton butSUB_NN_N;
    private JButton butMUL_ND_N;
    private JButton butMUL_NK_N;
    private JButton butMUL_NN_N;
    private JButton butSUB_NDN_N;
    private JButton butDIV_NN_DK;
    private JButton butDIV_NN_N;
    private JButton butMOD_NN_N;
    private JButton butGCF_NN_N;
    private JButton butLCM_NN_N;

    //кнопка, которая была последней нажата
    private JButton lastPressedButton;

    //метка возле поля вывода результата(JTextArea)
    private JLabel labelResult;

    //поле вывода результата
    private JTextArea resultArea;

    //м-во, хранящее название операций, в которых необходимо поле intNum
    private Set<String> withIntNum = new HashSet<>();
    //м-во, хранящее название операций, в которых не участвует поле num2
    private Set<String> withOutNum2 = new HashSet<>();




    public NaturalPanel()
    {
        //установка диспетчера компоновки
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);

        //ввод элеменов в множества
        withIntNum.add("MUL_ND_N");
        withIntNum.add("MUL_NK_N");
        withIntNum.add("SUB_NDN_N");

        withOutNum2.add("NZER_N_B");
        withOutNum2.add("ADD_1N_N");
        withOutNum2.add("MUL_ND_N");
        withOutNum2.add("MUL_NK_N");

        //дефолтный шрифт для меток
        Font fontForLabel = new Font("Arial", Font.BOLD, 15);

        //создание меток и установка им дефолтного шрифта
        labelNum1 = new JLabel("Первое натуральное число:");
        labelNum1.setFont(fontForLabel);
        labelNum2 = new JLabel("Второе натуральное число:");
        labelNum2.setFont(fontForLabel);
        labelIntNum = new JLabel("int k:");
        labelIntNum.setFont(fontForLabel);

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
        intNum = new JTextField();
        intNum.setPreferredSize(new Dimension(220, 30));
        intNum.setFont(fontforTextField);
        intNum.addFocusListener(new IntNumFocusListener());
        intNum.setText("" + intNumHintString);
        intNum.setForeground(Color.GRAY);
        intNumHintIsVisible = true;
        intNum.getDocument().addDocumentListener(new MyDocumentListener());

        //определение рамки текстовых полей ввода вида: "ошибка ввода"
        errorBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.RED);

        //инициализация флагов корректного ввода
        num1CorrectInput = true;
        num2CorrectInput = true;
        intNumCorrectInput = true;

        //создание кнопок
        butCOM_NN_D = new JButton(new ButtonAction("COM_NN_D"));
        butCOM_NN_D.setPreferredSize(new Dimension(115, 27));

        butNZER_N_B = new JButton(new ButtonAction("NZER_N_B"));
        butNZER_N_B.setPreferredSize(new Dimension(115, 27));

        butADD_1N_N = new JButton(new ButtonAction("ADD_1N_N"));
        butADD_1N_N.setPreferredSize(new Dimension(115, 27));

        butADD_NN_N = new JButton(new ButtonAction("ADD_NN_N"));
        butADD_NN_N.setPreferredSize(new Dimension(115, 27));

        butSUB_NN_N = new JButton(new ButtonAction("SUB_NN_N"));
        butSUB_NN_N.setPreferredSize(new Dimension(115, 27));

        butMUL_ND_N = new JButton(new ButtonAction("MUL_ND_N"));
        butMUL_ND_N.setPreferredSize(new Dimension(115, 27));

        butMUL_NK_N = new JButton(new ButtonAction("MUL_NK_N"));
        butMUL_NK_N.setPreferredSize(new Dimension(115, 27));

        butMUL_NN_N = new JButton(new ButtonAction("MUL_NN_N"));
        butMUL_NN_N.setPreferredSize(new Dimension(115, 27));

        butSUB_NDN_N = new JButton(new ButtonAction("SUB_NDN_N"));
        butSUB_NDN_N.setPreferredSize(new Dimension(115, 27));

        butDIV_NN_DK = new JButton(new ButtonAction("DIV_NN_DK"));
        butDIV_NN_DK.setPreferredSize(new Dimension(115, 27));

        butDIV_NN_N = new JButton(new ButtonAction("DIV_NN_N"));
        butDIV_NN_N.setPreferredSize(new Dimension(115, 27));

        butMOD_NN_N = new JButton(new ButtonAction("MOD_NN_N"));
        butMOD_NN_N.setPreferredSize(new Dimension(115, 27));

        butGCF_NN_N = new JButton(new ButtonAction("GCF_NN_N"));
        butGCF_NN_N.setPreferredSize(new Dimension(115, 27));

        butLCM_NN_N = new JButton(new ButtonAction("LCM_NN_N"));
        butLCM_NN_N.setPreferredSize(new Dimension(115, 27));

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
        add(labelIntNum, new GBC(0,4).setWeight(100, 0).setInsets(15, 0, 0, 45));
        add(intNum, new GBC(0,5).setWeight(100, 0).setInsets(0, 17, 15, 45));
        add(butCOM_NN_D, new GBC(1, 0).setWeight(100, 0).setInsets(30, 4, 7, 4));
        add(butNZER_N_B, new GBC(2, 0).setWeight(100, 0).setInsets(30, 4, 7, 4));
        add(butADD_1N_N, new GBC(3, 0).setWeight(100, 0).setInsets(30, 4, 7, 4));
        add(butADD_NN_N, new GBC(4, 0).setWeight(100, 0).setInsets(30, 4, 7, 4));
        add(butSUB_NN_N, new GBC(1, 1).setWeight(100, 0).setInsets(7, 4, 7, 4));
        add(butMUL_ND_N, new GBC(2, 1).setWeight(100, 0).setInsets(7, 4, 7, 4));
        add(butMUL_NK_N, new GBC(3, 1).setWeight(100, 0).setInsets(7, 4, 7, 4));
        add(butMUL_NN_N, new GBC(4, 1).setWeight(100, 0).setInsets(7, 4, 7, 4));
        add(butSUB_NDN_N, new GBC(1, 2).setWeight(100, 0).setInsets(7, 4, 7, 4));
        add(butDIV_NN_DK, new GBC(2, 2).setWeight(100, 0).setInsets(7, 4, 7, 4));
        add(butDIV_NN_N, new GBC(3, 2).setWeight(100, 0).setInsets(7, 4, 7, 4));
        add(butMOD_NN_N, new GBC(4, 2).setWeight(100, 0).setInsets(7, 4, 7, 4));
        add(butGCF_NN_N, new GBC(1, 3).setWeight(100, 0).setInsets(7, 4, 7, 4));
        add(butLCM_NN_N, new GBC(2, 3).setWeight(100, 0).setInsets(7, 4, 7, 4));
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
            if(intNumHintIsVisible)
            {
                intNumHintIsVisible = false;
                intNum.setText("");
                intNum.setForeground(Color.BLACK);
            }
            else
                intNumHintIsVisible = false;
        }

        @Override
        public void focusLost(FocusEvent e)
        {
            if(intNum.getText().isEmpty())
            {
                intNumHintIsVisible = true;
                intNum.setForeground(Color.GRAY);
                intNum.setText("" + intNumHintString);
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
                case "COM_NN_D":
                    putValue(Action.SHORT_DESCRIPTION, "2 - ПЕРВОЕ ЧИСЛО БОЛЬШЕ ВТОРОГО, 0 - РАВНЫ, 1 - ИНАЧЕ");
                    break;
                case "NZER_N_B":
                    putValue(Action.SHORT_DESCRIPTION, "true - ЕСЛИ ЧИСЛО НЕ РАВНО НУЛЮ, false - ИНАЧЕ");
                    break;
                case "ADD_1N_N":
                    putValue(Action.SHORT_DESCRIPTION, "ПРИБАВЛЕНИЕ ЕДИНИЦЫ К НАТУРАЛЬНОМУ ЧИСЛУ");
                    break;
                case "ADD_NN_N":
                    putValue(Action.SHORT_DESCRIPTION, "СЛОЖЕНИЕ ДВУХ НАТУРАЛЬНЫХ ЧИСЕЛ");
                    break;
                case "SUB_NN_N":
                    putValue(Action.SHORT_DESCRIPTION, "РАЗНОСТЬ ДВУХ НАТУРАЛЬНЫХ ЧИСЕЛ");
                    break;
                case "MUL_ND_N":
                    putValue(Action.SHORT_DESCRIPTION, "УМНОЖЕНИЕ НАТУРАЛЬНОГО ЧИСЛА НА ЦИФРУ");
                    break;
                case "MUL_NK_N":
                    putValue(Action.SHORT_DESCRIPTION, "УМНОЖЕНИЕ НАТУРАЛЬНОГО ЧИСЛА НА 10^k");
                    break;
                case "MUL_NN_N":
                    putValue(Action.SHORT_DESCRIPTION, "ПРОИЗВЕДЕНИЕ ДВУХ НАТУРАЛЬНЫХ ЧИСЕЛ");
                    break;
                case "SUB_NDN_N":
                    putValue(Action.SHORT_DESCRIPTION, "ВЫЧИТАНИЕ ИЗ ОДНОГО НАТУРАЛЬНОГО ЧИСЛА ДРУГОГО, УМНОЖЕННОГО НА ЦИФРУ");
                    break;
                case "DIV_NN_DK":
                    putValue(Action.SHORT_DESCRIPTION, "ПЕРВАЯ ЦИФРА ОТ ДЕЛЕНИЯ НАТУРАЛЬНЫХ ЧИСЕЛ");
                    break;
                case "DIV_NN_N":
                    putValue(Action.SHORT_DESCRIPTION, "ЧАСТНОЕ ОТ ДЕЛЕНИЯ НАТУРАЛЬНЫХ ЧИСЕЛ");
                    break;
                case "MOD_NN_N":
                    putValue(Action.SHORT_DESCRIPTION, "ОСТАТОК ОТ ДЕЛЕНИЯ НАТУРАЛЬНЫХ ЧИСЕЛ");
                    break;
                case "GCF_NN_N":
                    putValue(Action.SHORT_DESCRIPTION, "НОД ДВУХ НАТУРАЛЬНЫХ ЧИСЕЛ");
                    break;
                case "LCM_NN_N":
                    putValue(Action.SHORT_DESCRIPTION, "НОК ДВУХ НАТУРАЛЬНЫХ ЧИСЕЛ");
                    break;
            }
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(lastPressedButton != null)
            {
                lastPressedButton.setBackground(defaultButtonBackgroundColor);
                num2.setEnabled(true);
                intNum.setEnabled(true);
            }
            if(lastPressedButton != e.getSource())
            {
                lastPressedButton = (JButton) e.getSource();
                lastPressedButton.setBackground(Color.CYAN);
                String command = lastPressedButton.getActionCommand();
                if(!withIntNum.contains(command))
                    intNum.setEnabled(false);
                if(withOutNum2.contains(command))
                    num2.setEnabled(false);
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
                case "COM_NN_D":
                    if (!num1HintIsVisible && !num2HintIsVisible
                            && num1CorrectInput && num2CorrectInput
                            && !num1.getText().isEmpty() && !num2.getText().isEmpty())
                    {
                        int result = Natural.COM_NN_D(Natural.parseNatural(num1.getText()), Natural.parseNatural(num2.getText()));
                        if(result != -1)
                            resultArea.setText("" + result);
                        else
                            resultArea.setText("");
                    }
                    else
                        resultArea.setText("");
                    break;

                case "NZER_N_B":
                    if(!num1HintIsVisible && num1CorrectInput && !num1.getText().isEmpty())
                    {
                        Natural aNum1 = Natural.parseNatural(num1.getText());
                        if(aNum1 != null)
                            resultArea.setText("" + Natural.NZER_N_B(aNum1));
                        else
                            resultArea.setText("");
                    }
                    else
                        resultArea.setText("");
                    break;

                case "ADD_1N_N":
                    if(!num1HintIsVisible && num1CorrectInput && !num1.getText().isEmpty())
                    {
                        Natural result = Natural.ADD_1N_N(Natural.parseNatural(num1.getText()));
                        if(result != null)
                            resultArea.setText("" + result);
                        else
                            resultArea.setText("");
                    }
                    else
                        resultArea.setText("");
                    break;

                case "ADD_NN_N":
                    if(!num1HintIsVisible && !num2HintIsVisible
                            && num1CorrectInput && num2CorrectInput
                            && !num1.getText().isEmpty() && !num2.getText().isEmpty())
                    {
                        Natural result = Natural.ADD_NN_N(Natural.parseNatural(num1.getText()), Natural.parseNatural(num2.getText()));
                        if(result != null)
                            resultArea.setText("" + result);
                        else
                            resultArea.setText("");
                    }
                    else
                        resultArea.setText("");
                    break;

                case "SUB_NN_N":
                    if(!num1HintIsVisible && !num2HintIsVisible
                       && num1CorrectInput && num2CorrectInput
                       && !num1.getText().isEmpty() && !num2.getText().isEmpty())
                    {
                        Natural result = Natural.SUB_NN_N(Natural.parseNatural(num1.getText()), Natural.parseNatural(num2.getText()));
                        if(result != null)
                            resultArea.setText("" + result);
                        else
                            resultArea.setText("Ошибка! Результат отрицательный!");
                    }
                    else
                        resultArea.setText("");
                    break;

                case "MUL_ND_N":
                    if(!num1HintIsVisible && !intNumHintIsVisible
                       && num1CorrectInput && intNumCorrectInput
                       && !num1.getText().isEmpty() && !intNum.getText().isEmpty())
                    {
                        try
                        {
                            int k = java.lang.Integer.parseInt(intNum.getText());
                            Natural result = Natural.MUL_ND_N(Natural.parseNatural(num1.getText()), k);
                            if(result != null)
                                resultArea.setText("" + result);
                            else
                                resultArea.setText("Ошибка! k должен быть цифрой!");
                        }
                        catch (NumberFormatException e)
                        {
                            resultArea.setText("");
                        }
                    }
                    else
                        resultArea.setText("");
                    break;

                case "MUL_NK_N":
                    if(!num1HintIsVisible && !intNumHintIsVisible
                            && num1CorrectInput && num2CorrectInput
                            && !num1.getText().isEmpty() && !num2.getText().isEmpty())
                    {
                        try
                        {
                            int k = java.lang.Integer.parseInt(intNum.getText());
                            Natural result = Natural.MUL_NK_N(Natural.parseNatural(num1.getText()), k);
                            if(result != null)
                                resultArea.setText("" + result);
                            else
                                resultArea.setText("");
                        }
                        catch (NumberFormatException e)
                        {
                            resultArea.setText("");
                        }
                    }
                    else
                        resultArea.setText("");
                    break;

                case "MUL_NN_N":
                    if (!num1HintIsVisible && !num2HintIsVisible
                            && num1CorrectInput && num2CorrectInput
                            && !num1.getText().isEmpty() && !num2.getText().isEmpty())
                    {
                        Natural result = Natural.MUL_NN_N(Natural.parseNatural(num1.getText()), Natural.parseNatural(num2.getText()));
                        if(result != null)
                            resultArea.setText("" + result);
                        else
                            resultArea.setText("");
                    }
                    else
                        resultArea.setText("");
                    break;

                case "SUB_NDN_N":
                    if (!num1HintIsVisible && !num2HintIsVisible && !intNumHintIsVisible
                        && num1CorrectInput && num2CorrectInput && intNumCorrectInput
                        && !num1.getText().isEmpty() && !num2.getText().isEmpty() && !intNum.getText().isEmpty())
                    {
                        try
                        {
                            int k = java.lang.Integer.parseInt(intNum.getText());
                            Natural result = Natural.SUB_NDN_N(Natural.parseNatural(num1.getText()), k, Natural.parseNatural(num2.getText()));
                            if(result != null)
                                resultArea.setText("" + result);
                            else
                            {
                                if(0 <= k && k <= 9)
                                    resultArea.setText("Ошибка! Результат отрицательный!");
                                else
                                    resultArea.setText("Ошибка! k должен быть цифрой!");
                            }
                        }
                        catch (NumberFormatException e)
                        {
                            resultArea.setText("");
                        }
                    }
                    else
                        resultArea.setText("");
                    break;

                case "DIV_NN_DK":
                    if (!num1HintIsVisible && !num2HintIsVisible
                        && num1CorrectInput && num2CorrectInput
                        && !num1.getText().isEmpty() && !num2.getText().isEmpty())
                    {
                        int result = Natural.DIV_NN_DK(Natural.parseNatural(num1.getText()), Natural.parseNatural(num2.getText()));
                        if(result != -1)
                            resultArea.setText("" + result);
                        else
                            resultArea.setText("Ошибка! Деление на ноль запрещено!");
                    }
                    else
                        resultArea.setText("");
                    break;

                case "DIV_NN_N":
                    if (!num1HintIsVisible && !num2HintIsVisible
                        && num1CorrectInput && num2CorrectInput
                        && !num1.getText().isEmpty() && !num2.getText().isEmpty())
                    {
                        Natural result = Natural.DIV_NN_N(Natural.parseNatural(num1.getText()), Natural.parseNatural(num2.getText()));
                        if(result != null)
                            resultArea.setText("" + result);
                        else
                            resultArea.setText("Ошибка! Деление на ноль запрещено!");
                    }
                    else
                        resultArea.setText("");
                    break;

                case "MOD_NN_N":
                    if (!num1HintIsVisible && !num2HintIsVisible
                        && num1CorrectInput && num2CorrectInput
                        && !num1.getText().isEmpty() && !num2.getText().isEmpty())
                    {
                        Natural result = Natural.MOD_NN_N(Natural.parseNatural(num1.getText()), Natural.parseNatural(num2.getText()));
                        if(result != null)
                            resultArea.setText("" + result);
                        else
                            resultArea.setText("Ошибка! Деление на ноль запрещено!");
                    }
                    else
                        resultArea.setText("");
                    break;

                case "GCF_NN_N":
                    if (!num1HintIsVisible && !num2HintIsVisible
                            && num1CorrectInput && num2CorrectInput
                            && !num1.getText().isEmpty() && !num2.getText().isEmpty())
                    {
                        Natural result = Natural.GCF_NN_N(Natural.parseNatural(num1.getText()), Natural.parseNatural(num2.getText()));
                        if(result != null)
                            resultArea.setText("" + result);
                        else
                            resultArea.setText("");
                    }
                    else
                        resultArea.setText("");
                    break;

                case "LCM_NN_N":
                    if (!num1HintIsVisible && !num2HintIsVisible
                        && num1CorrectInput && num2CorrectInput
                        && !num1.getText().isEmpty() && !num2.getText().isEmpty())
                    {
                        Natural result = Natural.LCM_NN_N(Natural.parseNatural(num1.getText()), Natural.parseNatural(num2.getText()));
                        if(result != null)
                            resultArea.setText("" + result);
                        else
                            resultArea.setText("Ошибка! Оба числа должны быть отличны от нуля!");
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
        Pattern p = Pattern.compile("^\\d*$");
        Matcher m;

        if(!num1HintIsVisible)
        {
            m = p.matcher(num1.getText());
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
            m = p.matcher(num2.getText());
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


        if(!intNumHintIsVisible)
            if(!intNum.getText().isEmpty())
            {
                try
                {
                    int k = java.lang.Integer.parseInt(intNum.getText());

                    if(!intNumCorrectInput)
                    {
                        intNum.setBorder(defaultBorder);
                        intNumCorrectInput = true;
                    }

                }
                catch (NumberFormatException e)
                {
                    if(intNumCorrectInput)
                    {
                        intNum.setBorder(errorBorder);
                        intNumCorrectInput = false;
                    }
                }
            }
            else
                if(!intNumCorrectInput)
                {
                    intNum.setBorder(defaultBorder);
                    intNumCorrectInput = true;
                }
    }
}
