package net.darkhax.wawla.handler;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.api.IWailaEntityProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.darkhax.wawla.modules.Module;
import net.minecraft.entity.Entity;
import cpw.mods.fml.common.event.FMLInterModComms;

public class WailaEntityHandler implements IWailaEntityProvider {

    public WailaEntityHandler() {

        FMLInterModComms.sendMessage("Waila", "register", "net.darkhax.wawla.handler.WailaEntityHandler.onWailaRegistrar");
    }

    @Override
    public Entity getWailaOverride(IWailaEntityAccessor accessor, IWailaConfigHandler config) {

        Entity entity = accessor.getEntity();
        for (Module module : Module.getModules())
            module.onEntityOverride(entity, accessor, config);

        return (entity != null) ? entity : null;
    }

    @Override
    public List<String> getWailaHead(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {

        for (Module module : Module.getModules())
            module.onWailaEntityName(entity, currenttip, accessor, config);

        return currenttip;
    }

    @Override
    public List<String> getWailaBody(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {

        for (Module module : Module.getModules())
            module.onWailaEntityDescription(entity, currenttip, accessor, config);

        return currenttip;
    }

    @Override
    public List<String> getWailaTail(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {

        for (Module module : Module.getModules())
            module.onWailaEntityTail(entity, currenttip, accessor, config);

        return currenttip;
    }

    public static void onWailaRegistrar(IWailaRegistrar register) {

        WailaEntityHandler instance = new WailaEntityHandler();
        register.registerHeadProvider(instance, Entity.class);
        register.registerBodyProvider(instance, Entity.class);
        register.registerTailProvider(instance, Entity.class);
        register.registerOverrideEntityProvider(instance, Entity.class);

        for (Module module : Module.getModules())
            module.onWailaRegistrar(register);
    }
}