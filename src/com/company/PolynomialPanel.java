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


public class PolynomialPanel extends JPanel
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
    private JLabel labelPol1;
    private JLabel labelPol2;
    private JLabel labelFractionNum;
    private JLabel labelNaturalNum;

    //текстовые поля ввода
    private JTextField pol1;
    private JTextField pol2;
    private JTextField fractionNum;
    private JTextField naturalNum;

    //индикатор вывода подсказки ввода и сама подсказка ввода для полей pol1, pol2, fractionNum и naturalNum
    boolean pol1HintIsVisible;
    final String pol1HintString = "Example: -3*x^9 + 8*x^2 - 189";
    boolean pol2HintIsVisible;
    final String pol2HintString = "Example: x^2 - 1";
    boolean fractionNumHintIsVisible;
    final String fractionNumHintString = "Example: -35/1789";
    boolean naturalNumHintIsVisible;
    final String naturalNumHintString = "Example: 34567";

    //индикатор корректного ввода(здесь пустая строка тоже корректно!!!)
    boolean pol1CorrectInput;
    boolean pol2CorrectInput;
    boolean fractionNumCorrectInput;
    boolean naturalNumCorrectInput;

    //кнопки для выбора действия
    private JButton butADD_PP_P;
    private JButton butSUB_PP_P;
    private JButton butMUL_PQ_P;
    private JButton butMUL_PxK_P;
    private JButton butLED_P_Q;
    private JButton butDEG_P_Z;
    private JButton butFAC_P_Q;
    private JButton butMUL_PP_P;
    private JButton butDIV_PP_P;
    private JButton butMOD_PP_P;
    private JButton butGCF_PP_P;
    private JButton butDER_P_P;

    //кнопка, которая была последней нажата
    private JButton lastPressedButton;

    //метка возле поля вывода результата(JTextArea)
    private JLabel labelResult;

    //поле вывода результата
    private JTextArea resultArea;

    //м-во, хранящее название операций, в которых не участвует поле pol2
    private Set<String> withOutPol2 = new HashSet<>();




    public PolynomialPanel()
    {
        //установка диспетчера компоновки
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);

        //ввод элеменов в множество
        withOutPol2.add("MUL_PQ_P");
        withOutPol2.add("MUL_PxK_P");
        withOutPol2.add("LED_P_Q");
        withOutPol2.add("DEG_P_Z");
        withOutPol2.add("FAC_P_Q");
        withOutPol2.add("DER_P_P");

        //дефолтный шрифт для меток
        Font fontForLabel = new Font("Arial", Font.BOLD, 15);

        //создание меток и установка им дефолтного шрифта
        labelPol1 = new JLabel("Первый полином:");
        labelPol1.setFont(fontForLabel);
        labelPol2 = new JLabel("Второй полином:");
        labelPol2.setFont(fontForLabel);
        labelFractionNum = new JLabel("Дробное число:");
        labelFractionNum.setFont(fontForLabel);
        labelNaturalNum = new JLabel("Натуральное число:");
        labelNaturalNum.setFont(fontForLabel);

        //дефолтный шрифт для текстовых полей
        Font fontforTextField = new Font("Arial", Font.PLAIN, 16);

        //создание и установка свойств полю pol1
        pol1 = new JTextField();
        pol1.setPreferredSize(new Dimension(660, 30));
        pol1.setFont(fontforTextField);
        pol1.addFocusListener(new Pol1FocusListener());
        pol1.setText("" + pol1HintString);
        pol1.setForeground(Color.GRAY);
        pol1HintIsVisible = true;
        pol1.getDocument().addDocumentListener(new MyDocumentListener());

        //определение дефолтного цвета фона кнопки
        defaultButtonBackgroundColor = pol1.getBackground();
        //определение дефолтной рамки для текстовых полей ввода
        defaultBorder = pol1.getBorder();

        //создание и установка свойств полю pol2
        pol2 = new JTextField();
        pol2.setPreferredSize(new Dimension(660, 30));
        pol2.setFont(fontforTextField);
        pol2.addFocusListener(new Pol2FocusListener());
        pol2.setText("" + pol2HintString);
        pol2.setForeground(Color.GRAY);
        pol2HintIsVisible = true;
        pol2.getDocument().addDocumentListener(new MyDocumentListener());

        //создание и установка свойств полю fractionNum
        fractionNum = new JTextField();
        fractionNum.setPreferredSize(new Dimension(250, 30));
        fractionNum.setFont(fontforTextField);
        fractionNum.addFocusListener(new FractionNumFocusListener());
        fractionNum.setText("" + fractionNumHintString);
        fractionNum.setForeground(Color.GRAY);
        fractionNumHintIsVisible = true;
        fractionNum.getDocument().addDocumentListener(new MyDocumentListener());

        //создание и установка свойств полю naturalNum
        naturalNum = new JTextField();
        naturalNum.setPreferredSize(new Dimension(250, 30));
        naturalNum.setFont(fontforTextField);
        naturalNum.addFocusListener(new NaturalNumFocusListener());
        naturalNum.setText("" + naturalNumHintString);
        naturalNum.setForeground(Color.GRAY);
        naturalNumHintIsVisible = true;
        naturalNum.getDocument().addDocumentListener(new MyDocumentListener());

        //определение рамки текстовых полей ввода вида: "ошибка ввода"
        errorBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.RED);

        //инициализация флагов корректного ввода
        pol1CorrectInput = true;
        pol2CorrectInput = true;
        fractionNumCorrectInput = true;
        naturalNumCorrectInput = true;

        //создание кнопок
        butADD_PP_P = new JButton(new ButtonAction("ADD_PP_P"));
        butADD_PP_P.setPreferredSize(new Dimension(115, 27));

        butSUB_PP_P = new JButton(new ButtonAction("SUB_PP_P"));
        butSUB_PP_P.setPreferredSize(new Dimension(115, 27));

        butMUL_PQ_P = new JButton(new ButtonAction("MUL_PQ_P"));
        butMUL_PQ_P.setPreferredSize(new Dimension(115, 27));

        butMUL_PxK_P = new JButton(new ButtonAction("MUL_PxK_P"));
        butMUL_PxK_P.setPreferredSize(new Dimension(115, 27));

        butLED_P_Q = new JButton(new ButtonAction("LED_P_Q"));
        butLED_P_Q.setPreferredSize(new Dimension(115, 27));

        butDEG_P_Z = new JButton(new ButtonAction("DEG_P_Z"));
        butDEG_P_Z.setPreferredSize(new Dimension(115, 27));

        butFAC_P_Q = new JButton(new ButtonAction("FAC_P_Q"));
        butFAC_P_Q.setPreferredSize(new Dimension(115, 27));

        butMUL_PP_P = new JButton(new ButtonAction("MUL_PP_P"));
        butMUL_PP_P.setPreferredSize(new Dimension(115, 27));

        butDIV_PP_P = new JButton(new ButtonAction("DIV_PP_P"));
        butDIV_PP_P.setPreferredSize(new Dimension(115, 27));

        butMOD_PP_P = new JButton(new ButtonAction("MOD_PP_P"));
        butMOD_PP_P.setPreferredSize(new Dimension(115, 27));

        butGCF_PP_P = new JButton(new ButtonAction("GCF_PP_P"));
        butGCF_PP_P.setPreferredSize(new Dimension(115, 27));

        butDER_P_P = new JButton(new ButtonAction("DER_P_P"));
        butDER_P_P.setPreferredSize(new Dimension(115, 27));


        //создание метки возле поля "результат" и установка ей дефолтного шрифта
        labelResult = new JLabel("Результат:");
        labelResult.setFont(fontForLabel);

        //создание поля вывода результата, установка шрифта, установка запрета редактирования поля вручную
        resultArea = new JTextArea(TEXT_ROWS, TEXT_COLUMNS);
        resultArea.setFont(fontforTextField);
        resultArea.setEditable(false);

        //вывод компонентов на экран
        add(labelPol1, new GBC(0,0, 1, 1).setWeight(100, 100).setInsets(0, 0, 0, 0));
        add(pol1, new GBC(1,0, 5, 1).setWeight(100, 100).setInsets(0, 0, 0, 0));
        add(labelPol2, new GBC(0,1, 1, 1).setWeight(100, 100).setInsets(0, 5, 0, 0));
        add(pol2, new GBC(1,1, 5, 1).setWeight(100, 100).setInsets(0, 0, 0, 0));
        add(labelFractionNum, new GBC(0,2, 1, 1).setWeight(100, 100).setInsets(0, 12, 0, 0));
        add(fractionNum, new GBC(1,2, 2, 1).setWeight(100, 100).setInsets(0, 0, 0, 0));
        add(labelNaturalNum, new GBC(3,2, 1, 1).setWeight(100, 100).setInsets(0, 0, 0, 0));
        add(naturalNum, new GBC(4,2, 2, 1).setWeight(100, 100).setInsets(0, 0, 0, 0));
        add(butADD_PP_P, new GBC(0, 3).setWeight(100, 100).setInsets(15, 4, 0, 4));
        add(butSUB_PP_P, new GBC(1, 3).setWeight(100, 100).setInsets(15, 4, 0, 4));
        add(butMUL_PQ_P, new GBC(2, 3).setWeight(100, 100).setInsets(15, 4, 0, 4));
        add(butMUL_PxK_P, new GBC(3, 3).setWeight(100, 100).setInsets(15, 4, 0, 4));
        add(butLED_P_Q, new GBC(4, 3).setWeight(100, 100).setInsets(15, 4, 0, 4));
        add(butDEG_P_Z, new GBC(5, 3).setWeight(100, 100).setInsets(15, 4, 0, 4));
        add(butFAC_P_Q, new GBC(0, 4).setWeight(100, 100).setInsets(0, 4, 10, 4));
        add(butMUL_PP_P, new GBC(1, 4).setWeight(100, 100).setInsets(0, 4, 10, 4));
        add(butDIV_PP_P, new GBC(2, 4).setWeight(100, 100).setInsets(0, 4, 10, 4));
        add(butMOD_PP_P, new GBC(3, 4).setWeight(100, 100).setInsets(0, 4, 10, 4));
        add(butGCF_PP_P, new GBC(4, 4).setWeight(100, 100).setInsets(0, 4, 10, 4));
        add(butDER_P_P, new GBC(5, 4).setWeight(100, 100).setInsets(0, 4, 10, 4));
        add(labelResult, new GBC(0, 5).setWeight(100, 0).setInsets(15, 4, 4, 4).setFill(GBC.EAST));
        add(new JScrollPane(resultArea), new GBC(0, 6, 6, 1).setWeight(100, 0).setFill(GBC.BOTH));
    }

    //слушатель фокуса ввода текстовых полей
    private class Pol1FocusListener implements FocusListener
    {
        @Override
        public void focusGained(FocusEvent e)
        {
            if(pol1HintIsVisible)
            {
                pol1HintIsVisible = false;
                pol1.setText("");
                pol1.setForeground(Color.BLACK);
            }
            else
                pol1HintIsVisible = false;
        }

        @Override
        public void focusLost(FocusEvent e)
        {
            if(pol1.getText().isEmpty())
            {
                pol1HintIsVisible = true;
                pol1.setForeground(Color.GRAY);
                pol1.setText("" + pol1HintString);
            }
        }
    }

    private class Pol2FocusListener implements FocusListener
    {
        @Override
        public void focusGained(FocusEvent e)
        {
            if(pol2HintIsVisible)
            {
                pol2HintIsVisible = false;
                pol2.setText("");
                pol2.setForeground(Color.BLACK);
            }
            else
                pol2HintIsVisible = false;
        }

        @Override
        public void focusLost(FocusEvent e)
        {
            if(pol2.getText().isEmpty())
            {
                pol2HintIsVisible = true;
                pol2.setForeground(Color.GRAY);
                pol2.setText("" + pol2HintString);
            }
        }
    }

    private class FractionNumFocusListener implements FocusListener
    {
        @Override
        public void focusGained(FocusEvent e)
        {
            if(fractionNumHintIsVisible)
            {
                fractionNumHintIsVisible = false;
                fractionNum.setText("");
                fractionNum.setForeground(Color.BLACK);
            }
            else
                fractionNumHintIsVisible = false;
        }

        @Override
        public void focusLost(FocusEvent e)
        {
            if(fractionNum.getText().isEmpty())
            {
                fractionNumHintIsVisible = true;
                fractionNum.setForeground(Color.GRAY);
                fractionNum.setText("" + fractionNumHintString);
            }
        }
    }

    private class NaturalNumFocusListener implements FocusListener
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
                case "ADD_PP_P":
                    putValue(Action.SHORT_DESCRIPTION, "СЛОЖЕНИЕ ДВУХ ПОЛИНОМОВ");
                    break;
                case "SUB_PP_P":
                    putValue(Action.SHORT_DESCRIPTION, "РАЗНОСТЬ ДВУХ ПОЛИНОМОВ");
                    break;
                case "MUL_PQ_P":
                    putValue(Action.SHORT_DESCRIPTION, "УМНОЖЕНИЕ ПОЛИНОМА НА ДРОБЬ");
                    break;
                case "MUL_PxK_P":
                    putValue(Action.SHORT_DESCRIPTION, "УМНОЖЕНИЕ ПОЛИНОМА НА X В СТЕПЕНИ НАТУРАЛЬНОГО ЧИСЛА");
                    break;
                case "LED_P_Q":
                    putValue(Action.SHORT_DESCRIPTION, "КОЭФФИЦИЕНТ ПРИ СТАРШЕЙ СТЕПЕНИ ПОЛИНОМА");
                    break;
                case "DEG_P_Z":
                    putValue(Action.SHORT_DESCRIPTION, "СТАРШАЯ СТЕПЕНЬ ПОЛИНОМА");
                    break;
                case "FAC_P_Q":
                    putValue(Action.SHORT_DESCRIPTION, "НОД ЧИСЛИТЕЛЕЙ КОЭФФИЦИЕНТОВ ПОЛИНОМА, НОК ЗНАМЕНАТЕЛЕЙ КОЭФФИЦИЕНТОВ ПОЛИНОМА");
                    break;
                case "MUL_PP_P":
                    putValue(Action.SHORT_DESCRIPTION, "ПРОИЗВЕДЕНИЕ ДВУХ ПОЛИНОМОВ");
                    break;
                case "DIV_PP_P":
                    putValue(Action.SHORT_DESCRIPTION, "ЧАСТНОЕ ОТ ДЕЛЕНИЯ ДВУХ ПОЛИНОМОВ");
                    break;
                case "MOD_PP_P":
                    putValue(Action.SHORT_DESCRIPTION, "ОСТАТОК ОТ ДЕЛЕНИЯ ДВУХ ПОЛИНОМОВ");
                    break;
                case "GCF_PP_P":
                    putValue(Action.SHORT_DESCRIPTION, "НОД ДВУХ ПОЛИНОМОВ");
                    break;
                case "DER_P_P":
                    putValue(Action.SHORT_DESCRIPTION, "ПРОИЗВОДНАЯ ОТ ПОЛИНОМА");
                    break;
            }
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(lastPressedButton != null)
            {
                lastPressedButton.setBackground(defaultButtonBackgroundColor);
                pol2.setEnabled(true);
                fractionNum.setEnabled(true);
                naturalNum.setEnabled(true);
            }
            if(lastPressedButton != e.getSource())
            {
                lastPressedButton = (JButton) e.getSource();
                lastPressedButton.setBackground(Color.CYAN);
                String command = lastPressedButton.getActionCommand();
                fractionNum.setEnabled(false);
                naturalNum.setEnabled(false);
                if(withOutPol2.contains(command))
                {
                    pol2.setEnabled(false);
                    if(command.equals("MUL_PQ_P"))
                        fractionNum.setEnabled(true);
                    else
                        if(command.equals("MUL_PxK_P"))
                            naturalNum.setEnabled(true);
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
                case "ADD_PP_P":
                    if (!pol1HintIsVisible && !pol2HintIsVisible
                            && pol1CorrectInput && pol2CorrectInput
                            && !pol1.getText().isEmpty() && !pol2.getText().isEmpty())
                        resultArea.setText("" + Polynomial.ADD_PP_P(Polynomial.parsePolynomial(pol1.getText()), Polynomial.parsePolynomial(pol2.getText())));
                    else
                        resultArea.setText("");
                    break;

                case "SUB_PP_P":
                    if (!pol1HintIsVisible && !pol2HintIsVisible
                            && pol1CorrectInput && pol2CorrectInput
                            && !pol1.getText().isEmpty() && !pol2.getText().isEmpty())
                        resultArea.setText("" + Polynomial.SUB_PP_P(Polynomial.parsePolynomial(pol1.getText()), Polynomial.parsePolynomial(pol2.getText())));
                    else
                        resultArea.setText("");
                    break;

                case "MUL_PQ_P":
                    if (!pol1HintIsVisible && !fractionNumHintIsVisible
                            && pol1CorrectInput && fractionNumCorrectInput
                            && !pol1.getText().isEmpty() && !fractionNum.getText().isEmpty())
                        resultArea.setText("" + Polynomial.MUL_PQ_P(Polynomial.parsePolynomial(pol1.getText()), Fraction.parseFraction(fractionNum.getText())));
                    else
                        resultArea.setText("");
                    break;

                case "MUL_PxK_P":
                    if (!pol1HintIsVisible && !naturalNumHintIsVisible
                            && pol1CorrectInput && naturalNumCorrectInput
                            && !pol1.getText().isEmpty() && !naturalNum.getText().isEmpty())
                        resultArea.setText("" + Polynomial.MUL_PxK_P(Polynomial.parsePolynomial(pol1.getText()), Natural.parseNatural(naturalNum.getText())));
                    else
                        resultArea.setText("");
                    break;

                case "LED_P_Q":
                    if (!pol1HintIsVisible && pol1CorrectInput && !pol1.getText().isEmpty())
                        resultArea.setText("" + Polynomial.LED_P_Q(Polynomial.parsePolynomial(pol1.getText())));
                    else
                        resultArea.setText("");
                    break;

                case "DEG_P_Z":
                    if (!pol1HintIsVisible && pol1CorrectInput && !pol1.getText().isEmpty())
                        resultArea.setText("" + Polynomial.DEG_P_Z(Polynomial.parsePolynomial(pol1.getText())));
                    else
                        resultArea.setText("");
                    break;

                case "FAC_P_Q":
                    if (!pol1HintIsVisible && pol1CorrectInput && !pol1.getText().isEmpty())
                        resultArea.setText("" + Polynomial.FAC_P_Q(Polynomial.parsePolynomial(pol1.getText())));
                    else
                        resultArea.setText("");
                    break;

                case "MUL_PP_P":
                    if (!pol1HintIsVisible && !pol2HintIsVisible
                            && pol1CorrectInput && pol2CorrectInput
                            && !pol1.getText().isEmpty() && !pol2.getText().isEmpty())
                        resultArea.setText("" + Polynomial.MUL_PP_P(Polynomial.parsePolynomial(pol1.getText()), Polynomial.parsePolynomial(pol2.getText())));
                    else
                        resultArea.setText("");
                    break;

                case "DIV_PP_P":
                    if (!pol1HintIsVisible && !pol2HintIsVisible
                            && pol1CorrectInput && pol2CorrectInput
                            && !pol1.getText().isEmpty() && !pol2.getText().isEmpty())
                    {
                        Polynomial result = Polynomial.DIV_PP_P(Polynomial.parsePolynomial(pol1.getText()), Polynomial.parsePolynomial(pol2.getText()));
                        if(result != null)
                            resultArea.setText("" + result);
                        else
                            resultArea.setText("");
                    }
                    else
                        resultArea.setText("");
                    break;

                case "MOD_PP_P":
                    if (!pol1HintIsVisible && !pol2HintIsVisible
                            && pol1CorrectInput && pol2CorrectInput
                            && !pol1.getText().isEmpty() && !pol2.getText().isEmpty())
                    {
                        Polynomial result = Polynomial.MOD_PP_P(Polynomial.parsePolynomial(pol1.getText()), Polynomial.parsePolynomial(pol2.getText()));
                        if(result != null)
                            resultArea.setText("" + result);
                        else
                            resultArea.setText("");
                    }
                    else
                        resultArea.setText("");
                    break;

                case "GCF_PP_P":
                    if (!pol1HintIsVisible && !pol2HintIsVisible
                            && pol1CorrectInput && pol2CorrectInput
                            && !pol1.getText().isEmpty() && !pol2.getText().isEmpty())
                        resultArea.setText("" + Polynomial.GCF_PP_P(Polynomial.parsePolynomial(pol1.getText()), Polynomial.parsePolynomial(pol2.getText())));
                    else
                        resultArea.setText("");
                    break;

                case "DER_P_P":
                    if (!pol1HintIsVisible && pol1CorrectInput && !pol1.getText().isEmpty())
                        resultArea.setText("" + Polynomial.DER_P_P(Polynomial.parsePolynomial(pol1.getText())));
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
        Pattern fr = Pattern.compile("^(( )*(-)?( )*\\d+(( )*/( )*(0)*[1-9]\\d*)?( )*)?$");
        Pattern nat = Pattern.compile("^\\d*$");

        Matcher m;

        if(!pol1HintIsVisible)
        {
            if(checkPol(pol1.getText()))
            {
                if(!pol1CorrectInput)
                {
                    pol1.setBorder(defaultBorder);
                    pol1CorrectInput = true;
                }
            }
            else
            if(pol1CorrectInput)
            {
                pol1.setBorder(errorBorder);
                pol1CorrectInput = false;
            }
        }

        if(!pol2HintIsVisible)
        {
            if(checkPol(pol2.getText()))
            {
                if(!pol2CorrectInput)
                {
                    pol2.setBorder(defaultBorder);
                    pol2CorrectInput = true;
                }
            }
            else
            if(pol2CorrectInput)
            {
                pol2.setBorder(errorBorder);
                pol2CorrectInput = false;
            }
        }

        if(!fractionNumHintIsVisible)
        {
            m = fr.matcher(fractionNum.getText());
            if(m.matches())
            {
                if(!fractionNumCorrectInput)
                {
                    fractionNum.setBorder(defaultBorder);
                    fractionNumCorrectInput = true;
                }
            }
            else
            if(fractionNumCorrectInput)
            {
                fractionNum.setBorder(errorBorder);
                fractionNumCorrectInput = false;
            }
        }

        if(!naturalNumHintIsVisible)
        {
            m = nat.matcher(naturalNum.getText());
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

    private boolean checkPol(String str)
    {
        try {
            while (str.charAt(0) == ' ')
                str = str.substring(1, str.length());
            if(str.charAt(0) != '-' && str.charAt(0) != '+')
                str = "+" + str;
            Pattern pol = Pattern.compile("^(( )*(([-+]( )*\\d+(( )*/( )*(0)*[1-9]\\d*)?(( )*[*]?( )*[xX](( )*\\^( )*\\d+)?)?)|([-+]( )*[xX](( )*\\^( )*\\d+)?))( )*)*$");
            Matcher m = pol.matcher(str);

            return m.matches();
        }
        catch (IndexOutOfBoundsException e)
        {
            return true;
        }
    }
}
