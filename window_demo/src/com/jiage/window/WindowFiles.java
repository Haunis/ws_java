package com.jiage.window;

import java.awt.Color;
import java.awt.Container;

import javax.swing.AbstractListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

/**
 * 显示当前文件夹有多少文件/文件夹
 *
 */
public class WindowFiles extends JFrame {
	private static final long serialVersionUID = 1L;

	public void launchFrame(String[] contents) {
		Container container = getContentPane();
//		container.setLayout(null);
		JList<String> jList = new JList<>(new MyListModel(contents));
		JScrollPane jScrollPane = new JScrollPane(jList);
		jScrollPane.setBounds(10, 10, 100, 100);
		jScrollPane.setBackground(Color.BLUE);
		container.add(jList);
		container.setBackground(Color.GREEN);
		setTitle("use jlist ");
		setVisible(true);
		setSize(400, 300);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}

	public class MyListModel extends AbstractListModel<String> {
		private static final long serialVersionUID = 1L;
		private String[] contents;

		public MyListModel(String[] contents) {
			this.contents = contents;
		}

		@Override
		public String getElementAt(int index) {
			// 重写 getElementAt() 方法
			if (index < contents.length) {
				return contents[index++];
			} else {
				return null;
			}
		}

		@Override
		public int getSize() {
			// 重写 getSize() 方法
			return contents.length;
		}
	}
}
