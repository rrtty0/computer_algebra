package com.company;

public final class MyMath
{

    private MyMath()
    {
    }


    //НАТУРАЛЬНЫЕ ЧИСЛА

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
    public static boolean NZER_N_B(Natural num)
    {
        if(num == null)
            return false;

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
        if(num1 == null || num2 == null || num1.compareTo(num2) == -1)
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

        int l = num1.lastIndex() - num2.lastIndex();

        if(COM_NN_D(num1, MUL_NK_N(num2, l)) == 1)
            --l;

        Natural aNum2 = new Natural();
        aNum2.setNatural(MUL_NK_N(num2, l));

        int d = 5;
        if (COM_NN_D(num1, MUL_ND_N(aNum2, d)) == 1)
        {
            --d;
            while (COM_NN_D(num1, MUL_ND_N(aNum2, d)) == 1)
                --d;
        }
        else
            if(COM_NN_D(num1, MUL_ND_N(aNum2, d)) == 2)
            {
                while (d < 9 && COM_NN_D(num1, MUL_ND_N(aNum2, d + 1)) == 2)
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
            if(aNum1.compareTo(aNum2) != -1)
                aNum1.setNatural(SUB_NN_N(aNum1, aNum2));
            else
                aNum2.setNatural(SUB_NN_N(aNum2, aNum1));
        }

        if(aNum1.compareTo(aNum2) != -1)
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


    //ЦЕЛЫЕ ЧИСЛА

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

        if(!num.isNull())
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
            result.setInteger(ADD_NN_N(num1, num2), num1.getSign());
        else
        {
            int flag = COM_NN_D(num1, num2);

            if(flag == 2)
                result.setInteger(SUB_NN_N(num1, num2), num1.getSign());
            else
                if(flag == 1)
                    result.setInteger(SUB_NN_N(num2, num1), num2.getSign());
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

        Integer result = new Integer(MUL_NN_N(num1, num2), !(num1.getSign() == num2.getSign()));

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

        Integer result = new Integer(DIV_NN_N(num1, num2), !(num1.getSign() == num2.getSign()));

        return result;
    }

    //???? остаток ведь не может быть отрицательным
    public static Integer MOD_ZZ_Z(Integer num1, Integer num2)
    {
        if(num1 == null || num2 == null || num2.isNull())
            return null;

        return new Integer(MOD_NN_N(num1, num2), !(num1.getSign() == num2.getSign()));
    }




    //РАЦИОНАЛЬНЫЕ ЧИСЛА

    /**
     *
     * @param fraction
     * @return
     */
    public static Fraction RED_Q_Q(Fraction fraction)
    {
        if(fraction == null)
            return null;

        Natural nod = new Natural();
        nod.setNatural(GCF_NN_N(fraction.getNumerator(), fraction.getDenominator()));
        fraction.setNumerator(new Integer(DIV_NN_N(fraction.getNumerator(), nod), fraction.getSign()));
        fraction.setDenominator(DIV_NN_N(fraction.getDenominator(), nod));
        return fraction;
    }

    public static Boolean INT_Q_B(Fraction fraction)
    {
        Fraction this_fract = new Fraction(fraction);
        RED_Q_Q(this_fract);
        if(this_fract.getDenominator().size() == 1 && this_fract.getDenominator().get(0) == 1)
            return true;
        return false;
    }

    public static Fraction TRANS_Z_Q(Integer num)
    {
        Fraction fraction = new Fraction(num, Natural.parseNatural("1"));
        return fraction;
    }

    public static Integer TRANS_Q_Z(Fraction fraction)
    {
        Integer num = new Integer(fraction.getNumerator());
        return num;
    }

    public static Fraction ADD_QQ_Q(Fraction fr1, Fraction fr2)
    {
        Fraction this_fr1 = new Fraction(fr1);
        Fraction this_fr2 = new Fraction(fr2);

        Natural nok = new Natural(LCM_NN_N(this_fr1.getDenominator(), this_fr2.getDenominator()));
        this_fr1.getNumerator().setInteger(MUL_NN_N(DIV_NN_N(nok, this_fr1.getDenominator()), this_fr1.getNumerator()), this_fr1.getSign());
        this_fr2.getNumerator().setInteger(MUL_NN_N(DIV_NN_N(nok, this_fr2.getDenominator()), this_fr2.getNumerator()), this_fr2.getSign());
        Fraction result = new Fraction(ADD_ZZ_Z(this_fr1.getNumerator(), this_fr2.getNumerator()), nok);
        RED_Q_Q(result);

        return result;
    }

    public static Fraction SUB_QQ_Q(Fraction fr1, Fraction fr2)
    {
        Fraction this_fr2 = new Fraction(fr2);
        this_fr2.setSign(!this_fr2.getSign());
        return ADD_QQ_Q(fr1, this_fr2);
    }

    public static Fraction MUL_QQ_Q(Fraction fr1, Fraction fr2)
    {
        Fraction result = new Fraction(MUL_ZZ_Z(fr1.getNumerator(), fr2.getNumerator()), MUL_NN_N(fr1.getDenominator(), fr2.getDenominator()));
        RED_Q_Q(result);
        return result;
    }

    public static Fraction DIV_QQ_Q(Fraction fr1, Fraction fr2)
    {
        Fraction result = new Fraction(new Integer(MUL_NN_N(fr1.getNumerator(), fr2.getDenominator()), !(fr1.getSign() == fr2.getSign())), MUL_NN_N(fr1.getDenominator(), fr2.getNumerator()));
        RED_Q_Q(result);
        return result;
    }



    //КОМПЛЕКСНЫЕ ЧИСЛА





    //ПОЛИНОМЫ

/*
    public static Polynomial ADD_PP_P(Polynomial pol1, Polynomial pol2)
    {
        Polynomial result = new Polynomial(pol1);
        Monomial helpMon1 = new Monomial();
        Monomial helpMon2 = new Monomial();

        int size = pol2.size();
        for (int i = 0; i < size; i++)
            result.add(pol2.get(i));

        for (int i = 0; i < result.size() - 1; i++)
        {
            helpMon1.setMonomial(result.get(i));
            helpMon2.setMonomial(result.get(i + 1));
            if (Integer.equals(helpMon1.getDegree(), helpMon2.getDegree()) == 0)
            {
                helpMon1.setCoef(ADD_QQ_Q(helpMon1.getCoef(), helpMon2.getCoef()));
                result.removeElement(i + 1);
                if (helpMon1.isNull())
                {
                    result.removeElement(i);
                    i--;
                }
                else
                    result.get(i).setMonomial(helpMon1);
            }
        }

        helpMon1.deleteMonomial();
        helpMon2.deleteMonomial();

        return result;
    }

    public static Polynomial SUB_PP_P(Polynomial pol1, Polynomial pol2)
    {
        Polynomial this_pol2 = new Polynomial(pol2);
        int size = this_pol2.size();

        for(int i = 0; i < size; i++)
            this_pol2.get(i).getCoef().setSign(!this_pol2.get(i).getCoef().getSign());

        return new Polynomial(ADD_PP_P(pol1, this_pol2));
    }

    public static Polynomial MUL_PQ_P(Polynomial polynomial, Fraction fraction)
    {
        Polynomial result = new Polynomial(polynomial);

        for(int i = 0; i < result.size(); i++)
        {
            result.get(i).setCoef(MUL_QQ_Q(result.get(i).getCoef(), fraction));
            if(result.get(i).isNull())
            {
                result.removeElement(i);
                i--;
            }
        }

        return result;
    }

    public static Polynomial MUL_PxK_P(Polynomial polynomial, Monomial monomial)
    {
        Polynomial result = new Polynomial(polynomial);
        int size = result.size();


        for(int i = 0; i < size; i++)
            result.get(i).setDegree(ADD_ZZ_Z(result.get(i).getDegree(), monomial.getDegree()));

        return result;
    }

    public static Fraction LED_P_Q(Polynomial polynomial)
    {
        return polynomial.get(polynomial.size() - 1).getCoef();
    }

    public static Integer DEG_P_Z(Polynomial polynomial)
    {
        return polynomial.getHighDegree();
    }

    public static Fraction FAC_P_Q(Polynomial polynomial)
    {
        Fraction nodNok = new Fraction();
        int size = polynomial.size();

        for(int i = 0; i < size; i++)
        {
            nodNok.setNumerator(GCF_NN_N(polynomial.get(i).getCoef().getNumerator(), nodNok.getNumerator()), false);
            nodNok.setDenominator(LCM_NN_N(polynomial.get(i).getCoef().getDenominator(), nodNok.getDenominator()));
        }

        return nodNok;
    }

    public static Polynomial MUL_PP_P(Polynomial pol1, Polynomial pol2)
    {
        Polynomial result = new Polynomial();
        int sizePol2 = pol2.size();

        for(int i = 0; i < sizePol2; i++)
            result.setPolynomial(ADD_PP_P(MUL_PxK_P(MUL_PQ_P(pol1, pol2.get(i).getCoef()), pol2.get(i)), result));


        return result;
    }

    public static Polynomial DIV_PP_P(Polynomial pol1, Polynomial pol2)
    {
        Polynomial curPol1 = new Polynomial(pol1);
        Polynomial result = new Polynomial();
        Monomial mon = new Monomial();
        Fraction senCoefPol2 = new Fraction(pol2.get(pol2.size() - 1).getCoef());
        Integer senDegPol2 = new Integer(pol2.getHighDegree());

        if(Integer.equals(pol1.getHighDegree(), senDegPol2) == 1)
            return new Polynomial();

        while(Integer.equals(curPol1.getHighDegree(), senDegPol2) != 1)
        {
            mon.setDegree(SUB_ZZ_Z(curPol1.getHighDegree(), senDegPol2));
            mon.setCoef(DIV_QQ_Q(curPol1.get(curPol1.size() - 1).getCoef(), senCoefPol2));
            result.add(mon);
            curPol1.setPolynomial(SUB_PP_P(curPol1, MUL_PQ_P(MUL_PxK_P(pol2, mon), mon.getCoef())));
        }

        return result;
    }

    public static Polynomial MOD_PP_P(Polynomial pol1, Polynomial pol2)
    {
        Polynomial result = new Polynomial(pol1);
        Monomial mon = new Monomial();
        Fraction senCoefPol2 = new Fraction(pol2.get(pol2.size() - 1).getCoef());
        Integer senDegPol2 = new Integer(pol2.getHighDegree());

        while(Integer.equals(result.getHighDegree(), senDegPol2) != 1)
        {
            mon.setDegree(SUB_ZZ_Z(result.getHighDegree(), senDegPol2));
            result.setPolynomial(SUB_PP_P(result, MUL_PQ_P(MUL_PxK_P(pol2, mon), DIV_QQ_Q(result.get(result.size() - 1).getCoef(), senCoefPol2))));
        }

        return result;
    }

    public static Polynomial GCF_PP_P(Polynomial pol1, Polynomial pol2)
    {
        Polynomial thisPol1 = new Polynomial(pol1);
        Polynomial thisPol2 = new Polynomial(pol2);

        while(!thisPol1.isNull() && !thisPol2.isNull())
        {
            if(Integer.equals(thisPol1.getHighDegree(), thisPol2.getHighDegree()) != 1)
                thisPol1.setPolynomial(MOD_PP_P(thisPol1, thisPol2));
            else
                thisPol2.setPolynomial(MOD_PP_P(thisPol2, thisPol1));
        }

        if(thisPol1.isNull())
            return thisPol2;
        return thisPol1;
    }

    public static Polynomial DER_P_P(Polynomial polynomial)
    {
        Polynomial result = new Polynomial(polynomial);
        Fraction degree = new Fraction();

        result.removeElement(new Integer());

        for(int i = 0; i < result.size(); i++)
        {
            degree.setNumerator(result.get(i).getDegree());
            result.get(i).setCoef(MUL_QQ_Q(result.get(i).getCoef(), degree));
            result.get(i).setDegree(SUB_ZZ_Z(result.get(i).getDegree(), Integer.parseInteger("1")));
        }

        return result;
    }
    */
}
