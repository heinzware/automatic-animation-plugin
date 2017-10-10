package de.heinzen.probplugin.animation;

import de.prob2.ui.plugin.MenuEnum;
import de.prob2.ui.plugin.ProBPlugin;
import de.prob2.ui.prob2fx.CurrentProject;
import de.prob2.ui.project.Project;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.CheckMenuItem;
import ro.fortsoft.pf4j.PluginWrapper;

/**
 * Description of class
 *
 * @author Christoph Heinzen
 * @version 0.1.0
 * @since 10.10.17
 */
public class AnimationPlugin extends ProBPlugin {

    private CheckMenuItem menuItem;
    private CurrentProject currentProject;
    private ChangeListener<Project> projectListener;

    public AnimationPlugin(PluginWrapper pluginWrapper) {
        super(pluginWrapper);
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
            if (newValue != null && currentProject.getRunconfigurations().size() == 1 && enabled()) {
                currentProject.startAnimation(currentProject.getRunconfigurations().get(0));
            }
        };
        currentProject.addListener(projectListener);
    }

    @Override
    public void stopPlugin() {
        getProBConnection().removeMenuItem(MenuEnum.FILE_MENU, menuItem);
        currentProject.removeListener(projectListener);
    }

    private void createMenu() {
        menuItem = new CheckMenuItem("Automatic Animation");
        getProBConnection().addMenuItem(MenuEnum.FILE_MENU, 3, menuItem);
    }

    private boolean enabled() {
        return menuItem.isSelected();
    }
}
