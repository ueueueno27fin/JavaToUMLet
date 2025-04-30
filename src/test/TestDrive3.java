package test;

import umletConverter_class.ConverterUMLet;

public class TestDrive3 {
    public static void main(String[] args) {
        String code = "public abstract class Animal {\n"
        		+ "    private String name;\n"
        		+ "\n"
        		+ "    public abstract void makeSound();\n"
        		+ "\n"
        		+ "    public String getName() {\n"
        		+ "        return name;\n"
        		+ "    }\n"
        		+ "}\n"
        		+ "";
		ConverterUMLet converter = new ConverterUMLet(code);
		System.out.println(converter.convertCode());
    }
}
