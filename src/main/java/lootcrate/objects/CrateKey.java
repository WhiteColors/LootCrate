package lootcrate.objects;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import lootcrate.utils.ObjUtils;

public class CrateKey implements ConfigurationSerializable
{
    private ItemStack item;
    private boolean glowing;

    public CrateKey(ItemStack item, boolean glowing)
    {
	this.setItem(item);
	this.setGlowing(glowing);
    }
    
    public CrateKey(Map<String, Object> data)
    {
	if(data == null) return;
	
	this.item = ItemStack.deserialize((Map<String, Object>) data.get("Item"));
	this.glowing = (boolean) data.get("Glowing");
    }
    
    public CrateKey(MemorySection section)
    {
    	Map<String, Object> map = ObjUtils.MemoryToMap(section);
   	
    	if(map.get("Item") == null) return;
    	
	this.item = ItemStack.deserialize(ObjUtils.MemoryToMap((MemorySection) map.get("Item")));
	this.glowing = (boolean) map.get("Glowing");
    }

    public ItemStack getItem()
    {
	if (isGlowing())
	{
	    item.addUnsafeEnchantment(Enchantment.OXYGEN, 1);
	    ItemMeta meta = item.getItemMeta();
	    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
	    item.setItemMeta(meta);
	}
	return item;
    }

    public void setItem(ItemStack item)
    {
	this.item = item;
    }

    public boolean isGlowing()
    {
	return glowing;
    }

    public void setGlowing(boolean glowing)
    {
	this.glowing = glowing;
    }

    public Map<String, Object> serialize()
    {
	Map<String, Object> map = new LinkedHashMap<String, Object>();

	if(item == null) return map;
	
	map.put("Item", getItem().serialize());
	map.put("Glowing", isGlowing());

	return map;
    }
}
