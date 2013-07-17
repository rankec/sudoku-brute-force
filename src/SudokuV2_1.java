import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Rankec
 * Date: 08.04.13
 * Time: 22:03
 * To change this template use File | Settings | File Templates.
 */
public class SudokuV2_1 {

    RandomNumber r;
    ArrayList<Integer> possibilities;

    PossibilitiesLeft possiLeft = new PossibilitiesLeft();
    PossibilitiesTop possiTop = new PossibilitiesTop();
    PossibilitiesField possiField = new PossibilitiesField();

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

    public SudokuV2_1()
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
               // System.out.println("x: "+ x + " y: " + y);
                if (x < 0)
                {
                    int a = 0;
                }


                ArrayList<Integer> possibleNumbers = getPossibilities(x, y);
                int sizeArray = possibleNumbers.size();
                if (sizeArray != 0)
                {
                    int number = possibleNumbers.get(r.getNewRandom(sizeArray));
                    sudokuField[x][y] = number;
                    tried[x][y][number] = true;

                    possiField.delPos(x,y,number);
                    possiTop.delPos(y,number);
                    possiLeft.delPos(x,number);
                }
                else
                {
                    resetTriedAtXY(x, y);

                    if (sudokuField[x][y] != -1)
                    {

                        sudokuField[x][y] = -1;

                    }
                    y -= 1;

                    if (y < 0)
                    {
                        y += 8;
                        x--;
                    }

                    if (x == -1)
                    {
                        SudokuV2_1 newOne = new SudokuV2_1();
                        newOne.generate();
                        sudokuField = newOne.getSudokuArray();
                        x = 9;
                        y = 10;
                    }
                    else {


                    int number = sudokuField[x][y];
                    possiField.addPos(x,y,number);
                    possiTop.addPos(y,number);
                    possiLeft.addPos(x,number);
                    }
                    y -= 1;



                }

                //printField();
                //System.out.println("-----------");
            }
        }
        //printField();
        //System.out.println("-----------");
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
        boolean[] posListLeft = possiLeft.getPossibilitiesList(x);
        boolean[] posListTop = possiTop.getPossibilitiesList(y);
        boolean[] posListField = possiField.getPossibilitiesList(x,y);


        for (int i = 0; i < 9; i++)
        {
            if (posListLeft[i] && posListTop[i] && posListField[i] && !tried[x][y][i])
                possibilities.add(i);
        }

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

        possiLeft = new PossibilitiesLeft();
        possiTop = new PossibilitiesTop();
        possiField = new PossibilitiesField();

    }

    public int[][] getSudokuArray()
    {
        return sudokuField;
    }


}




