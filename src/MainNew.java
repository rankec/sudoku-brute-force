import java.util.LinkedList;

public class MainNew
{

    public static void main(String[] args)
    {
        // write your code here
        long start = System.currentTimeMillis();
        for (int i = 0; i < 25; i++)
        {
            long startIn = System.currentTimeMillis();
            int a = 0;
            int[][] finalField = new int[9][9];
            int[][][] tried = new int[9][9][9];
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
            System.out.println("Feld Nummer:" + (i + 1));
            generateField(0, 0, finalField, tried, 0, 0);
            System.out.println("Duration in ms: " + (System.currentTimeMillis() - startIn));
        }
        System.out.println("Total duration in ms: " + (System.currentTimeMillis() - start));

    }

    private static void generateField(int x, int y, int[][] finalField, int[][][] tried, int direction, int a)
    {
        x = x + direction;
        if (direction == -1)
        {
            // reset tried mit x + 1
            tried = resetTriedAtXY(x + 1, y, tried);
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

        int[] possibleNumbers = getPossibilitiesLive(x, y, finalField, tried);
        if (possibleNumbers.length == 0)
        {
            generateField(x, y, finalField, tried, -1, a);
        }
        else
        {
            RandomNumber r = new RandomNumber();
            int random = r.getNewRandom(possibleNumbers.length);
            finalField[x][y] = possibleNumbers[random];
            tried[x][y][finalField[x][y] - 1] = finalField[x][y];

            if (x == 8 && y == 8)
            {
                printFeld(finalField, a);
                if (checkValidity(finalField))
                {
                    System.out.println("Valid Field!");
                }
                else
                {
                    System.out.println("Invalid Field!");
                }
            }
            else
            {
                //printFeld(finalField, a);
                generateField(x, y, finalField, tried, 1, a);
            }
        }

    }

    private static int[][][] resetTriedAtXY(int x, int y, int[][][] tried)
    {
        for (int i = 0; i < 9; i++)
        {
            tried[x][y][i] = 0;
        }

        return tried;
    }

    private static int[] getPossibilitiesLive(int x, int y, int[][] finalField, int[][][] tried)
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

    private static void generateFieldOld(int x, int y, int[][] finalField, int[][][] possible, int direction, int a)
    {
        // directions:
        // 1 = next field
        // -1 = last field

        // System.out.println("i am at y:" + y + " and x:" + x);

        x = x + direction;

        if (direction == -1)
        {

            if (x == -1)
            {
                x = 8;
                y--;
            }

            if (x == 8 && y == -1)
            {
                x = 0;
                y = 0;
            }

            a++;
            int value = finalField[x][y];
            finalField[x][y] = 0;
            possible = addNumber(x, y, value, possible, finalField);
        }

        if (x == 9)
        {
            x = 0;
            y++;
        }

        int[] possibleNumbers = getPossibilities(x, y, possible);
        if (possibleNumbers.length == 0)
        {
            // System.out.println("Error at y:" + y + " and x:" + x);
            // System.out.println("A = " + a);
            generateField(x, y, finalField, possible, -1, a);
        }
        else
        {
            RandomNumber r = new RandomNumber();
            int random = r.getNewRandom(possibleNumbers.length);
            finalField[x][y] = possibleNumbers[random];

            deleteNumber(x, y, finalField[x][y], possible);
            if (x == 8 && y == 8)
            {
                printFeld(finalField, a);
            }
            else
            {
                generateField(x, y, finalField, possible, +1, a);
            }
        }

    }

    private static int[] getPossibilities(int x, int y, int[][][] feld)
    {
        LinkedList<Integer> possibilitiesList = new LinkedList<Integer>();

        for (int z = 0; z < 9; z++)
        {
            if (feld[x][y][z] > 0)
            {
                possibilitiesList.addLast(feld[x][y][z]);
            }
        }

        int[] possibilities = new int[possibilitiesList.size()];

        for (int i = 0; i < possibilities.length; i++)
        {
            possibilities[i] = possibilitiesList.get(i);
        }

        return possibilities;
    }

    private static int[][][] deleteNumber(int x, int y, int value, int[][][] feld)
    {
        // horizontal
        for (int i = x; i < 9; i++)
        {
            feld[i][y][value - 1] = 0;
        }
        // vertikal
        for (int i = y; i < 9; i++)
        {
            feld[x][i][value - 1] = 0;
        }

        // rest vom Feld
        int colum = rowColumGetter(x);
        int row = rowColumGetter(y);

        for (int i = row; i < row + 3; i++)
        {
            for (int j = colum; j < colum + 3; j++)
            {
                if (j >= x && i == y)
                {
                    feld[j][i][value - 1] = 0;
                }
                if (i > y)
                {
                    feld[j][i][value - 1] = 0;
                }
            }
        }
        return feld;
    }

    private static int[][][] addNumber(int x, int y, int value, int[][][] feld, int[][] finalFeld)
    {
        // horizontal
        for (int i = x + 1; i < 9; i++)
        {
            if (!valueBlocked(i, y, value, finalFeld)) // Wenn das feld nicht geblockt -> wieder freigeben
            {
                feld[i][y][value - 1] = value;
            }
        }
        // vertikal
        for (int i = y + 1; i < 9; i++)
        {
            if (!valueBlocked(x, i, value, finalFeld)) // Wenn das feld nicht geblockt -> wieder freigeben
            {
                feld[x][i][value - 1] = value;
            }
        }
        // rest vom Feld

        int colum = rowColumGetter(x);
        int row = rowColumGetter(y);

        for (int i = row; i < row + 3; i++)
        {
            for (int j = colum; j < colum + 3; j++)
            {
                if (i > y)
                {
                    if (!valueBlocked(j, i, value, finalFeld))
                    {
                        feld[j][i][value - 1] = value;
                    }
                }
                else if (j > x && i == y)
                {
                    if (!valueBlocked(j, i, value, finalFeld))
                    {
                        feld[j][i][value - 1] = value;
                    }
                }
            }
        }
        return feld;
    }

    private static void printFeld(int feld[][], int a)
    {
        for (int y = 0; y < 9; y++)
        {
            for (int x = 0; x < 9; x++)
            {
                if (x == 8)
                {
                    System.out.println(feld[x][y]);
                }
                else
                {
                    System.out.print(feld[x][y]);
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

    private static boolean valueBlocked(int x, int y, int value, int[][] feld)
    {
        /*
         * for (int i = 0; i < x; i++)
         * {
         * if (feld[i][y] == value)
         * return true;
         * }
         */
        for (int i = 0; i < y; i++)
        {
            if (feld[x][i] == value)
                return true;
        }

        int colum = rowColumGetter(x);
        int row = rowColumGetter(y);

        for (int i = row; i < row + 3; i++)
        {
            for (int j = colum; j < colum + 3; j++)
            {
                if (row < y)
                {
                    if (feld[colum][row] == value)
                        return true;
                }
                else if (row == y && colum < x)
                {
                    if (feld[colum][row] == value)
                        return true;
                }
            }
        }

        return false;
    }

    private static boolean checkValidity(int[][] finalField)
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

}
