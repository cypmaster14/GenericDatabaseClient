package com;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

public class MyJTree extends JTree {
	
	public DefaultMutableTreeNode root;
	UserGui gui;
	
	public MyJTree(DefaultMutableTreeNode root,UserGui gui)
	{
		super(root);
		this.root=root;
		this.gui=gui;
	}
	
	
	
	
	public void initialize()
	{
		
		try {
			
			
			DatabaseMetaData dbmd=gui.con.getMetaData();
			String[] types = {"TABLE"};
			ResultSet rs=dbmd.getTables(null, null, "%", types);
			boolean gasit=false;
			
			while(rs.next())
			{
				
				if(gasit==false)
				{
					if(rs.getString("TABLE_NAME").compareTo("OL$NODES")==0)
						gasit=true;
				}
				else
				{
					if(rs.getString("TABLE_NAME").compareTo("AUDIT_ACTIONS")==0)
						break;
					else
					{
						//System.out.println(rs.getString("TABLE_NAME"));
						root.add(new DefaultMutableTreeNode(rs.getString("TABLE_NAME")));
					}
						
				}
				
				
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
