package com.company;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.regex.Pattern;


public class ComplexPanel extends JPanel
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

    //текстовые поля ввода
    private JTextField num1;
    private JTextField num2;

    //индикатор вывода подсказки ввода и сама подсказка ввода для полей num1, num2
    boolean num1HintIsVisible;
    final String num1HintString = "Example: 1/3 - 6*i";
    boolean num2HintIsVisible;
    final String num2HintString = "Example: 1/2*i";

    //индикатор корректного ввода(здесь пустая строка тоже корректно!!!)
    boolean num1CorrectInput;
    boolean num2CorrectInput;

    //кнопки для выбора действия
    private JButton butCON_C_C;
    private JButton butADD_CC_C;
    private JButton butSUB_CC_C;
    private JButton butMUL_CC_C;
    private JButton butDIV_CC_C;


    //кнопка, которая была последней нажата
    private JButton lastPressedButton;

    //метка возле поля вывода результата(JTextArea)
    private JLabel labelResult;

    //поле вывода результата
    private JTextArea resultArea;




    public ComplexPanel()
    {
        //установка диспетчера компоновки
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);

        //дефолтный шрифт для меток
        Font fontForLabel = new Font("Arial", Font.BOLD, 15);

        //создание меток и установка им дефолтного шрифта
        labelNum1 = new JLabel("Первое комплексное число:");
        labelNum1.setFont(fontForLabel);
        labelNum2 = new JLabel("Второе комплексное число:");
        labelNum2.setFont(fontForLabel);

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

        //определение рамки текстовых полей ввода вида: "ошибка ввода"
        errorBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.RED);

        //инициализация флагов корректного ввода
        num1CorrectInput = true;
        num2CorrectInput = true;

        //создание кнопок
        butCON_C_C = new JButton(new ButtonAction("CON_C_C"));
        butCON_C_C.setPreferredSize(new Dimension(115, 27));

        butADD_CC_C = new JButton(new ButtonAction("ADD_CC_C"));
        butADD_CC_C.setPreferredSize(new Dimension(115, 27));

        butSUB_CC_C = new JButton(new ButtonAction("SUB_CC_C"));
        butSUB_CC_C.setPreferredSize(new Dimension(115, 27));

        butMUL_CC_C = new JButton(new ButtonAction("MUL_CC_C"));
        butMUL_CC_C.setPreferredSize(new Dimension(115, 27));

        butDIV_CC_C = new JButton(new ButtonAction("DIV_CC_C"));
        butDIV_CC_C.setPreferredSize(new Dimension(115, 27));

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
        add(labelNum2, new GBC(0,2).setWeight(100, 150).setInsets(15, 0, 0, 45));
        add(num2, new GBC(0,3).setWeight(100, 150).setInsets(0, 17, 15, 45));
        add(butCON_C_C, new GBC(1, 0).setWeight(100, 100).setInsets(30, 4, 7, 4));
        add(butADD_CC_C, new GBC(2, 0).setWeight(100, 100).setInsets(30, 4, 7, 4));
        add(butSUB_CC_C, new GBC(3, 0).setWeight(100, 100).setInsets(30, 4, 7, 4));
        add(butMUL_CC_C, new GBC(4, 0).setWeight(100, 100).setInsets(30, 4, 7, 4));
        add(butDIV_CC_C, new GBC(1, 1).setWeight(100, 100).setInsets(7, 4, 7, 4));
        add(labelResult, new GBC(0, 6).setWeight(100, 100).setInsets(15, 4, 4, 4).setFill(GBC.EAST));
        add(new JScrollPane(resultArea), new GBC(0, 7, 5, 1).setWeight(100, 100).setFill(GBC.BOTH));
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
                case "CON_C_C":
                    putValue(Action.SHORT_DESCRIPTION, "ПОЛУЧЕНИЕ СОПРЯЖЕННОГО ЧИСЛА");
                    break;
                case "ADD_CC_C":
                    putValue(Action.SHORT_DESCRIPTION, "СУММА ДВУХ КОМПЛЕКСНЫХ ЧИСЕЛ");
                    break;
                case "SUB_CC_C":
                    putValue(Action.SHORT_DESCRIPTION, "РАЗНОСТЬ ДВУХ КОМПЛЕКСНЫХ ЧИСЕЛ");
                    break;
                case "MUL_CC_C":
                    putValue(Action.SHORT_DESCRIPTION, "ПРОИЗВЕДЕНИЕ ДВУХ КОМПЛЕКСНЫХ ЧИСЕЛ");
                    break;
                case "DIV_CC_C":
                    putValue(Action.SHORT_DESCRIPTION, "ЧАСТНОЕ ОТ ДЕЛЕНИЯ ДВУХ КОМПЛЕКСНЫХ ЧИСЕЛ");
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
            }
            if(lastPressedButton != e.getSource())
            {
                lastPressedButton = (JButton) e.getSource();
                lastPressedButton.setBackground(Color.CYAN);
                String command = lastPressedButton.getActionCommand();
                if(command.equals("CON_C_C"))
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
                case "CON_C_C":
                    if (!num1HintIsVisible && num1CorrectInput && !num1.getText().isEmpty())
                        resultArea.setText("" + Complex.CON_C_C(Complex.parseComplex(num1.getText())));
                    else
                        resultArea.setText("");
                    break;

                case "ADD_CC_C":
                    if(!num1HintIsVisible && !num2HintIsVisible
                            && num1CorrectInput && num2CorrectInput
                            && !num1.getText().isEmpty() && !num2.getText().isEmpty())
                        resultArea.setText("" + Complex.ADD_CC_C(Complex.parseComplex(num1.getText()), Complex.parseComplex(num2.getText())));
                    else
                        resultArea.setText("");
                    break;

                case "SUB_CC_C":
                    if(!num1HintIsVisible && !num2HintIsVisible
                            && num1CorrectInput && num2CorrectInput
                            && !num1.getText().isEmpty() && !num2.getText().isEmpty())
                        resultArea.setText("" + Complex.SUB_CC_C(Complex.parseComplex(num1.getText()), Complex.parseComplex(num2.getText())));
                    else
                        resultArea.setText("");
                    break;

                case "MUL_CC_C":
                    if(!num1HintIsVisible && !num2HintIsVisible
                            && num1CorrectInput && num2CorrectInput
                            && !num1.getText().isEmpty() && !num2.getText().isEmpty())
                        resultArea.setText("" + Complex.MUL_CC_C(Complex.parseComplex(num1.getText()), Complex.parseComplex(num2.getText())));
                    else
                        resultArea.setText("");
                    break;

                case "DIV_CC_C":
                    if(!num1HintIsVisible && !num2HintIsVisible
                            && num1CorrectInput && num2CorrectInput
                            && !num1.getText().isEmpty() && !num2.getText().isEmpty())
                    {
                        Complex result = Complex.DIV_CC_C(Complex.parseComplex(num1.getText()), Complex.parseComplex(num2.getText()));
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
        Pattern p1 = Pattern.compile("^(( )*(-)?( )*\\d+(( )*/( )*(0)*[1-9]\\d*)?( )*(([-+]( )*\\d+(( )*/( )*(0)*[1-9]\\d*)?(( )*\\*)?)|([-+]))( )*i( )*)?$");
        Pattern p2 = Pattern.compile("^(( )*(-)?( )*\\d+(( )*/( )*(0)*[1-9]\\d*)?( )*)?$");
        Pattern p3 = Pattern.compile("^(( )*(((-)?( )*\\d+(( )*/( )*(0)*[1-9]\\d*)?(( )*\\*)?)|(( )*-))i( )*)?$");

        if(!num1HintIsVisible)
        {
            if(p1.matcher(num1.getText()).matches() || p2.matcher(num1.getText()).matches() || p3.matcher(num1.getText()).matches())
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
            if(p1.matcher(num2.getText()).matches() || p2.matcher(num2.getText()).matches() || p3.matcher(num2.getText()).matches())
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
    }
}
