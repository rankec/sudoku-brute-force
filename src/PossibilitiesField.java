/**
 * Created with IntelliJ IDEA.
 * User: Rankec
 * Date: 12.04.13
 * Time: 22:03
 * To change this template use File | Settings | File Templates.
 */
public class PossibilitiesField extends PossibilitiesStructure {

    public boolean[] getPossibilitiesList(int x, int y)
    {
        int xMax = (x / 3);
        int yMax = (y / 3);

        if (xMax == 0)
        {
            return posList[0 + yMax];
        }
        else if (xMax == 1)
        {
            return posList[3 + yMax];
        }
        else
        {
            return posList[6 + yMax];
        }
    }

    public void addPos(int x, int y, int value)
    {
        int xMax = (x / 3);
        int yMax = (y / 3);

        if (xMax == 0)
        {
            posList[0 + yMax][value] = true;
        }
        else if (xMax == 1)
        {
            posList[3 + yMax][value] = true;
        }
        else
        {
            posList[6 + yMax][value] = true;
        }
    }

    public void delPos(int x, int y, int value)
    {
        int xMax = (x / 3);
        int yMax = (y / 3);

        if (xMax == 0)
        {
            posList[0 + yMax][value] = false;
        }
        else if (xMax == 1)
        {
            posList[3 + yMax][value] = false;
        }
        else
        {
            posList[6 + yMax][value] = false;
        }
    }
}
