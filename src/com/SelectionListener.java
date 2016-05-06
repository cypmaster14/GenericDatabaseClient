package com;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.xml.transform.Result;

public class SelectionListener  implements TreeSelectionListener{
	
	UserGui gui;
	
	public  SelectionListener( UserGui gui) {
		// TODO Auto-generated constructor stub
		this.gui=gui;
	}

	@Override
	public void valueChanged(TreeSelectionEvent arg0) {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) arg0.getPath().getLastPathComponent();
        String selectedNodeName = selectedNode.toString();
        
        if(selectedNodeName.compareTo(gui.tree.root.toString())==0)
        		return;
        
        //System.out.println(selectedNodeName);
        Statement stmt;
		try {
			stmt = gui.con.createStatement();
			ResultSet rs= stmt.executeQuery("Select * from "+selectedNodeName);
			ResultSetMetaData rsmd= rs.getMetaData();
			int numberOfColums= rsmd.getColumnCount();
			
			for(int i=1;i<=numberOfColums;i++)
				selectedNode.add(new DefaultMutableTreeNode(rsmd.getColumnName(i)));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
        
        
        
		
	}
	
	

}
