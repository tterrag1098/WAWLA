package net.darkhax.wawla.handler;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.darkhax.wawla.modules.Module;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.event.FMLInterModComms;

public class WailaBlockHandler implements IWailaDataProvider {

    public WailaBlockHandler() {

        FMLInterModComms.sendMessage("Waila", "register", "net.darkhax.wawla.handler.WailaBlockHandler.callbackRegister");
    }

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {

        return (accessor.getStack() != null) ? accessor.getStack() : null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {

        for (Module module : Module.getModules())
            module.onWailaBlockName(itemStack, currenttip, accessor);

        return currenttip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {

        for (Module module : Module.getModules())
            module.onWailaBlockDescription(itemStack, currenttip, accessor);

        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {

        for (Module module : Module.getModules())
            module.onWailaBlockTail(itemStack, currenttip, accessor);

        return currenttip;
    }

    public static void callbackRegister(IWailaRegistrar register) {

        WailaBlockHandler instance = new WailaBlockHandler();
        register.registerBodyProvider(instance, Block.class);
    }
}