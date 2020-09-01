package bolg1;

public class Person implements Cloneable {
    private String name="wan";

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
