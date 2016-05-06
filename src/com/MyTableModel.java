
package com;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author cipri_000
 */
public class MyTableModel extends DefaultTableModel{
    public MyTableModel(Object[][] os, Object[] os1) {
       super(os,os1);
    }
     @Override
    public boolean isCellEditable(int row, int column) {
       //all cells false
       return false;
    }
}
