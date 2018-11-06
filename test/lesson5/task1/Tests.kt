package lesson5.task1

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class Tests {
    @Test
    @Tag("Example")
    fun shoppingListCostTest() {
        val itemCosts = mapOf(
                "Хлеб" to 50.0,
                "Молоко" to 100.0
        )
        assertEquals(
                150.0,
                shoppingListCost(
                        listOf("Хлеб", "Молоко"),
                        itemCosts
                )
        )
        assertEquals(
                150.0,
                shoppingListCost(
                        listOf("Хлеб", "Молоко", "Кефир"),
                        itemCosts
                )
        )
        assertEquals(
                0.0,
                shoppingListCost(
                        listOf("Хлеб", "Молоко", "Кефир"),
                        mapOf()
                )
        )
    }

    @Test
    @Tag("Example")
    fun filterByCountryCode() {
        val phoneBook = mutableMapOf(
                "Quagmire" to "+1-800-555-0143",
                "Adam's Ribs" to "+82-000-555-2960",
                "Pharmakon Industries" to "+1-800-555-6321"
        )

        filterByCountryCode(phoneBook, "+1")
        assertEquals(2, phoneBook.size)

        filterByCountryCode(phoneBook, "+1")
        assertEquals(2, phoneBook.size)

        filterByCountryCode(phoneBook, "+999")
        assertEquals(0, phoneBook.size)
    }

    @Test
    @Tag("Example")
    fun removeFillerWords() {
        assertEquals(
                "Я люблю Котлин".split(" "),
                removeFillerWords(
                        "Я как-то люблю Котлин".split(" "),
                        "как-то"
                )
        )
        assertEquals(
                "Я люблю Котлин".split(" "),
                removeFillerWords(
                        "Я как-то люблю таки Котлин".split(" "),
                        "как-то",
                        "таки"
                )
        )
        assertEquals(
                "Я люблю Котлин".split(" "),
                removeFillerWords(
                        "Я люблю Котлин".split(" "),
                        "как-то",
                        "таки"
                )
        )
    }

    @Test
    @Tag("Example")
    fun buildWordSet() {
        assertEquals(
                buildWordSet("Я люблю Котлин".split(" ")),
                mutableSetOf("Я", "люблю", "Котлин")
        )
        assertEquals(
                buildWordSet("Я люблю люблю Котлин".split(" ")),
                mutableSetOf("Котлин", "люблю", "Я")
        )
        assertEquals(
                buildWordSet(listOf()),
                mutableSetOf<String>()
        )
    }

    @Test
    @Tag("Normal")
    fun mergePhoneBooks() {
        assertEquals(
                mapOf("" to "b(Yv, ", "a" to ""),
                mergePhoneBooks(
                        mapOf("" to "b(Yv", "a" to ""),
                        mapOf("" to "")
                )
        )
        assertEquals(
                mapOf("Emergency" to "112", "Police" to "02"),
                mergePhoneBooks(
                        mapOf("Emergency" to "112"),
                        mapOf("Emergency" to "112", "Police" to "02")
                )
        )
        assertEquals(
                mapOf("Emergency" to "112, 911", "Police" to "02"),
                mergePhoneBooks(
                        mapOf("Emergency" to "112"),
                        mapOf("Emergency" to "911", "Police" to "02")
                )
        )
        assertEquals(
                mapOf("Emergency" to "112, 911", "Fire department" to "01", "Police" to "02"),
                mergePhoneBooks(
                        mapOf("Emergency" to "112", "Fire department" to "01"),
                        mapOf("Emergency" to "911", "Police" to "02")
                )
        )
        assertEquals(
                mapOf("Emergency" to "112, 912", "Police" to "03, 02", "Fire" to "90", "Water" to "7395"),
                mergePhoneBooks(
                        mapOf("Emergency" to "112", "Police" to "03", "Fire" to "90"),
                        mapOf("Emergency" to "911", "Emergency" to "912", "Police" to "02", "Water" to "7395")
                )
        )
    }

    @Test
    @Tag("Easy")
    fun buildGrades() {
        assertEquals(
                mapOf<Int, List<String>>(),
                buildGrades(mapOf())
        )
        // TODO: Sort the values here or let the students do it?
        assertEquals(
                mapOf(5 to listOf("Семён", "Михаил"), 3 to listOf("Марат")),
                buildGrades(mapOf("Марат" to 3, "Семён" to 5, "Михаил" to 5))
        )
        assertEquals(
                mapOf(3 to listOf("Семён", "Михаил", "Марат")),
                buildGrades(mapOf("Марат" to 3, "Семён" to 3, "Михаил" to 3))
        )
    }


    @Test
    @Tag("Easy")
    fun containsIn() {
        assertTrue(containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")))
        assertFalse(containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")))
        assertFalse(containsIn(mapOf("abc" to "zrk", "try" to "45", "ghr" to "____4"), mapOf("a" to "z", "b" to "sweet",
                "abc" to "zrk", "ghr" to "____4", "try" to "4465")))
        assertTrue(containsIn(mapOf(), mapOf("a" to "zee", "b" to "sweet")))
        assertFalse(containsIn(mapOf("a" to "z"), mapOf()))
    }

    @Test
    @Tag("Normal")
    fun averageStockPrice() {
        assertEquals(
                mapOf<String, Double>(),
                averageStockPrice(listOf())
        )
        assertEquals(
                mapOf("MSFT" to 100.0, "NFLX" to 40.0),
                averageStockPrice(listOf("MSFT" to 100.0, "NFLX" to 40.0))
        )
        assertEquals(
                mapOf("MSFT" to 150.0, "NFLX" to 40.0),
                averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
        )
        assertEquals(
                mapOf("MSFT" to 150.0, "NFLX" to 45.0),
                averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0, "NFLX" to 50.0))
        )
    }

    @Test
    @Tag("Normal")
    fun findCheapestStuff() {
        assertNull(
                findCheapestStuff(
                        mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
                        "торт"
                )
        )
        assertEquals(
                "Мария",
                findCheapestStuff(
                        mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
                        "печенье"
                )
        )



    }

    @Test
    @Tag("Hard")
    fun propagateHandshakes() {


        assertEquals(
                mapOf(
                        "Marat" to setOf("Mikhail", "Sveta"),
                        "Sveta" to setOf("Marat", "Mikhail"),
                        "Mikhail" to setOf("Sveta", "Marat")
                ),
                propagateHandshakes(
                        mapOf(
                                "Marat" to setOf("Mikhail", "Sveta"),
                                "Sveta" to setOf("Marat"),
                                "Mikhail" to setOf("Sveta")
                        )
                )
        )
        assertEquals(
                mapOf(
                        "Marat" to setOf("Mikhail"),
                        "Mikhail" to setOf()
                ),
                propagateHandshakes(
                        mapOf(
                                "Marat" to setOf("Mikhail"),
                                "Mikhail" to setOf()
                        )
                )
        )
        assertEquals(
                mapOf(
                        "Marat" to setOf("Sveta", "Mikhail", "Zhenya", "Kolya","Gena"),
                        "Sveta" to setOf("Mikhail", "Marat", "Zhenya", "Kolya","Gena"),
                        "Mikhail" to setOf("Sveta", "Marat", "Zhenya", "Kolya","Gena"),
                        "Zhenya" to setOf("Kolya","Gena", "Marat", "Sveta", "Mikhail"),
                        "Kolya" to setOf("Gena", "Marat", "Sveta", "Mikhail", "Zhenya"),
                        "Gena" to setOf()
                ),
                propagateHandshakes(
                        mapOf(
                                "Marat" to setOf("Sveta"),
                                "Sveta" to setOf("Mikhail"),
                                "Mikhail" to setOf("Zhenya"),
                                "Zhenya" to setOf("Kolya"),
                                "Kolya" to setOf("Gena", "Marat")
                        )
                )
        )
        assertEquals(
                mapOf(
                        "D" to setOf("A", "B", "C", "Z", "Y", "W", "V"),
                        "C" to setOf("A", "B", "Z", "D", "Y", "W", "V"),
                        "B" to setOf("A", "C", "Z", "D", "Y", "W", "V"),
                        "A" to setOf( "B", "C", "Z", "D", "Y", "W", "V"),
                        "Z" to setOf("Y", "W", "V"),
                        "Y" to setOf( "W", "V"),
                        "W" to setOf(),
                        "V" to setOf()
                ),
                propagateHandshakes(
                        mapOf(
                                "D" to setOf("C", "Z"),
                                "C" to setOf("B","D"),
                                "B" to setOf("A", "C"),
                                "A" to setOf( "B"),
                                "Z" to setOf("Y"),
                                "Y" to setOf("W", "V")
                        )
                )
        )
        assertEquals(
                mapOf(
                        "Sveta" to setOf("Zhenya", "Mikhail", "Pavel", "Zolya"),
                        "Mikhail" to setOf("Zhenya", "Sveta", "Pavel", "Zolya"),
                        "Zhenya" to setOf(),
                        "Zolya" to setOf("Sveta", "Zhenya", "Mikhail", "Pavel"),
                        "Pavel" to setOf()
                ),
                propagateHandshakes(
                        mapOf(
                                "Zolya" to setOf("Zhenya", "Mikhail", "Pavel"),
                                "Mikhail" to setOf("Sveta"),
                                "Sveta" to setOf("Mikhail", "Pavel", "Zolya" )
                        )
                )
        )
        assertEquals(
                mapOf(
                        "Marat" to setOf("Sveta", "Mikhail", "Zhenya", "Kolya", "Gena",
                                "Igor", "Ivan","Pavel", "Nikita"),
                        "Sveta" to setOf("Mikhail", "Zhenya", "Kolya", "Gena", "Nikita",
                                "Igor", "Ivan"),
                        "Mikhail" to setOf(),
                        "Zhenya" to setOf("Mikhail", "Kolya", "Gena", "Nikita",
                                "Igor", "Ivan"),
                        "Kolya" to setOf(),
                        "Gena" to setOf("Mikhail", "Nikita", "Igor", "Ivan", "Kolya"),
                        "Igor" to setOf("Mikhail"),
                        "Ivan" to setOf("Kolya"),
                        "Nikita" to setOf(),
                        "Pavel" to setOf(),
                        "Jack" to setOf("Pavel")
                ),
                propagateHandshakes(
                        mapOf(
                                "Marat" to setOf("Sveta", "Pavel"),
                                "Jack" to setOf("Pavel"),
                                "Sveta" to setOf("Mikhail", "Zhenya"),
                                "Mikhail" to setOf(),
                                "Zhenya" to setOf("Kolya", "Gena"),
                                "Kolya" to setOf(),
                                "Gena" to setOf("Igor", "Ivan", "Nikita"),
                                "Igor" to setOf("Mikhail"),
                                "Ivan" to setOf("Kolya"),
                                "Nikita" to setOf(),
                                "Pavel" to setOf()
                        )
                )
        )
        assertEquals(
                mapOf(
                        "Marat" to setOf("Sveta", "Mikhail", "Zhenya", "Kolya","Gena"),
                        "Sveta" to setOf("Mikhail", "Marat", "Zhenya", "Kolya","Gena"),
                        "Mikhail" to setOf("Sveta", "Marat", "Zhenya", "Kolya","Gena"),
                        "Zhenya" to setOf("Kolya","Gena"),
                        "Kolya" to setOf("Gena"),
                        "Gena" to setOf()
                ),
                propagateHandshakes(
                        mapOf(
                                "Marat" to setOf("Sveta", "Zhenya"),
                                "Sveta" to setOf("Mikhail", "Marat"),
                                "Mikhail" to setOf("Sveta"),
                                "Zhenya" to setOf("Kolya"),
                                "Kolya" to setOf("Gena")
                        )
                )
        )
        assertEquals(
                mapOf(
                        "Marat" to setOf("Sveta", "Mikhail"),
                        "Sveta" to setOf("Mikhail", "Marat"),
                        "Mikhail" to setOf("Sveta", "Marat")
                ),
                propagateHandshakes(
                        mapOf(
                                "Marat" to setOf("Sveta"),
                                "Sveta" to setOf("Mikhail", "Marat"),
                                "Mikhail" to setOf("Sveta")
                        )
                )
        )
        assertEquals(
                mapOf(
                        "Marat" to setOf("Sveta", "Mikhail", "Zhenya", "Kolya","Gena"),
                        "Sveta" to setOf("Mikhail", "Marat", "Zhenya", "Kolya","Gena"),
                        "Mikhail" to setOf("Sveta", "Marat", "Zhenya", "Kolya","Gena"),
                        "Zhenya" to setOf("Sveta", "Mikhail", "Marat", "Kolya","Gena"),
                        "Kolya" to setOf("Sveta", "Mikhail", "Zhenya", "Marat","Gena"),
                        "Gena" to setOf()
                ),
                propagateHandshakes(
                        mapOf(
                                "Marat" to setOf("Sveta", "Zhenya"),
                                "Sveta" to setOf("Mikhail"),
                                "Mikhail" to setOf("Kolya"),
                                "Zhenya" to setOf("Gena", "Marat"),
                                "Kolya" to setOf("Zhenya")
                        )
                )
        )
    }

    @Test
    @Tag("Easy")
    fun subtractOf() {
        var from = mutableMapOf("a" to "z", "b" to "c")

        subtractOf(from, mapOf())
        assertEquals(from, mapOf("a" to "z", "b" to "c"))

        subtractOf(from, mapOf("b" to "z"))
        assertEquals(from, mapOf("a" to "z", "b" to "c"))

        subtractOf(from, mapOf("a" to "z"))
        assertEquals(from, mapOf("b" to "c"))

        from = mutableMapOf()
        subtractOf(from, mapOf())
        assertEquals(from, mapOf<String, String>())
    }

    @Test
    @Tag("Easy")
    fun whoAreInBoth() {
        assertEquals(
                emptyList<String>(),
                whoAreInBoth(emptyList(), emptyList())
        )
        assertEquals(
                listOf("Marat"),
                whoAreInBoth(listOf("Marat", "Mikhail"), listOf("Marat", "Kirill"))
        )
        assertEquals(
                emptyList<String>(),
                whoAreInBoth(listOf("Marat", "Mikhail"), listOf("Sveta", "Kirill"))
        )
    }

    @Test
    @Tag("Normal")
    fun canBuildFrom() {
        assertFalse(canBuildFrom(emptyList(), "foo"))
        assertTrue(canBuildFrom(listOf('a', 'b', 'o'), "baobab"))
        assertFalse(canBuildFrom(listOf('a', 'm', 'r'), "Marat"))
        assertTrue(canBuildFrom(listOf('I'), "i"))
    }

    @Test
    @Tag("Normal")
    fun extractRepeats() {
        assertEquals(
                emptyMap<String, Int>(),
                extractRepeats(emptyList())
        )
        assertEquals(
                mapOf("a" to 2),
                extractRepeats(listOf("a", "b", "a"))
        )
        assertEquals(
                emptyMap<String, Int>(),
                extractRepeats(listOf("a", "b", "c"))
        )
    }

    @Test
    @Tag("Normal")
    fun hasAnagrams() {
        assertFalse(hasAnagrams(emptyList()))
        assertTrue(hasAnagrams(listOf("рот", "свет", "тор")))
        assertFalse(hasAnagrams(listOf("рот", "свет", "код", "дверь")))
        assertTrue(hasAnagrams(listOf("рот", "свет", "рот")))
        assertFalse(hasAnagrams(listOf("t",
                "9&wl;FxRinxR@oOo2",
                "k/#>qb8VttCrk|f\nId?&S V9k8!|0+a\nh_u^!1_?YuGHk\"iYZ;4dfq`C;:\nA<Uzp!QXEd#5q@h=:",
                "|",
                "4N",
                "6k$1rZ|P\"\nZAJG:VD\"TE[ex:Quq>N-O|fW[]6jz[[IF+pt1=-2>)P'UY)S8Ta@g0T!\tCNc#Q40vR9e$'Iez={XR6^\nh62){&]K_<C.S[^v[||reXNTmzlI*g\"\n",
                "Y`vb",
                "Q!",
                "8A:D(Wo!Pd$5!OufEsh^tc0Wx;CSdr1=,'tQ*+oK@B2DIns&tm>_SM/KEe?u\\u9p",
                "'\nw5\"a[D\t|a6u+\$r+\taM/*)Jw,0eO/oc,_sp~.)<]Vg(,w63}Tk\$zRvUIw$:P/gblwkW P!Tb]^f| Q=6P/|+<Zh`!]#f\t,v+?8&",
                "4l7\tY;Gr1~ql0r>0:`c(]t#D[F>U+P=1Lb`_o.xSt;(b e+Nm>goK{MtSs{6ck<\t!a\\QfuK10+P^bi\$q#gn5szVu*vtCi4lRZ3#$!l3K8y\$Dbep>k~\tF7+\\5j0da:4<D&1#-(}ow|$,L&Ak+eqO7/D/R)K9BVrM`X{h<")))
    }

    @Test
    @Tag("Hard")
    fun findSumOfTwo() {
        assertEquals(
                Pair( 7, 9),
                findSumOfTwo(listOf(40700,
                        40699,
                        34662,
                        40699,
                        31110,
                        28564,
                        40345,
                        0,
                        2125,
                        0,
                        40699,
                        48508,
                        31108,
                        41883,
                        40700,
                        30048,
                        1,
                        40699,
                        30908,
                        48146,
                        13707,
                        0,
                        27579,
                        40700,
                        1,
                        40700,
                        40700,
                        1,
                        1395,
                        1,
                        18765,
                        40699,
                        40699,
                        40700,
                        18394,
                        40700,
                        18752,
                        40699,
                        0,
                        1,
                        40699,
                        40700,
                        38084,
                        40699,
                        1,
                        40700,
                        40699,
                        0,
                        1,
                        24048,
                        40699,
                        1,
                        43272,
                        40699,
                        42932,
                        13720,
                        40699,
                        1,
                        26892,
                        40700,
                        40700,
                        41877,
                        38544,
                        40699,
                        0,
                        0,
                        0,
                        40699,
                        28527,
                        40699,
                        23517,
                        0,
                        14227,
                        31964,
                        1,
                        0,
                        39795,
                        40699,
                        1,
                        31590,
                        40699,
                        9191,
                        25661,
                        40699,
                        584,
                        4598,
                        40700,
                        0,
                        40700,
                        9506,
                        32628,
                        28217,
                        13026,
                        9292,
                        0,
                        40699,
                        13879,
                        1,
                        40699,
                        1,
                        1,
                        26477,
                        40700,
                        40700,
                        1,
                        27781,
                        40699,
                        19963,
                        40699,
                        2269,
                        1), 0)
        )
        assertEquals(
                Pair(-1, -1),
                findSumOfTwo(emptyList(), 1)
        )
        assertEquals(
                Pair(0, 2),
                findSumOfTwo(listOf(1, 2, 3), 4)
        )
        assertEquals(
                Pair(-1, -1),
                findSumOfTwo(listOf(1, 2, 3), 6)
        )
        assertEquals(
                Pair(1, 4),
                findSumOfTwo(listOf(2, 1, 3, 109, 19, 6, 88, 7), 20)
        )
        assertEquals(
                Pair(0, 9),
                findSumOfTwo(listOf(77, 778, 3, 109, 19, 6, 88, 7, 5, 33), 110)
        )

    }

    @Test
    @Tag("Impossible")
    fun bagPacking() {
        assertEquals(
                setOf("Кубок"),
                bagPacking(
                        mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
                        850
                )
        )
        assertEquals(
                emptySet<String>(),
                bagPacking(
                        mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
                        450
                )
        )
        assertEquals(
                setOf("A"),
                bagPacking(
                        mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000), "A" to (999 to 1000000)),
                        1001
                )
        )
    }

    // TODO: map task tests
}
