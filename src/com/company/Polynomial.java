package com.company;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polynomial implements Comparable, Iterable<Monomial>
{
    private List<Monomial> staff = new LinkedList<>();


    public Polynomial()
    {
        staff.add(new Monomial());
    }

    public Polynomial(Polynomial polynomial) throws NullPointerException
    {
        if(polynomial == null)
            throw new NullPointerException("Параметр - ссылка на null");

        copyOf(polynomial);
    }

    public boolean setPolynomial(Polynomial polynomial)
    {
        if(polynomial == null)
            return false;

        removeAll();
        copyOf(polynomial);
        return true;
    }

    private void copyOf(Polynomial polynomial)
    {
        Iterator<Monomial> iterator = polynomial.iterator();

        while(iterator.hasNext())
            add(iterator.next());
    }

    public boolean setPolynomial(String str)
    {
        try
        {
            while (str.charAt(0) == ' ')
                str = str.substring(1, str.length());
            if(str.charAt(0) != '-' && str.charAt(0) != '+')
                str = "+" + str;
            Pattern p = Pattern.compile("^(( )*(([-+]( )*\\d+(( )*/( )*(0)*[1-9]\\d*)?(( )*[*]?( )*[xX](( )*\\^( )*\\d+)?)?)|([-+]( )*[xX](( )*\\^( )*\\d+)?))( )*)+$");
            Matcher m = p.matcher(str);
            if (m.matches())
            {
                p = Pattern.compile("( )*(([-+]( )*\\d+(( )*/( )*(0)*[1-9]\\d*)?(( )*[*]?( )*[xX](( )*\\^( )*\\d+)?)?)|([-+]( )*[xX](( )*\\^( )*\\d+)?))( )*");
                m = p.matcher(str);
                Monomial monomial;
                while (m.find())
                {
                    monomial = new Monomial();
                    monomial.setMonomial(str.substring(m.start(), m.end()));
                    goodAdd(monomial);
                }
            }

        }
        catch (IndexOutOfBoundsException e)
        {
            return false;
        }

        return true;
    }

    public boolean inputPolynomial()
    {
        Scanner input = new Scanner(System.in);
        String str = input.nextLine();

        return setPolynomial(str);
    }

    public void outputPolynomial()
    {
        System.out.print(toString());
    }

    public static Polynomial parsePolynomial(String str)
    {
        Polynomial polynomial = new Polynomial();
        if(polynomial.setPolynomial(str))
            return polynomial;

        return null;
    }

    /**
     * Проверка на равенство объектов типа Polynomial
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

        Polynomial other = (Polynomial) obj;

        if(size() != other.size())
            return false;

        boolean equal = true;

        Iterator<Monomial> it1 = staff.listIterator();
        Iterator<Monomial> it2 = other.staff.listIterator();

        while (equal)
            equal = it1.next().equals(it2.next());

        return equal;
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

        Polynomial other = (Polynomial) o;

        Iterator<Monomial> it1 = staff.listIterator(staff.size());
        Iterator<Monomial> it2 = other.staff.listIterator(staff.size());
        Monomial mon1;
        Monomial mon2;

        while (((ListIterator<Monomial>) it1).hasPrevious() && ((ListIterator<Monomial>) it2).hasPrevious())
        {
            mon1 = ((ListIterator<Monomial>) it1).previous();
            mon2 = ((ListIterator<Monomial>) it2).previous();

            int resultOfCompare = mon1.getDegree().compareTo(mon2.getDegree());

            if(resultOfCompare == 1 || resultOfCompare == -1)
                return resultOfCompare;

            resultOfCompare = mon1.getCoef().compareTo(mon2.getCoef());

            if(resultOfCompare == 1 || resultOfCompare == -1)
                return resultOfCompare;
        }

        if(!((ListIterator<Monomial>) it1).hasPrevious() && !((ListIterator<Monomial>) it2).hasPrevious())
            return 0;

        if(!((ListIterator<Monomial>) it1).hasPrevious())
            return -1;

        return 1;

    }

    /**
     * Поучение хеш-кода числа
     * @return Хеш-код числа
     */
    @Override
    public int hashCode()
    {
        Iterator<Monomial> iterator = staff.listIterator();
        int hashCode = 0;

        while (iterator.hasNext())
            hashCode += iterator.next().hashCode();

        return hashCode;
    }

    /**
     * Получение копии данного объекта
     * @return Копия данного объекта
     */
    @Override
    protected Object clone()
    {
        Polynomial polynomial = new Polynomial(this);

        return polynomial;
    }

    /**
     * Формирует строку по данному числу
     * @return Строка, сформированная по данному числу
     */
    @Override
    public String toString()
    {
        String str;
        String curMonomial;

        Iterator<Monomial> iterator = staff.listIterator(staff.size());
        str = ((ListIterator<Monomial>) iterator).previous().toString();

        while (((ListIterator<Monomial>) iterator).hasPrevious())
        {
            curMonomial = ((ListIterator<Monomial>) iterator).previous().toString();
            if(curMonomial.charAt(0) != '-')
                curMonomial = '+' + curMonomial;
            str += curMonomial;
        }

        return str;
    }

    public Monomial get(Natural degree)
    {
        if(degree == null)
            return null;

        Iterator<Monomial> iterator = staff.listIterator();
        Monomial monomial;

        while (iterator.hasNext())
        {
            monomial = iterator.next();
            if(degree.compareTo(monomial.getDegree()) == 1)
                return null;
            if(degree.compareTo(monomial.getDegree()) == 0)
                return monomial;
        }

        return null;
    }

    public void removeNulls()
    {
        Iterator<Monomial> iterator = staff.listIterator();
        Monomial monomial;

        if(!isNull())
        {
            while (iterator.hasNext())
            {
                monomial = iterator.next();
                if(monomial.getCoef().isNull())
                    iterator.remove();
            }
        }

        if(staff.isEmpty())
            staff.add(new Monomial());
    }

    public int size()
    {
        if(isNull())
            return 0;
        return staff.size();
    }

    public Natural getHighDegree()
    {
        return staff.listIterator(staff.size()).previous().getDegree();
    }

    public void add(Monomial monomial)
    {
        Monomial copyMon = new Monomial(monomial);
        goodAdd(copyMon);
    }

    private boolean goodAdd(Monomial monomial)
    {
        int i;
        Monomial mon;

        if(monomial == null)
            return false;

        if(isNull())
            removeAll();

        Iterator<Monomial> iterator = staff.listIterator();

        if (!monomial.isNull())
        {
            while(iterator.hasNext())
            {
              i = ((ListIterator<Monomial>) iterator).nextIndex();
              mon = iterator.next();
              if(mon.getDegree().equals(monomial.getDegree()))
              {
                  mon.setCoef(Fraction.ADD_QQ_Q(monomial.getCoef(), mon.getCoef()));
                  if(mon.isNull())
                  {
                      ((ListIterator<Monomial>) iterator).previous();
                      iterator.remove();
                  }

                  if(staff.isEmpty())
                      staff.add(new Monomial());

                  return true;
              }
              else
                  if(mon.getDegree().compareTo(monomial.getDegree()) == 1)
                  {
                      staff.add(i, monomial);
                      if(mon.isNull())
                      {
                          ((ListIterator<Monomial>) iterator).previous();
                          iterator.remove();
                      }

                      return true;
                  }
            }
            ((ListIterator<Monomial>) iterator).add(monomial);
            removeNulls();
        }

        if(staff.isEmpty())
            staff.add(new Monomial());

        return true;
    }

    private void removeAll()
    {
        while(staff.size() != 0)
        {
            staff.get(0).deleteMonomial();
            staff.remove(0);
        }
    }

    public boolean removeMonomial(Natural degree)
    {
        if(!isNull())
        {
           Iterator<Monomial> iterator = staff.listIterator();
           while (iterator.hasNext())
           {
               Monomial monomial = iterator.next();
               if(monomial.getDegree().equals(degree))
               {
                   monomial.deleteMonomial();
                   ((ListIterator<Monomial>) iterator).previous();
                   iterator.remove();
               }
           }

            if(staff.isEmpty())
            {
                staff.add(new Monomial());
                return true;
            }
        }

        return false;
    }

    public boolean isNull()
    {
        return (staff.size() == 1 && staff.get(0).getDegree().isNull() && staff.get(0).isNull());
    }

    @Override
    public ListIterator<Monomial> iterator() {
        ListIterator<Monomial> it = new ListIterator<Monomial>() {

            ListIterator<Monomial> iter = staff.listIterator();

            @Override
            public boolean hasNext() {
                return iter.hasNext();
            }

            @Override
            public Monomial next() {
                return iter.next();
            }

            @Override
            public boolean hasPrevious() {
                return iter.hasPrevious();
            }

            @Override
            public Monomial previous() {
                return iter.previous();
            }

            @Override
            public int nextIndex() {
                return iter.nextIndex();
            }

            @Override
            public int previousIndex() {
                return iter.previousIndex();
            }

            @Override
            public void remove() {
                if(iter.hasNext())
                {
                    iter.next().deleteMonomial();
                    iter.previous();
                    iter.remove();

                    if(staff.isEmpty())
                        staff.add(new Monomial());
                }
            }

            @Override
            public void set(Monomial monomial) {
                iter.set(monomial);
            }

            @Override
            public void add(Monomial monomial) {
                Polynomial.this.add(monomial);
            }
        };
        return it;
    }

    public ListIterator<Monomial> iteratorToHighDegree() {
        ListIterator<Monomial> it = new ListIterator<Monomial>() {

            ListIterator<Monomial> iter = staff.listIterator(staff.size());

            @Override
            public boolean hasNext() {
                return iter.hasNext();
            }

            @Override
            public Monomial next() {
                return iter.next();
            }

            @Override
            public boolean hasPrevious() {
                return iter.hasPrevious();
            }

            @Override
            public Monomial previous() {
                return iter.previous();
            }

            @Override
            public int nextIndex() {
                return iter.nextIndex();
            }

            @Override
            public int previousIndex() {
                return iter.previousIndex();
            }

            @Override
            public void remove() {
                if(iter.hasNext())
                {
                    iter.next().deleteMonomial();
                    iter.previous();
                    iter.remove();

                    if(staff.isEmpty())
                        staff.add(new Monomial());
                }
            }

            @Override
            public void set(Monomial monomial) {
                iter.set(monomial);
            }

            @Override
            public void add(Monomial monomial) {
                Polynomial.this.add(monomial);
            }
        };
        return it;
    }




    //МАТЕМАТИКА ДЛЯ ПОЛИНОМОВ


    public static Polynomial ADD_PP_P(Polynomial pol1, Polynomial pol2)
    {
        if(pol1 == null || pol2 == null)
            return null;

        Polynomial result = new Polynomial(pol1);

        Iterator<Monomial> iterator = pol2.iterator();

        while (iterator.hasNext())
            result.add(iterator.next());

        return result;
    }

    public static Polynomial SUB_PP_P(Polynomial pol1, Polynomial pol2)
    {
        if(pol1 == null || pol2 == null)
            return null;

        Polynomial result = new Polynomial(pol1);
        Monomial monomial;

        Iterator<Monomial> iterator = pol2.iterator();

        while (iterator.hasNext())
        {
            monomial = (Monomial) iterator.next().clone();
            monomial.setSign(!monomial.getSign());
            result.add(monomial);
        }

        return result;
    }

    public static Polynomial MUL_PQ_P(Polynomial polynomial, Fraction fraction)
    {
        if(polynomial == null || fraction == null)
            return null;

        if(fraction.isNull())
            return new Polynomial();

        if(fraction.equals(Fraction.parseFraction("1")))
            return polynomial;

        Polynomial result = new Polynomial();
        Monomial monomial;

        Iterator<Monomial> iterator = polynomial.iteratorToHighDegree();

        while (((ListIterator<Monomial>) iterator).hasPrevious())
        {
            monomial = (Monomial) ((ListIterator<Monomial>) iterator).previous().clone();
            monomial.setCoef(Fraction.MUL_QQ_Q(monomial.getCoef(), fraction));
            result.add(monomial);
        }

        return result;
    }

    public static Polynomial MUL_PxK_P(Polynomial polynomial, Natural degree)
    {
        if(polynomial == null || degree == null)
            return null;

        if(degree.isNull())
            return polynomial;

        Polynomial result = new Polynomial();
        Monomial monomial;
        Iterator<Monomial> iterator = polynomial.iteratorToHighDegree();

        while (((ListIterator<Monomial>) iterator).hasPrevious())
        {
            monomial = (Monomial) ((ListIterator<Monomial>) iterator).previous().clone();
            monomial.setDegree(Natural.ADD_NN_N(monomial.getDegree(), degree));
            result.add(monomial);
        }

        return result;
    }

    public static Fraction LED_P_Q(Polynomial polynomial)
    {
        if(polynomial == null)
            return null;

        return polynomial.iteratorToHighDegree().previous().getCoef();
    }

    public static Natural DEG_P_Z(Polynomial polynomial)
    {
        if(polynomial == null)
            return null;

        return polynomial.getHighDegree();
    }

    public static Fraction FAC_P_Q(Polynomial polynomial)
    {
        if(polynomial == null)
            return null;

        Fraction nodNok = new Fraction();
        Monomial monomial;

        Iterator<Monomial> iterator = polynomial.iterator();

        while (iterator.hasNext())
        {
            monomial = iterator.next();
            nodNok.getNumerator().setInteger(Natural.GCF_NN_N(Integer.TRANS_Z_N(monomial.getCoef().getNumerator()), Integer.TRANS_Z_N(nodNok.getNumerator())), false);
            nodNok.setDenominator(Natural.LCM_NN_N(monomial.getCoef().getDenominator(), nodNok.getDenominator()));
        }

        return nodNok;
    }

    public static Polynomial MUL_PP_P(Polynomial pol1, Polynomial pol2)
    {
        if(pol1 == null || pol2 == null)
            return null;

        Polynomial result = new Polynomial();
        Monomial monomial;
        Polynomial polynomial;

        if(pol1.isNull())
            return result;

        Iterator<Monomial> iterator = pol2.iteratorToHighDegree();

        while (((ListIterator<Monomial>) iterator).hasPrevious())
        {
            monomial = ((ListIterator<Monomial>) iterator).previous();
            polynomial = MUL_PxK_P(MUL_PQ_P(pol1, monomial.getCoef()), monomial.getDegree());
            Iterator<Monomial> it = polynomial.iteratorToHighDegree();
            while (((ListIterator<Monomial>) it).hasPrevious())
                result.add(((ListIterator<Monomial>) it).previous());
        }

        return result;
    }

    public static Polynomial DIV_PP_P(Polynomial pol1, Polynomial pol2)
    {
        if(pol1 == null || pol2 == null || pol2.isNull())
            return null;

        Polynomial result = new Polynomial();
        Polynomial aPol1 = new Polynomial(pol1);
        Monomial currentMonomialInResult;
        Monomial helpMonomial;

        while ((Natural.COM_NN_D(aPol1.getHighDegree(), pol2.getHighDegree()) != 1 && (!aPol1.getHighDegree().equals(new Natural()) || !pol2.getHighDegree().equals(new Natural())))
            || (aPol1.getHighDegree().equals(new Natural()) && pol2.getHighDegree().equals(new Natural()) && !aPol1.isNull()))
        {
            currentMonomialInResult = new Monomial(Fraction.DIV_QQ_Q(Polynomial.LED_P_Q(aPol1), Polynomial.LED_P_Q(pol2)),
                                            Natural.SUB_NN_N(aPol1.getHighDegree(), pol2.getHighDegree()));

            result.add(currentMonomialInResult);

            Iterator<Monomial> iterator = pol2.iteratorToHighDegree();
            while (((ListIterator<Monomial>) iterator).hasPrevious())
            {
                helpMonomial = (Monomial) ((ListIterator<Monomial>) iterator).previous().clone();
                helpMonomial.setSign(!helpMonomial.getSign());
                aPol1.add(new Monomial(Fraction.MUL_QQ_Q(currentMonomialInResult.getCoef(), helpMonomial.getCoef()),
                        Natural.ADD_NN_N(currentMonomialInResult.getDegree(), helpMonomial.getDegree())));
            }
        }

        return result;
    }

    public static Polynomial MOD_PP_P(Polynomial pol1, Polynomial pol2)
    {
        if(pol1 == null || pol2 == null || pol2.isNull())
            return null;

        Polynomial result = new Polynomial();
        Polynomial aPol1 = new Polynomial(pol1);
        Monomial currentMonomialInResult;
        Monomial helpMonomial;

        while ((Natural.COM_NN_D(aPol1.getHighDegree(), pol2.getHighDegree()) != 1 && (!aPol1.getHighDegree().equals(new Natural()) || !pol2.getHighDegree().equals(new Natural())))
                || (aPol1.getHighDegree().equals(new Natural()) && pol2.getHighDegree().equals(new Natural()) && !aPol1.isNull()))
        {
            currentMonomialInResult = new Monomial(Fraction.DIV_QQ_Q(Polynomial.LED_P_Q(aPol1), Polynomial.LED_P_Q(pol2)),
                    Natural.SUB_NN_N(aPol1.getHighDegree(), pol2.getHighDegree()));

            result.add(currentMonomialInResult);

            Iterator<Monomial> iterator = pol2.iteratorToHighDegree();
            while (((ListIterator<Monomial>) iterator).hasPrevious())
            {
                helpMonomial = (Monomial) ((ListIterator<Monomial>) iterator).previous().clone();
                helpMonomial.setSign(!helpMonomial.getSign());
                aPol1.add(new Monomial(Fraction.MUL_QQ_Q(currentMonomialInResult.getCoef(), helpMonomial.getCoef()),
                        Natural.ADD_NN_N(currentMonomialInResult.getDegree(), helpMonomial.getDegree())));
            }
        }

        return aPol1;
    }

    public static Polynomial GCF_PP_P(Polynomial pol1, Polynomial pol2)
    {
        if(pol1 == null || pol2 == null)
            return null;

        Polynomial aPol1 = new Polynomial(pol1);
        Polynomial aPol2 = new Polynomial(pol2);

        while (!aPol1.isNull() && !aPol2.isNull())
        {
            if(Natural.COM_NN_D(aPol1.getHighDegree(), aPol2.getHighDegree()) != 1)
                aPol1.setPolynomial(MOD_PP_P(aPol1, aPol2));
            else
                aPol2.setPolynomial(MOD_PP_P(aPol2, aPol1));
        }

        if(aPol1.isNull())
        {
            if(aPol2.getHighDegree().equals(new Natural()))
                return parsePolynomial("1");
            return aPol2;
        }

        if(aPol1.getHighDegree().equals(new Natural()))
            return parsePolynomial("1");

        return aPol1;
    }

    public static Polynomial DER_P_P(Polynomial polynomial)
    {
        if(polynomial == null)
            return null;

        Polynomial result = new Polynomial();
        Monomial monomial;
        Fraction degree = new Fraction();
        Iterator<Monomial> iterator = polynomial.iteratorToHighDegree();

        while (((ListIterator<Monomial>) iterator).hasPrevious())
        {
            monomial = (Monomial) ((ListIterator<Monomial>) iterator).previous().clone();
            if(!monomial.getDegree().isNull())
            {
                degree.setNumerator(Integer.TRANS_N_Z(monomial.getDegree()));
                monomial.setCoef(Fraction.MUL_QQ_Q(monomial.getCoef(), degree));
                monomial.setDegree(Natural.SUB_NN_N(monomial.getDegree(), degree.getDenominator()));
                result.add(monomial);
            }
        }

        return result;

    }

}