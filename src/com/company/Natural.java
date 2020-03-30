package com.company;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс почти бесконечно длинных натуральных чисел(и ноль)
 * @version 1.01 2018-31-10
 * @author Misha
 */
public class Natural implements Comparable{

    /**
     * Массив из элеменотв типа Byte
     */
    private ArrayList<Byte> array = new ArrayList<>();


    /**
     * Строит объект типа Natural, инициализируя его нулем
     */
    public Natural()
    {
        array.add((byte)0);
    }

    /**
     * Строит объект типа Natural, инициализируя его объектом типа Natural(конструктор копирования)
     * @param number объект, значением которого необходимо инициализировать новый объект
     */
    public Natural(Natural number) throws NullPointerException
    {
        if(number == null)
            throw new NullPointerException("Параметр - ссылка на null");
        else
            array.addAll(number.array);
    }

    /**
     * Присваивает объекту типа Natural другой объект типа Natural
     * @param number Объект, значением которого необходимо инициализировать новый объект
     * @return true - инициализация прошла успешно, false - иначе
     */
    public boolean setNatural(Natural number)
    {
        if(number == null)
            return false;

        array.clear();
        array.addAll(number.array);

        return true;
    }

    /**
     * Парсинг String в Natural
     * @param str Строка, парсинг которой необходимо выполнить
     * @return true - парсинг был успешно осуществлен, false - иначе
     */
    public boolean setNatural(String str)
    {
        Pattern p = Pattern.compile("^\\d+$");
        Matcher m = p.matcher(str);
        if(m.matches())
        {
            array.clear();
            for(int i = str.length() - 1; i >= 0; i--)
                array.add((byte)(str.codePointAt(i) - 48));
            trimNulls();

            return true;
        }
        else
        {
            System.out.println("Natural: setNatural(String): Ошибка ввода!");

            return false;
        }
    }

    /**
     * Считывает с клавиатуры строку, которую впоследствии записывается в объект типа Natural
     * @return true - если объект успешно введен, false - если объект не успешно введен
     */
    public boolean inputNatural()
    {
        Scanner input = new Scanner(System.in);
        String str = input.nextLine();

        return setNatural(str);
    }

    /**
     * Вывод на экран натурального числа
     */
    public void outputNatural()
    {
        System.out.print(toString());
    }

    /**
     * Парсер строки в объект типа Natural
     * @param str Строка, парсинг которой необходимо выполнить
     * @return newNumber Если строка содержит корректные символы - объект типа Natural, иначе null
     */
    public static Natural parseNatural(String str)
    {
        Natural newNumber = new Natural();
        if(newNumber.setNatural(str))
            return newNumber;

        return null;
    }

    /**
     * Проверка на равенство объектов типа Natural
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

        Natural other = (Natural)obj;

        return array.equals(other.array);
    }

    /**
     * Сравнивает объекты типа Natural
     * @param o Объект, с которым необходимо выполнить сравнение
     * @return 1 - вызывающий объект больше, 0 - равны, -1 - иначе
     */
    @Override
    public int compareTo(Object o) throws NullPointerException, IllegalArgumentException
    {
        if(this == o)
            return 0;

        if(o == null)
            throw new NullPointerException();

        if(getClass() != o.getClass())
            throw new IllegalArgumentException();

        Natural other = (Natural)o;

        if(lastIndex() > other.lastIndex())
            return 1;

        if(lastIndex() < other.lastIndex())
            return -1;

        int lastIndex = lastIndex();

        for(int i = lastIndex; i >= 0; --i)
            if(array.get(i) > other.array.get(i))
                return 1;
            else
                if(array.get(i) < other.array.get(i))
                    return -1;

        return 0;
    }

    /**
     * Поучение хеш-кода числа
     * @return Хеш-код числа
     */
    @Override
    public int hashCode()
    {
        return array.hashCode();
    }


    /**
     * Получение копии данного объекта
     * @return Копия данного объекта
     */
    @Override
    public Object clone()
    {
        Natural newNumber = new Natural();
        newNumber.array.clear();
        newNumber.array.addAll(array);

        return newNumber;
    }

    /**
     * Формирует строку по данному числу
     * @return Строка, сформированная по данному числу
     */
    @Override
    public String toString()
    {
        String str = "";
        int size = array.size() - 1;
        for(int i = size; i >= 0 ; --i)
            str += array.get(i).toString();

        return str;
    }

    /**
     * Устанавливает по заданному индексу заданную цифру
     * @param index Индекс, по которому необходимо установить заданную цифру
     * @param element Цифра, которую необходимо уставновить по заданному индексу
     * @return true - если значение установлено успешно, иначе - false
     */
    public boolean set(int index, int element)
    {
        try
        {
            if(0 <= element && element <= 9)
            {
                if (index > lastIndex())
                {
                    while (index > lastIndex() + 1)
                        array.add((byte) 0);
                    array.add((byte) element);
                }
                else
                    array.set(index, (byte) element);
            }
            else
                return false;
        }
        catch (IndexOutOfBoundsException e)
        {
            return false;
        }
        trimNulls();

        return true;
    }

    /**
     * Получение цифры по заданному индексу
     * @param index Индекс цифры
     * @return Цифра, находящаяся в данном числе по индексу index
     */
    public int get(int index)
    {
        if(index > lastIndex())
            System.out.println("Natural get:\nОшибка! Слишком большой индекс!");
        else
            if(index < 0)
                System.out.println("get:\nОшибка! Индекс не может быть отрицательным!");
            else
                return array.get(index);

        return -1;
    }

    /**
     * Добавление старшего разряда
     * @param element Цифра, которая добавляется в старший разряд
     * @return true - цифра успешно добавлена, false - не добавлена
     */
    public boolean add(int element)
    {
        if(0 <= element && element <= 9)
        {
            if (element != 0)
                array.add((byte) element);
            return true;
        }

        return false;
    }

    /**
     * Добавление цифры по заданному индексу
     * @param index Индекс, по которому нужно добавить цифру
     * @param element Цифра, которую необходимо вставить по заданному индексу
     * @return true - цифра успешно добавлена, false - не добавлена
     */
    public boolean add(int index, int element)
    {
        if (0 <= element && element <= 9)
        {
            if(index <= lastIndex())
                array.add(index, (byte)element);
            else
                return set(index, element);

            trimNulls();

            return true;
        }

        return false;
    }

    /**
     * Получение кол-ва разрядов числа
     * @return Число разрядов числа
     */
    public int size()
    {
        return array.size();
    }

    /**
     * Получение индекса старшего разряда числа
     * @return Индекс старшего разряда числа
     */
    public int lastIndex()
    {
        return array.size() - 1;
    }

    /**
     * Проверка на равенство числа нулю
     * @return true - число равно нулю, false - иначе
     */
    public boolean isNull()
    {
        return (array.size() == 1 && array.get(0) == 0);
    }

    /**
     * Удаление лишних нулей в начале числа
     */
    protected void trimNulls()
    {
        while(lastIndex() > 0 && array.get(lastIndex()) == 0)
            array.remove(lastIndex());
    }




    //МАТЕМАТИКА ДЛЯ НАТУРАЛЬНЫХ ЧИСЕЛ


    /**
     * Сравнивает два объекта типа Natural
     * @param num1 Первый объект типа Natural
     * @param num2 Второй объект типа Natural
     * @return 0 - если объекты равны, 1 - если num2 больше num1,
     * 2 - если num1 больше num2, -1 - если один(оба) из аргументов null
     */
    public static int COM_NN_D(Natural num1, Natural num2)
    {
        if(num1 == null || num2 == null)
            return -1;

        int result = num1.compareTo(num2);

        if(result == 1)
            return 2;

        if(result == -1)
            return 1;

        return 0;
    }

    /**
     * Проверка объекта типа Natural на неравность нулю
     * @param num Объект типа Natural
     * @return true - если объект не равен нулю, false - если равен нулю
     */
    public static boolean NZER_N_B(Natural num) throws NullPointerException
    {
        if(num == null)
            throw new NullPointerException("Параметр - ссылка на null");

        if(num.isNull())
            return false;

        return true;
    }

    /**
     * Прибавляет к объекту типа Natural единицу
     * @param num Число, к которому необъодимо пребавить единицу
     * @return Сумма num и единицы
     */
    public static Natural ADD_1N_N(Natural num)
    {
        if(num == null)
            return null;

        int overflow = 0;
        int index = 0;
        Natural result = new Natural(num);

        do
        {
            overflow = (result.get(index) + 1) / 10;
            result.set(index, (result.get(index) + 1) % 10);
            index++;
        }
        while (overflow != 0 && index <= result.lastIndex());

        if(overflow != 0)
            result.add(index, overflow);

        return result;
    }

    /**
     * Сложение двух объектов типа Natural
     * @param num1 Первое слагаемое
     * @param num2 Второе слагаемое
     * @return Сумма двух объектов
     */
    public static Natural ADD_NN_N(Natural num1, Natural num2)
    {
        if(num1 == null || num2 == null)
            return null;

        int overflow = 0;
        int i = 0;
        Natural result = new Natural(num1);
        int minLastIndex = num1.lastIndex();


        if(MyMath.COM_NN_D(num1, num2) == 2)
        {
            minLastIndex = num2.lastIndex();
            result.setNatural(num1);
        }
        else
        {
            minLastIndex = num1.lastIndex();
            result.setNatural(num2);
        }

        while (i <= minLastIndex)
        {
            result.set(i, (num1.get(i) + num2.get(i) + overflow) % 10);
            overflow = (num1.get(i) + num2.get(i) + overflow) / 10;
            ++i;
        }

        while (i <= result.lastIndex() && overflow != 0)
        {
            if((result.get(i) + overflow) / 10 == 0)
            {
                result.set(i, result.get(i) + overflow);
                overflow = 0;
            }
            else
            {
                result.set(i, (result.get(i) + overflow) % 10);
                overflow = 1;
            }
            ++i;
        }

        if(overflow != 0)
            result.add(i, overflow);

        return result;
    }

    /**
     * Разность двух объектов типа Natural
     * @param num1 Число, из которого вычетают
     * @param num2 Число, которое вычетают
     * @return Разность num1 и num2
     */
    public static Natural SUB_NN_N(Natural num1, Natural num2)
    {
        if(num1 == null || num2 == null || COM_NN_D(num1, num2) == 1)
            return null;

        int i = 0;
        int overflow = 0;
        Natural result = new Natural(num1);

        while (i <= num2.lastIndex())
        {
            if((num1.get(i) - num2.get(i) - overflow) < 0)
            {
                result.set(i, num1.get(i) - num2.get(i) - overflow + 10);
                overflow = 1;
            }
            else
            {
                result.set(i, num1.get(i) - num2.get(i) - overflow );
                overflow = 0;
            }
            ++i;
        }

        while (overflow != 0)
        {
            if((num1.get(i) - overflow) < 0)
            {
                result.set(i, num1.get(i) - overflow + 10);
                overflow = 1;
            }
            else
            {
                result.set(i, num1.get(i) - overflow);
                overflow = 0;
            }
            ++i;
        }

        return result;
    }

    /**
     * Умножение объекта типа Natural на цифру
     * @param natural Объект типа Natural
     * @param k Цифра, на которую происходит умножение
     * @return Произведение Объекта типа Natural и цифры
     */
    public static Natural MUL_ND_N(Natural natural, int k)
    {
        if(natural == null || k < 0 || k > 9)
            return null;

        Natural result = new Natural();
        int i = 0;
        int overflow = 0;

        if (!NZER_N_B(natural))
            return natural;
        else
        {
            while (i <= natural.lastIndex())
            {
                result.set(i, (natural.get(i) * k + overflow) % 10);
                overflow = (natural.get(i) * k + overflow) / 10;
                ++i;
            }

            if (overflow != 0)
                result.add(i, overflow);

            return result;
        }
    }

    /**
     * Умножение объекта типа Natural на 10^k
     * @param num Объект типа Natural
     * @param k Степень десяти, домножаемого на объект типа Natural
     * @return Произведение num и 10^k
     */
    public static Natural MUL_NK_N(Natural num, int k)
    {
        if(num == null)
            return null;

        Natural result = new Natural(num);

        for (int i = 0; i < k; ++i)
            result.add(0, 0);

        return result;
    }

    /**
     * Умножение натуральный чисел
     * @param num1 Первый сомножитель
     * @param num2 Второй сомножитель
     * @return Произведение num1 и num2
     */
    public static Natural MUL_NN_N(Natural num1, Natural num2)
    {
        if(num1 == null || num2 == null)
            return null;

        Natural result = new Natural();
        for(int i = 0; i <= num2.lastIndex(); ++i)
            result.setNatural(ADD_NN_N(MUL_NK_N(MUL_ND_N(num1, num2.get(i)), i), result));

        return result;
    }

    /**
     * Вычитание из одного натурального числа другого, умноженного на цифру
     * @param num1 Натуральное число, из которого происходит вычитание
     * @param d Цифра, на которую происходит умножение
     * @param num2 Натуральное число, на которое домножается цифра
     * @return Разность num1 и d*num2
     */
    public static Natural SUB_NDN_N(Natural num1, int d, Natural num2)
    {
        if(num1 == null || num2 == null || d < 0 || d > 9)
            return null;

        Natural aNum1 = new Natural(num1);
        Natural aNum2 = new Natural();

        aNum2.setNatural(MUL_ND_N(num2, d));
        if(COM_NN_D(aNum1, aNum2) != 1)
            return SUB_NN_N(aNum1, aNum2);
        else
            return null;
    }

    /**
     * Первая цифра от результата деления двух натуральных чисел
     * @param num1 Делимое - натуральное число
     * @param num2 Делитель - натуральное число
     * @return Первая цифра от результата деления num1 на num2
     */
    public static int DIV_NN_DK(Natural num1, Natural num2)
    {
        if(num1 == null || num2 == null || num2.isNull())
            return -1;

        if(COM_NN_D(num1, num2) == 1)
            return 0;

        int l = num1.lastIndex() - num2.lastIndex();

        if(COM_NN_D(num1, MUL_NK_N(num2, l)) == 1)
            --l;

        Natural aNum2 = new Natural();
        aNum2.setNatural(MUL_NK_N(num2, l));

        int d = 5;
        if (COM_NN_D(num1, MUL_ND_N(aNum2, d)) == 1)
        {
            do
                --d;
            while (COM_NN_D(num1, MUL_ND_N(aNum2, d)) == 1);
        }
        else
            if(COM_NN_D(num1, MUL_ND_N(aNum2, d)) == 2)
            {
                while (d < 9 && COM_NN_D(num1, MUL_ND_N(aNum2, d + 1)) != 1)
                    ++d;
            }

        return d;
    }

    /**
     * Деление двух натуральных чисел
     * @param num1 Делимое
     * @param num2 Делитель
     * @return Частное от деление num1 на num2
     */
    public static Natural DIV_NN_N(Natural num1, Natural num2)
    {
        if(num1 == null || num2 == null || num2.isNull())
            return null;

        Natural aNum1 = new Natural(num1);
        Natural result = new Natural();

        while (COM_NN_D(aNum1, num2) != 1)
        {
            int k = aNum1.lastIndex() - num2.lastIndex();

            if(COM_NN_D(aNum1, MUL_NK_N(num2, k)) == 1)
                --k;

            int curValue = DIV_NN_DK(aNum1, num2);
            result.set(k, curValue);

            aNum1.setNatural(SUB_NDN_N(aNum1, curValue, MUL_NK_N(num2, k)));
        }

        return result;
    }

    /**
     * Остаток от деления двух натуральных чисел
     * @param num1 Делимое
     * @param num2 Делитель
     * @return Остаток от деления num1 на num2
     */
    public static Natural MOD_NN_N(Natural num1, Natural num2)
    {
        if(num1 == null || num2 == null || num2.isNull())
            return null;

        Natural modResult = new Natural(num1);
        Natural divResult = new Natural();

        while (COM_NN_D(modResult, num2) != 1)
        {
            int k = 0;
            while (COM_NN_D(modResult, MUL_NK_N(num2, k)) != 1)
                ++k;
            --k;

            int curValue = DIV_NN_DK(modResult, num2);
            divResult.set(k, curValue);

            modResult.setNatural(SUB_NDN_N(modResult, curValue, MUL_NK_N(num2, k)));
        }

        return modResult;
    }

    /**
     * НОД двух натуральных чисел
     * @param num1 Первое натуральное число
     * @param num2 Второе натуральное число
     * @return НОД num1 и num2
     */
    public static Natural GCF_NN_N(Natural num1, Natural num2)
    {
        if(num1 == null || num2 == null)
            return null;

        Natural aNum1 = new Natural(num1);
        Natural aNum2 = new Natural(num2);

        while (!aNum1.isNull() && !aNum2.isNull())
        {
            if(COM_NN_D(aNum1, aNum2) != 1)
                aNum1.setNatural(SUB_NN_N(aNum1, aNum2));
            else
                aNum2.setNatural(SUB_NN_N(aNum2, aNum1));
        }

        if(COM_NN_D(aNum1, aNum2) != 1)
            return aNum1;
        return aNum2;
    }

    /**
     * НОК двух натуральных чисел
     * @param num1 Первое натуральное число
     * @param num2 Второе натуральное число
     * @return НОК num1 и num2
     */
    public static Natural LCM_NN_N(Natural num1, Natural num2)
    {
        if(num1 == null || num2 == null || num1.isNull() || num2.isNull())
            return null;
        return DIV_NN_N(MUL_NN_N(num1, num2), GCF_NN_N(num1, num2));

    }

}