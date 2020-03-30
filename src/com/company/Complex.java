package com.company;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Complex implements Comparable
{
    private Fraction real = new Fraction();
    private Fraction imaginary = new Fraction();

    public Complex()
    {
    }

    public Complex(Fraction real, Fraction imaginary) throws NullPointerException
    {
        if(real == null || imaginary == null)
            throw new NullPointerException("Параметр(ы) - ссылка(и) на null");

        this.real.setFraction(real);
        this.imaginary.setFraction(imaginary);
    }

    public Complex(Complex complex)
    {
        if(complex == null)
            throw new NullPointerException("Параметр - ссылка на null");

        real.setFraction(complex.getReal());
        imaginary.setFraction(complex.getImaginary());
    }

    public boolean setComplex(Fraction real, Fraction imaginary)
    {
        if(real == null || imaginary == null)
            return false;

        this.real.setFraction(real);
        this.imaginary.setFraction(imaginary);
        return true;
    }

    public boolean setComplex(Complex complex)
    {
        if(complex == null)
            return false;

        real.setFraction(complex.real);
        imaginary.setFraction(complex.imaginary);
        return true;
    }

    public boolean setComplex(String str)
    {
        Pattern p1 = Pattern.compile("^( )*(-)?( )*\\d+(( )*/( )*(0)*[1-9]\\d*)?( )*(([-+]( )*\\d+(( )*/( )*(0)*[1-9]\\d*)?(( )*\\*)?)|([-+]))( )*i( )*$");
        Pattern p2 = Pattern.compile("^( )*(-)?( )*\\d+(( )*/( )*(0)*[1-9]\\d*)?( )*$");
        Pattern p3 = Pattern.compile("^( )*(((-)?( )*\\d+(( )*/( )*(0)*[1-9]\\d*)?(( )*\\*)?)|(( )*-))i( )*$");
        Pattern help;

        Matcher m = p1.matcher(str);

        if(m.matches())
        {
            str = str.replaceAll(" ", "");
            help = Pattern.compile("(-)?\\d+(/(0)*[1-9]\\d*)?");
            m = help.matcher(str);
            m.find();
            real.setFraction(str.substring(m.start(), m.end()));
            if(m.find())
                imaginary.setFraction(str.substring(m.start(), m.end()));
            else
            {
                if(str.charAt(str.indexOf('i') - 1) == '-')
                    imaginary.setFraction("-1");
                else
                    imaginary.setFraction("1");
            }

            return true;
        }

        m = p2.matcher(str);

        if(m.matches())
        {
            str = str.replaceAll(" ", "");
            real.setFraction(str.substring(0, str.length()));
            imaginary.setFraction("0");
            return true;
        }

        m = p3.matcher(str);

        if(m.matches())
        {
            str = str.replaceAll(" ", "");
            help = Pattern.compile("(-)?\\d+(/(0)*[1-9]\\d*)?");
            m = help.matcher(str);
            if(m.find())
                imaginary.setFraction(str.substring(m.start(), m.end()));
            real.setFraction("0");
            return true;
        }

        System.out.println("setComplex: Ошибка ввода!");
        return false;
    }

    public boolean inputComplex()
    {
        Scanner input = new Scanner(System.in);
        String str = input.nextLine();

        return setComplex(str);
    }

    public void outputComplex()
    {
        System.out.print(toString());
    }

    public static Complex parseComplex(String str)
    {
        Complex complex = new Complex();
        if(complex.setComplex(str))
            return complex;

        return null;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
        return true;

        if(obj == null)
            return false;

        if(getClass() != obj.getClass())
            return false;

        Complex other = (Complex) obj;

        return (real.equals(other.getReal()) && imaginary.equals(other.getImaginary()));
    }

    @Override
    public int compareTo(Object o)
    {
        if(this == o)
            return 0;

        if(o == null)
            return 2;

        if(getClass() != o.getClass())
            return 2;

        Complex other = (Complex) o;

        return Fraction.ADD_QQ_Q(Fraction.MUL_QQ_Q(real, real), Fraction.MUL_QQ_Q(imaginary, imaginary)).compareTo(Fraction.ADD_QQ_Q(Fraction.MUL_QQ_Q(other.getReal(), other.getReal()), Fraction.MUL_QQ_Q(other.getImaginary(), other.getImaginary())));
    }

    @Override
    public int hashCode()
    {
        return real.hashCode() + imaginary.hashCode();
    }

    @Override
    public Object clone()
    {
        Complex complex = new Complex(this);

        return complex;
    }

    @Override
    public String toString()
    {
        String imagin;

        if(isNull())
            return "0";
        else
        {
            if(imaginary.isNull())
                return real.toString();
            else
            {
                if(imaginary.toString().equals("-1"))
                    imagin = "-i";
                else
                if(imaginary.toString().equals("1"))
                    imagin = "i";
                else
                    imagin = imaginary.toString() + "*i";

                if(real.isNull())
                    return imagin;

                if(imagin.charAt(0) != '-')
                    return real.toString() + '+' + imagin;

                return real.toString() + imagin;
            }
        }
    }

    public void setReal(Fraction real)
    {
        this.real.setFraction(real);
    }

    public void setImaginary(Fraction imaginary)
    {
        this.imaginary.setFraction(imaginary);
    }

    public Fraction getReal()
    {
        return this.real;
    }

    public Fraction getImaginary()
    {
        return this.imaginary;
    }

    public boolean isNull()
    {
        if(real.isNull() && imaginary.isNull())
            return true;
        return false;
    }




    //МАТЕМАТИКА ДЛЯ КОМПЛЕКСНЫХ ЧИСЕЛ


    public static Complex CON_C_C(Complex num)
    {
        if(num == null)
            return null;

        Complex conjugate = new Complex(num);

        if(!num.isNull())
            conjugate.getImaginary().setSign(!conjugate.getImaginary().getSign());

        return conjugate;
    }

    public static Complex ADD_CC_C(Complex num1, Complex num2)
    {
        if(num1 == null || num2 == null)
            return null;

        Complex result = new Complex();

        result.setReal(Fraction.ADD_QQ_Q(num1.getReal(), num2.getReal()));
        result.setImaginary(Fraction.ADD_QQ_Q(num1.getImaginary(), num2.getImaginary()));

        return result;
    }

    public static Complex SUB_CC_C(Complex num1, Complex num2)
    {
        if(num1 == null || num2 == null)
            return null;

        Complex aNum2 = new Complex(num2);

        aNum2.getReal().setSign(!aNum2.getReal().getSign());
        aNum2.getImaginary().setSign(!aNum2.getImaginary().getSign());

        return new Complex(ADD_CC_C(num1, aNum2));
    }

    public static Complex MUL_CC_C(Complex num1, Complex num2)
    {
        if(num1 == null || num2 == null)
            return null;

        Complex result = new Complex();

        result.setReal(Fraction.SUB_QQ_Q(Fraction.MUL_QQ_Q(num1.getReal(), num2.getReal()), Fraction.MUL_QQ_Q(num1.getImaginary(), num2.getImaginary())));
        result.setImaginary(Fraction.ADD_QQ_Q(Fraction.MUL_QQ_Q(num1.getImaginary(), num2.getReal()), Fraction.MUL_QQ_Q(num1.getReal(), num2.getImaginary())));

        return result;
    }

    public static Complex DIV_CC_C(Complex num1, Complex num2)
    {
        if(num1 == null || num2 == null || num2.isNull())
            return null;

        Complex result = new Complex();
        Complex aNum1 = new Complex(num1);
        Complex aNum2 = new Complex(num2);

        aNum1.setComplex(MUL_CC_C(aNum1, CON_C_C(aNum2)));
        aNum2.setComplex(MUL_CC_C(aNum2, CON_C_C(aNum2)));

        result.setReal(Fraction.DIV_QQ_Q(aNum1.getReal(), aNum2.getReal()));
        result.setImaginary(Fraction.DIV_QQ_Q(aNum1.getImaginary(), aNum2.getReal()));

        return result;
    }

}