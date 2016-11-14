/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rastak.git.util;

import org.rastak.git.GetAffectedFilesInCommitForm;
import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author r.rastakfard
 */
public class CommitTableEditor extends AbstractCellEditor implements  TableCellRenderer {

    JPanel panel;
    public CommitTableEditor(GetAffectedFilesInCommitForm affectedFilesInCommitFormOrg) {
        this.affectedFilesInCommitFormOrg = affectedFilesInCommitFormOrg;
        AbstractAction action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Row :" + commitTable.getSelectedRow() + "    " + e.getActionCommand() + " clicked");
//                affectedFilesInCommitFormOrg.getChangeListForCommit(DEFAULT, enabled, DEFAULT, DEFAULT)
            }
        };
        JButton button1 = new JButton(action);
        button1.setText("Show Change List");

        panel = new JPanel();
        panel.add(button1);
//        panel.setBackground(commitTable.getBackground());
    }

    
    
    private JTable commitTable;
    GetAffectedFilesInCommitForm affectedFilesInCommitFormOrg;

    public void setCommitTable(JTable commitTable) {
        this.commitTable = commitTable;
    }
    
//    
//    @Override
//    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
//        JPanel panel = (JPanel) commitTable.getCellRenderer(row, column).getTableCellRendererComponent(table, value, isSelected, isSelected, row, column);
//        panel.setBackground(table.getSelectionBackground());
//        return panel;
//    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
        return panel;
    }
}
