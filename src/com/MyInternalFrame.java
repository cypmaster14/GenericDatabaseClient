package com;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class MyInternalFrame extends JInternalFrame {

	
	public JPanel rootPanel= new JPanel();
	public JTable table;
	public JScrollPane scrollPane;
	
	public MyInternalFrame(JTable tabel)
	{
		this.table=tabel;
		scrollPane= new JScrollPane(tabel);
		rootPanel.setLayout(new BorderLayout());
		this.setContentPane(rootPanel);
		rootPanel.add(scrollPane, BorderLayout.CENTER);
	}
	
	
}
