package com.company;

import java.util.*;

/**
 * Класс почти бесконечно длинных целых чисел
 * @version 1.01 2018-30-12
 * @author Misha
 */
public class Integer extends Natural implements Comparable
{
    /**
     * Знак числа: false - плюс, true - минус
     */
    private Boolean sign;


    /**
     * Строит объект типа Integer, инициализируя его нулем и знаком минус
     */
    public Integer()
    {
        sign = false;
    }

    /**
     * Строит объект типа Integer, инициализируя его объектом типа Natural и знаком
     * @param number Объект типа Natural, которым произойдет инициализация
     * @param sign Знак, которым произойдет инициализация
     */
    public Integer(Natural number, boolean sign)
    {
        super(number);

        if(number.isNull())
            this.sign = false;
        else
            this.sign = sign;
    }

    /**
     * Строит объект типа Integer, инициализируя его объектом типа Integer(конструктор копирования)
     * @param number объект, значением которого необходимо инициализировать новый объект
     */
    public Integer(Integer number)
    {
        super(number);

        if(number == null)
            sign = false;
        else
            sign = number.getSign();
    }

    /**
     * Присваивает объекту типа Integer объект типа Natural с указанным знаком
     * @param number Объект типа Natural, которым произойдет инициализация
     * @param sign Знак, которым произойдет инициализация
     * @return true - инициализация прошла успешно, false - иначе
     */
    public boolean setInteger(Natural number, boolean sign)
    {
        if(super.setNatural(number))
        {
            if(number.isNull())
                this.sign = false;
            else
                this.sign = sign;

            return true;
        }

        return false;
    }

    /**
     * Присваивает объекту типа Integer другой объект типа Integer
     * @param number Объект, значением которого необходимо инициализировать новый объект
     * @return true - инициализация прошла успешно, false - иначе
     */
    public boolean setInteger(Integer number)
    {
        if(number != null)
            return setInteger(number, number.getSign());
        return false;
    }

    /**
     * Парсинг String в Integer
     * @param str Строка, парсинг которой необходимо выполнить
     * @return true - парсинг был успешно осуществлен, false - иначе
     */
    public boolean setInteger(String str)
    {
        try
        {
            if (str.charAt(0) == '-')
            {
                Natural digits = parseNatural(str.substring(1, str.length()));
                if (digits != null)
                {
                    setNatural(digits);
                    sign = !isNull();
                    return true;
                }
            }
            else
            {
                if (setNatural(str))
                {
                    sign = false;
                    return true;
                }
            }
        }
        catch (IndexOutOfBoundsException e)
        {
            System.out.println("Integer: setInteger: ошибка ввода!");
            return false;
        }

        System.out.println("Integer: setInteger: ошибка ввода!");

        return false;
    }

    /**
     * Считывает с клавиатуры строку, которую впоследствии записывается в объект типа Integer
     * @return true - если объект успешно введен, false - если объект не успешно введен
     */
    public boolean inputInteger()
    {
        Scanner input = new Scanner(System.in);
        String str = input.nextLine();
        return setInteger(str);
    }

    /**
     * Вывод на экран целого числа
     */
    public void outputInteger()
    {
        System.out.print(toString());
    }

    /**
     * Парсер строки в объект типа Integer
     * @param str Строка, парсинг которой необходимо выполнить
     * @return newNumber Если строка содержит корректные символы - объект типа Integer, иначе null
     */
    public static Integer parseInteger(String str)
    {
        Integer newNumber = new Integer();
        if (newNumber.setInteger(str))
            return newNumber;

        return null;
    }

    /**
     * Проверка на равенство объектов типа Integer
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

        Integer other = (Integer) obj;

        return super.equals(other) && (sign == other.getSign());
    }

    /**
     * Сравнивает объекты типа Integer
     * @param o Объект, с которым необходимо выполнить сравнение
     * @return 1 - вызывающий объект больше, 0 - равны, -1 - иначе, 2 - ошибка
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

        Integer other = (Integer) o;

        if(!sign && other.getSign())
            return 1;

        if(sign && !other.getSign())
            return -1;

        int naturalCompareTo = super.compareTo(other);

        if(!sign)
            return naturalCompareTo;

        if(naturalCompareTo == 1)
            return -1;

        if(naturalCompareTo == -1)
            return 1;

        return 0;
    }

    /**
     * Поучение хеш-кода числа
     * @return Хеш-код числа
     */
    @Override
    public int hashCode()
    {
        return super.hashCode() + sign.hashCode();
    }

    /**
     * Получение копии данного объекта
     * @return Копия данного объекта
     */
    @Override
    public Object clone()
    {
        Integer newNumber = new Integer();
        newNumber.setInteger(this);

        return newNumber;
    }

    /**
     * Формирует строку по данному числу
     * @return Строка, сформированная по данному числу
     */
    @Override
    public String toString()
    {
        String str = super.toString();
        if(sign)
            str = '-' + str;
        return str;
    }

    /**
     * Устанавливает по заданному индексу заданную цифру
     * @param index Индекс, по которому необходимо установить заданную цифру
     * @param element Цифра, которую необходимо уставновить по заданному индексу
     * @return true - если значение установлено успешно, иначе - false
     */
    @Override
    public boolean set(int index, int element)
    {
        boolean result = super.set(index, element);

        if(result && isNull())
            sign = false;

        return result;

    }

    /**
     * Устанавливает знак целого числа
     * @param sign Знак числа: false - плюс, true - минус
     */
    public boolean setSign(boolean sign)
    {
        if(!isNull())
            this.sign = sign;

        return !isNull();
    }

    /**
     * Получение знака целого числа
     * @return Знак числа: false - плюс, true - минус
     */
    public boolean getSign()
    {
        return sign;
    }




    //МАТЕМАТИКА ДЛЯ ЦЕЛЫХ ЧИСЕЛ


    /**
     * Модуль целого числа, результат - натуральное
     * @param num Целое число
     * @return Модуль num
     */
    public static Natural ABS_Z_N(Integer num)
    {
        if(num == null)
            return null;

        return new Natural(num);
    }

    /**
     * Получение знака числа
     * @param num Целое число, знак которого необходимо получить
     * @return 0 - число равно нулю, 1 - положительное, 2 - иначе, -1 - ошибка
     */
    public static int POZ_Z_D(Integer num)
    {
        if(num == null)
            return -1;

        if(num.isNull())
            return 0;

        if(num.getSign())
            return 2;

        return 1;
    }

    /**
     * Смена знака целого числа
     * @param num Число, знак которого необходимо поменять
     * @return Число, с измененным знаком
     */
    public static Integer MUL_ZM_Z(Integer num)
    {
        if(num == null)
            return null;

        num.setSign(!num.getSign());

        return num;
    }

    /**
     * Конвертация натцрального в целое
     * @param naturalNum Натуральное число, конвертацию которого необходимо выполнить
     * @return Целое число со знаком +, полученное по натуральному числу
     */
    public static Integer TRANS_N_Z(Natural naturalNum)
    {
        if(naturalNum == null)
            return null;

        Integer integerNum = new Integer();
        integerNum.setNatural(naturalNum);

        return integerNum;
    }

    /**
     * Конвертация целого в натуральное(с потерей знака целого числа)
     * @param integerNum Целое число, конвертацию которого необходимо выполнить
     * @return Натуральное число, полученное по целому числу
     */
    public static Natural TRANS_Z_N(Integer integerNum)
    {
        if(integerNum == null)
            return null;

        return ABS_Z_N(integerNum);
    }

    /**
     * Сложение двух целых чисел
     * @param num1 Первое слагаемое
     * @param num2 Второе слагаемое
     * @return Сумма двух целых чисел
     */
    public static Integer ADD_ZZ_Z(Integer num1, Integer num2)
    {
        if(num1 == null || num2 == null)
            return null;

        Integer result = new Integer();

        if(num1.getSign() == num2.getSign())
            result.setInteger(Natural.ADD_NN_N(TRANS_Z_N(num1), TRANS_Z_N(num2)), num1.getSign());
        else
        {
            int flag = Natural.COM_NN_D(TRANS_Z_N(num1), TRANS_Z_N(num2));

            if(flag == 2)
            {
                result.setInteger(Natural.SUB_NN_N(TRANS_Z_N(num1), TRANS_Z_N(num2)), num1.getSign());
            }
            else
                if(flag == 1)
                    result.setInteger(Natural.SUB_NN_N(TRANS_Z_N(num2), TRANS_Z_N(num1)), num2.getSign());
        }

        return result;
    }

    /**
     * Разность двух целых чисел
     * @param num1 Число, из которого вычетают
     * @param num2 Число, которое вычетают
     * @return Разность num1 и num2
     */
    public static Integer SUB_ZZ_Z(Integer num1, Integer num2)
    {
        if(num1 == null || num2 == null)
            return null;

        Integer aNum2 = new Integer(num2);
        aNum2.setSign(!aNum2.getSign());
        return new Integer(ADD_ZZ_Z(num1, aNum2));
    }

    /**
     * Умножение двух целых чисел
     * @param num1 Первый сомножитель
     * @param num2 Второй сомножитель
     * @return Произведение num1 и num2
     */
    public static Integer MUL_ZZ_Z(Integer num1, Integer num2)
    {
        if(num1 == null || num2 == null)
            return null;

        Integer result = new Integer(MUL_NN_N(TRANS_Z_N(num1), TRANS_Z_N(num2)), !(num1.getSign() == num2.getSign()));

        return result;
    }

    /**
     * Деление двух целых чисел
     * @param num1 Делимое
     * @param num2 Делитель
     * @return Частное от деление num1 на num2
     */
    //разобраться с отрицательными делимыми
    public static Integer DIV_ZZ_Z(Integer num1, Integer num2)
    {
        if(num1 == null || num2 == null || num2.isNull())
            return null;

        Integer result = new Integer(DIV_NN_N(TRANS_Z_N(num1), TRANS_Z_N(num2)), !(num1.getSign() == num2.getSign()));

        return result;
    }

    //???? остаток ведь не может быть отрицательным
    public static Integer MOD_ZZ_Z(Integer num1, Integer num2)
    {
        if(num1 == null || num2 == null || num2.isNull())
            return null;

        return new Integer(MOD_NN_N(TRANS_Z_N(num1), TRANS_Z_N(num2)), !(num1.getSign() == num2.getSign()));
    }

}