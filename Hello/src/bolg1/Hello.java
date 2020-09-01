package bolg1;

import java.util.Objects;

public class Hello implements Cloneable {
    private String str="tai";
    private Person hello;
    private int c;// 堆

    public Hello() {
        this.hello = new Person();
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        Hello hello = new Hello();
        Object clone = hello.clone();
        System.out.println(hello==clone);
        boolean equals = hello.equals(clone);
        System.out.println(hello.equals(clone));
        System.out.println(hello.getClass()==clone.getClass());
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Hello clone =(Hello) super.clone();
        clone.hello = (Person) this.hello.clone();
        return clone;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hello hello1 = (Hello) o;
        return c == hello1.c &&
                Objects.equals(str, hello1.str) &&
                Objects.equals(hello, hello1.hello);
    }

    @Override
    public int hashCode() {
        return Objects.hash(str, hello, c);
    }


}
