import javax.swing.JFrame;

import umletConverter_class.gui.ConverterUMLetFrame;

public class UMLetGenerator {
	public static void main(String[] args) {
		ConverterUMLetFrame cuf = new ConverterUMLetFrame();
		JFrame frame = cuf.generateFrame();
		frame.setVisible(true);
	}	
}
