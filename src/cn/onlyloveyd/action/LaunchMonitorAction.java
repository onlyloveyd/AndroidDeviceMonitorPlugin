package cn.onlyloveyd.action;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import org.apache.http.util.TextUtils;

import java.io.IOException;

import static cn.onlyloveyd.ui.SettingsConfigurable.KEY_DEFAULT_DIR;

public class LaunchMonitorAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        String path = PropertiesComponent.getInstance().getValue(KEY_DEFAULT_DIR);
        if (TextUtils.isEmpty(path)) {
            Messages.showMessageDialog(
                    "请在“Settings-> Other Settings”中设置Android Device Monitor启动脚本位置",
                    "Warning",
                    Messages.getInformationIcon()
            );
        } else {
            runBat();
        }
    }

    public void runBat() {
        String lunchPath = PropertiesComponent.getInstance().getValue(KEY_DEFAULT_DIR);
        String cmd = "cmd /c start /b " + lunchPath;
        try {
            Process ps = Runtime.getRuntime().exec(cmd);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
