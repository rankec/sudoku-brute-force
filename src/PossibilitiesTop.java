/**
 * Created with IntelliJ IDEA.
 * User: Rankec
 * Date: 12.04.13
 * Time: 22:02
 * To change this template use File | Settings | File Templates.
 */
public class PossibilitiesTop extends PossibilitiesStructure {

    public boolean[] getPossibilitiesList(int y)
    {
        return posList[y];
    }

    public void addPos(int y, int value)
    {
        posList[y][value] = true;
    }

    public void delPos(int y, int value)
    {
        posList[y][value] = false;
    }
}
