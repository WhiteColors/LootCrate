package lootcrate.other;

public enum Option
{
    DISPATCH_COMMAND_ITEM_AMOUNT("dispatch-command-item-time", DataType.BOOLEAN),
    RESOURCE_ID("resource-id", DataType.INTEGER);
    
    String key;
    DataType type;

    Option(String key, DataType type)
    {
	this.key = key;
	this.type = type;
    }

    public String getKey()
    {
	return this.key;
    }
    
    public DataType getType()
    {
	return this.type;
    }
}
