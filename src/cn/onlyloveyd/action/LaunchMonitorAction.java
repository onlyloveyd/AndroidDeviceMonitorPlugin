package cn.onlyloveyd.action;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

import org.apache.http.util.TextUtils;

import java.io.IOException;

import static cn.onlyloveyd.ui.SettingsConfigurable.KEY_DEFAULT_DIR;

public class LaunchMonitorAction extends AnAction {
    private static boolean isWindows = true;

    static {
        String os = System.getProperty("os.name");
        if (os != null) {
            os = os.toLowerCase();
        }
        isWindows = os.contains("windows");
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        String path = PropertiesComponent.getInstance().getValue(KEY_DEFAULT_DIR);
        if (TextUtils.isEmpty(path)) {
            Messages.showMessageDialog(
                    "请在“Other Settings”中设置 Android SDK Location",
                    "Warning",
                    Messages.getInformationIcon()
            );
        } else {
            runBat();
        }
    }

    public void runBat() {
        String cmd;

        if (isWindows) {
            String lunchPath = PropertiesComponent.getInstance().getValue(KEY_DEFAULT_DIR) + "/tools/monitor.bat";
            cmd = "cmd /c start /b " + lunchPath;
        } else {
            String lunchPath = PropertiesComponent.getInstance().getValue(KEY_DEFAULT_DIR) + "/tools/monitor";
            cmd = "/bin/bash  " + lunchPath;
        }
        try {
            Process ps = Runtime.getRuntime().exec(cmd);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            Messages.showMessageDialog(
                    ioe.getMessage(),
                    "启动失败",
                    Messages.getInformationIcon()
            );
        }
    }
}
