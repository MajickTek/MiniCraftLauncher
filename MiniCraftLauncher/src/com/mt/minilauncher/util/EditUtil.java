package com.mt.minilauncher.util;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.mt.minilauncher.objects.VersionObject;

public class EditUtil {
	public static VersionObject editInfo(VersionObject vo) {
		JPanel dialogPanel = new JPanel();
        BoxLayout bl = new BoxLayout(dialogPanel, BoxLayout.Y_AXIS);
        dialogPanel.setLayout(bl);
        
        JTextField urlField = new JTextField(vo != null ? vo.url : "", 20),
        		versionField = new JTextField(vo != null ? vo.version : "", 20),
        		changelogURLField = new JTextField(vo != null ? vo.changelogURL : "", 20);
        JCheckBox downloadStatusCheckbox = new JCheckBox();
        JCheckBox isRunningCheckbox = new JCheckBox();
        JCheckBox canEditCheckBox = new JCheckBox();
        
        downloadStatusCheckbox.setSelected(vo != null ? vo.isDownloaded : false);
        downloadStatusCheckbox.setEnabled(false);
        isRunningCheckbox.setSelected(vo != null ? vo.isRunning: false);
        isRunningCheckbox.setEnabled(false);
        
        canEditCheckBox.setSelected(vo != null ? vo.canEdit : false);
        
        urlField.setEditable(vo != null ? canEditCheckBox.isSelected() : false);
        changelogURLField.setEditable(vo != null ? canEditCheckBox.isSelected(): false);
        versionField.setEditable(vo != null ? canEditCheckBox.isSelected() : false);
        
        dialogPanel.add(new JLabel("Allow Editing?"));
        dialogPanel.add(canEditCheckBox);
        dialogPanel.add(new JLabel("Download URL:"));
        dialogPanel.add(urlField);
        dialogPanel.add(new JLabel("Changelog URL:"));
        dialogPanel.add(changelogURLField);
        dialogPanel.add(new JLabel("Version String:"));
        dialogPanel.add(versionField);
        dialogPanel.add(new JLabel("Is Downloaded? (this should be unmodifiable):"));
        dialogPanel.add(downloadStatusCheckbox);
        dialogPanel.add(new JLabel("Is Running? (this should be unmodifiable):"));
        dialogPanel.add(isRunningCheckbox);
        dialogPanel.add(new JLabel("Launches with: " + vo.getLauncherWrapper().getWrapper().getClass().getSimpleName()));
        
        
        canEditCheckBox.addActionListener(listener -> {
        	urlField.setEditable(!urlField.isEditable());
        	changelogURLField.setEditable(!changelogURLField.isEditable());
        	versionField.setEditable(!versionField.isEditable());
        });
        int result = JOptionPane.showConfirmDialog(null, dialogPanel, String.format("Edit Version \"%s\"", vo.version), JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(result == JOptionPane.OK_OPTION && vo != null) {
        	vo.url = urlField.getText();
        	vo.version = versionField.getText();
        	vo.canEdit = canEditCheckBox.isSelected();
        	
        } else if(vo == null) {
        	return new VersionObject();
        } else {
        	return vo;
        }
        return null;
	}
}
