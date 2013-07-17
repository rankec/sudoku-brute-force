import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Rankec
 * Date: 08.04.13
 * Time: 22:03
 * To change this template use File | Settings | File Templates.
 */
public class SudokuV2_6 {

    RandomNumber r;
    ArrayList<Integer> possibilities;

    int[][] sudokuField = { {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                            {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                            {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                            {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                            {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                            {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                            {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                            {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                            {-1,-1,-1,-1,-1,-1,-1,-1,-1}};

    boolean[][][] tried;


    public SudokuV2_6()
    {
        r = new RandomNumber();
        possibilities = new ArrayList<Integer>();
        tried = new boolean[9][9][9];
    }


    public void generate()
    {
        for (int x = 0; x < 9; x++)
        {
            for (int y = 0; y < 9; y++)
            {
                getPossibilities(x, y);
                int sizeArray = possibilities.size();
                if (sizeArray != 0)
                {
                    int number = possibilities.get(r.getNewRandom(sizeArray));
                    sudokuField[x][y] = number;
                    tried[x][y][number] = true;
                }
                else
                {
                    resetTriedAtXY(x, y);
                    sudokuField[x][y] = -1;
                    y -= 2;

                    if (y < 0)
                    {
                        y += 8;
                        x--;
                    }
                }

            }
        }
    }


    private void resetTriedAtXY(int x, int y)
    {
        for (int i = 0; i < 9; i++)
        {
            tried[x][y][i] = false;
        }
    }

    private void getPossibilities(int x, int y)
    {
        possibilities.clear();
        boolean[] numbers = {true, true, true, true, true, true, true, true, true};

        int xMax = (x / 3) * 3;
        int yMax = (y / 3) * 3;
        /*int max =  getMax(x, y);

        for (int i = 0; i < max; i++)
        {
            if (i < y)
                numbers[sudokuField[x][i]] = false;
            if (i < x)
                numbers[sudokuField[i][y]] = false;
        }
        */

        // Die zahlen links horiziontal vor dem Feld
        for (int yn = 0; yn < y; yn++)
        {
            numbers[sudokuField[x][yn]] = false;
        }

        //Die Zahlen vertikal über dem Feld

        for (int xn = 0; xn < xMax; xn++)
        {
            numbers[sudokuField[xn][y]] = false;
        }

        // Die Zahlen im Feld
        for (int xn = xMax; xn < x; xn++)
        {
            for (int yn = yMax; yn < yMax + 3; yn++)
            {
                numbers[sudokuField[xn][yn] ] = false;
            }
        }

        // auf die Möglichkeitenliste setzen
        for (int i = 0; i < 9; i++)
        {
            if (numbers[i] && !tried[x][y][i])
                possibilities.add(i);
        }
    }

    private int getMax(int x, int y)
    {
        if (y > x)
            return y;
        return x;
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

    public void checkValidity()
    {

        int add;
        int mul;
        boolean validity = true;
        // Reihen checken
        for (int y = 0; y < 9; y++)
        {
            add = 0;
            mul = 1;
            for (int x = 0; x < 9; x++)
            {
                add += sudokuField[x][y] + 1;
                mul *= sudokuField[x][y] + 1;
            }
            if (add != 45 && mul != 362880)
            {
                System.out.println("add: " + add + " mul: " + mul);
                validity = false;
            }
        }
        // Spalten checken
        for (int x = 0; x < 9; x++)
        {
            add = 0;
            mul = 1;
            for (int y = 0; y < 9; y++)
            {
                add += sudokuField[x][y] + 1;
                mul *= sudokuField[x][y] + 1;
            }
            if (add != 45 && mul != 362880)
            {
                System.out.println("add: " + add + " mul: " + mul);
                validity = false;
                // return false;
            }
        }
        // Felder Prüfen

        if (validity)
        {
            System.out.println("Field is valid!");
        }
        else
        {
            System.out.println("Field is invalid!");
        }

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
                }
            }
        }
    }
}




