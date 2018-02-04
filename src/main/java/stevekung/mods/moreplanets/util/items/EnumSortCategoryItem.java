package stevekung.mods.moreplanets.util.items;

public enum EnumSortCategoryItem
{
    GENERAL,
    ARROW,
    PROJECTILE,
    INGOT,
    UPGRADE,
    BUCKET_FLUID,
    BUCKET_GAS,
    FOOD,
    PLANT_SEEDS,
    PLACEABLE_PLANT,
    PLATE,
    HEAVY_PLATE,
    ROCKET,
    SCHEMATIC,
    DUNGEON_KEY,
    DOOR,
    RECORD,
    SWORD,
    PICKAXE,
    AXE,
    SHOVEL,
    HOE,
    FISHING_ROD,
    BOW,
    OTHER_TOOL,
    HELMET,
    CHESTPLATE,
    LEGGINGS,
    BOOTS,
    HELMET_BREATHABLE;

    private static EnumSortCategoryItem[] values = EnumSortCategoryItem.values();

    public static EnumSortCategoryItem[] valuesCached()
    {
        return EnumSortCategoryItem.values;
    }
}