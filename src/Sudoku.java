import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: Rankec
 * Date: 14.01.13
 * Time: 18:05
 * To change this template use File | Settings | File Templates.
 */
public class Sudoku
{
    RandomNumber r;

    int[][] finalField;

    int[][][] tried;

    int[][][] posNumbers;

    int a;

    public Sudoku()
    {
        r = new RandomNumber();
        a = 0;
        finalField = new int[9][9];
        tried = new int[9][9][9];
        posNumbers = new int[9][9][9];

        for (int x = 0; x < 9; x++)
        {
            for (int y = 0; y < 9; y++)
            {
                finalField[x][y] = 0;

                for (int z = 0; z < 9; z++)
                {
                    tried[x][y][z] = 0;
                    posNumbers[x][y][z] = z + 1;
                }
            }
        }
    }
    public void generateFieldSchleife()
    {
        for (int y = 0; y < 9; y++)
        {
            for (int x = 0; x < 9;)
            {
                int[] possibleNumbers = getPossibilitiesLive(x, y);
                if (possibleNumbers.length != 0)
                {
                    int random = r.getNewRandom(possibleNumbers.length);
                    int number = possibleNumbers[random];
                    delPossibleAtPos(x,y,number);
                    finalField[x][y] = number;
                    tried[x][y][number - 1] = number;
                    if (x != 8 || y != 8)
                    {
                        x++;
                    }
                    else
                    {
                        return;
                    }
                }
                else
                {
                    resetTriedAtXY(x, y);
                    addPossibleAtPos(x,y,finalField[x][y]);
                    finalField[x][y] = 0;
                    x--;
                }
                if (x == -1)
                {
                    x = 8;
                    y--;
                }
            }
        }
    }

    public void generateField(int x, int y, int direction)
    {
        x = x + direction;
        if (direction == -1)
        {
            // reset tried mit x + 1
            resetTriedAtXY(x + 1, y);
            finalField[x + 1][y] = 0;
            if (x == -1)
            {
                x = 8;
                y--;
            }
            a++; // Kontrolle wie oft
        }

        if (x == 9)
        {
            x = 0;
            y++;
        }

        int[] possibleNumbers = getPossibilitiesLive(x, y);
        if (possibleNumbers.length == 0)
        {
            generateField(x, y, -1);
        }
        else
        {
            int random = r.getNewRandom(possibleNumbers.length);
            finalField[x][y] = possibleNumbers[random];
            tried[x][y][finalField[x][y] - 1] = finalField[x][y];

            if (x == 8 && y == 8)
            {
                /*if (checkValidity())
                {
                    System.out.println("Valid Field!");
                }
                else
                {
                    System.out.println("Invalid Field!");
                }
                */
            }
            else
            {
                generateField(x, y, 1);
            }
        }

    }

    private void resetTriedAtXY(int x, int y)
    {
        for (int i = 0; i < 9; i++)
        {
            tried[x][y][i] = 0;
        }
    }

    private int [] getPosWithList(int x, int y)
    {
        int [] possibleNumbers = new int[9];

        for (int i = 0; i < 9; i++)
        {
            possibleNumbers[i] = posNumbers[x][y][i];
        }

        return possibleNumbers;
    }

    private void delPossibleAtPos(int x, int y, int num)
        {
         posNumbers[x][y][num -1] = 0;


        }

    private void addPossibleAtPos(int xPos, int yPos, int num)
        {
            posNumbers[xPos][yPos][num -1] = num;

            for (int x = xPos; x < 9; x++)
                {
                    posNumbers[x][yPos][num -1] = num;



                }

        }

    private int[] getPossibilitiesLive(int x, int y)
    {
        LinkedList<Integer> possibleNumbers = new LinkedList<Integer>();

        for (int number = 1; number <= 9; number++)
        {
            boolean next = false;
            // gucken ob die Nummer horizontal davor schon verwendet wurde
            for (int i = 0; i < x; i++)
            {
                if (finalField[i][y] == number)
                {
                    next = true;
                    break;
                }
            }
            if (next)
            {
                continue;
            }
            // vertikales prüfen, ob die Zahl schon verwendet wurde
            for (int i = 0; i < y; i++)
            {
                if (finalField[x][i] == number)
                {
                    next = true;
                    break;
                }
            }
            if (next)
            {
                continue;
            }

            // Feld überprüfen, ob die Zahl schon verwendet wird
            int colum = rowColumGetter(x);
            int row = rowColumGetter(y);

            for (int i = row; i < row + 3; i++)
            {
                for (int j = colum; j < colum + 3; j++)
                {
                    if (j < x && i == y || i < y)
                    {
                        if (finalField[j][i] == number)
                        {
                            next = true;
                            break;
                        }
                    }
                }
            }
            if (next)
            {
                continue;
            }
            // Tried überprüfen, und gucken was schon verwendet wurde
            for (int i = 0; i < 9; i++)
            {
                if (tried[x][y][i] == number)
                {
                    next = true;
                    break;
                }
            }
            if (next)
            {
                continue;
            }
            possibleNumbers.addLast(number);

        }

        int[] possibilities = new int[possibleNumbers.size()];
        for (int i = 0; i < possibleNumbers.size(); i++)
        {
            possibilities[i] = possibleNumbers.get(i);
        }
        return possibilities;
    }

    public void printField()
    {
        for (int y = 0; y < 9; y++)
        {
            for (int x = 0; x < 9; x++)
            {
                if (x == 8)
                {
                    System.out.println(finalField[x][y]);
                }
                else
                {
                    System.out.print(finalField[x][y]);
                }
            }
        }
        System.out.println("Count " + a);
    }

    private static int rowColumGetter(int i)
    {
        if (i < 3)
        {
            return 0;
        }
        else if (i < 6)
        {
            return 3;
        }
        else
        {
            return 6;
        }
    }

    private boolean checkValidity()
    {

        int add = 0;
        int mul = 0;
        boolean validity = false;
        // Reihen checken
        for (int y = 0; y < 9; y++)
        {
            add = 0;
            mul = 0;
            for (int x = 0; x < 9; x++)
            {
                add += finalField[x][y];
                mul *= finalField[x][y];
            }
            if (add != 45 && mul != 362880)
            {
                System.out.println("add: " + add + " mul: " + mul);
                return false;
            }
        }
        // Spalten checken
        for (int x = 0; x < 9; x++)
        {
            add = 0;
            mul = 0;
            for (int y = 0; y < 9; y++)
            {
                add += finalField[x][y];
                mul *= finalField[x][y];
            }
            if (add != 45 && mul != 362880)
            {
                System.out.println("add: " + add + " mul: " + mul);
                return false;
            }
        }
        // Felder Prüfen

        return true;
    }

    public void reset()
    {
        a = 0;
        for (int x = 0; x < 9; x++)
        {
            for (int y = 0; y < 9; y++)
            {
                finalField[x][y] = 0;

                for (int z = 0; z < 9; z++)
                {
                    tried[x][y][z] = 0;
                }
            }
        }
    }

}
