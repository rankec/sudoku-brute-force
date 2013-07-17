import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Rankec
 * Date: 08.04.13
 * Time: 22:03
 * To change this template use File | Settings | File Templates.
 */
public class SudokuV2_2 {

    int a = 0;
    int b = 0;
    RandomNumber r;
    ArrayList<Integer> possibilities;
    boolean[][] posLeft = {{true,true,true,true,true,true,true,true,true},
                            {true,true,true,true,true,true,true,true,true},
                            {true,true,true,true,true,true,true,true,true},
                            {true,true,true,true,true,true,true,true,true},
                            {true,true,true,true,true,true,true,true,true},
                            {true,true,true,true,true,true,true,true,true},
                            {true,true,true,true,true,true,true,true,true},
                            {true,true,true,true,true,true,true,true,true},
                            {true,true,true,true,true,true,true,true,true}};
    boolean backward = false;

    int[][] sudokuField = { {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                            {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                            {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                            {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                            {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                            {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                            {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                            {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                            {-1,-1,-1,-1,-1,-1,-1,-1,-1}};

    boolean[][][] tried = { {{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false}},
                            {{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false}},
                            {{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false}},
                            {{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false}},
                            {{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false}},
                            {{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false}},
                            {{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false}},
                            {{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false}},
                            {{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false}}};

    public SudokuV2_2()
    {
        r = new RandomNumber();
        possibilities = new ArrayList<Integer>();
    }


    public void generate()
    {
        for (int x = 0; x < 9; x++)
        {
            for (int y = 0; y < 9; y++)
            {
                ArrayList<Integer> possibleNumbers = getPossibilities(x, y);
                int sizeArray = possibleNumbers.size();
                if (sizeArray != 0)
                {
                    int number = possibleNumbers.get(r.getNewRandom(sizeArray));
                    sudokuField[x][y] = number;
                    tried[x][y][number] = true;
                }
                else
                {

                    resetTriedAtXY(x, y);
                    sudokuField[x][y] = -1; // den Wert des aktuellen Feldes zurücksetzen
                    y -= 1;                 // Ein Feld weiter nach links gehen

                    if (y < 0)   // schauen ob nun ein ungültiger bereich erreicht ist. dann eine zeile höher gehen
                    {
                        b+= 1;
                        y += 8;
                        x--;
                    }
                    posLeft[x][sudokuField[x][y]] = true; // für die Pos Links wieder den Wert hinzufügen

                    y -= 1; // Ein Feld abziehen da die Schleife einen drauf rechnet

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

    private ArrayList<Integer> getPossibilities(int x, int y)
    {
        possibilities.clear();
        boolean[] numbers = {true, true, true, true, true, true, true, true, true};

        int xMax = (x / 3) * 3;
        int yMax = (y / 3) * 3;

        // Die zahlen links horiziontal vor dem Feld

            if (y != 0)
                posLeft[x][sudokuField[x][y-1]] = false;



        //Die Zahlen vertikal über dem Feld

        for (int xn = 0; xn < xMax; xn++)
        {
            numbers[sudokuField[xn][y] ] = false;
        }



        // Die Zahlen im Feld
        for (int xn = xMax; xn < x; xn++)
        {
            for (int yn = yMax; yn < yMax + 3; yn++)
            {
                numbers[sudokuField[xn][yn] ] = false;
            }
        }

        //Alles auf der selben Zeile nach links  // Zusammenfassung mit nur links möglich
        /*for (int yn = yMax; yn < y; yn++)
        {
            numbers[sudokuField[x][yn]] = false;
        }
        */
       /* if (y == 0 || backward)
        {
            for (int i = 0; i < 9; i++)
            {
                if (numbers[i] && !tried[x][y][i])
                    possibilities.add(i);
            }
        }
        else
        {*/
            for (int i = 0; i < 9; i++)
            {
                if (numbers[i] && posLeft[x][i] && !tried[x][y][i])
                    possibilities.add(i);
            }
        //}

        return possibilities;
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
        a = 0;
        b = 0;
        for (int x = 0; x < 9; x++)
        {
            for (int y = 0; y < 9; y++)
            {
                sudokuField[x][y] = -1;
                posLeft[x][y] = true;

                for (int z = 0; z < 9; z++)
                {
                    tried[x][y][z] = false;
                }
            }
        }
    }


    public void printA(){
        System.out.println("A: " + a+ " B: " + b);
    }


}




