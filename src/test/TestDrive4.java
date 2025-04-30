package test;

import umletConverter_class.ConverterUMLet;

public class TestDrive4 {
    public static void main(String[] args) {
        String code = "public abstract class Sample4 {\n"
        		+ "\n"
        		+ "    // フィールド\n"
        		+ "    private int id;\n"
        		+ "    private String name;\n"
        		+ "\n"
        		+ "    // コンストラクタ\n"
        		+ "    public Sample4(int id, String name) {\n"
        		+ "        this.id = id;\n"
        		+ "        this.name = name;\n"
        		+ "    }\n"
        		+ "\n"
        		+ "    // 抽象メソッド\n"
        		+ "    public abstract void makeSound();\n"
        		+ "\n"
        		+ "    // 静的メソッド\n"
        		+ "    public static void staticMethod() {\n"
        		+ "        System.out.println(\"This is a static method.\");\n"
        		+ "    }\n"
        		+ "\n"
        		+ "    // 通常のメソッド\n"
        		+ "    public void normalMethod() {\n"
        		+ "        System.out.println(\"This is a normal method.\");\n"
        		+ "    }\n"
        		+ "\n"
        		+ "}\n"
        		+ "";
		ConverterUMLet converter = new ConverterUMLet(code);
		System.out.println(converter.convertCode());
    }
}
