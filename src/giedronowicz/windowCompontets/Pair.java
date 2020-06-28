package giedronowicz.windowCompontets;

public class Pair {
    public String number;
    public String task;
    public boolean isSelected;

    public Pair( String number, String task )
    {
        this.number = number;
        this.task = task;
        this.isSelected = false;
    }

    @Override
    public String toString() {
        return task;
    }
}
