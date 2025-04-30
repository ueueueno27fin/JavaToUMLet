public class Sample4 extends Sample3 {

    private int id;
    private String name;

    public Sample4(int id, String name) {
        super(name, 0); // 名前を親クラスに渡し、年齢は0で設定
        this.id = id;
        this.name = name;
    }

    @Override
    public void makeSound() {
        System.out.println("Sample4 makes a sound!");
    }

    public static void staticMethod() {
        System.out.println("This is a static method.");
    }

    public void normalMethod() {
        System.out.println("This is a normal method.");
    }
}
