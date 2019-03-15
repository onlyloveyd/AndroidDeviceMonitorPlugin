package cn.onlyloveyd.ui;


import com.intellij.icons.AllIcons;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.vfs.VirtualFile;
import org.apache.http.util.TextUtils;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class SettingsConfigurable implements Configurable {

    public static final String KEY_DEFAULT_DIR = "android_device_monitor_default_dir";
    public JTextField locationFiled = new JTextField();
    public JButton select = new JButton(AllIcons.Nodes.PpFile);
    private JPanel settingsPanel = new JPanel();
    private JLabel titleLabel = new JLabel(
            "monitor.bar location");
    private boolean modified = false;
    private String locationPath = "";

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Android Device Monitor Location";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        String path = PropertiesComponent.getInstance().getValue(KEY_DEFAULT_DIR);
        if (!TextUtils.isEmpty(path)) {
            locationFiled.setText(path);
        }
        select.addActionListener(e -> {
            FileChooserDescriptor descriptor = FileChooserDescriptorFactory.createSingleFileDescriptor();
            VirtualFile virtualFile = FileChooser.chooseFile(descriptor, ProjectUtil.guessCurrentProject(select), null);
            if (virtualFile != null) {
                String tmpPath = virtualFile.getCanonicalPath();
                if (!tmpPath.equals(locationPath)) {
                    locationFiled.setText(tmpPath);
                    setModified(true);
                }
            }
        });
        JPanel root = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
        gbl.columnWeights = new double[]{1.0, 5.0, 1.0};
        gbl.rowWeights = new double[]{1.0, 10.0, 1.0};
        root.setLayout(gbl);

        root.add(titleLabel);
        root.add(locationFiled);
        root.add(select);

        GridBagConstraints gbs = new GridBagConstraints();
        gbs.fill = GridBagConstraints.HORIZONTAL;
        gbs.gridwidth = 1;
        gbs.gridheight = 1;
        gbs.gridx = 0;
        gbs.gridy = 0;
        gbl.setConstraints(titleLabel, gbs);

        gbs = new GridBagConstraints();
        gbs.fill = GridBagConstraints.HORIZONTAL;
        gbs.gridwidth = 1;
        gbs.gridheight = 1;
        gbs.gridx = 1;
        gbs.gridy = 0;
        gbl.setConstraints(locationFiled, gbs);

        gbs = new GridBagConstraints();
        gbs.fill = GridBagConstraints.HORIZONTAL;
        gbs.gridwidth = 1;
        gbs.gridheight = 1;
        gbs.gridx = 2;
        gbs.gridy = 0;
        gbl.setConstraints(select, gbs);

        settingsPanel.setLayout(new BorderLayout());

        // title
        settingsPanel.add(root, BorderLayout.PAGE_START);

        return settingsPanel;
    }

    @Override
    public boolean isModified() {
        return modified;
    }


    public void setModified(boolean modified) {
        this.modified = modified;
    }


    @Override
    public void apply() {
        PropertiesComponent.getInstance().setValue(KEY_DEFAULT_DIR, locationFiled.getText());
    }
}
