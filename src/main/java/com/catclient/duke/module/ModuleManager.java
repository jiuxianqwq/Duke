package com.catclient.duke.module;

import com.catclient.duke.Duke;
import com.catclient.duke.event.api.annotations.EventTarget;
import com.catclient.duke.event.impl.KeyboardEvent;
import com.catclient.duke.module.impl.movement.Sprint;
import com.catclient.duke.utils.wrapper.Wrapper;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author：jiuxian_baka
 * @Date：2025/11/30 04:22
 * @Filename：ModuleManager
 */
public class ModuleManager implements Wrapper {
    private List<Module> modules = new ArrayList<>();

    public ModuleManager() {
        init();
    }

    public void init() {
        Duke.getInstance().getEventManager().register(this);
        addModule(
            new Sprint()
        );
    }

    private void addModule(Module... module) {
        modules.addAll(Arrays.asList(module));
    }

    public List<Module> getModules(Category category) {
        List<Module> modules = new ArrayList<>();
        for (Module module : this.modules) {
            if (module.getCategory() == category) {
                modules.add(module);
            }
        }
        return modules;
    }

    public Module getModule(String name) {
        for (Module module : this.modules) {
            if (module.getName().equals(name)) {
                return module;
            }
        }
        return null;
    }

    public <T extends Module> T getModule(Class<T> clazz) {
        for (Module module : modules) {
            if (module.getClass().equals(clazz)) {
                return clazz.cast(module);
            }
        }
        return null;
    }

    @EventTarget
    public void onKey(KeyboardEvent event) {
        if (event.getAction() == GLFW.GLFW_PRESS) {
            for (Module module : modules) {
                if (module.getKey() == event.getKey()) {
                    module.toggle();
                }
            }
        }
    }

}
