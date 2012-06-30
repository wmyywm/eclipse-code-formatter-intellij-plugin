package krasa.formatter;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import krasa.formatter.settings.ProjectSettingsComponent;
import krasa.formatter.settings.Settings;
import krasa.formatter.utils.ProjectUtils;

import javax.swing.*;

/**
 * @author Vojtech Krasa
 */
public class ChangeFormatterToolbarAction extends AnAction {

    public static final Icon ICON = IconLoader.getIcon("/krasa/formatter/eclipse.gif");
    public static final Icon ICON1 = IconLoader.getIcon("/krasa/formatter/IDEA.gif");

    public void actionPerformed(AnActionEvent e) {
        final Settings state = getSettings(e);
        if (state != null) {
            state.setFormatter(Settings.Formatter.DEFAULT == state.getFormatter() ? Settings.Formatter.ECLIPSE : Settings.Formatter.DEFAULT);
            updateIcon(state, e.getPresentation());
        }
    }

    private Settings getSettings(AnActionEvent e) {
        Project project = e.getProject();
        if (project != null) {
            ProjectSettingsComponent instance = ProjectSettingsComponent.getInstance(project);
            return instance.getSettings();
        }
        return null;
    }

    private void updateIcon(Settings state, Presentation presentation) {
        ProjectUtils.applyToAllOpenedProjects(state);
        if (state.getFormatter() == Settings.Formatter.DEFAULT) {
            presentation.setIcon(ICON1);
        } else {
            presentation.setIcon(ICON);
        }
    }


    @Override
    public void update(AnActionEvent e) {
        super.update(e);
        Presentation presentation = e.getPresentation();
        final Settings state = getSettings(e);
        if (state != null) {
            updateIcon(state, presentation);
        }

    }


}
