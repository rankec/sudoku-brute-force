import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Rankec
 * Date: 15.04.13
 * Time: 20:58
 * To change this template use File | Settings | File Templates.
 */
public class InternetSudoku {
    RandomNumber r = new RandomNumber();


    public void generateGrid()
    {
        Squares[] squares = new Squares[80]; //an arraylist of squares: see line 86
        ArrayList<Integer>[] available = new ArrayList[80];
        //an arraylist of generic lists (nested lists)
        //we use this to keep track of what numbers we can still use in what squares
        int c = 0; //use this to count the square we are up to

        for (int x = 0; x < available.length;x++)
        {
            available[x] = new ArrayList<Integer>();
            for (int i = 1; i < 10; i++)
            {
                available[x].add(i);
            }
        }


        while (c <= 81) // 'we want to fill every square object with values
        {
            if (!(available[c].size() == 0))    // if every number has been tried
                                                // and failed then backtrack
            {
                int i = getRnd(0, available[c].size() - 1);
                int z = available[c].get(i);
                if (Conflicts(squares, item(c, z)) == false)    //do a check with the proposed number
                {
                    squares[c] = item(c, z);
                    available[c].remove(i); //we also remove it from its individual list
                    c+= 1; //move to the next number

                }
                else
                {
                    available[c].remove(i); //this number conflicts so we remove it from its list
                }
            }
            else
            {
                for (int y = 1; y < 10; y++) // forget anything about the current square
                {
                    available[c].add(y);    //by resetting its available numbers
                }
                squares[c -1] = null; //go back and retry a different number
                c-= 1;  //in the previous square
            }
        }
    // this produces the output list of squares
        printField(squares);

    }


    public void printField(Squares[] square)
    {
        for (int x = 0; x < 81; x++)
        {

                if (x + 1 % 9 == 0)
                {
                    System.out.println(square[x].getValue());
                }
                else
                {
                    System.out.print(square[x].getValue());
                }

        }
    }

    private int getRnd(int lower, int upper)
    {

        return r.getNewRandom(lower,upper);

    }

    private boolean Conflicts(Squares currentValues[], Squares test)
    {
        for(Squares s: currentValues)
        {
            if ((s.getAcross() != 0 && s.getAcross() == test.getAcross()) || (s.getDown() != 0 && s.getDown() == test.getDown())
            || (s.getRegion() != 0 && s.getRegion() == test.getRegion()))
            {
                if (s.getValue() == test.getValue())
                    return true;

            }

        }
        return false;
    }

    private Squares item(int n, int v)
    {
        n += 1;
        Squares item = new Squares();
        item.setAcross(getAcrossFromNumber(n));
        item.setDown(getDownFromNumber(n));
        item.setRegion(getRegionFromNumber(n));
        item.setValue(v);
        item.setIndex(n - 1);
        return item;
    }


   private int getAcrossFromNumber(int n)
   {
       int k;
       k = n % 9;
       if (k == 0)
           return 9;
       return k;
   }

    private int getDownFromNumber(int n)
    {
        int k;
        if (getAcrossFromNumber(n) == 9)
            k = n/9;
        else
            k = n/9 + 1;
        return k;
    }

    private int getRegionFromNumber(int n)
    {
        int k;
        int a = getAcrossFromNumber(n);
        int d = getDownFromNumber(n);

        if (1 <= a && a < 4 && 1 <= d && d <4)
            k = 1;
        else if(4 <= a && a < 7 && 1 <= d && d <4)
            k = 2;
        else if(7 <= a && a < 10 && 1 <= d && d <4)
            k = 3;
        else if (1 <= a && a < 4 && 4 <= d && d < 7)
            k = 4;
        else if(4 <= a && a < 7 && 4 <= d && d < 7)
            k = 5;
        else if(7 <= a && a < 10 && 4 <= d && d < 7)
            k = 6;
        else if(1 <= a && a < 4 && 7 <= d && d < 10)
            k = 7;
        else if(4 <= a && a < 7 && 7 <= d && d < 10)
            k = 8;
        else
            k = 9;

        return k;

    }
}
