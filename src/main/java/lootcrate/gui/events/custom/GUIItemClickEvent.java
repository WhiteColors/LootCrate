package lootcrate.gui.events.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;

import lootcrate.gui.frames.types.Frame;
import lootcrate.gui.items.GUIItem;

public class GUIItemClickEvent extends Event implements Cancellable
{

    private boolean cancelled;
    private Frame frame;
    private InventoryClickEvent clickEvent;

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    public GUIItemClickEvent(InventoryClickEvent clickEvent, Frame frame)
    {
	this.clickEvent = clickEvent;
	this.frame = frame;
    }

    @Override
    public boolean isCancelled()
    {
	return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled)
    {
	this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers()
    {
	return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList()
    {
	return HANDLERS_LIST;
    }

    public Player getPlayer()
    {
	if (clickEvent.getWhoClicked() instanceof Player)
	    return (Player) clickEvent.getWhoClicked();
	return null;
    }

    public GUIItem getItem()
    {
	return frame.getContents()[clickEvent.getSlot()];
    }

    public Frame getFrame()
    {
	return this.frame;
    }

    public InventoryClickEvent getClickEvent()
    {
	return clickEvent;
    }

    public boolean sameFrame(Frame frame)
    {
	return this.frame == frame;
    }

}
