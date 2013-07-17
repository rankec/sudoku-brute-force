/**
 * Created with IntelliJ IDEA.
 * User: Rankec
 * Date: 12.04.13
 * Time: 22:02
 * To change this template use File | Settings | File Templates.
 */
public class PossibilitiesLeft extends PossibilitiesStructure {

    public boolean[] getPossibilitiesList(int x)
    {
       return posList[x];
    }

    public void addPos(int x, int value)
    {
        posList[x][value] = true;
    }

    public void delPos(int x, int value)
    {
        posList[x][value] = false;
    }
}
