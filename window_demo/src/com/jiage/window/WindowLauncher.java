package com.jiage.window;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import com.jiage.demo.ExcelDemo;
import com.jiage.excel.ExcelDao;
import com.jiage.excel.ResultListener;
import com.jiage.utils.Utils;

/**
 * 可视化编程
 * 
 * 参考：https://www.cnblogs.com/xiaomengyuan/p/9087987.html
 * https://www.cnblogs.com/hthuang/p/3460234.html
 *
 */
public class WindowLauncher extends Frame implements ResultListener {
	private static final long serialVersionUID = 992098650056266773L;
	private WindowAdapter mWindowAdapter = new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	};
	private Button bt;
	private Button bt2;
	private Label mLbResult;
	private int mCount;
	private static final String STRING_EXCUTE = "Excute";
	private static final String STRING_EXIT = "Exit";
	private static final int DEFAULT_LABEL_TEXT_SIZE = 42;

	public void launchFrame() {
		initView();
		registerListener();
	}

	private void initView() {
		setTitle("ver1.0--7.12");
//		setLayout(new GridLayout());
//		FlowLayout fl = new FlowLayout();
//		fl.setHgap(10);
//		setLayout(fl);
		setLayout(null);

		int screenWidth = Utils.getScreenWidth();
		int screenHeight = Utils.getScreenHeight();
//		System.out.println("screenWidth=" + screenWidth + " ,screenHeight=" + screenHeight);
		int windowWidth = screenWidth / 3;
		int windowHeight = screenHeight / 3;
		int xLocation = (screenWidth - windowWidth) / 2;
		int yLocation = (screenHeight - windowHeight) / 2;
		setSize(windowWidth, windowHeight);// 定义窗口大小
		setLocation(xLocation, yLocation);// 定义窗口位置
		setVisible(true);// 定义是否可见，true为不可见
		setBackground(new Color(240, 240, 240));
		setResizable(false);

		int laberWidht = windowWidth;
		int labelHeight = windowHeight * 3 / 10;
		mLbResult = new Label("", Label.CENTER);
		mLbResult.setSize(laberWidht, labelHeight);
		mLbResult.setLocation(0, (windowHeight - labelHeight) / 2);
		mLbResult.setFont(new Font(null, Font.BOLD, DEFAULT_LABEL_TEXT_SIZE));
		mLbResult.setForeground(new Color(255, 20, 20));
//		mLbResult.setBackground(Color.GREEN);

		int buttonWidth = windowWidth / 5;
		int buttonHeight = 40;
		bt = new Button(STRING_EXCUTE);
		bt.setFont(new Font(null, Font.ROMAN_BASELINE, 18));
		bt.setBounds(40, windowHeight * 7 / 10, buttonWidth, buttonHeight);
		bt2 = new Button(STRING_EXIT);
		bt2.setFont(new Font(null, Font.ROMAN_BASELINE, 18));
		bt2.setBounds(windowWidth - buttonWidth - 40, windowHeight * 7 / 10, buttonWidth, buttonHeight);

		add(mLbResult);
		add(bt);
		add(bt2);

	}

	private void registerListener() {
		addWindowListener(mWindowAdapter);
		bt.addActionListener(new BtClickListener(bt.getLabel()));
		bt2.addActionListener(new BtClickListener(bt2.getLabel()));
	}

	/**
	 * 遍历当前文件夹
	 */
	private void traverseDir() {
		File dir = new File("./");
		System.out.println("dir exists : " + dir.exists());
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			for (File file : files) {
				System.out.println("file : " + file.getName());
			}
			if (files != null && files.length > 0) {
				String[] contents = new String[files.length];
				for (int i = 0; i < files.length; i++) {
					contents[i] = files[i].getName();
				}
				new WindowFiles().launchFrame(contents);
			}
		}
	}

	private class BtClickListener implements ActionListener {
		private String btLabel;

		public BtClickListener(String btLabel) {
			this.btLabel = btLabel;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (btLabel) {
			case STRING_EXCUTE:
				mCount++;
				new ExcelDao(WindowLauncher.this).analyzeExcel();
//				new ExcelDemo().writeExcel();;
//				traverseDir();
				break;
			case STRING_EXIT:
				System.exit(0);
				break;
			}
		}
	}

	@Override
	public void onError(String info) {
		mLbResult.setFont(new Font(null, Font.BOLD, 15));
		mLbResult.setText(info);
	}

	@Override
	public void onComplete() {
		mLbResult.setFont(new Font(null, Font.BOLD, 18));
		mLbResult.setText(" Completed !!!");
	}
}
