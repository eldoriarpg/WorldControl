package de.eldoria.eldoworldcontrol.permissionvalidation;

public class Permission {
    private static final String PREFIX = "ewc.";

    public static final String BUILD = PREFIX + "build";
    public static final String BLOCK_BREAK = BUILD + ".break.";
    public static final String BLOCK_BREAK_AND_RECEIVE = BUILD + ".breakandrecieve.";
    public static final String PLACE = BUILD + ".place.";

    public static final String CREATIVE_MODE = "creative";

    public static final String ACTION = PREFIX + "action";
    public static final String USE = ACTION + ".use.";
    public static final String THROW = ACTION + ".throw.";
    public static final String CRAFT = ACTION + ".craft."; //supports creative
    public static final String DROP = ACTION + ".drop."; //supports creative
    public static final String ENCHANT = ACTION + ".enchant."; //supports creative
    public static final String INTERACT = ACTION + ".interact.";
    public static final String PICKUP = ACTION + ".pickup.";
    public static final String USE_BED = ACTION + ".usebed";
    public static final String BUCKET = ACTION + ".bucket";
    public static final String BUCKET_FILL = BUCKET + ".fill.";
    public static final String BUCKET_EMPTY = BUCKET + ".empty.";

    public static final String CONTAINER = PREFIX + "container"; //supports creative
    public static final String WITHDRAW_ITEM = ".withdrawitem.";
    public static final String DEPOSIT_ITEM = ".deposititem.";
    public static final String CHEST = CONTAINER + ".chest"; //supports creative
    public static final String DISPENSER = CONTAINER + ".dispenser"; //supports creative
    public static final String DROPPER = CONTAINER + ".dropper"; //supports creative
    public static final String FURNACE = CONTAINER + ".furnace"; //supports creative
    public static final String WORKBENCH = CONTAINER + ".workbench"; //supports creative
    public static final String CRAFTING = CONTAINER + ".crafting"; //supports creative
    public static final String ENCHANTING = CONTAINER + ".enchanting"; //supports creative
    public static final String BREWING = CONTAINER + ".brewing"; //supports creative
    public static final String PLAYER = CONTAINER + ".player"; //supports creative
    public static final String CREATIVE = CONTAINER + ".creative"; //supports creative
    public static final String MERCHANT = CONTAINER + ".merchant"; //supports creative
    public static final String ENDER_CHEST = CONTAINER + ".enderchest"; //supports creative
    public static final String ANVIL = CONTAINER + ".anvil"; //supports creative
    public static final String BEACON = CONTAINER + ".beacon"; //supports creative
    public static final String HOPPER = CONTAINER + ".hopper"; //supports creative
    public static final String SHULKER_BOX = CONTAINER + ".shulkerbox"; //supports creative

    public static final String PLAYER_BEHAVIOUR = PREFIX + "playerbehaviour";
    public static final String RECEIVE_HUNGER = PLAYER_BEHAVIOUR + ".receivehunger";
    public static final String LOGIN = PLAYER_BEHAVIOUR + ".login";

    public static final String DAMAGE = PREFIX + "damage";
    public static final String RECEIVE_DAMAGE = DAMAGE + ".receivedamage";
    public static final String DAMAGE_TAKE = DAMAGE + ".take.";
    public static final String DAMAGE_DEAL = DAMAGE + ".deal.";

    public static final String ENTITY = PREFIX + "entity";
    public static final String MOB_TARGET = ENTITY + ".mobTarget.";
    public static final String TAME = ENTITY + ".tame.";
    public static final String RIDE = ENTITY + ".ride.";

    public static final String MOVEMENT = PREFIX + "movement";
    public static final String SNEAK = MOVEMENT + ".sneak";
    public static final String SPRINT = MOVEMENT + ".sprint";
    public static final String SWIM = MOVEMENT + ".swim";
    public static final String GLIDE = MOVEMENT + ".glide";

}
