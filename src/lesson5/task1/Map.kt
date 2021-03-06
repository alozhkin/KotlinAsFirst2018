@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson5.task1

import kotlin.math.max

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
    val mapC = mutableMapOf<String, MutableList<String>>()
    val mapD = mutableMapOf<String, String>()
    for ((key, value) in mapA) {
        mapC.getOrPut(key) { mutableListOf() }.add(value)
    }
    for ((key, value) in mapB) {
        if (value != mapA[key]) {
            mapC.getOrPut(key) { mutableListOf() }.add(value)
        }
    }
    for (key in mapC.keys) {
        mapD[key] = mapC[key]!!.joinToString(separator = ", ")
    }
    return mapD
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
fun containsIn(a: Map<String, String>, b: Map<String, String>): Boolean =
    a.all { b.contains(it.key) && b[it.key] == it.value }


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
    val prices = stockPrices.groupBy({it.first}, {it.second})
    val average = mutableMapOf<String, Double>()
    for (key in prices.keys) {
        var count = 0
        for (price in prices[key]!!) {
            count++
        }
        average[key] = prices[key]!!.fold(0.0) { result, it -> result + it / count }
    }
    return average
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
fun findCheapestStuff(stuff: Map<String, Pair<String, Double>>, kind: String): String? =
        stuff.filter { (key, value) -> kind == value.first }.minBy { (key,value) -> value.second }?.key

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

fun propagateHandshakes(friends: Map<String, Set<String>>): Map<String, Set<String>> {

    val nameStack = mutableListOf<String>()
    val buffer = mutableMapOf<String, MutableSet<String>>()
    val alreadyWas = mutableMapOf<String, Boolean>()

    //--------------------------------------------------------------------------------------------------------
    fun foo(name: String) {
        // вызываю функцию, пока не дойду до вершины, которая уже была
        if ((friends[name]?.isEmpty() == true || friends[name]?.isEmpty() == null) && buffer[name] == null) {
            // создаю для имён, отсутствующих в FRIENDS, запись в буфере и добавляю их в значения всех имён в стеке
            // создаю для имён, имеющих пустое множество в качестве значения, запись в буфере
            buffer[name] = mutableSetOf()
            for (str in nameStack) {
                buffer.getOrPut(str) { mutableSetOf() }.add(name)
            }
        } else {
            nameStack.add(name)
            // добавляю значения имени, для которого вызывалась функция, ко всем значениям имён в стеке
            for (elem in friends[name]!!) {
                for (str in nameStack) {
                    if (str != elem) {
                        buffer.getOrPut(str) { mutableSetOf() }.add(elem)
                    }
                }
                if (buffer[elem] == null) {
                    foo(elem)
                }
            }
            nameStack.remove(nameStack.last())
        }
    }
    //-------------------------------------------------------------------------------------------------------
    // родительские вершины могут вызываться из дочерних, ещё до того, как функция прошла все вершины, необходимые,
    // чтобы получить их полные значения. Чтобы этого избежать необходимо во второй раз пройти по графу и отдать
    // дочерним вершинам уже полученные полные значения родительских вершин и их имена.
    fun foo2(str: String) {
        if (friends[str] != null && buffer[str] != null) {
            nameStack.add(str)
            for (elem in friends[str]!!.sortedBy { !nameStack.contains(it) }) {
                for (name in nameStack) {
                    buffer[name]!!.addAll(buffer[elem]!! + elem - name)
                }
                if (alreadyWas[elem] != true) {
                    alreadyWas[elem] = true
                    foo2(elem)
                }
            }
            nameStack.remove(nameStack.last())
        }
    }
    //----------------------------------------------------------------------------------------------------

    friends.keys.forEach { key ->
        if (!buffer.containsKey(key)) {
            foo(key)
            foo2(key)
        }
    }

    return buffer
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
    for ((key, value) in b) {
        if (a.containsKey(key) && a[key] == value) {
            a.remove(key)
        }
    }
}

/**
 * Простая
 *
 * Для двух списков людей найти людей, встречающихся в обоих списках
 */
fun whoAreInBoth(a: List<String>, b: List<String>): List<String> = a.filter { b.contains(it) }.distinct()

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
    val wordSet = word.toSet().map { it.toLowerCase() }.toSet()
    val charsSet = chars.toSet().map { it.toLowerCase() }.toSet()
    return charsSet.containsAll(wordSet)
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
    val map = mutableMapOf<String, Int>()
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
    val temp = words.map { it.toList().sorted() }
    val sortedWords = temp.mapIndexed { index, i -> index to i }.toMap()
    for (i in 0 until words.size) {
        for (j in i + 1 until words.size) {
            if (sortedWords[i] == sortedWords[j]) {
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

    val map = mutableMapOf<Int, Int>()

    list.forEachIndexed { index, value ->
        val indexOfPair = map[number - value]
        if (indexOfPair != null) {
            return indexOfPair to index
        } else {
            map[value] = index
        }
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
