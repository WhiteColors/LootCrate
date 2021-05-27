package lootcrate.commands.subs;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.command.CommandSender;

import com.google.common.collect.ImmutableMap;

import lootcrate.LootCrate;
import lootcrate.commands.SubCommand;
import lootcrate.enums.Message;
import lootcrate.enums.Permission;
import lootcrate.enums.Placeholder;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;
import lootcrate.utils.CommandUtils;
import lootcrate.utils.TabUtils;

public class SubCommandLootCrateCommand extends SubCommand
{
    private String[] args;
    private CommandSender sender;
    private LootCrate plugin;

    public SubCommandLootCrateCommand(LootCrate plugin, CommandSender sender, String[] args)
    {
	super(plugin, sender, args, Permission.COMMAND_LOOTCRATE_COMMAND, Permission.COMMAND_LOOTCRATE_ADMIN);
	this.plugin = plugin;
	this.sender = sender;
	this.args = args;
    }

    @Override
    public void runSubCommand(boolean playerRequired)
    {
	if (this.testPlayer(playerRequired))
	    return;
	if (!this.testPermissions())
	    return;

	if (args.length <= 3)
	{
	    plugin.getMessageManager().sendMessage(sender, Message.LOOTCRATE_COMMAND_COMMAND_USAGE, null);
	    return;
	}

	if (CommandUtils.tryParse(args[1]) == null || CommandUtils.tryParse(args[2]) == null)
	{
	    plugin.getMessageManager().sendMessage(sender, Message.LOOTCRATE_COMMAND_COMMAND_USAGE, null);
	    return;
	}

	Crate crate = plugin.getCacheManager().getCrateById(CommandUtils.tryParse(args[1]));
	if (crate == null)
	{
	    plugin.getMessageManager().sendMessage(sender, Message.LOOTCRATE_NOT_FOUND,
		    ImmutableMap.of(Placeholder.CRATE_ID, "" + CommandUtils.tryParse(args[1])));
	    return;
	}
	CrateItem item = plugin.getCrateManager().getCrateItemById(crate, Integer.parseInt(args[2]));
	if (item == null)
	{
	    plugin.getMessageManager().sendMessage(sender, Message.LOOTCRATE_ITEM_NOT_FOUND,
		    ImmutableMap.of(Placeholder.ITEM_ID, "" + CommandUtils.tryParse(args[2])));
	    return;
	}
	String command = CommandUtils.builder(args, 3);
	item.getCommands().add(command);
	crate.replaceItem(item);
	plugin.getCacheManager().update(crate);

	plugin.getCacheManager().update(crate);
	plugin.getMessageManager().sendMessage(sender, Message.LOOTCRATE_COMMAND_COMMAND_SUCCESS,
		ImmutableMap.of(Placeholder.CRATE_ID, "" + crate.getId(), Placeholder.CRATE_NAME, crate.getName(),
			Placeholder.ITEM_ID, "" + item.getId(), Placeholder.ITEM_TYPE, "" + item.getItem().getType(),
			Placeholder.ITEM_NAME, item.getItem().getItemMeta().getDisplayName()));

    }

    @Override
    public List<String> runTabComplete()
    {
	List<String> list = new LinkedList<String>();

	if (!sender.hasPermission(Permission.COMMAND_LOOTCRATE_COMMAND.getKey())
		&& !sender.hasPermission(Permission.COMMAND_LOOTCRATE_ADMIN.getKey()))
	    return list;

	if (args.length == 2)
	{
	    list.add("[CrateID]");
	    TabUtils.addCratesToList(list, plugin.getCacheManager());
	}
	if (args.length == 3)
	{
	    if (CommandUtils.tryParse(args[1]) == null)
	    {
		list.add("Invalid Crate");
		return list;
	    }
	    list.add("[ItemID]");
	    TabUtils.addCrateItemsToListFromID(list, plugin.getCacheManager(), Integer.parseInt(args[1]));
	}
	if (args.length == 4)
	    list.add("[Command] - Use {player} as placeholder");
	return list;
    }

}
