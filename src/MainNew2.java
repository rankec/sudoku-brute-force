/**
 * Created with IntelliJ IDEA.
 * User: Rankec
 * Date: 14.01.13
 * Time: 18:20
 * To change this template use File | Settings | File Templates.
 */

import java.util.LinkedList;

public class MainNew2
{
    public static void main(String[] args)
    {
        /*
        int anzahl = 500000;

        SudokuV2_5 sudoku = new SudokuV2_5();

        long start = System.currentTimeMillis();
        for (int i = 0; i < anzahl; i++)
        {
            sudoku.generate();
            sudoku.reset();
        }
        long time = System.currentTimeMillis() - start;

        System.out.println("Time: " + time + "ms");
         */

        int anzahl = 5000;
        int times = 30;
        long start;
        long time;
        LinkedList<Long> SudokuNew = new LinkedList<Long>();
        LinkedList<Long> byteSudoku = new LinkedList<Long>();
        SudokuV2_5 sudoku = new SudokuV2_5();
        SudokuV2_6 sudokuByte = new SudokuV2_6();
        for (int z = 0; z < times; z++)
        {
            start = System.currentTimeMillis();
            for (int i = 0; i < anzahl; i++)
            {
                sudoku.generate();
                sudoku.reset();
            }
            time = System.currentTimeMillis() - start;
            SudokuNew.addLast(time);

            start = System.currentTimeMillis();

            for (int i = 0; i < anzahl; i++)
            {
                sudokuByte.generate();
                sudokuByte.reset();
            }
            time = System.currentTimeMillis() - start;
            byteSudoku.addLast(time);
        }
        //Durchschnittszeit berechnen
        long byteTime = 0;
        long intTime = 0;
        for (int i = 0; i < times; i++)
        {
            byteTime += byteSudoku.get(i);
            intTime += SudokuNew.get(i);
        }
        System.out.println("Time for Modul 1: " + (intTime / times));
        System.out.println("Time for Modul 2: " + (byteTime / times));


    }
}
