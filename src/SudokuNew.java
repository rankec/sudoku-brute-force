import com.sun.org.apache.xpath.internal.functions.FuncFalse;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Rankec
 * Date: 17.03.13
 * Time: 19:19
 * To change this template use File | Settings | File Templates.
 */
public class SudokuNew {

    int[][] sudokuField; // sudokuField[x][y]
    /*
            y0 y1 y2  y3 y4 y5  y6 y7 y8
        x0  3  8  1 | 9  4  6 | 5  2  7
        x1  2  4  5 | 7  1  8 | 9  3  6
        x2  9  6  7 | 3  2  5 | 8  1  4
            - - - - - - - - - - - - - -
        x3  1  7  6 | 2  8  3 | 4  5  9
        x4  4  9  2 | 1  5  7 | 6  8  3
        x5  5  3  8 | 4  6  9 | 2  7  1
            - - - - - - - - - - - - - -
        x6  7  2  3 | 5  9  4 | 1  6  8
        x7  8  1  4 | 6  7  2 | 3  9  5
        x8  6  5  9 | 8  3  1 | 7  4  2
     */
    boolean[][][] tried; // Versuchte Zahlen pro Feld. Die Zahlen sind der Reihenfolge nach [x][y][0] = 1. Angaben in bool. True = versucht
    boolean[][][] possibilities;// Möglichkeiten pro feld. Wie tried. true = noch benutzbar

    RandomNumber r;

    public SudokuNew()
    {
        // Vorbelegung der Arrays

        r = new RandomNumber();
        sudokuField = new int[9][9];
        tried = new boolean[9][9][9];
        possibilities = new boolean[9][9][9];
        for (int x = 0; x < 9; x++)
        {
            for (int y = 0; y < 9; y++)
            {
                sudokuField[x][y] = -1;
                for (int z = 0; z < 9; z++)
                {
                    tried[x][y][z] = false;
                    possibilities[x][y][z] = true;
                }
            }
        }
    }

    public void generate() // this will generate a complete Sudoku, which is saved in sudokuField
    {
        for (int x = 0; x < 9; x++)
        {
            for (int y = 0; y < 9; y++)
            {
                //String possible = getPossibilitiesString(x,y);
                //int[] possible = getPossibilitiesNew(x, y);
                ArrayList<Integer> possible = getPossibilitiesNew(x,y);

                if (possible.size() != 0)
                {
                    int random = r.getNewRandom(possible.size());

                    //String tmp = possible.substring(random,random+1);

                    int num = possible.get(random);//Integer.parseInt(tmp);

                         //   possibleNumbers[0random];
                    sudokuField[x][y] = num;
                    delPossibilitie(x,y,num);
                    tried[x][y][num] = true;

                }
                else
                {
                    resetTriedAtPos(x,y);
                    //Feld zurücksetzen und möglickeiten zurücksetzen
                    if (sudokuField[x][y] != -1)
                    {
                        addPossibilitie(x,y,sudokuField[x][y]);
                        sudokuField[x][y] = -1;
                    }
                    // auf das Feld davor gehen
                    y -= 1;
                    if (y < 0)
                    {
                        x--;
                        y += 8;
                    }
                    if (x < 0)
                    {
                        int adadbaj = 0;
                    }
                    addPossibilitie(x,y,sudokuField[x][y]);
                    sudokuField[x][y] = -1;
                    y -= 1;
                }

                //bekomme möglichkeiten
                // Fall 1: Möglichkeiten vorhanden
                    // zufallszahl
                    // suche eine Möglichkeit zufällig aus
                    // entferne die Möglichkeit für folgende felder
                    // trage es in das feld ein
                    // Möglichkeit versucht hinzufügen
                    // zum nächsten feld
                // Fall 2: keine Möglichkeiten vorhanden
                    // Versuchte möglichkeiten resetten ( ab wann notwendig)
                    // gehe ein feld zurück
                    // füge die möglichkeiten für die folge felder hinzu

            }

        }
    }


    private ArrayList<Integer> getPossibilitiesNew(int x, int y)
    {
        ArrayList<Integer> pos = new ArrayList<Integer>();
        for (int i = 0; i < 9; i++)
        {
            if (this.possibilities[x][y][i] && !tried[x][y][i])
            {
                pos.add(i);
            }
        }



        return pos;
    }

    private String getPossibilitiesString(int x, int y)
    {
        String tmp = "";
        for (int i = 0; i < 9; i++)
        {
            if (this.possibilities[x][y][i] && !tried[x][y][i])
            {
                tmp = tmp + i;
            }
        }
        return tmp;
    }

    private int[] getPossibilities(int x, int y)
    {
        int[] tmpPossibilities = new int[9];
        int[] possibilities;
        int size = 0;
        for (int i = 0; i < 9; i++)
        {
            if (this.possibilities[x][y][i] && !tried[x][y][i])
            {
                tmpPossibilities[i] = i + 1;
                size++;
            }
        }
        if(size == 0)
        {
            return new int[0];
        }

        possibilities = new int[size--];
        for (int e: tmpPossibilities)
        {
            if (e > 0)
            {
                possibilities[size--] = e -1;
                if (size == -1)
                    break;
            }
        }
        return possibilities;
    }

    private void delPossibilitie(int x, int y, int delPos)
    {
        /*
        * Löscht die Möglichkeiten (Feld selbst nicht) ab der angegebenen Position .
        *
        * 1) für das Feld/ Quadrat
        *
        * 2) senkrecht nach unten nach dem Feld (x-Werte)
        *
        * 3) horizontal nach rechts nach dem Feld(y-Werte)
        *
        * */
        // 1)


        /*

        int yMax, xMax;

        // tausch gegen rechenlogik  !!!
        if (y < 3)
        {
             yMax = 3;
        }
        else if (y < 6)
        {
            yMax = 6;
        }
        else
        {
            yMax = 9;
        }

        if (x < 3)
        {
            xMax = 3;
        }
        else if (x < 6)
        {
            xMax = 6;
        }
        else
        {
            xMax = 9;
        }
        */



        /*
        0 = 0   max 3
        1 = 0   max 3
        2 = 0   max 3
        3 = 1   max 6
        4 = 1   max 6
        5 = 1   max 6
        6 = 2   max 9
        7 = 2   max 9
        8 = 2   max 9
        */

        int xMaxR = ((x / 3) + 1) * 3;
        int yMaxR = ((y / 3) + 1) * 3;
        int yStart = yMaxR - 3;


        // 1)
        for (int xn = x; xn < xMaxR; xn++)
        {
            if (xn > x)
            {
                for (int yn = yStart; yn < yMaxR; yn++)
                {
                    possibilities[xn][yn][delPos] = false;
                }
            }
            else
            {
                for (int yn = y + 1; yn < yMaxR; yn++)
                {
                    possibilities[xn][yn][delPos] = false;
                }
            }
        }
        // 2)
            for (int yn = yMaxR; yn < 9; yn++)
            {
                possibilities[x][yn][delPos] = false;
            }
        // 3)

            for (int xn = xMaxR; xn < 9; xn++)
            {
                possibilities[xn][y][delPos] = false;
            }

    }

    private void addPossibilitie(int x, int y, int addPos)
    {
        /* wird verwendet, nachdem man ein Feld zurück gegangen ist

            fügt die Möglichkeiten wieder hinzu. Wie delPossibilities sie weggelöscht hat.

            Die Koordinaten werden von dem Feld angegeben, von der aus zurückgegangen wird


        * 1) für das Feld/ Quadrat
        *
        * 2) senkrecht nach unten nach dem Feld (x-Werte)
        *
        * 3) horizontal nach rechts nach dem Feld (y-Werte)


        */

        int xMaxR = ((x / 3) + 1) * 3;
        int yMaxR = ((y / 3) + 1) * 3;
        int yStart = yMaxR - 3;


        // 1)
        for (int yn = yStart; yn < yMaxR; yn++)
        {
            if (!isValueOverIt(x, yn, addPos))
            {
                for (int xn = x; xn < xMaxR; xn++)
                {
                    if (xn == x && yn == y)
                    {
                        if (xn + 1 < xMaxR)
                            xn++;
                        else {
                            break;
                        }
                    }
                    possibilities[xn][yn][addPos] = true;
                }
            }
        }
        // 2)
        for (int yn = yMaxR; yn < 9; yn++)
        {
            if (!isValueOverIt(x,yn,addPos))
                possibilities[x][yn][addPos] = true;
        }
        // 3)
        for (int xn = xMaxR; xn < 9; xn++)
        {
            possibilities[xn][y][addPos] = true;
        }
    }

    private boolean isValueOverIt(int x, int y, int value)
    {
        // Hilfsmethode für addPossibilities
        // stellt fest ob ein Wert über einer angegebenen Position ist
        for (int i = 0; i < x; i++)
        {
            if (sudokuField[i][y] == value)
                return true;
        }
        return false;
    }

    private void resetTriedAtPos(int x, int y)
    {
        for (int i = 0; i < 9; i++)
        {
            tried[x][y][i] = false;
        }
    }



    public void printField()
    {
        for (int x = 0; x < 9; x++)
        {
            for (int y = 0; y < 9; y++)
            {
                if (y == 8)
                {
                    System.out.println(sudokuField[x][y]);
                }
                else
                {
                    System.out.print(sudokuField[x][y]);
                }
            }
        }
    }

    public boolean checkValidity()
    {

        int add = 0;
        int mul = 0;
        boolean validity = false;
        // Reihen checken
        for (int x = 0; x < 9; x++)
        {
            add = 0;
            mul = 1;
            for (int y = 0; y < 9; y++)
            {
                add += sudokuField[x][y]+1;
                mul *= sudokuField[x][y]+1;
            }
            if (add != 45 && mul != 362880)
            {
                System.out.println("add: " + add + " mul: " + mul);
                return false;
            }
        }
        // Spalten checken
        for (int y = 0; y < 9; y++)
        {
            add = 0;
            mul = 1;
            for (int x = 0; x < 9; x++)
            {
                add += sudokuField[x][y]+1;
                mul *= sudokuField[x][y]+1;
            }
            if (add != 45 && mul != 362880)
            {
                System.out.println("add: " + add + " mul: " + mul);
                return false;
            }
        }
        // Felder Prüfen
        System.out.println("Feld ist gültig!");
        return true;
    }


    public void reset()
    {
        for (int x = 0; x < 9; x++)
        {
            for (int y = 0; y < 9; y++)
            {
                sudokuField[x][y] = -1;

                for (int z = 0; z < 9; z++)
                {
                    tried[x][y][z] = false;
                    possibilities[x][y][z] = true;
                }
            }
        }
    }
}
