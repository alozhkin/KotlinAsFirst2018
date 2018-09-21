@file:Suppress("UNUSED_PARAMETER")
package lesson3.task1

import lesson1.task1.sqr
import java.lang.Math.pow
import kotlin.math.*

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}
// пришлось добавить функцию так как стандартная pow не принимает Int, а с приведением типов мне лень возиться
//считает только степени от 0 до беск
fun power(n: Int, m: Int): Int {
    var result = n
    for (i in m downTo 2) {
        result *= n
    }
    if (m == 0) return 1
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    for (m in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n/2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
        when {
            n == m -> 1
            n < 10 -> 0
            else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
        }

/**
 * Тривиальная
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
//почему комменты серые?
fun digitNumber(n: Int): Int {
    if (n != 0) {
        var count = 0
        var anotherN = n
        while ((anotherN > 0) || (anotherN < 0)) {
             anotherN /= 10
             count++
        }
        return count
    } else return 1
}

/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {
    if ((n == 1) || (n == 2)) return 1
    var previousValue = 1
    var previousPreviousValue = 1
    var result = previousPreviousValue + previousValue
    for (i in 3..n) {
        result = previousPreviousValue + previousValue
        previousPreviousValue = previousValue
        previousValue = result
    }
    return result
}

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int {
    val max = if (n > m) n else m
    val min = if (n > m) m else n
    var result = max
    if (max % min == 0) {
        return max
    } else {
        while (result % min != 0) {
            result += max
        }
        return result
    }
}


/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    var divider = 2
    while (n % divider != 0) {
        divider++
    }
    return divider
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int {
    var divider = 1
    for (i in 2 until n) {
        if (n % i == 0) divider = i
    }
    return divider
}

/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean {
    var divider = 1
    if (n == m) return false
    for (i in 2 .. n / 2) {
        if ((n % i == 0) && (m % i == 0)) divider = i
    }
    return (divider == 1)
}


/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean =
    (floor(sqrt(n.toDouble())) - ceil(sqrt(m.toDouble())) >= 0.0)



/**
 * Средняя
 *
 * Гипотеза Коллатца. Рекуррентная последовательность чисел задана следующим образом:
 *
 *   ЕСЛИ (X четное)
 *     Xслед = X /2
 *   ИНАЧЕ
 *     Xслед = 3 * X + 1
 *
 * например
 *   15 46 23 70 35 106 53 160 80 40 20 10 5 16 8 4 2 1 4 2 1 4 2 1 ...
 * Данная последовательность рано или поздно встречает X == 1.
 * Написать функцию, которая находит, сколько шагов требуется для
 * этого для какого-либо начального X > 0.
 */
fun collatzSteps(x: Int): Int {
    var count = 0
    var number = x
    while (number != 1) {
        if (number % 2 == 0) {
            number /= 2
            count++
        } else {
            number = number * 3 + 1
            count++
        }
    }
    return count
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */

fun sin(x: Double, eps: Double): Double {
    var number = x
    //приводим х к интервалу 0..2PI
    while (abs(number) >= 2 * PI) {
        if (number >= 2 * PI) number -= 2 * PI
        else if (number <= 2 * PI) number += 2 * PI
    }

    var numberOfSeries = pow(number, 3.0) / factorial(3)
    var sum = number
    var coefficientOfSeries = 3
    //нужен, чтобы менять знак каждого следующего члена последовательности через (-1)^count
    var count = 1.0
    while (abs(numberOfSeries) >= eps) {
        numberOfSeries = pow(number, coefficientOfSeries.toDouble()) / factorial(coefficientOfSeries) * pow(-1.0, count)
        sum += numberOfSeries
        coefficientOfSeries += 2
        count++
    }
    return sum
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */

fun cos(x: Double, eps: Double): Double {
    var number = x
    //приводим х к интервалу 0..2PI
    while (abs(number) >= 2 * PI) {
        if (number >= 2 * PI) number -= 2 * PI
        else if (number <= 2 * PI) number += 2 * PI
    }

    var numberOfSeries = pow(number, 2.0) / factorial(2)
    var sum = 1.0
    var coefficientOfSeries = 2
    //нужен, чтобы менять знак каждого следующего члена последовательности через (-1)^count
    var count = 1.0
    while (abs(numberOfSeries) >= eps) {
        numberOfSeries = pow(number, coefficientOfSeries.toDouble()) / factorial(coefficientOfSeries) * pow(-1.0, count)
        sum += numberOfSeries
        coefficientOfSeries += 2
        count++
    }
    return sum
}

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun revert(n: Int): Int {
    var number = n
    var answer = 0
    while (number != 0) {
        answer = answer * 10 + number % 10
        number /= 10
    }
    return answer
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun isPalindrome(n: Int): Boolean {
    val numberOfDigits = digitNumber(n)
    if (numberOfDigits == 1) return true
    val number = n
    var count = 0
    for (i in numberOfDigits downTo (numberOfDigits / 2)) {
        //смотри описание функции power() выше
        if ((number / power(10, i - 1) % 10) == (number / power(10, count) % 10)) {
            count++
        } else {
            return false
        }
    }
    return true
}

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun hasDifferentDigits(n: Int): Boolean {
    val numeralOnEqualDigits = n % 10
    val numberOfDigits = digitNumber(n)
    if (numberOfDigits == 1) return false
    for (i in numberOfDigits downTo 2) {
        if ((n / power(10, i - 1) % 10) == numeralOnEqualDigits) continue else return true
    }
    return false
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun squareSequenceDigit(n: Int): Int {
    if (n == 1) return 1
    var digitsSum = 0
    var count = 1
    while (digitsSum < n) {
        var sqrNum = sqr(count)
        var numberOfDigits = digitNumber(sqrNum)
        digitsSum += numberOfDigits
        count++
    }
    return sqr(count - 1) / power(10, digitsSum - n) % 10
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun fibSequenceDigit(n: Int): Int {
    if (n == 1) return 1
    var digitsSum = 0
    var count = 1
    while (digitsSum < n) {
        var sqrNum = fib(count)
        var numberOfDigits = digitNumber(sqrNum)
        digitsSum += numberOfDigits
        count++
    }
    return fib(count - 1) / power(10, digitsSum - n) % 10
}
