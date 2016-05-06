package com;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.tree.DefaultMutableTreeNode;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

public class UserGui {
	
	DBConnection connection = DBConnection.getInstance();
	MainWindow mainWindow= new MainWindow("GenericDatabaseClient");
	Connection con;
	MyJTree tree;
	JSplitPane splitPane;
	JSplitPane right;
	JTextArea query;
	JButton submitQuery;
	private JScrollPane treeScroller;
	
	JPanel inputForQueries,containerPanel;
	public static JPanel resultPanel= new JPanel();
	public JDesktopPane paneDesktop = new JDesktopPane();
	Document report= new Document();
	
	
	
	
	
	public UserGui() throws SQLException, FileNotFoundException, DocumentException {
		con=DriverManager.getConnection(connection.url,connection.username,connection.password);
		inputForQueries= new JPanel();
		PdfWriter.getInstance(report,new FileOutputStream("report.pdf"));
		report.open();
	}
	
	
	public void setGui()
	{
		mainWindow.setSize(800,600);
		mainWindow.addWindowListener( new WindowAdapter() {
			 @Override
	            public void windowClosing(WindowEvent e) {
				 	String objButton[]={"Yes","No"};
				 	int promptResult=JOptionPane.showOptionDialog(null, "Are you sure you want to exit", "GenericDatabaseClient", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, objButton, objButton[1]);
				 	
				 	if(promptResult==JOptionPane.YES_OPTION)
				 	{
				 		try
				 		{
				 			con.close();
				 			report.close();
				 			System.exit(0);
				 		}
				 		catch(SQLException excep)
				 		{
				 			excep.printStackTrace();
				 		}
				 	}			 
			 	} 
			
		});
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Tables");
		tree=new MyJTree(root, this);
		tree.initialize();
		tree.addTreeSelectionListener(new SelectionListener(this));
		treeScroller= new JScrollPane(tree);
		
		
		
		inputForQueries = new JPanel();
		inputForQueries.setLayout(new GridLayout(1,1));
		JPanel aux = new JPanel();
		submitQuery= new JButton("Submit");
		submitQuery.addActionListener(new SubmitQueryListener(this));
		query= new JTextArea(5,30);
		aux.setLayout(new FlowLayout());
		aux.add(new JLabel("Enter your query"));
		JPanel aux2= new JPanel(new BorderLayout());
		aux2.add(query,BorderLayout.CENTER);
		aux.add(aux2);
		aux.add(submitQuery);
		inputForQueries.add(aux);
		
		
		resultPanel.setLayout(null);
		right= new JSplitPane(JSplitPane.VERTICAL_SPLIT,inputForQueries,resultPanel);
		right.setResizeWeight(0.3f);
		splitPane= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,treeScroller,right);
		splitPane.setResizeWeight(0.3f);
		
		JMenuBar menu = new JMenuBar();
		JMenu graph= new JMenu("Graph");
		menu.add(graph);
		JMenuItem generateGraph = new JMenuItem("Generate Graph");
		generateGraph.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new Bonus(con);
				
			}
		});
		graph.add(generateGraph);
		
		
		
		
		
		mainWindow.setJMenuBar(menu);
		mainWindow.getContentPane().add(splitPane);		
		mainWindow.setVisible(true);
		
		
		
		
		
		
	}

}
