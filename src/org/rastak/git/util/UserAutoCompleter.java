/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rastak.git.util;

import org.rastak.git.util.AutoCompleter;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author r.rastakfard
 */
public class UserAutoCompleter extends AutoCompleter {

    private Set<String> users;

    public UserAutoCompleter(JTextComponent comp,Set<String> users) {
        super(comp);
        this.users = users;
    }

    protected boolean updateListData() {
        String value = textComp.getText();
        if (StringUtils.isEmpty(value)){
            return false;
        }
        List<String> foundUsers = new ArrayList<String>();

        for (String user : users) {
            if (user.startsWith(value)) {
                foundUsers.add(user);
            }
        }

        if (foundUsers.isEmpty()) {
            list.setListData(new String[0]);
            return true;
        } else {
            list.setListData(foundUsers.toArray());
            return true;
        }
    }

    protected void acceptedListItem(String selected) {
        if (selected == null) {
            return;
        }
        textComp.setText(selected);
    }
}
