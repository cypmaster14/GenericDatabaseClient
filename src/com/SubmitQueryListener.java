package com;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.TabExpander;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class SubmitQueryListener implements ActionListener {
	
	UserGui gui;
	
	
	public SubmitQueryListener(UserGui gui) {
		// TODO Auto-generated constructor stub
		this.gui=gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		String query=gui.query.getText().trim();
		JInternalFrame internFrame= new JInternalFrame("QueryResult");
		internFrame.setSize(300, 200);
		internFrame.setMaximizable(true);
		internFrame.setResizable(true);
		internFrame.setClosable(true);
		internFrame.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		try
		{
			Statement st= gui.con.createStatement();
			if(query.toLowerCase().startsWith("select"))
			{
				ResultSet rs= st.executeQuery(query);
				ResultSetMetaData rsmd=rs.getMetaData();
				int numberOfColumns= rsmd.getColumnCount();
				String tableHead[]= new String[numberOfColumns];
				
				
				for(int i=1;i<=numberOfColumns;i++)
				{
					System.out.print(rsmd.getColumnName(i)+" ");
					tableHead[i-1]=rsmd.getColumnName(i);
				}
				System.out.println();
				
				List<List<String>> totalResult= new ArrayList<>();
				while(rs.next())
				{
					List<String> rowResult=new ArrayList<>(); 
					for(int i=1;i<=numberOfColumns;i++)
					{
						System.out.print(rs.getString(i)+" ");
						rowResult.add(rs.getString(i));
					}
					totalResult.add(rowResult);
					
					System.out.println();
				}
				
				int numberOfRows=totalResult.size();
				String tableContent[][]= new String[numberOfRows][numberOfColumns];
				int i=0,j=0;
				for(List<String> linie:totalResult)
				{
					j=0;
					for(String camp:linie)
					{
						tableContent[i][j]=camp;
						j++;
					}
					i++;
				}
				
				
				JTable table = new JTable();
				table.setModel(new MyTableModel(tableContent, tableHead));
				table.setShowGrid(false);
		        table.setShowHorizontalLines(false);
		        table.setShowVerticalLines(false);
		        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		        renderer.setHorizontalAlignment(JLabel.CENTER);
		        table.setDefaultRenderer(Object.class, renderer);
				internFrame.add(table);
				internFrame.setVisible(true);			
				
				JScrollPane paneScroll= new JScrollPane();
				internFrame.setContentPane(paneScroll);
				paneScroll.setViewportView(table);
				UserGui.resultPanel.add(internFrame);
				
				///Incepem sa scriem in tabel
				PdfPTable reportTabel= new PdfPTable(numberOfColumns);
				for(i=0;i<tableHead.length;i++)
				{
					reportTabel.addCell(new PdfPCell(new Paragraph(tableHead[i])));
				}
				
				for(i=0;i<numberOfRows;i++)
				{
					for(j=0;j<numberOfColumns;j++)
					{
						reportTabel.addCell(new PdfPCell(new Paragraph(tableContent[i][j])));
					}
				}
				
				
				
				
				gui.report.add(reportTabel);
				
				
			}
			
			else
			{
				int numberOfColumnsAffected = st.executeUpdate(query);
				System.out.println("Command:"+query+" affected "+numberOfColumnsAffected+" rows");
				
				
			}
			
		}
		catch(SQLException ex)
		{
			JOptionPane.showMessageDialog(null, "Invalid Query");
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
	}
	
	

}
