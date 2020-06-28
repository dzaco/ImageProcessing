package giedronowicz.windowCompontets;

import giedronowicz.Task;

import javax.swing.*;
import javax.tools.Tool;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Window extends JFrame {


    public Window() {
        super("Przetwarzanie obrazów - Jacek Giedronowicz");


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setLayout( new GridLayout(3,3) );

        getContentPane().add(new Pane( this.list1() ));
        getContentPane().add(new Pane( this.list2() ));
        getContentPane().add(new Pane( this.list3() ));
        getContentPane().add(new Pane( this.list4() ));
        getContentPane().add(new Pane( this.list5() ));
        getContentPane().add(new Pane( this.list6() ));


        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.pack();
        this.setVisible(true);
    }


    private List<List<Pair>> tasks()
    {
//        List<String> list1 = new ArrayList<>();     // lista 1 : Operacje ujednolicania obrazów
//        list1.add("1. Ujednolicenie obrazów szarych geometryczne");
//        list1.add("2. Ujednolicenie obrazów szarych rozdzielczościowe");
//        list1.add("3. Ujednolicenie obrazów RGB geometryczne");
//        list1.add("4. Ujednolicenie obrazów RGB rozdzielczościowe");
//
//        List<String> list2 = new ArrayList<>();     // lista 2 : Operacje sumowania arytmetycznego obrazów szarych
//        list2.add("1.1. Sumowanie stałej z obrazem oraz dwóch obrazów");
//        list2.add("1.2. Sumowanie dwóch obrazów");
//        list2.add("2.1. Mnozenie obrazu przez zadaną liczb̨ę");
//        list2.add("2.2. Mnozenie obrazu przez inny obraz");
//        list2.add("3. Mieszanie obrazów z okreslonym współczynnikiem");
//        list2.add("4. Potęgowanie obrazu");
//        list2.add("5.1. Dzielenie obrazu przez liczbę");
//        list2.add("5.2. Dzielenie obrazu przez inny obraz");
//        list2.add("6. Pierwiastkowanie obrazu");
//        list2.add("7. Logarytmowanie obrazu");
//
//        List<String> list3 = new ArrayList<>();     // lista 3 : Operacje sumowania arytmetycznego obrazów barwowych
//        list3.add("1.1. Sumowanie stałej z obrazem oraz dwóch obrazów");
//        list3.add("1.2. Sumowanie dwóch obrazów");
//        list3.add("2.1. Mnozenie obrazu przez zadaną liczb̨ę");
//        list3.add("2.2. Mnozenie obrazu przez inny obraz");
//        list3.add("3. Mieszanie obrazów z okreslonym współczynnikiem");
//        list3.add("4. Potęgowanie obrazu");
//        list3.add("5.1. Dzielenie obrazu przez liczbę");
//        list3.add("5.2. Dzielenie obrazu przez inny obraz");
//        list3.add("6. Pierwiastkowanie obrazu");
//        list3.add("7. Logarytmowanie obrazu");
//
//        List<String> list4 = new ArrayList<>();     // lista 4 : Operacje geometryczne na obrazie
//        list4.add("1. Przemieszczenie obrazu o zadany wektor");
//        list4.add("2. Jednorodne i niejednorodne skalowanie obrazu");
//        list4.add("3. Obracanie obrazu o dowolny kąt");
//        list4.add("4.1. Symetrie względem osi układu");
//        list4.add("4.2. Symetrie względem zadanej prostej");
//        list4.add("5. Wycinanie fragmentów obrazu");
//        list4.add("6. Kopiowanie fragmentów obrazów");
//
//        List<String> list5 = new ArrayList<>();     // lista 5 : Operacje na histogramie obrazu szarego
//        list5.add("1. Obliczanie histogramu");
//        list5.add("2. Przemieszczanie histogramu");
//        list5.add("3. Rozciąganie histogramu");
//        list5.add("4. Progowanie lokalne");
//        list5.add("5. Progowanie globalne");
//
//        List<String> list6 = new ArrayList<>();     // lista 6 : Operacje na histogramie obrazu barwowego
//        list6.add("1. Obliczanie histogramu");
//        list6.add("2. Przemieszczanie histogramu");
//        list6.add("3. Rozci¡ganie histogramu");
//        list6.add("4.1. Progowanie 1-progowe lokalne");
//        list6.add("4.2. Progowanie 1-progowe globalne");
//        list6.add("5.1. Progowanie wieloprogowe lokalne");
//        list6.add("5.2. Progowanie wieloprogowe globalne");
//
//        List<List<String>> allTasks = new ArrayList<>();
//        allTasks.add(list1);
//        allTasks.add(list2);
//        allTasks.add(list3);
//        allTasks.add(list4);
//        allTasks.add(list5);
//        allTasks.add(list6);
//        return  allTasks;
//        Object[] list1 = {  // lista 1 : Operacje ujednolicania obrazów
//            "1. Ujednolicenie obrazów szarych geometryczne",
//            "2. Ujednolicenie obrazów szarych rozdzielczościowe",
//            "3. Ujednolicenie obrazów RGB geometryczne",
//            "4. Ujednolicenie obrazów RGB rozdzielczościowe"
//        };
//
//
//        Object[] list2 = {        // lista 2 : Operacje sumowania arytmetycznego obrazów szarych
//                "1.1. Sumowanie stałej z obrazem oraz dwóch obrazów",
//                "1.2. Sumowanie dwóch obrazów",
//                "2.1. Mnozenie obrazu przez zadan̨ą liczb̨ę",
//                "2.2. Mnozenie obrazu przez inny obraz",
//                "3. Mieszanie obrazów z okreslonym współczynnikiem",
//                "4. Potęgowanie obrazu",
//                "5.1. Dzielenie obrazu przez liczbę",
//                "5.2. Dzielenie obrazu przez inny obraz",
//                "6. Pierwiastkowanie obrazu",
//                "7. Logarytmowanie obrazu"
//        };
//
//        Object[] list3 = {        // lista 3 : Operacje sumowania arytmetycznego obrazów barwowych
//                "1.1. Sumowanie stałej z obrazem oraz dwóch obrazów",
//                "1.2. Sumowanie dwóch obrazów",
//                "2.1. Mnozenie obrazu przez zadan̨ą liczb̨ę",
//                "2.2. Mnozenie obrazu przez inny obraz",
//                "3. Mieszanie obrazów z okreslonym współczynnikiem",
//                "4. Potęgowanie obrazu",
//                "5.1. Dzielenie obrazu przez liczbę",
//                "5.2. Dzielenie obrazu przez inny obraz",
//                "6. Pierwiastkowanie obrazu",
//                "7. Logarytmowanie obrazu"
//        };
//
//
//        Object[] list4 = {      // lista 4 : Operacje geometryczne na obrazie
//            "1. Przemieszczenie obrazu o zadany wektor",
//            "2. Jednorodne i niejednorodne skalowanie obrazu",
//            "3. Obracanie obrazu o dowolny kąt",
//            "4.1. Symetrie względem osi układu",
//            "4.2. Symetrie względem zadanej prostej",
//            "5. Wycinanie fragmentów obrazu",
//            "6. Kopiowanie fragmentów obrazów"
//        };
//
//
//        Object[] list5 = {     // lista 5 : Operacje na histogramie obrazu szarego
//            "1. Obliczanie histogramu",
//            "2. Przemieszczanie histogramu",
//            "3. Rozciąganie histogramu",
//            "4. Progowanie lokalne",
//            "5. Progowanie globalne"
//            };
//
//        Object[] list6 = {     // lista 6 : Operacje na histogramie obrazu barwowego
//                "1. Obliczanie histogramu",
//                "2. Przemieszczanie histogramu",
//                "3. Rozci¡ganie histogramu",
//                "4.1. Progowanie 1-progowe lokalne",
//                "4.2. Progowanie 1-progowe globalne",
//                "5.1. Progowanie wieloprogowe lokalne",
//                "5.2. Progowanie wieloprogowe globalne"
//        };
//
//        Object[] allTasks = {list1, list2, list3, list4, list5, list6};
//
//        return allTasks;

        List<Pair> list1 = new ArrayList<>();     // lista 1 : Operacje ujednolicania obrazów
        list1.add(new Pair(null,"Operacje ujednolicania obrazów"));
        list1.add(new Pair("1_1","1. Ujednolicenie obrazów szarych geometryczne"));
        list1.add(new Pair("1_2","2. Ujednolicenie obrazów szarych rozdzielczościowe"));
        list1.add(new Pair("1_3","3. Ujednolicenie obrazów RGB geometryczne"));
        list1.add(new Pair("1_4","4. Ujednolicenie obrazów RGB rozdzielczościowe"));

        List<Pair> list2 = new ArrayList<>();     // lista 2 : Operacje sumowania arytmetycznego obrazów szarych
        list2.add(new Pair (null,"Operacje sumowania arytmetycznego obrazów szarych"));
        list2.add(new Pair ("2_1_1","1.1. Sumowanie stałej z obrazem oraz dwóch obrazów"));
        list2.add(new Pair ("2_1_2","1.2. Sumowanie dwóch obrazów"));
        list2.add(new Pair ("2_2_1","2.1. Mnozenie obrazu przez zadan̨ą liczb̨ę"));
        list2.add(new Pair ("2_2_2","2.2. Mnozenie obrazu przez inny obraz"));
        list2.add(new Pair ("2_3","3. Mieszanie obrazów z okreslonym współczynnikiem"));
        list2.add(new Pair ("2_4","4. Potęgowanie obrazu"));
        list2.add(new Pair ("2_5_1","5.1. Dzielenie obrazu przez liczbę"));
        list2.add(new Pair ("2_5_1","5.2. Dzielenie obrazu przez inny obraz"));
        list2.add(new Pair ("2_6","6. Pierwiastkowanie obrazu"));
        list2.add(new Pair ("2_7","7. Logarytmowanie obrazu"));

        List<Pair> list3 = new ArrayList<>();     // lista 3 : Operacje sumowania arytmetycznego obrazów barwowych
        list3.add(new Pair(null,"Operacje sumowania arytmetycznego obrazów barwowych"));
        list3.add(new Pair("3_1_1","1.1. Sumowanie stałej z obrazem oraz dwóch obrazów"));
        list3.add(new Pair("3_1_1","1.2. Sumowanie dwóch obrazów"));
        list3.add(new Pair("3_1_1","2.1. Mnozenie obrazu przez zadan̨ą liczb̨ę"));
        list3.add(new Pair("3_1_1","2.2. Mnozenie obrazu przez inny obraz"));
        list3.add(new Pair("3_1_1","3. Mieszanie obrazów z okreslonym współczynnikiem"));
        list3.add(new Pair("3_1_1","4. Potęgowanie obrazu"));
        list3.add(new Pair("3_1_1","5.1. Dzielenie obrazu przez liczbę"));
        list3.add(new Pair("3_1_1","5.2. Dzielenie obrazu przez inny obraz"));
        list3.add(new Pair("3_1_1","6. Pierwiastkowanie obrazu"));
        list3.add(new Pair("3_1_1","7. Logarytmowanie obrazu"));

        List<Pair> list4 = new ArrayList<>();     // lista 4 : Operacje geometryczne na obrazie
        list4.add(new Pair(null,"Operacje geometryczne na obrazie"));
        list4.add(new Pair("4_1","1. Przemieszczenie obrazu o zadany wektor"));
        list4.add(new Pair("4_1","2. Jednorodne i niejednorodne skalowanie obrazu"));
        list4.add(new Pair("4_1","3. Obracanie obrazu o dowolny kąt"));
        list4.add(new Pair("4_1","4.1. Symetrie względem osi układu"));
        list4.add(new Pair("4_1","4.2. Symetrie względem zadanej prostej"));
        list4.add(new Pair("4_1","5. Wycinanie fragmentów obrazu"));
        list4.add(new Pair("4_1","6. Kopiowanie fragmentów obrazów"));

        List<Pair> list5 = new ArrayList<>();     // lista 5 : Operacje na histogramie obrazu szarego
        list5.add(new Pair(null,"Operacje na histogramie obrazu szarego"));
        list5.add(new Pair("5_1","1. Obliczanie histogramu"));
        list5.add(new Pair("5_2","2. Przemieszczanie histogramu"));
        list5.add(new Pair("5_3","3. Rozciąganie histogramu"));
        list5.add(new Pair("5_4","4. Progowanie lokalne"));
        list5.add(new Pair("5_5","5. Progowanie globalne"));

        List<Pair> list6 = new ArrayList<>();     // lista 6 : Operacje na histogramie obrazu barwowego
        list6.add(new Pair(null,"Operacje na histogramie obrazu barwowego"));
        list6.add(new Pair("6_1","1. Obliczanie histogramu"));
        list6.add(new Pair("6_2","2. Przemieszczanie histogramu"));
        list6.add(new Pair("6_3","3. Rozci¡ganie histogramu"));
        list6.add(new Pair("6_4_1","4.1. Progowanie 1-progowe lokalne"));
        list6.add(new Pair("6_4_2","4.2. Progowanie 1-progowe globalne"));
        list6.add(new Pair("6_5_1","5.1. Progowanie wieloprogowe lokalne"));
        list6.add(new Pair("6_5_2","5.2. Progowanie wieloprogowe globalne"));

        List<List<Pair>> allTasks = new ArrayList<>();
        allTasks.add(list1);
        allTasks.add(list2);
        allTasks.add(list3);
        allTasks.add(list4);
        allTasks.add(list5);
        allTasks.add(list6);
        return  allTasks;

    }

    public List<Pair> list1() {
        List<Pair> list1 = new ArrayList<>();     // lista 1 : Operacje ujednolicania obrazów
        list1.add(new Pair(null,"1. Operacje ujednolicania obrazów"));
        list1.add(new Pair("1_1","1. Ujednolicenie obrazów szarych geometryczne"));
        list1.add(new Pair("1_2","2. Ujednolicenie obrazów szarych rozdzielczościowe"));
        list1.add(new Pair("1_3","3. Ujednolicenie obrazów RGB geometryczne"));
        list1.add(new Pair("1_4","4. Ujednolicenie obrazów RGB rozdzielczościowe"));
        return list1;
    }
    public List<Pair> list2() {
        List<Pair> list2 = new ArrayList<>();     // lista 2 : Operacje sumowania arytmetycznego obrazów szarych
        list2.add(new Pair (null,"2. Operacje sumowania arytmetycznego obrazów szarych"));
        list2.add(new Pair ("2_1_1","1.1. Sumowanie stałej z obrazem"));
        list2.add(new Pair ("2_1_2","1.2. Sumowanie dwóch obrazów"));
        list2.add(new Pair ("2_2_1","2.1. Mnożenie obrazu przez zadaną liczb̨ę"));
        list2.add(new Pair ("2_2_2","2.2. Mnożenie obrazu przez inny obraz"));
        list2.add(new Pair ("2_3","3. Mieszanie obrazów z okreslonym współczynnikiem"));
        list2.add(new Pair ("2_4","4. Potęgowanie obrazu"));
        list2.add(new Pair ("2_5_1","5.1. Dzielenie obrazu przez liczbę"));
        list2.add(new Pair ("2_5_2","5.2. Dzielenie obrazu przez inny obraz"));
        list2.add(new Pair ("2_6","6. Pierwiastkowanie obrazu"));
        list2.add(new Pair ("2_7","7. Logarytmowanie obrazu"));
        return list2;
    }
    public List<Pair> list3() {
        List<Pair> list3 = new ArrayList<>();     // lista 3 : Operacje sumowania arytmetycznego obrazów barwowych
        list3.add(new Pair(null,"3. Operacje sumowania arytmetycznego obrazów barwowych"));
        list3.add(new Pair("3_1_1","1.1. Sumowanie stałej z obrazem"));
        list3.add(new Pair("3_1_2","1.2. Sumowanie dwóch obrazów"));
        list3.add(new Pair("3_2_1","2.1. Mnożenie obrazu przez zadaną liczb̨ę"));
        list3.add(new Pair("3_2_2","2.2. Mnożenie obrazu przez inny obraz"));
        list3.add(new Pair("3_3","3. Mieszanie obrazów z okreslonym współczynnikiem"));
        list3.add(new Pair("3_4","4. Potęgowanie obrazu"));
        list3.add(new Pair("3_5_1","5.1. Dzielenie obrazu przez liczbę"));
        list3.add(new Pair("3_5_2","5.2. Dzielenie obrazu przez inny obraz"));
        list3.add(new Pair("3_6","6. Pierwiastkowanie obrazu"));
        list3.add(new Pair("3_7","7. Logarytmowanie obrazu"));
        return list3;
    }
    public List<Pair> list4() {
        List<Pair> list4 = new ArrayList<>();     // lista 4 : Operacje geometryczne na obrazie
        list4.add(new Pair(null,"4. Operacje geometryczne na obrazie"));
        list4.add(new Pair("4_1","1. Przemieszczenie obrazu o zadany wektor"));
        list4.add(new Pair("4_2","2. Jednorodne i niejednorodne skalowanie obrazu"));
        list4.add(new Pair("4_3","3. Obracanie obrazu o dowolny kąt"));
        list4.add(new Pair("4_4_1","4.1. Symetrie względem osi układu"));
        list4.add(new Pair("4_4_2","4.2. Symetrie względem zadanej prostej"));
        list4.add(new Pair("4_5","5. Wycinanie fragmentów obrazu"));
        list4.add(new Pair("4_6","6. Kopiowanie fragmentów obrazów"));
        return list4;
    }
    public List<Pair> list5() {
        List<Pair> list5 = new ArrayList<>();     // lista 5 : Operacje na histogramie obrazu szarego
        list5.add(new Pair(null,"5. Operacje na histogramie obrazu szarego"));
        list5.add(new Pair("5_1","1. Obliczanie histogramu"));
        list5.add(new Pair("5_2","2. Przemieszczanie histogramu"));
        list5.add(new Pair("5_3","3. Rozciąganie histogramu"));
        list5.add(new Pair("5_4","4. Progowanie lokalne"));
        list5.add(new Pair("5_5","5. Progowanie globalne"));
        return list5;
    }
    public List<Pair> list6() {
        List<Pair> list6 = new ArrayList<>();     // lista 6 : Operacje na histogramie obrazu barwowego
        list6.add(new Pair(null,"6. Operacje na histogramie obrazu barwowego"));
        list6.add(new Pair("6_1","1. Obliczanie histogramu"));
        list6.add(new Pair("6_2","2. Przemieszczanie histogramu"));
        list6.add(new Pair("6_3","3. Rozciąganie histogramu"));
        list6.add(new Pair("6_4_1","4.1. Progowanie 1-progowe lokalne"));
        list6.add(new Pair("6_4_2","4.2. Progowanie 1-progowe globalne"));
        list6.add(new Pair("6_5_1","5.1. Progowanie wieloprogowe lokalne"));
        list6.add(new Pair("6_5_2","5.2. Progowanie wieloprogowe globalne"));
        return list6;
    }


}
