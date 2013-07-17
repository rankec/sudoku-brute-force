/**
 * Created with IntelliJ IDEA.
 * User: Rankec
 * Date: 10.01.13
 * Time: 17:54
 * To change this template use File | Settings | File Templates.
 */
import java.util.Random;

public class RandomNumber
{

    Random r;

    public RandomNumber()
    {
        r = new Random();

    }


    public int getNewRandom(int min, int max)
    {
        return r.nextInt(max - min) + min;
    }

    public int getNewRandom(int max)
    {

        /*  old version

        int min = 0;

        return r.nextInt(max - min) + min;
        */

        return r.nextInt(max);
    }
}
