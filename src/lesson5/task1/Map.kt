@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson5.task1

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * Пример
 *
 * Для заданного списка покупок `shoppingList` посчитать его общую стоимость
 * на основе цен из `costs`. В случае неизвестной цены считать, что товар
 * игнорируется.
 */
fun shoppingListCost(
        shoppingList: List<String>,
        costs: Map<String, Double>): Double {
    var totalCost = 0.0

    for (item in shoppingList) {
        val itemCost = costs[item]
        if (itemCost != null) {
            totalCost += itemCost
        }
    }

    return totalCost
}

/**
 * Пример
 *
 * Для набора "имя"-"номер телефона" `phoneBook` оставить только такие пары,
 * для которых телефон начинается с заданного кода страны `countryCode`
 */
fun filterByCountryCode(
        phoneBook: MutableMap<String, String>,
        countryCode: String) {
    val namesToRemove = mutableListOf<String>()

    for ((name, phone) in phoneBook) {
        if (!phone.startsWith(countryCode)) {
            namesToRemove.add(name)
        }
    }

    for (name in namesToRemove) {
        phoneBook.remove(name)
    }
}

/**
 * Пример
 *
 * Для заданного текста `text` убрать заданные слова-паразиты `fillerWords`
 * и вернуть отфильтрованный текст
 */
fun removeFillerWords(
        text: List<String>,
        vararg fillerWords: String): List<String> {
    val fillerWordSet = setOf(*fillerWords)

    val res = mutableListOf<String>()
    for (word in text) {
        if (word !in fillerWordSet) {
            res += word
        }
    }
    return res
}

/**
 * Пример
 *
 * Для заданного текста `text` построить множество встречающихся в нем слов
 */
fun buildWordSet(text: List<String>): MutableSet<String> {
    val res = mutableSetOf<String>()
    for (word in text) res.add(word)
    return res
}

/**
 * Средняя
 *
 * Объединить два ассоциативных массива `mapA` и `mapB` с парами
 * "имя"-"номер телефона" в итоговый ассоциативный массив, склеивая
 * значения для повторяющихся ключей через запятую.
 * В случае повторяющихся *ключей* значение из mapA должно быть
 * перед значением из mapB.
 *
 * Повторяющиеся *значения* следует добавлять только один раз.
 *
 * Например:
 *   mergePhoneBooks(
 *     mapOf("Emergency" to "112", "Police" to "02"),
 *     mapOf("Emergency" to "911", "Police" to "02")
 *   ) -> mapOf("Emergency" to "112, 911", "Police" to "02")
 */
fun mergePhoneBooks(mapA: Map<String, String>, mapB: Map<String, String>): Map<String, String> {
    val mapC = mapA.toMutableMap()
    for ((key, value) in mapB) {
        if (mapA.containsKey(key)) {
            if (!mapA.containsValue(value)) {
                mapC[key] = "${mapA[key]}, $value"
            }
        } else {
            mapC[key] = value
        }
    }
    return mapC
}

/**
 * Простая
 *
 * По заданному ассоциативному массиву "студент"-"оценка за экзамен" построить
 * обратный массив "оценка за экзамен"-"список студентов с этой оценкой".
 *
 * Например:
 *   buildGrades(mapOf("Марат" to 3, "Семён" to 5, "Михаил" to 5))
 *     -> mapOf(5 to listOf("Семён", "Михаил"), 3 to listOf("Марат"))
 */
fun buildGrades(grades: Map<String, Int>): Map<Int, List<String>> {
    val students = mutableMapOf<Int, List<String>>()
    val listOfKeys = grades.keys.sortedDescending()
    for (key in listOfKeys) {
        students[grades[key]!!] = (students[grades[key]!!] ?: listOf()) + listOf(key)
    }
    return students
}

/**
 * Простая
 *
 * Определить, входит ли ассоциативный массив a в ассоциативный массив b;
 * это выполняется, если все ключи из a содержатся в b с такими же значениями.
 *
 * Например:
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")) -> true
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")) -> false
 */
fun containsIn(a: Map<String, String>, b: Map<String, String>): Boolean {
    val c = mergePhoneBooks(a,b)
    return c == b
}

/**
 * Средняя
 *
 * Для заданного списка пар "акция"-"стоимость" вернуть ассоциативный массив,
 * содержащий для каждой акции ее усредненную стоимость.
 *
 * Например:
 *   averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
 *     -> mapOf("MSFT" to 150.0, "NFLX" to 40.0)
 */
fun averageStockPrice(stockPrices: List<Pair<String, Double>>): Map<String, Double> {
    val sum = mutableMapOf<String, Double>()
    val mapOfCounts = mutableMapOf<String, Int>()
    for ((key, value) in stockPrices) {
        if (sum.containsKey(key)) {
            sum[key] = sum[key]!! + value
            mapOfCounts[key] = mapOfCounts[key]!! + 1
        } else {
            sum[key] = sum[key] ?: 0.0 + value
            mapOfCounts[key] = 1
        }
    }
    sum.forEach { (key) ->
        sum[key] = sum[key]!! / mapOfCounts[key]!!
    }
    return sum
}

/**
 * Средняя
 *
 * Входными данными является ассоциативный массив
 * "название товара"-"пара (тип товара, цена товара)"
 * и тип интересующего нас товара.
 * Необходимо вернуть название товара заданного типа с минимальной стоимостью
 * или null в случае, если товаров такого типа нет.
 *
 * Например:
 *   findCheapestStuff(
 *     mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
 *     "печенье"
 *   ) -> "Мария"
 */
fun findCheapestStuff(stuff: Map<String, Pair<String, Double>>, kind: String): String? {
    var minPrice = Double.MAX_VALUE
    var result: String? = null
    for ((key, value) in stuff) {
        if (value.first == kind && minPrice >= value.second) {
            result = key
            minPrice = value.second
        }
    }
    return result
}

/**
 * Сложная
 *
 * Для заданного ассоциативного массива знакомых через одно рукопожатие `friends`
 * необходимо построить его максимальное расширение по рукопожатиям, то есть,
 * для каждого человека найти всех людей, с которыми он знаком через любое
 * количество рукопожатий.
 * Считать, что все имена людей являются уникальными, а также что рукопожатия
 * являются направленными, то есть, если Марат знает Свету, то это не означает,
 * что Света знает Марата.
 *
 * Например:
 *   propagateHandshakes(
 *     mapOf(
 *       "Marat" to setOf("Mikhail", "Sveta"),
 *       "Sveta" to setOf("Marat"),
 *       "Mikhail" to setOf("Sveta")
 *     )
 *   ) -> mapOf(
 *          "Marat" to setOf("Mikhail", "Sveta"),
 *          "Sveta" to setOf("Marat", "Mikhail"),
 *          "Mikhail" to setOf("Sveta", "Marat")
 *        )
 */
// Назовём "родительскими" вершинами те, которые имеют "дочерние" вершины в качестве значений
// Я решил задачу через два прохода по графу, используя рекурсию
// Во FRIENDS хранится значение агрумента propagateHandshakes
// В BUFFER заносятся промежуточные и конечные значения для каждого человека
// NAMESTACK -- хранит список всех имён, вызванных до этого
var FRIENDS = mapOf<String, Set<String>>()
var BUFFER = mutableMapOf<String, MutableSet<String>>()
var NAMESTACK = mutableListOf<String>()


fun prpg (str: String) {
    if (FRIENDS[str] != null) {
        // вызываю функцию, пока не дойду до родительской вершины, имени, отсутствующего в FRIENDS,
        // или пустого множества
        if (!NAMESTACK.contains(str)) {
            // добавляю имя, для которого вызывалась функция, ко всем значениям имён в стеке
            for (name in NAMESTACK) {
                if (BUFFER[name] == null) {
                    BUFFER[name] = mutableSetOf()
                }
                BUFFER[name]!!.add(str)
            }
            if (FRIENDS[str]!!.isEmpty()) {
                // создаю для имён, имеющих пустое множество в качестве значения, запись в буфере
                BUFFER[str] = mutableSetOf()
            } else {
                // добавляю имя в стек и перехожу к дочерним вершинам
                NAMESTACK.add(str)
                for (element in FRIENDS[str]!!) {
                    prpg(element)
                }
                NAMESTACK.remove(NAMESTACK.last())
            }
        }
    } else {
        // создаю для имён, отсутствующих в FRIENDS, запись в буфере и добавляю их в значения всех имён в стеке
        for (name in NAMESTACK) {
            if (BUFFER[name] == null) {
                BUFFER[name] = mutableSetOf()
            }
            BUFFER[name]!!.add(str)
        }
        BUFFER[str] = mutableSetOf()
    }
}
// родительские вершины могут вызываться из дочерних, ещё до того, как функция прошла все вершины, необходимые,
// чтобы получить их полные значения. Чтобы этого избежать необходимо во второй раз пройти по графу и отдать
// дочерним вершинам уже полученные полные значения родительских вершин и их имена.
fun prpg2 (str: String) {
    if (FRIENDS[str] != null && BUFFER[str] != null) {
        if (!NAMESTACK.contains(str)) {
            // вызываю функцию для всех дочерних вершин, пока не наткнусь на родительскую или пустое множество
            // очень важно, чтобы в случае развлетвления функция сначала вызывалась для родительских вершин,
            // чтобы они могли отдать значения
            NAMESTACK.add(str)
            for (element in FRIENDS[str]!!.sortedBy { !NAMESTACK.contains(it) }) {
                prpg2(element)
            }
            NAMESTACK.remove(NAMESTACK.last())
        } else {
            // здесь программа отдаёт значение и имя верхней вершины всем именам в стеке
            for (name in NAMESTACK) {
                if (name != str) {
                    // name вычитается, потому что нельзя быть знакомым с самим собой
                    BUFFER[name]!!.addAll(BUFFER[str]!! + str)
                    BUFFER[name]!!.remove(name)
                }
            }
        }
    }
}

fun propagateHandshakes(friends: Map<String, Set<String>>): Map<String, Set<String>> {
    BUFFER = mutableMapOf()
    NAMESTACK = mutableListOf()
    FRIENDS = friends
    friends.forEach { (key) ->
        if (!BUFFER.containsKey(key)) {
            prpg(key)
            prpg2(key)
        }
    }
    return BUFFER
}



/**
 * Простая
 *
 * Удалить из изменяемого ассоциативного массива все записи,
 * которые встречаются в заданном ассоциативном массиве.
 * Записи считать одинаковыми, если и ключи, и значения совпадают.
 *
 * ВАЖНО: необходимо изменить переданный в качестве аргумента
 *        изменяемый ассоциативный массив
 *
 * Например:
 *   subtractOf(a = mutableMapOf("a" to "z"), mapOf("a" to "z"))
 *     -> a changes to mutableMapOf() aka becomes empty
 */
fun subtractOf(a: MutableMap<String, String>, b: Map<String, String>): Unit {
    val listToRemove = mutableListOf<String>()
    for ((key, value) in b) {
        if (a.containsKey(key) && a[key] == value) {
            listToRemove.add(key)
        }
    }
    for (element in listToRemove) {
        a.remove(element)
    }
}

/**
 * Простая
 *
 * Для двух списков людей найти людей, встречающихся в обоих списках
 */
fun whoAreInBoth(a: List<String>, b: List<String>): List<String> = a.filter { b.contains(it) }

/**
 * Средняя
 *
 * Для заданного набора символов определить, можно ли составить из него
 * указанное слово (регистр символов игнорируется)
 *
 * Например:
 *   canBuildFrom(listOf('a', 'b', 'o'), "baobab") -> true
 */
fun canBuildFrom(chars: List<Char>, word: String): Boolean {
    val setOfChar = chars.toSet()
    for (letter in word) {
        if (!setOfChar.contains(letter)) {
            return false
        }
    }
    return true
}


/**
 * Средняя
 *
 * Найти в заданном списке повторяющиеся элементы и вернуть
 * ассоциативный массив с информацией о числе повторений
 * для каждого повторяющегося элемента.
 * Если элемент встречается только один раз, включать его в результат
 * не следует.
 *
 * Например:
 *   extractRepeats(listOf("a", "b", "a")) -> mapOf("a" to 2)
 */
fun extractRepeats(list: List<String>): Map<String, Int> {
    var map = mutableMapOf<String, Int>()
    for (elem in list) {
        map[elem] = (map[elem] ?: 0) + 1
    }
    return map.filter { it.value != 1 }
}

/**
 * Средняя
 *
 * Для заданного списка слов определить, содержит ли он анаграммы
 * (два слова являются анаграммами, если одно можно составить из второго)
 *
 * Например:
 *   hasAnagrams(listOf("тор", "свет", "рот")) -> true
 */
fun hasAnagrams(words: List<String>): Boolean {
    for (i in 0 until words.size) {
        for (j in i + 1 until words.size) {
            if (canBuildFrom(words[i].toList(), words[j])) {
                return true
            }
        }
    }
    return false
}

/**
 * Сложная
 *
 * Для заданного списка неотрицательных чисел и числа определить,
 * есть ли в списке пара чисел таких, что их сумма равна заданному числу.
 * Если да, верните их индексы в виде Pair<Int, Int>;
 * если нет, верните пару Pair(-1, -1).
 *
 * Индексы в результате должны следовать в порядке (меньший, больший).
 *
 * Постарайтесь сделать ваше решение как можно более эффективным,
 * используя то, что вы узнали в данном уроке.
 *
 * Например:
 *   findSumOfTwo(listOf(1, 2, 3), 4) -> Pair(0, 2)
 *   findSumOfTwo(listOf(1, 2, 3), 6) -> Pair(-1, -1)
 */
fun findSumOfTwo(list: List<Int>, number: Int): Pair<Int, Int> {
    if (list.isEmpty()) {
        return -1 to -1
    }
    val map = mutableMapOf<Int, Int>()
    var middle = 0
    var temp = Int.MAX_VALUE
    //наполняю мап отсортированным списком и ищу значение близкое к number / 2
    for ((index, elem) in list.sorted().withIndex()) {
        map[index] = elem
        if (abs(number / 2 - elem) < temp) {
            middle = index
            temp = abs(number / 2 - elem)
        }
    }

    var count = middle
    var i = middle + 1

    //проверяю все подходящие значения больше middle, увеличивая middle
    while (map[count]!! + (map[count + 1] ?: number - map[count]!!) < number) {
        while (map[count]!! + map[i]!! <= number) {
            if (map[count]!! + map[i]!! == number) {
                return min(list.indexOf(map[count]), list.indexOf(map[i])) to
                        max(list.indexOf(map[count]), list.indexOf(map[i]))
            }
            i++
        }
        while (count - i != 0) {

        }
        count++
    }

    count = middle
    i = middle - 1

    //проверяю все подходящие значения меньше middle, уменьшая middle
    while (map[count]!! + (map[count - 1] ?: - map[count]!!) > number) {
        while (map[count]!! + map[i]!! >= number) {
            if (map[count]!! + map[i]!! == number) {
                return min(list.indexOf(map[count]), list.indexOf(map[i])) to
                        max(list.indexOf(map[count]), list.indexOf(map[i]))
            }
            i--
        }
        count--
    }

    count = middle
    i = middle - 1

    //проверяю все подходящие значения меньше middle, увеличивая middle
    while (count < map.size) {
        while (map[count]!! + (map[i] ?: - map[count]!!) >= number) {
            if (map[count]!! + map[i]!! == number) {
                return min(list.indexOf(map[count]), list.indexOf(map[i])) to
                        max(list.indexOf(map[count]), list.indexOf(map[i]))
            }
            i--
        }
        count++
    }

    count = middle
    i = middle + 1

    //проверяю все подходящие значения больше middle, уменьшая middle
    while (count >= 0) {
        while (map[count]!! + (map[i] ?: number - map[count]!! + 1) <= number) {
            if (map[count]!! + map[i]!! == number) {
                return min(list.indexOf(map[count]), list.indexOf(map[i])) to
                        max(list.indexOf(map[count]), list.indexOf(map[i]))
            }
            i++
        }
        count--
    }

    return -1 to -1
}

/**
 * Очень сложная
 *
 * Входными данными является ассоциативный массив
 * "название сокровища"-"пара (вес сокровища, цена сокровища)"
 * и вместимость вашего рюкзака.
 * Необходимо вернуть множество сокровищ с максимальной суммарной стоимостью,
 * которые вы можете унести в рюкзаке.
 *
 * Например:
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     850
 *   ) -> setOf("Кубок")
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     450
 *   ) -> emptySet()
 */

fun bagPacking(treasures: Map<String, Pair<Int, Int>>, capacity: Int): Set<String> {
    val result = mutableSetOf<String>()

    val items = mutableMapOf<Int, String>()
    val costs = mutableMapOf<Int, Int>()
    val itemsWeights = mutableMapOf<Int, Int>()
    var i = 1
    for ((key, value) in treasures) {
        items[i] = key
        costs[i] = value.second
        itemsWeights[i] = value.first
        i++
    }
    //создаём двумерный массив (далее "таблица"), который будет хранить промежуточные и окончательное значения
    //максимальной стоимости
    val array = Array(items.size + 1) { Array(capacity + 1) { 0 } }
    //начиная с первого предмета в списке, ищем максимальную стоимость при грузоподъёмности от 0 до capacity
    //сравниваем максимальную стоимость без предмета и с предметом и лучший результат заносим в таблицу
    //в последней ячейке останется точная максимальная стоимость
    //начинаем проход по ней с 1, так как её "края" array[0][valueOfCapacity] и array[numOfItem][0] не содержат
    //в себе предметов или имеют нулевую грузоподъёмность, и очевидно равны нулю
    for (numOfItem in 1..items.size) {
        for (valueOfCapacity in 1..capacity) {
            val costWithoutItem = array[numOfItem - 1][valueOfCapacity]
            //проверяем входит ли предмет в рюкзак по весу
            if (valueOfCapacity >= itemsWeights[numOfItem]!!) {
                val costWithItem =
                        array[numOfItem - 1][valueOfCapacity - itemsWeights[numOfItem]!!] + costs[numOfItem]!!
                array[numOfItem][valueOfCapacity] = max(costWithItem, costWithoutItem)
            } else {
                array[numOfItem][valueOfCapacity] = costWithoutItem
            }
        }
    }
    //------------------------------------------------------------------------------------------------------------
    //с помощью рекурсии ищем те предметы, на которых меняется общая стоимость и записываем их в result
    //пока не дойдём до краёв таблицы
    fun findItems(numOfItem: Int, valueOfCapacity: Int) {
        if (numOfItem != 0 && valueOfCapacity != 0) {
            if (array[numOfItem][valueOfCapacity] != array[numOfItem - 1][valueOfCapacity]) {
                findItems(numOfItem - 1, valueOfCapacity - itemsWeights[numOfItem]!!)
                result.add(items[numOfItem]!!)
            } else {
                findItems(numOfItem - 1, valueOfCapacity)
            }
        }
    }
    //------------------------------------------------------------------------------------------------------------
    findItems(items.size, capacity)
    return result
}
