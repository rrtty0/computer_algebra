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


public class FractionPanel extends JPanel
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
    private JLabel labelFr1;
    private JLabel labelFr2;
    private JLabel labelIntegerNum;

    //текстовые поля ввода
    private JTextField fr1;
    private JTextField fr2;
    private JTextField integerNum;

    //индикатор вывода подсказки ввода и сама подсказка ввода для полей fr1, fr2 и intNum
    boolean fr1HintIsVisible;
    final String fr1HintString = "Example: -6789/9876";
    boolean fr2HintIsVisible;
    final String fr2HintString = "Example: 12/3";
    boolean integerNumHintIsVisible;
    final String integerNumHintString = "Example: -909090";

    //индикатор корректного ввода(здесь пустая строка тоже корректно!!!)
    boolean fr1CorrectInput;
    boolean fr2CorrectInput;
    boolean integerNumCorrectInput;

    //кнопки для выбора действия
    private JButton butRED_Q_Q;
    private JButton butINT_Q_B;
    private JButton butTRANS_Z_Q;
    private JButton butTRANS_Q_Z;
    private JButton butADD_QQ_Q;
    private JButton butSUB_QQ_Q;
    private JButton butMUL_QQ_Q;
    private JButton butDIV_QQ_Q;


    //кнопка, которая была последней нажата
    private JButton lastPressedButton;

    //метка возле поля вывода результата(JTextArea)
    private JLabel labelResult;

    //поле вывода результата
    private JTextArea resultArea;

    //м-во, хранящее название операций, в которых не участвует поле fr2
    private Set<String> withOutFr2 = new HashSet<>();




    public FractionPanel()
    {
        //установка диспетчера компоновки
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);

        //ввод элеменов в множество
        withOutFr2.add("RED_Q_Q");
        withOutFr2.add("INT_Q_B");
        withOutFr2.add("TRANS_Z_Q");
        withOutFr2.add("TRANS_Q_Z");

        //дефолтный шрифт для меток
        Font fontForLabel = new Font("Arial", Font.BOLD, 15);

        //создание меток и установка им дефолтного шрифта
        labelFr1 = new JLabel("Первая дробь:");
        labelFr1.setFont(fontForLabel);
        labelFr2 = new JLabel("Вторая дробь:");
        labelFr2.setFont(fontForLabel);
        labelIntegerNum = new JLabel("Целое число:");
        labelIntegerNum.setFont(fontForLabel);

        //дефолтный шрифт для текстовых полей
        Font fontforTextField = new Font("Arial", Font.PLAIN, 16);

        //создание и установка свойств полю fr1
        fr1 = new JTextField();
        fr1.setPreferredSize(new Dimension(220, 30));
        fr1.setFont(fontforTextField);
        fr1.addFocusListener(new Fr1FocusListener());
        fr1.setText("" + fr1HintString);
        fr1.setForeground(Color.GRAY);
        fr1HintIsVisible = true;
        fr1.getDocument().addDocumentListener(new MyDocumentListener());

        //определение дефолтного цвета фона кнопки
        defaultButtonBackgroundColor = fr1.getBackground();
        //определение дефолтной рамки для текстовых полей ввода
        defaultBorder = fr1.getBorder();

        //создание и установка свойств полю fr2
        fr2 = new JTextField();
        fr2.setPreferredSize(new Dimension(220, 30));
        fr2.setFont(fontforTextField);
        fr2.addFocusListener(new Fr2FocusListener());
        fr2.setText("" + fr2HintString);
        fr2.setForeground(Color.GRAY);
        fr2HintIsVisible = true;
        fr2.getDocument().addDocumentListener(new MyDocumentListener());

        //создание и установка свойств полю intNum
        integerNum = new JTextField();
        integerNum.setPreferredSize(new Dimension(220, 30));
        integerNum.setFont(fontforTextField);
        integerNum.addFocusListener(new IntNumFocusListener());
        integerNum.setText("" + integerNumHintString);
        integerNum.setForeground(Color.GRAY);
        integerNumHintIsVisible = true;
        integerNum.getDocument().addDocumentListener(new MyDocumentListener());

        //определение рамки текстовых полей ввода вида: "ошибка ввода"
        errorBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.RED);

        //инициализация флагов корректного ввода
        fr1CorrectInput = true;
        fr2CorrectInput = true;
        integerNumCorrectInput = true;

        //создание кнопок
        butRED_Q_Q = new JButton(new ButtonAction("RED_Q_Q"));
        butRED_Q_Q.setPreferredSize(new Dimension(115, 27));

        butINT_Q_B = new JButton(new ButtonAction("INT_Q_B"));
        butINT_Q_B.setPreferredSize(new Dimension(115, 27));

        butTRANS_Z_Q = new JButton(new ButtonAction("TRANS_Z_Q"));
        butTRANS_Z_Q.setPreferredSize(new Dimension(115, 27));

        butTRANS_Q_Z = new JButton(new ButtonAction("TRANS_Q_Z"));
        butTRANS_Q_Z.setPreferredSize(new Dimension(115, 27));

        butADD_QQ_Q = new JButton(new ButtonAction("ADD_QQ_Q"));
        butADD_QQ_Q.setPreferredSize(new Dimension(115, 27));

        butSUB_QQ_Q = new JButton(new ButtonAction("SUB_QQ_Q"));
        butSUB_QQ_Q.setPreferredSize(new Dimension(115, 27));

        butMUL_QQ_Q = new JButton(new ButtonAction("MUL_QQ_Q"));
        butMUL_QQ_Q.setPreferredSize(new Dimension(115, 27));

        butDIV_QQ_Q = new JButton(new ButtonAction("DIV_QQ_Q"));
        butDIV_QQ_Q.setPreferredSize(new Dimension(115, 27));

        //создание метки возле поля "результат" и установка ей дефолтного шрифта
        labelResult = new JLabel("Результат:");
        labelResult.setFont(fontForLabel);

        //создание поля вывода результата, установка шрифта, установка запрета редактирования поля вручную
        resultArea = new JTextArea(TEXT_ROWS, TEXT_COLUMNS);
        resultArea.setFont(fontforTextField);
        resultArea.setEditable(false);

        //вывод компонентов на экран
        add(labelFr1, new GBC(0,0).setWeight(100, 0).setInsets(30, 0, 0, 45));
        add(fr1, new GBC(0,1).setWeight(100, 0).setInsets(0, 17, 15, 45));
        add(labelFr2, new GBC(0,2).setWeight(100, 0).setInsets(15, 0, 0, 45));
        add(fr2, new GBC(0,3).setWeight(100, 0).setInsets(0, 17, 15, 45));
        add(labelIntegerNum, new GBC(0,4).setWeight(100, 0).setInsets(15, 0, 0, 45));
        add(integerNum, new GBC(0,5).setWeight(100, 0).setInsets(0, 17, 15, 45));
        add(butRED_Q_Q, new GBC(1, 0).setWeight(100, 0).setInsets(30, 4, 7, 4));
        add(butINT_Q_B, new GBC(2, 0).setWeight(100, 0).setInsets(30, 4, 7, 4));
        add(butTRANS_Z_Q, new GBC(3, 0).setWeight(100, 0).setInsets(30, 4, 7, 4));
        add(butTRANS_Q_Z, new GBC(4, 0).setWeight(100, 0).setInsets(30, 4, 7, 4));
        add(butADD_QQ_Q, new GBC(1, 1).setWeight(100, 0).setInsets(7, 4, 7, 4));
        add(butSUB_QQ_Q, new GBC(2, 1).setWeight(100, 0).setInsets(7, 4, 7, 4));
        add(butMUL_QQ_Q, new GBC(3, 1).setWeight(100, 0).setInsets(7, 4, 7, 4));
        add(butDIV_QQ_Q, new GBC(4, 1).setWeight(100, 0).setInsets(7, 4, 7, 4));
        add(labelResult, new GBC(0, 6).setWeight(100, 0).setInsets(15, 4, 4, 4).setFill(GBC.EAST));
        add(new JScrollPane(resultArea), new GBC(0, 7, 5, 1).setWeight(100, 0).setFill(GBC.BOTH));
    }

    //слушатель фокуса ввода текстовых полей
    private class Fr1FocusListener implements FocusListener
    {
        @Override
        public void focusGained(FocusEvent e)
        {
            if(fr1HintIsVisible)
            {
                fr1HintIsVisible = false;
                fr1.setText("");
                fr1.setForeground(Color.BLACK);
            }
            else
                fr1HintIsVisible = false;
        }

        @Override
        public void focusLost(FocusEvent e)
        {
            if(fr1.getText().isEmpty())
            {
                fr1HintIsVisible = true;
                fr1.setForeground(Color.GRAY);
                fr1.setText("" + fr1HintString);
            }
        }
    }

    private class Fr2FocusListener implements FocusListener
    {
        @Override
        public void focusGained(FocusEvent e)
        {
            if(fr2HintIsVisible)
            {
                fr2HintIsVisible = false;
                fr2.setText("");
                fr2.setForeground(Color.BLACK);
            }
            else
                fr2HintIsVisible = false;
        }

        @Override
        public void focusLost(FocusEvent e)
        {
            if(fr2.getText().isEmpty())
            {
                fr2HintIsVisible = true;
                fr2.setForeground(Color.GRAY);
                fr2.setText("" + fr2HintString);
            }
        }
    }

    private class IntNumFocusListener implements FocusListener
    {
        @Override
        public void focusGained(FocusEvent e)
        {
            if(integerNumHintIsVisible)
            {
                integerNumHintIsVisible = false;
                integerNum.setText("");
                integerNum.setForeground(Color.BLACK);
            }
            else
                integerNumHintIsVisible = false;
        }

        @Override
        public void focusLost(FocusEvent e)
        {
            if(integerNum.getText().isEmpty())
            {
                integerNumHintIsVisible = true;
                integerNum.setForeground(Color.GRAY);
                integerNum.setText("" + integerNumHintString);
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
                case "RED_Q_Q":
                    putValue(Action.SHORT_DESCRIPTION, "СОКРАЩЕНИЕ ДРОБИ");
                    break;
                case "INT_Q_B":
                    putValue(Action.SHORT_DESCRIPTION, "ПРОВЕРКА: ЯВЛЯЕТСЯ ЛИ ЧИСЛО ЦЕЛЫМ. TRUE - ДА, FALSE - НЕТ");
                    break;
                case "TRANS_Z_Q":
                    putValue(Action.SHORT_DESCRIPTION, "КОНВЕРТАЦИЯ ЦЕЛОГО ЧИСЛА В ДРОБНОЕ");
                    break;
                case "TRANS_Q_Z":
                    putValue(Action.SHORT_DESCRIPTION, "ПОЛУЧЕНИЕ ЦЕЛОЙ ЧАСТИ ДРОБИ");
                    break;
                case "ADD_QQ_Q":
                    putValue(Action.SHORT_DESCRIPTION, "СУММА ДВУХ ДРОБЕЙ");
                    break;
                case "SUB_QQ_Q":
                    putValue(Action.SHORT_DESCRIPTION, "РАЗНОСТЬ ДВУХ ДРОБЕЙ");
                    break;
                case "MUL_QQ_Q":
                    putValue(Action.SHORT_DESCRIPTION, "ПРОИЗВЕДЕНИЕ ДВУХ ДРОБЕЙ");
                    break;
                case "DIV_QQ_Q":
                    putValue(Action.SHORT_DESCRIPTION, "ДЕЛЕНИЕ ДВУХ ДРОБЕЙ");
                    break;
            }
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(lastPressedButton != null)
            {
                lastPressedButton.setBackground(defaultButtonBackgroundColor);
                fr1.setEnabled(true);
                fr2.setEnabled(true);
                integerNum.setEnabled(true);
            }
            if(lastPressedButton != e.getSource())
            {
                lastPressedButton = (JButton) e.getSource();
                lastPressedButton.setBackground(Color.CYAN);
                String command = lastPressedButton.getActionCommand();
                if(withOutFr2.contains(command))
                {
                    fr2.setEnabled(false);
                    if(command.equals("TRANS_Z_Q"))
                        fr1.setEnabled(false);
                    else
                        integerNum.setEnabled(false);
                }
                else
                    integerNum.setEnabled(false);

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
                case "RED_Q_Q":
                    if (!fr1HintIsVisible && fr1CorrectInput && !fr1.getText().isEmpty())
                        resultArea.setText("" + Fraction.RED_Q_Q(Fraction.parseFraction(fr1.getText())));
                    else
                        resultArea.setText("");
                    break;

                case "INT_Q_B":
                    if(!fr1HintIsVisible && fr1CorrectInput && !fr1.getText().isEmpty())
                        try{
                            resultArea.setText("" + Fraction.INT_Q_B(Fraction.parseFraction(fr1.getText())));
                        }
                        catch (Exception e)
                        {
                            resultArea.setText("");
                        }
                    else
                        resultArea.setText("");
                    break;

                case "TRANS_Z_Q":
                    if(!integerNumHintIsVisible && integerNumCorrectInput && !integerNum.getText().isEmpty())
                        resultArea.setText("" + Fraction.TRANS_Z_Q(Integer.parseInteger(integerNum.getText())));
                    else
                        resultArea.setText("");
                    break;

                case "TRANS_Q_Z":
                    if(!fr1HintIsVisible && fr1CorrectInput && !fr1.getText().isEmpty())
                        resultArea.setText("" + Fraction.TRANS_Q_Z(Fraction.parseFraction(fr1.getText())));
                    else
                        resultArea.setText("");
                    break;

                case "ADD_QQ_Q":
                    if(!fr1HintIsVisible && !fr2HintIsVisible
                            && fr1CorrectInput && fr2CorrectInput
                            && !fr1.getText().isEmpty() && !fr2.getText().isEmpty())
                        resultArea.setText("" + Fraction.ADD_QQ_Q(Fraction.parseFraction(fr1.getText()), Fraction.parseFraction(fr2.getText())));
                    else
                        resultArea.setText("");
                    break;

                case "SUB_QQ_Q":
                    if(!fr1HintIsVisible && !fr2HintIsVisible
                            && fr1CorrectInput && fr2CorrectInput
                            && !fr1.getText().isEmpty() && !fr2.getText().isEmpty())
                        resultArea.setText("" + Fraction.SUB_QQ_Q(Fraction.parseFraction(fr1.getText()), Fraction.parseFraction(fr2.getText())));
                    else
                        resultArea.setText("");
                    break;

                case "MUL_QQ_Q":
                    if(!fr1HintIsVisible && !fr2HintIsVisible
                            && fr1CorrectInput && fr2CorrectInput
                            && !fr1.getText().isEmpty() && !fr2.getText().isEmpty())
                        resultArea.setText("" + Fraction.MUL_QQ_Q(Fraction.parseFraction(fr1.getText()), Fraction.parseFraction(fr2.getText())));
                    else
                        resultArea.setText("");
                    break;

                case "DIV_QQ_Q":
                    if(!fr1HintIsVisible && !fr2HintIsVisible
                            && fr1CorrectInput && fr2CorrectInput
                            && !fr1.getText().isEmpty() && !fr2.getText().isEmpty())
                    {
                        Fraction result = Fraction.DIV_QQ_Q(Fraction.parseFraction(fr1.getText()), Fraction.parseFraction(fr2.getText()));
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
        Pattern p1 = Pattern.compile("^(( )*(-)?( )*\\d+(( )*/( )*(0)*[1-9]\\d*)?( )*)?$");
        Matcher m;

        if(!fr1HintIsVisible)
        {
            m = p1.matcher(fr1.getText());
            if(m.matches())
            {
                if(!fr1CorrectInput)
                {
                    fr1.setBorder(defaultBorder);
                    fr1CorrectInput = true;
                }
            }
            else
            if(fr1CorrectInput)
            {
                fr1.setBorder(errorBorder);
                fr1CorrectInput = false;
            }
        }

        if(!fr2HintIsVisible)
        {
            m = p1.matcher(fr2.getText());
            if(m.matches())
            {
                if(!fr2CorrectInput)
                {
                    fr2.setBorder(defaultBorder);
                    fr2CorrectInput = true;
                }
            }
            else
            if(fr2CorrectInput)
            {
                fr2.setBorder(errorBorder);
                fr2CorrectInput = false;
            }
        }

        if(!integerNumHintIsVisible)
        {
            m = p1.matcher(integerNum.getText());
            if(m.matches())
            {
                if(!integerNumCorrectInput)
                {
                    integerNum.setBorder(defaultBorder);
                    integerNumCorrectInput = true;
                }
            }
            else
            if(integerNumCorrectInput)
            {
                integerNum.setBorder(errorBorder);
                integerNumCorrectInput = false;
            }
        }
    }
}
