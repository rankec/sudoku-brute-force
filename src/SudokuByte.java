import java.util.ArrayList;
import java.util.LinkedList;
import javolution.util.FastList;

/**
 * Created with IntelliJ IDEA.
 * User: Rankec
 * Date: 16.01.13
 * Time: 16:00
 * To change this template use File | Settings | File Templates.
 */
public class SudokuByte
{
    RandomNumber r;

    byte[][] finalField;

    byte[][][] tried;

    ArrayList<Byte> possibilities;
    FastList<Byte> possibilitiesFL;

    int a,b,c = 0;

    public SudokuByte()
    {
        possibilities = new ArrayList<Byte>();
        possibilitiesFL = new FastList<Byte>();
        r = new RandomNumber();
        finalField = new byte[9][9];
        tried = new byte[9][9][9];
        for (byte x = 0; x < 9; x++)
        {
            for (byte y = 0; y < 9; y++)
            {
                finalField[x][y] = 0;
                for (byte z = 0; z < 9; z++)
                {
                    tried[x][y][z] = 0;
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
                //ArrayList<Byte> possibleNumbers = getPossibilitiesLiveV2(x, y);
                //int[] possibleNumbers = getPossibilitiesLiveV3(x,y);
                FastList<Byte> possibleNumbers = getPossibilitiesLiveV4(x, y);

                if (possibleNumbers.size() != 0)
                {
                    int random = r.getNewRandom(possibleNumbers.size());
                    //byte number = possibleNumbers.get(random);
                    byte number = (byte) possibleNumbers.get(random);

                    //System.out.println("RND:" + random + " Num:" + number);
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
                    finalField[x][y] = 0;
                    x--;
                }
                if (x == -1)
                {
                    x = 8;
                    y--;
                }

               // printField();
                //System.out.println("----------------");
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
            // a++; // Kontrolle wie oft
        }

        if (x == 9)
        {
            x = 0;
            y++;
        }

        byte[] possibleNumbers = getPossibilitiesLive(x, y);
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
                /*
                 * if (checkValidity())
                 * {
                 * System.out.println("Valid Field!");
                 * }
                 * else
                 * {
                 * System.out.println("Invalid Field!");
                 * }
                 */
                return;
            }
            else
            {
                generateField(x, y, 1);
            }
        }

    }

    private void resetTriedAtXY(int x, int y)
    {
        for (byte i = 0; i < 9; i++)
        {
            tried[x][y][i] = 0;
        }
    }

    private byte[] getPossibilitiesLive(int x, int y)
    {
        LinkedList<Byte> possibleNumbers = new LinkedList<Byte>();

        for (byte number = 1; number <= 9; number++)
        {
            boolean next = false;
            // gucken ob die Nummer horizontal davor schon verwendet wurde
            for (byte i = 0; i < x; i++)
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
            for (byte i = 0; i < y; i++)
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
            for (byte i = 0; i < 9; i++)
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
            possibleNumbers.add(number);
            //possibleNumbers.addLast(number);

        }

        byte[] possibilities = new byte[possibleNumbers.size()];
        for (int i = 0; i < possibleNumbers.size(); i++)
        {
            possibilities[i] = possibleNumbers.get(i);
        }
        return possibilities;
    }

    private ArrayList<Byte> getPossibilitiesLiveV2(int x, int y)
    {
        possibilities.clear();
        int[] numbers = {1, 1, 1, 1, 1, 1, 1, 1, 1};
        //ArrayList<Byte> possibilities = new ArrayList<Byte>();
        //boolean[] numbers = {true,true,true,true,true,true,true,true,true};
        int xMax = (x / 3) * 3;
        int yMax = (y / 3) * 3;

        // Die zahlen links horiziontal vor dem Feld

        for (int yn = 0; yn < yMax; yn++)
        {
            numbers[finalField[x][yn] - 1] = 0;//false;
        }

        //Die Zahlen vertikal über dem Feld

        for (int xn = 0; xn < xMax; xn++)
        {
            numbers[finalField[xn][y] - 1] = 0;//false;
        }

        // Die Zahlen im Feld
        for (int yn = yMax; yn < y; yn++)
        {
                for (int xn = xMax; xn < xMax + 3; xn++)
                {
                    numbers[finalField[xn][yn] - 1] = 0;//false;
                }
        }

        //Alles auf der selben Zeile nach links  // Zusammenfassung mit nur links möglich
        for (int xn = xMax; xn < x; xn++)
        {
            numbers[finalField[xn][y] - 1] = 0;//false;
        }

        for (byte i = 0; i < 9; i++)
        {
            if (numbers[i] == 1 && tried[x][y][i] == 0)
                possibilities.add( (byte)(i + 1) );
        }

        return possibilities;
    }

    private int[] getPossibilitiesLiveV3(int x, int y)
    {

        int size = 0;
        int[] numbers = {1, 1, 1, 1, 1, 1, 1, 1, 1};
        //ArrayList<Byte> possibilities = new ArrayList<Byte>();
        //boolean[] numbers = {true,true,true,true,true,true,true,true,true};
        int xMax = (x / 3) * 3;
        int yMax = (y / 3) * 3;

        // Die zahlen links horiziontal vor dem Feld

        for (int yn = 0; yn < yMax; yn++)
        {
            numbers[finalField[x][yn] - 1] = 0;//false;

        }

        //Die Zahlen vertikal über dem Feld

        for (int xn = 0; xn < xMax; xn++)
        {
            numbers[finalField[xn][y] - 1] = 0;//false;

        }

        // Die Zahlen im Feld
        for (int yn = yMax; yn < y; yn++)
        {
            for (int xn = xMax; xn < xMax + 3; xn++)
            {
                numbers[finalField[xn][yn] - 1] = 0;//false;

            }
        }

        //Alles auf der selben Zeile nach links  // Zusammenfassung mit nur links möglich
        for (int xn = xMax; xn < x; xn++)
        {
            numbers[finalField[xn][y] - 1] = 0;//false;

        }

        for (int i = 0; i < 9; i++)
        {
            if (numbers[i] == 1 && tried[x][y][i] == 0)
                size++;
        }

        int[] posArray = new int[size--];

        for (int i = 0; i < 9; i++)
        {
            if (numbers[i] == 1 && tried[x][y][i] == 0)
                posArray[size--] = i + 1;
        }

        return posArray;
    }

    private FastList<Byte> getPossibilitiesLiveV4(int x, int y)
    {
        possibilitiesFL.clear();
        int[] numbers = {1, 1, 1, 1, 1, 1, 1, 1, 1};
        //ArrayList<Byte> possibilities = new ArrayList<Byte>();
        //boolean[] numbers = {true,true,true,true,true,true,true,true,true};
        int xMax = (x / 3) * 3;
        int yMax = (y / 3) * 3;

        // Die zahlen links horiziontal vor dem Feld

        for (int yn = 0; yn < yMax; yn++)
        {
            numbers[finalField[x][yn] - 1] = 0;//false;
        }

        //Die Zahlen vertikal über dem Feld

        for (int xn = 0; xn < xMax; xn++)
        {
            numbers[finalField[xn][y] - 1] = 0;//false;
        }

        // Die Zahlen im Feld
        for (int yn = yMax; yn < y; yn++)
        {
            for (int xn = xMax; xn < xMax + 3; xn++)
            {
                numbers[finalField[xn][yn] - 1] = 0;//false;
            }
        }

        //Alles auf der selben Zeile nach links  // Zusammenfassung mit nur links möglich
        for (int xn = xMax; xn < x; xn++)
        {
            numbers[finalField[xn][y] - 1] = 0;//false;
        }

        for (byte i = 0; i < 9; i++)
        {
            if (numbers[i] == 1 && tried[x][y][i] == 0)
                possibilitiesFL.add( (byte)(i + 1) );
        }

        return possibilitiesFL;
    }

    public void printField()
    {
        for (byte y = 0; y < 9; y++)
        {
            for (byte x = 0; x < 9; x++)
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
    }

    private byte rowColumGetter(int i)
    {
        if (i < 6 && i > 2)
        {
            //b++;
            return 3;
        }
        else if (i < 3)
        {
            //a++;
            return 0;
        }
        else
        {
            //c++;
            return 6;
        }
    }

    public void checkValidity()
    {

        int add = 0;
        int mul = 0;
        boolean validity = true;
        // Reihen checken
        for (byte y = 0; y < 9; y++)
        {
            add = 0;
            mul = 0;
            for (byte x = 0; x < 9; x++)
            {
                add += finalField[x][y];
                mul *= finalField[x][y];
            }
            if (add != 45 && mul != 362880)
            {
                System.out.println("add: " + add + " mul: " + mul);
                validity = false;
            }
        }
        // Spalten checken
        for (byte x = 0; x < 9; x++)
        {
            add = 0;
            mul = 0;
            for (byte y = 0; y < 9; y++)
            {
                add += finalField[x][y];
                mul *= finalField[x][y];
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
        for (byte x = 0; x < 9; x++)
        {
            for (byte y = 0; y < 9; y++)
            {
                finalField[x][y] = 0;

                for (byte z = 0; z < 9; z++)
                {
                    tried[x][y][z] = 0;
                }
            }
        }
    }

    public void printABC()
    {
        System.out.println("A: "+ a + " B: " + b + " C: " + c);
    }

}
