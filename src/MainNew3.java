/**
 * Created with IntelliJ IDEA.
 * User: Rankec
 * Date: 14.01.13
 * Time: 18:20
 * To change this template use File | Settings | File Templates.
 */

public class MainNew3
{
    public static void main(String[] args)
    {
        int anzahl = 1;
        long start;
        long time;

        InternetSudoku sudoku = new InternetSudoku();


        start = System.currentTimeMillis();
        for (int i = 0; i < anzahl; i++)
        {
            sudoku.generateGrid();
            //sudoku.printA();
            //sudoku.reset();
        }
        //sudoku.generate();
        //sudoku.printField();
        //sudoku.checkValidity();




        int a = 0;

    }



}
