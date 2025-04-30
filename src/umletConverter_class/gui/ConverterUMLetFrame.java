package umletConverter_class.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import umletConverter_class.ConverterUMLet;

/**
 * フレーム作成クラス
 */
public class ConverterUMLetFrame implements ActionListener {
	private final int FRAME_WIDTH = 1200;
	private final int FRAME_HEIGHT = 920;
	private JTextArea textArea, convertedArea;
	private JButton convertButton;

	private int errorNum;
	private boolean success;
	/**
	 * 生成メソッド
	 * @return JFrame
	 */
	public JFrame generateFrame() {
		// JFrameを作成
		JFrame frame = new JFrame("UMLet Converter");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setLayout(new BorderLayout());

		// 上部ラベルの作成
		JPanel upperPanel = new JPanel();
		upperPanel.setPreferredSize(new Dimension(1200, 125));
		upperPanel.setLayout(new GridLayout(1, 7)); // 1行7列のレイアウト

		// 画像の設定（125x125サイズにリサイズ）
		ImageIcon javaIcon = new ImageIcon("./img/Java_logo.png");
		ImageIcon umletIcon = new ImageIcon("./img/UMLet_logo.png");

		Image javaImg = javaIcon.getImage().getScaledInstance(125, 125, Image.SCALE_SMOOTH); // 125x125にリサイズ
		Image umletImg = umletIcon.getImage().getScaledInstance(125, 125, Image.SCALE_SMOOTH); // 125x125にリサイズ

		javaIcon = new ImageIcon(javaImg);
		umletIcon = new ImageIcon(umletImg);

		// 画像のラベルを作成（サイズ指定済み）
		JLabel javaPng = new JLabel(javaIcon);
		javaPng.setPreferredSize(new Dimension(125, 125)); // サイズ指定

		JLabel umletPng = new JLabel(umletIcon);
		umletPng.setPreferredSize(new Dimension(125, 125)); // サイズ指定

		// 文字列ラベルの設定
		JLabel stringLabel = new JLabel(">>Java to UMLet>>");
		Font fontString = new Font("stsongstdlight", Font.PLAIN, 35); // フォント設定
		stringLabel.setFont(fontString);
		stringLabel.setHorizontalAlignment(JLabel.CENTER); // 中央揃え

		// 上部の3:1:3配置
		upperPanel.add(javaPng); // 左側のJavaロゴ
		upperPanel.add(stringLabel); // 中央の文字列
		upperPanel.add(umletPng); // 右側のUMLetロゴ

		// フレームに上部パネルを追加
		frame.add(upperPanel, BorderLayout.NORTH);

		// 下部パネルにnullレイアウトを設定
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(null); // 座標指定のためnullレイアウトに設定

		// 左側のテキストエリア（3の部分）
		textArea = new JTextArea();
		textArea.setBounds(0, 0, FRAME_WIDTH * 3 / 7, FRAME_HEIGHT - 170); // 左側のテキストエリアのサイズと位置
		JScrollPane jsp = new JScrollPane(textArea);
		jsp.setBounds(0, 0, FRAME_WIDTH * 3 / 7, FRAME_HEIGHT - 170); // JScrollPaneにboundsを設定

		// 変換ボタン（中央1）
		convertButton = new JButton("変換");
		convertButton.setFont(new Font("stsongstdlight", Font.PLAIN, 30));
		convertButton.setBounds(FRAME_WIDTH * 3 / 7 + (FRAME_WIDTH / 7 - 170) / 2, (FRAME_HEIGHT - 125 - 170) / 2, 170,
				170); // 中央に配置し、サイズを170x170に設定
		convertButton.addActionListener(this);

		// 右側の変換結果テキストエリア（3の部分）
		convertedArea = new JTextArea();
		convertedArea.setBounds(FRAME_WIDTH * 4 / 7, 0, FRAME_WIDTH * 3 / 7, FRAME_HEIGHT - 170); // 右側のテキストエリアのサイズと位置
		JScrollPane convertedJsp = new JScrollPane(convertedArea);
		convertedJsp.setBounds(FRAME_WIDTH * 4 / 7, 0, FRAME_WIDTH * 3 / 7, FRAME_HEIGHT - 170); // JScrollPaneにboundsを設定
		
		// コンポーネントをbottomPanelに追加
		bottomPanel.add(jsp);
		bottomPanel.add(convertButton);
		bottomPanel.add(convertedJsp);
		
		// 下部パネルに追加する名前パネルを作成
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new BorderLayout());  // BorderLayoutを設定

		// 名前ラベルの作成
		JLabel nameLabel = new JLabel("Made by 上野");
		nameLabel.setFont(new Font("Arial", Font.BOLD, 15));
		nameLabel.setBackground(Color.darkGray);
		nameLabel.setForeground(Color.WHITE);  // 文字色を赤に設定
		nameLabel.setHorizontalAlignment(JLabel.CENTER);  // 中央揃え

		// 名前ラベルをnamePanelに追加
		namePanel.setBackground(Color.DARK_GRAY);
		namePanel.add(nameLabel, BorderLayout.CENTER);  // 中央に追加

		// bottomPanelをフレームに追加
		frame.add(bottomPanel, BorderLayout.CENTER);

		// namePanelをフレームの下部に追加
		frame.add(namePanel, BorderLayout.SOUTH);



		// パネルをフレームに追加
		frame.add(bottomPanel, BorderLayout.CENTER);

		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT); // フレームサイズ設定
		return frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	    if (textArea.getText().length() > 0) {
	        ConverterUMLet converter = new ConverterUMLet(textArea.getText());
	        String convertedText = converter.convertCode();
	        convertedArea.setText(convertedText);
	        System.out.println(convertedText);
	        if (!success) {
	            success = true;
	        }
	    } else {
	        // エラーメッセージの作成
	        String errorMessage = "変換失敗〜\n";
	        errorNum++;
	        if (errorNum == 2) {
	            errorMessage += "お前2回も同じこと言わすな？？？\n";
	        } else if (errorNum == 3) {
	            errorMessage += "二度あることは三度あるらしいな？";
	        } else if (errorNum > 3) {
	            errorMessage += "" + errorNum + "回もやってんの？？？\n懲りないなぁ？お前なぁ？？\n";
	            if (success) {
	                errorMessage += "てかなんで一度できたことができなくなる？？？？？";
	            }
	        } else if (success) {
	            errorMessage += "なんで一度できたことができなくなる？？？？？";
	        }
	        System.out.println(errorMessage);
	        // ボタンラベルをエラー回数に基づいて変更
	        String[] buttonLabels = (errorNum == 1) ? new String[]{"OK"} : new String[]{"ほんますんません"};

	        // カスタムボタンラベルでエラーダイアログを表示
	        JOptionPane.showOptionDialog(
	            null,               // 親コンポーネント
	            errorMessage,       // メッセージ
	            "エラー",            // タイトル
	            JOptionPane.DEFAULT_OPTION,  // ボタンの種類（デフォルト）
	            JOptionPane.ERROR_MESSAGE,   // アイコン（エラーアイコン）
	            null,               // アイコンを指定したい場合
	            buttonLabels,       // ボタンラベルを条件に応じて変更
	            null                // ボタンのデフォルト選択肢
	        );
	    }
	}
}
