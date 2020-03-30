package com.company;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс почти бесконечно длинных дробей
 * @version 1.01 2018-30-12
 * @author Misha
 */
public class Fraction implements Comparable
{
    /**
     * Целое число - числитель
     */
    private Integer numerator = new Integer();

    /**
     * Натуральное число - знаменатель
     */
    private Natural denominator = Natural.parseNatural("1");

    /**
     * Строит объект типа Fraction, инициализируя его нулем и знаком минус
     */
    public Fraction()
    {
    }

    /**
     * Строит объект типа Fraction, инициализируя его объектом типа Integer и Natural
     * @param numerator Объект типа Integer, которым произойдет инициализация числителя
     * @param denominator Объект типа Natural, которым произойдет инициализация знаменателя
     */
    public Fraction(Integer numerator, Natural denominator) throws NullPointerException, IllegalArgumentException
    {
        if(numerator == null || denominator == null)
            throw new NullPointerException("Параметр(ы) - ссылка(и) на null");

        if(denominator.isNull())
            throw new IllegalArgumentException("Знаменатель не может быть равен нулю");

        this.numerator.setInteger(numerator);
        if(numerator.isNull())
            this.denominator.setNatural(Natural.parseNatural("1"));
        else
            this.denominator.setNatural(denominator);

        reduction();
    }

    /**
     * Строит объект типа Fraction, инициализируя его объектом типа Fraction(конструктор копирования)
     * @param number объект, значением которого необходимо инициализировать новый объект
     */
    public Fraction(Fraction number) throws NullPointerException
    {
        if(number == null)
            throw new NullPointerException("Параметр - ссылка на null");

        numerator.setInteger(number.numerator);
        denominator.setNatural(number.denominator);
    }

    /**
     * Присваивает числителю объекта типа Fraction объект Integer, а знаменателю - объект типа Natural
     * @param numerator Объект типа Integer, которым произойдет инициализация числителя
     * @param denominator Объект типа Natural, которым произойдет инициализация знаменателя
     * @return true - инициализация прошла успешно, false - иначе
     */
    public boolean setFraction(Integer numerator, Natural denominator)
    {
        if(numerator != null && denominator != null && !denominator.isNull())
        {
            this.numerator.setInteger(numerator);
            if(numerator.isNull())
                this.denominator.setNatural("1");
            else
                this.denominator.setNatural(denominator);

            reduction();

            return true;
        }
        return false;
    }

    /**
     * Присваивает объекту типа Fraction другой объект типа Fraction
     * @param number Объект, значением которого необходимо инициализировать новый объект
     * @return true - инициализация прошла успешно, false - иначе
     */
    public boolean setFraction(Fraction number)
    {
        if(number != null)
            return setFraction(number.getNumerator(), number.getDenominator());
        return false;
    }

    /**
     * Парсинг String в Fraction
     * @param str Строка, парсинг которой надо выполнить
     * @return true - парсинг был успешно осуществлен, false - иначе
     */
    protected boolean setFraction(String str)
    {
        Pattern p = Pattern.compile("^( )*(-)?( )*\\d+(( )*/( )*(0)*[1-9]\\d*)?( )*$");
        Matcher m = p.matcher(str);

        if(m.matches())
        {
            str = str.replaceAll(" ", "");
            p = Pattern.compile("(-)?\\d+");
            m = p.matcher(str);
            m.find();
            numerator.setInteger(str.substring(m.start(), m.end()));

            if(m.find() && !numerator.isNull())
            {
                denominator.setNatural(str.substring(m.start(), m.end()));
                reduction();
            }
            else
                denominator.setNatural("1");

            return true;
        }

        System.out.println("Fraction.setFraction: Ошибка ввода!");

        return false;
    }

    /**
     * Парсинг Двух объектов типа String в Fraction
     * @param numerator Числитель, представленный в типе String
     * @param denominator Знаменатель, представленный в типе String
     * @return true - парсинг был успешно осуществлен, false - иначе
     */
    public boolean setFraction(String numerator, String denominator)
    {
        return setFraction(numerator + '/' + denominator);
    }

    /**
     * Считывает с клавиатуры строку, которую впоследствии записывается в объект типа Fraction
     * @return true - если объект успешно введен, false - если объект не успешно введен
     */
    public boolean inputFraction()
    {
        Scanner input = new Scanner(System.in);
        String str = input.nextLine();

        return setFraction(str);
    }

    /**
     * Вывод дроби на экран
     */
    public void outputFraction()
    {
        System.out.print(toString());
    }

    /**
     * Парсер строки в объект типа Fraction
     * @param str Строка, парсинг которой необходимо выполнить
     * @return newNumber Если строка содержит корректные символы - объект типа Fraction, иначе null
     */
    public static Fraction parseFraction(String str)
    {
        Fraction fraction = new Fraction();
        if(fraction.setFraction(str))
            return fraction;

        return null;
    }

    /**
     * Проверка на равенство объектов типа Fraction
     * @param obj Объект, с которым проходит проверка на равенство
     * @return Значение true - объекты равны, false - не равны
     */
    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;

        if(obj == null)
            return false;

        if(getClass() != obj.getClass())
            return false;

        Fraction other = (Fraction) obj;

        Natural nok = Natural.LCM_NN_N(denominator, other.getDenominator());

        return Integer.MUL_ZZ_Z(numerator, new Integer(Natural.DIV_NN_N(nok, denominator), false))
                .equals(Integer.MUL_ZZ_Z(other.getNumerator(), new Integer(Natural.DIV_NN_N(nok, other.getDenominator()), false)));
    }

    /**
     * Сравнивает объекты типа Fraction
     * @param o Объект, с которым необходимо выполнить сравнение
     * @return 1 - вызывающий объект больше, 0 - равны, -1 - иначе
     */
    @Override
    public int compareTo(Object o)
    {
        if(this == o)
            return 0;

        if(o == null)
            return 2;

        if(getClass() != o.getClass())
            return 2;

        Fraction other = (Fraction) o;

        Natural nod = Natural.GCF_NN_N(denominator, other.getDenominator());

        return Integer.MUL_ZZ_Z(numerator, new Integer(nod, false)).compareTo(Integer.MUL_ZZ_Z(other.getNumerator(), new Integer(nod, false)));
    }

    /**
     * Поучение хеш-кода числа
     * @return Хеш-код числа
     */
    @Override
    public int hashCode()
    {
        return numerator.hashCode() + denominator.hashCode();
    }

    /**
     * Получение копии данного объекта
     * @return Копия данного объекта
     */
    @Override
    public Object clone()
    {
        Fraction newNumber = new Fraction(this);

        return newNumber;
    }

    /**
     * Формирует строку по данному числу
     * @return Строка, сформированная по данному числу
     */
    @Override
    public String toString()
    {
        String str = numerator.toString();

        if(!denominator.equals(Natural.parseNatural("1")) && !numerator.isNull())
            str += "/" + denominator.toString();

        return str;
    }

    /**
     * Устанавливает значение числителя
     * @param numerator Объект, которым инициализируется числитель
     */
    public boolean setNumerator(Integer numerator)
    {
        if(numerator != null && numerator.isNull())
            this.denominator.setNatural(Natural.parseNatural("1"));

        this.numerator.setInteger(numerator);

        reduction();

        return true;
    }

    /**
     * Устанавливает значение знаменателя
     * @param denominator Объект, которым инициализируется знаменатель
     */
    public boolean setDenominator(Natural denominator)
    {
        if(denominator != null && denominator.isNull())
            return false;

        if(numerator.isNull())
            return false;

        this.denominator.setNatural(denominator);

        reduction();

        return true;
    }

    /**
     * Получение числителя дроби
     * @return Числитель дроби - тип Integer
     */
    public Integer getNumerator()
    {
        return this.numerator;
    }

    /**
     * Получение знаменателя дроби
     * @return Знаменатель дроби - тип Natural
     */
    public Natural getDenominator()
    {
        return this.denominator;
    }

    /**
     * Устанавливает знак дроби
     * @param sign Знак дроби: false - плюс, true - минус
     */
    public boolean setSign(boolean sign)
    {
        return this.numerator.setSign(sign);
    }

    /**
     * Получение знака дроби
     * @return Знак дроби: false - плюс, true - минус
     */
    public boolean getSign()
    {
        return this.numerator.getSign();
    }

    /**
     * Проверка на равенство дроби нулю
     * @return true - дробь равна нулю, false - иначе
     */
    public boolean isNull()
    {
        return numerator.isNull();
    }

    /**
     * Сокращение дроби
     */
    private void reduction()
    {
        Natural nod = new Natural();
        nod.setNatural(Natural.GCF_NN_N(numerator, denominator));
        numerator.setInteger(new Integer(Natural.DIV_NN_N(numerator, nod), getSign()));
        denominator.setNatural(Natural.DIV_NN_N(denominator, nod));
    }





    //МАТЕМАТИКА ДЛЯ ДРОБНЫХ ЧИСЕЛ


    /**
     * Сокращение дроби
     * @param fraction Дробь, которую необходимо сократить
     * @return Сокращенная дробь
     */
    public static Fraction RED_Q_Q(Fraction fraction)
    {
        if(fraction == null)
            return null;

        fraction.reduction();

        return fraction;
    }

    /**
     * Проверка: является ли число целым числом
     * @param fraction Дробь, которую необходимо проверить
     * @return true - дробь является целым числом, false - иначе
     */
    public static boolean INT_Q_B(Fraction fraction) throws NullPointerException
    {
        if(fraction == null)
            throw new NullPointerException("Параметр - ссылка на null");

        Fraction aFraction = new Fraction(fraction);
        aFraction.reduction();
        if(aFraction.getDenominator().equals(Natural.parseNatural("1")))
            return true;

        return false;
    }

    /**
     * Конвертация целого числа в дробное
     * @param num Целое число
     * @return Сформированное дробное число
     */
    public static Fraction TRANS_Z_Q(Integer num)
    {
        if(num == null)
            return null;

        Fraction fraction = new Fraction(num, Natural.parseNatural("1"));

        return fraction;
    }

    /**
     * Получение целой части дроби
     * @param fraction Дробь, целую часть которой необходимо выделить
     * @return Целая часть дроби
     */
    public static Integer TRANS_Q_Z(Fraction fraction)
    {
        if(fraction == null)
            return null;

        return new Integer(Natural.DIV_NN_N(fraction.getNumerator(), fraction.getDenominator()), fraction.getSign());
    }

    /**
     * Сложение двух дробей
     * @param fr1 Первое слагаемое
     * @param fr2 Второе слагаемое
     * @return Сумма дробей
     */
    public static Fraction ADD_QQ_Q(Fraction fr1, Fraction fr2)
    {
        if(fr1 == null || fr2 == null)
            return null;

        Fraction this_fr1 = new Fraction(fr1);
        Fraction this_fr2 = new Fraction(fr2);

        Natural nok = new Natural(Natural.LCM_NN_N(this_fr1.getDenominator(), this_fr2.getDenominator()));
        this_fr1.getNumerator().setInteger(Natural.MUL_NN_N(Natural.DIV_NN_N(nok, this_fr1.getDenominator()), this_fr1.getNumerator()), this_fr1.getSign());
        this_fr2.getNumerator().setInteger(Natural.MUL_NN_N(Natural.DIV_NN_N(nok, this_fr2.getDenominator()), this_fr2.getNumerator()), this_fr2.getSign());
        Fraction result = new Fraction(Integer.ADD_ZZ_Z(this_fr1.getNumerator(), this_fr2.getNumerator()), nok);
        result.reduction();

        return result;
    }

    /**
     * Вычитание двух дробей
     * @param fr1 Дробь, из которой вычитают
     * @param fr2 Дробь, которую вычитают
     * @return Разность дробей
     */
    public static Fraction SUB_QQ_Q(Fraction fr1, Fraction fr2)
    {
        if(fr1 == null || fr2 == null)
            return null;

        Fraction this_fr2 = new Fraction(fr2);
        this_fr2.setSign(!this_fr2.getSign());
        return ADD_QQ_Q(fr1, this_fr2);
    }

    /**
     * Умножение дробных чисел
     * @param fr1 Дробь - первый сомножитель
     * @param fr2 Дробь - второй сомножитель
     * @return Произведение дробных чисел
     */
    public static Fraction MUL_QQ_Q(Fraction fr1, Fraction fr2)
    {
        if(fr1 == null || fr2 == null)
            return null;

        Fraction result = new Fraction(Integer.MUL_ZZ_Z(fr1.getNumerator(), fr2.getNumerator()), Integer.MUL_NN_N(fr1.getDenominator(), fr2.getDenominator()));
        result.reduction();
        return result;
    }

    /**
     * Деление дробных чисел
     * @param fr1 Дробь - делимое
     * @param fr2 Дробь - делитель
     * @return Частное от деления
     */
    public static Fraction DIV_QQ_Q(Fraction fr1, Fraction fr2)
    {
        if(fr1 == null || fr2 == null || fr2.isNull())
            return null;

        Fraction result = new Fraction(new Integer(Natural.MUL_NN_N(fr1.getNumerator(), fr2.getDenominator()), !(fr1.getSign() == fr2.getSign())), Natural.MUL_NN_N(fr1.getDenominator(), fr2.getNumerator()));
        result.reduction();
        return result;
    }

}