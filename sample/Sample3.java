public abstract class Sample3 {
    private String name;
    private int age;

    public Sample3(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public abstract void makeSound();

    public void sleep() {
        System.out.println(name + " is sleeping.");
    }
}
