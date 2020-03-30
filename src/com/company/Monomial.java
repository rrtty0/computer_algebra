package com.company;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Monomial implements Comparable
{
    private Fraction coef = new Fraction();
    private Natural degree = new Natural();

    public Monomial()
    {
    }

    public Monomial(Fraction coef, Natural degree) throws NullPointerException
    {
        if(coef == null || degree == null)
            throw new NullPointerException("Параметр(ы) - ссылка(и) на null");

        this.coef.setFraction(coef);
        this.degree.setNatural(degree);
    }

    public Monomial(Monomial monomial) throws NullPointerException
    {
        if(monomial == null)
            throw new NullPointerException("Параметр - ссылка на null");

        coef.setFraction(monomial.coef);
        degree.setNatural(monomial.degree);
    }

    public boolean setMonomial(Fraction coef, Natural degree)
    {
        if(coef != null && degree != null)
        {
            this.coef.setFraction(coef);
            this.degree.setNatural(degree);
            return true;
        }
        return false;
    }

    public boolean setMonomial(Monomial monomial)
    {
        if(monomial != null)
            return setMonomial(monomial.getCoef(), monomial.getDegree());
        return false;
    }

    public boolean setMonomial(String str)
    {
        try
        {
            while (str.charAt(0) == '0')
                str = str.substring(1, str.length());
            if(str.charAt(0) != '-' && str.charAt(0) != '+')
                str = "+" + str;
            Pattern p1 = Pattern.compile("^( )*[-+]( )*[xX](( )*\\^( )*\\d+)?( )*$");
            Pattern p2 = Pattern.compile("^( )*[-+]( )*\\d+(( )*/( )*(0)*[1-9]\\d*)?(( )*[*]?( )*[xX](( )*\\^( )*\\d+)?)?( )*$");
            Pattern help;
            Matcher m = p1.matcher(str);

            if (m.matches())
            {
                str = str.replaceAll(" ", "");
                if (str.charAt(0) == '-')
                    coef.setFraction("-1");
                else
                    coef.setFraction("1");
                help = Pattern.compile("\\d+");
                m = help.matcher(str);
                if (m.find())
                    degree.setNatural(str.substring(m.start(), m.end()));
                else
                    degree.setNatural("1");

                return true;
            }

            m = p2.matcher(str);

            if (m.matches())
            {
                str = str.replaceAll(" ", "");
                help = Pattern.compile("[-+]\\d+(/(0)*[1-9]\\d*)?");
                m = help.matcher(str);
                if (m.find())
                {
                    if (str.charAt(0) == '+')
                        coef.setFraction(str.substring(m.start() + 1, m.end()));
                    else
                        coef.setFraction(str.substring(m.start(), m.end()));
                    str = str.substring(m.end(), str.length());
                }

                help = Pattern.compile("\\d+");
                m = help.matcher(str);

                if (m.find())
                    degree.setNatural(str.substring(m.start(), m.end()));
                else
                {
                    help = Pattern.compile("x");
                    m = help.matcher(str);
                    if (m.find())
                        degree.setNatural("1");
                    else
                        degree.setNatural("0");
                }

                return true;
            }
        }
        catch (IndexOutOfBoundsException e)
        {
            return  false;
        }
        return false;
    }

    public boolean setMonomial(String fraction, String degree)
    {
        return setMonomial(fraction + degree);
    }

    public boolean setMonomial(String numerator, String denominator, String degree)
    {
        if(coef.setFraction(numerator, denominator))
            return this.degree.setNatural(degree);
        return false;
    }

    public boolean inputMonomial()
    {
        Scanner input = new Scanner(System.in);
        String str = input.nextLine();

        return setMonomial(str);
    }

    public void outputMonomial(boolean flag)
    {
        if(coef.getNumerator().getSign() == false && flag)
            System.out.print("+");
        System.out.print(toString());
    }

    public static Monomial parseMonomial(String str)
    {
        Monomial monomial = new Monomial();
        if(monomial.setMonomial(str))
            return monomial;

        return null;
    }

    /**
     * Проверка на равенство объектов типа Monomial
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

        Monomial other = (Monomial) obj;

        return coef.equals(other.getCoef()) && (coef.isNull() || degree.equals(other.getDegree()));
    }

    /**
     * Сравнивает объекты типа Monomial
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

        Monomial other = (Monomial) o;

        if(coef.equals(other.getCoef()) && coef.isNull())
            return 0;

        int resultOfCompare = degree.compareTo(other.getDegree());

        if(resultOfCompare != 0)
            return resultOfCompare;

        resultOfCompare = coef.compareTo(other.getCoef());

        return resultOfCompare;
    }

    /**
     * Поучение хеш-кода числа
     * @return Хеш-код числа
     */
    @Override
    public int hashCode()
    {
        return coef.hashCode() + degree.hashCode();
    }

    /**
     * Получение копии данного объекта
     * @return Копия данного объекта
     */
    @Override
    public Object clone()
    {
        Monomial monomial = new Monomial(this);

        return monomial;
    }

    /**
     * Формирует строку по данному числу
     * @return Строка, сформированная по данному числу
     */
    @Override
    public String toString()
    {
        String str = "";

        if(coef.isNull())
            return "0";

        if(coef.equals(Fraction.parseFraction("-1")))
            str = '-' + str;
        else
            if(!coef.equals(Fraction.parseFraction("1")))
                str += coef.toString();

        if(degree.isNull())
            if(str.equals("-"))
                return str + '1';
            else
                if(str.isEmpty())
                    return "1";
                else
                    return str;

        if(str.isEmpty() || str.equals("-"))
            str += "x";
        else
            str += "*x";

        if(!degree.equals(Natural.parseNatural("1")))
            str += "^" + degree.toString();

        return str;
    }

    public boolean setCoef(Fraction coef)
    {
        if(coef != null)
            return this.coef.setFraction(coef);
        return false;
    }

    public boolean setDegree(Natural degree)
    {
        if(degree != null)
            return this.degree.setNatural(degree);
        return false;
    }

    public Fraction getCoef()
    {
        return coef;
    }

    public Natural getDegree()
    {
        return degree;
    }

    public void deleteMonomial()
    {
        //coef.getNumerator().clear();
        //coef.getDenominator().clear();
        //degree.clear();
    }

    public boolean setSign(boolean sign)
    {
        return coef.getNumerator().setSign(sign);
    }

    public boolean getSign()
    {
        return coef.getNumerator().getSign();
    }

    public boolean isNull()
    {
        return coef.isNull();
    }

}
