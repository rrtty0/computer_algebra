# computer algebra
 
![GitHub last commit](https://img.shields.io/github/last-commit/rrtty0/computer_algebra?style=plastic)
![GitHub tag (latest by date)](https://img.shields.io/github/v/tag/rrtty0/computer_algebra)

- [Installation](#anc1)
- [Usage](#anc2)
- [Contribution](#anc3)
- [Supported operations](#anc4)
   * [Natural](#anc4.1)
   * [Integer](#anc4.2)
   * [Fraction](#anc4.3)
   * [Complex](#anc4.4)
   * [Polynomial](#anc4.5)
- [License](#anc5)

---

Implementation of your own "computer algebra" with the ability to work with "infinitely" large natural, integer, rational, complex numbers, as well as polynomials.


<a id="anc1"></a>

## Installation
- The sources of project can be downloaded from the [Github repo](https://github.com/rrtty0/computer_algebra.git).

* You can either clone the public repository:
```
        $ git clone https://github.com/rrtty0/computer_algebra.git 
```

<a id="anc2"></a>

## Usage
To use this application you need:
- _Open_ root-folder with this project at your local computer
- _Move_ to `out/artifacts/computer_algebra_jar`
- _Run_ file `computer_algebra.jar`

<a id="anc3"></a>

## Contribution
1. _Clone repo_ and _create a new branch_:
```
        $ git clone https://github.com/rrtty0/computer_algebra.git
        $ git branch name_for_new_branch
        $ git checkout name_for_new_branch
```
2. _Make changes_ and _test_
3. _Submit Pull Request_ with comprehensive description of changes

<a id="anc4"></a>

## Supported operations

The list of operations, that user can perform

<a id="anc4.1"></a>

### Natural
- `COM_NN_D`  - Comparison of natural numbers
- `NZER_N_B`  - Comparison of a natural number with zero
- `ADD_1N_N`  - Adding a unit to a natural number
- `ADD_NN_N`  - Add of two natural numbers
- `SUB_NN_N`  - The difference of two natural numbers
- `MUL_ND_N`  - Multiplication of a natural number by a digit
- `MUL_NK_N`  - Multiplication of a natural number by 10^k
- `MUL_NN_N`  - Multiplication of two natural numbers
- `SUB_NDN_N` - Subtracting another natural number, multiplied by a digit
- `DIV_NN_DK` - First digit from division of natural numbers
- `DIV_NN_N`  - Private from division of natural numbers
- `MOD_NN_N`  - Residue from division of natural numbers
- `GCF_NN_N`  - Greatest common divisor of two natural numbers
- `LCM_NN_N`  - Least common multiple of two natural numbers

<a id="anc4.2"></a>

### Integer
- `ABS_Z_N`   - Integer module, result - natural
- `POZ_Z_D`   - Obtaining a number sign: 0 - number equals zero, 1 - positive, 2 - negative
- `MUL_ZM_Z`  - Change sign of integer number
- `TRANS_N_Z` - Conversion of a natural number into a whole
- `TRANS_Z_N` - Integral to natural conversion
- `ADD_ZZ_Z`  - Add of two integral numbers
- `SUB_ZZ_Z`  - Difference of two integral numbers
- `MUL_ZZ_Z`  - Multiplication of two integer numbers
- `DIV_ZZ_Z`  - Quotient from division of two integral numbers
- `MOD_ZZ_Z`  - Remaining from division of two integer numbers

<a id="anc4.3"></a>

### Fraction
- `RED_Q_Q`   - Reduction of fractions
- `INT_Q_B`   - Check: is the number an integer. true - yes, false - no
- `TRANS_Z_Q` - Integer to fractional conversion
- `TRANS_Q_Z` - Receiving the whole part of the fraction
- `ADD_QQ_Q`  - Sum of two frames
- `SUB_QQ_Q`  - Difference of two fractions
- `MUL_QQ_Q`  - Multiplication of two fractions
- `DIV_QQ_Q`  - Division of two fractions

<a id="anc4.4"></a>

### Complex
- `CON_C_C`   - Getting a paired number
- `ADD_CC_C`  - Sum of two complex numbers
- `SUB_CC_C`  - The difference of two complex numbers
- `MUL_CC_C`  - Multiplication of two complex numbers
- `DIV_CC_C`  - Private from division of two complex numbers

<a id="anc4.5"></a>

### Polynomial
- `ADD_PP_P`  - Addition of two polynoms
- `SUB_PP_P`  - Difference of two polynoms
- `MUL_PQ_P`  - Multiplication of polynom by a fraction
- `MUL_PxK_P` - Multiplication of polynom by x in the degree of natural number
- `LED_P_Q`   - Coefficient at the highest degree of polynom
- `DEG_P_Z`   - Higher polynom degree
- `FAC_P_Q`   - Greatest common divisor of numerators of polynom coefficients, noc of denominators of polynom coefficients
- `MUL_PP_P`  - Multiplication of two polynoms
- `DIV_PP_P`  - Quotient from division of two polynoms
- `MOD_PP_P`  - Remaining from division of two polynoms
- `GCF_PP_P`  - Greatest common divisor of two polynoms
- `DER_P_P`   - Derivative of polynom

<a id="anc5"></a>

## License
Source Available License Agreement - [Apache License v2.0](./LICENSE)