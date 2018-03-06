package de.heinzen.probplugin.animation;

import de.prob2.ui.plugin.*;
import de.prob2.ui.prob2fx.CurrentProject;
import de.prob2.ui.project.Project;
import de.prob2.ui.project.preferences.Preference;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.CheckMenuItem;
import org.pf4j.PluginWrapper;

/**
 * @author Christoph Heinzen
 * @version 0.1.0
 * @since 10.10.17
 */
public class AnimationPlugin extends ProBPlugin {

    private CheckMenuItem menuItem;
    private CurrentProject currentProject;
    private ChangeListener<Project> projectListener;

    public AnimationPlugin(PluginWrapper wrapper, ProBPluginManager manager, ProBPluginHelper helper) {
        super(wrapper, manager, helper);
    }

    @Override
    public String getName() {
        return "Automatic Animation Plugin";
    }

    @Override
    public void startPlugin() {
        createMenu();
        currentProject = getInjector().getInstance(CurrentProject.class);

        projectListener = (observable, oldValue, newValue) -> {
            if (newValue != null && currentProject.getMachines().size() == 1 && menuItem.isSelected()) {
                currentProject.startAnimation(currentProject.getMachines().get(0), Preference.DEFAULT);
            }
        };
        currentProject.addListener(projectListener);
    }

    @Override
    public void stopPlugin() {
        getProBPluginHelper().removeMenuItem(MenuEnum.FILE_MENU, menuItem);
        currentProject.removeListener(projectListener);

    }

    private void createMenu() {
        menuItem = new CheckMenuItem("Automatic Animation");
        getProBPluginHelper().addMenuItem(MenuEnum.FILE_MENU, 3, menuItem);
    }

}
