/**
 * Created with IntelliJ IDEA.
 * User: Rankec
 * Date: 15.04.13
 * Time: 21:09
 * To change this template use File | Settings | File Templates.
 */
public class Squares {

    int across;
    int down;
    int region;
    int value;
    int index;

    public Squares()
    {
        across = 0;
        down = 0;
        region = 0;
        value = 0;
        index = 0;
    }

    public int getAcross() {
        return across;
    }

    public void setAcross(int across) {
        this.across = across;
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
