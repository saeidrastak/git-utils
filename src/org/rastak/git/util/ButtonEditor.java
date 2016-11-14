/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rastak.git.util;

import org.rastak.git.GetAffectedFilesInCommitForm;
import static org.rastak.git.GetAffectedFilesInCommitForm.CHANGE_LIST_FILE_NAME;
import static org.rastak.git.GetAffectedFilesInCommitForm.SEPARATOR;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author r.rastakfard
 */
public class ButtonEditor extends DefaultCellEditor implements TableCellRenderer {

    protected JButton button;
    private JFrame parent;

    private String commitId;

    private boolean isPushed;

    public ButtonEditor(JCheckBox checkBox, JFrame parent) {
        super(checkBox);
        this.parent = parent;

    }

    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
//        if (isSelected) {
//            button.setForeground(table.getSelectionForeground());
//            button.setBackground(table.getSelectionBackground());
//        } else {
//            button.setForeground(table.getForeground());
//            button.setBackground(table.getBackground());
//        }
        commitId = (String) table.getModel().getValueAt(row, 1);
        isPushed = true;
        return button;
    }

    public Object getCellEditorValue() {
        if (isPushed) {
            try {
                GetAffectedFilesInCommitForm parentForm = (GetAffectedFilesInCommitForm) parent;
                parentForm.deleteLastSavedSources();
                HashMap<String, List<String>> affectedFilesInCommits = parentForm.getAffectedFilesInCommits(Arrays.asList(commitId));
                List<String> changedFiles = affectedFilesInCommits.get(commitId);
                Set<String> commits = new HashSet<String>();
                commits.addAll(changedFiles);                                
                parentForm.appendCommitedFilesInChangeListFile(commitId, changedFiles);                
                parentForm.UpdateChangeListPanel(commits, commitId, commitId);
                JOptionPane.showMessageDialog(null, parentForm.getChangeListPanel(), "Change List Files", JOptionPane.PLAIN_MESSAGE);
            } catch (IOException ex) {
                Logger.getLogger(ButtonEditor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        isPushed = false;
        return new String("");
    }

    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        BufferedImage image = null;
        try {
            URL file = getClass().getResource("download-icon.png");
            image = ImageIO.read(file);
        } catch (IOException ioex) {
            System.err.println("load error: " + ioex.getMessage());
        }
        ImageIcon icon = new ImageIcon(image);
        button = new JButton(icon);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        button.setToolTipText("Show change list & download files");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
        return button;
    }
}
