package de.eldoria.eldoworldcontrol.core.permissions;

import de.eldoria.eldoworldcontrol.core.data.PermissionGroups;
import de.eldoria.eldoworldcontrol.core.reloading.Reloadable;
import de.eldoria.eldoworldcontrol.core.reloading.SharedData;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionType;

import java.util.Arrays;
import java.util.Set;

public class PermissionValidator implements Reloadable {

    private final PermissionVerboseLogger logger;

    private PermissionGroups groups;

    public PermissionValidator(PermissionVerboseLogger logger) {
        this.logger = logger;
    }

    private static final String PREFIX = "ewc.";

    /**
     * Perform a permission check on the player.]
     *
     * @param player          player to check
     * @param permissionNodes multiple permission nodes, which will be joined with a "." seperator
     * @return true if the player has the permission
     */
    private boolean rawPermissionCheck(Player player, String... permissionNodes) {
        return rawPermissionCheck(player, String.join(".", Arrays.asList(permissionNodes)));
    }

    /**
     * Perform a permission check on the player.
     *
     * @param player     player to check
     * @param permission permission to check as a string
     * @return true if the player has the permission
     */
    private boolean rawPermissionCheck(Player player, String permission) {
        boolean hasPermission = player.hasPermission(PREFIX + permission);
        logger.log(player, PREFIX + permission, hasPermission);
        return hasPermission;
    }

    ///////////////////////
    // Permission Check //
    ///////////////////////

    /**
     * Check if a player has the permission to enter a bed.
     *
     * @param player player to check
     * @return true if the player has the permission
     */
    public boolean canEnterBed(Player player) {
        return rawPermissionCheck(player, "enterBed");
    }

    /**
     * Check if a player has the permission to fill a bucket with a specific material.
     *
     * @param player   player to check
     * @param material material to check
     * @return true if the player has the permission
     */
    public boolean canFillBucketWith(Player player, Material material) {
        return groupPermissionCheck(player, material, "bucket", "fill");
    }

    /**
     * Check if a player has the permission to fill a bucket with a specific entity.
     *
     * @param player     player to check
     * @param entityType entity type to check
     * @return true if the player has the permission
     */
    public boolean canFillBucketWith(Player player, EntityType entityType) {
        return groupPermissionCheck(player, entityType, "bucket", "fill");
    }

    /**
     * Check if a player has the permission to empty a bucket with a specific material.
     *
     * @param player   player to check
     * @param material material to check
     * @return true if the player has the permission
     */
    public boolean canEmptyBucketWith(Player player, Material material) {
        return groupPermissionCheck(player, material, "bucket", "empty");
    }

    /**
     * Check if a player has the permission to empty a bucket with a specific entity.
     *
     * @param player     player to check
     * @param entityType entity type to check
     * @return true if the player has the permission
     */
    public boolean canEmptyBucketWith(Player player, EntityType entityType) {
        return groupPermissionCheck(player, entityType, "bucket", "empty");
    }

    /**
     * Check if a player has the permission to craft a material.
     *
     * @param player   player to check
     * @param material material to check
     * @return true if the player has the permission
     */
    public boolean canCraft(Player player, Material material) {
        return groupPermissionCheck(player, material, "craft");
    }

    /**
     * Check if a player has the permission to drop a material.
     *
     * @param player   player to check
     * @param material material to check
     * @return true if the player has the permission
     */
    public boolean canDrop(Player player, Material material) {
        return groupPermissionCheck(player, material, "drop");
    }

    /**
     * Check if a player has the permission to drop a material in creative mode.
     *
     * @param player   player to check
     * @param material material to check
     * @return true if the player has the permission
     */
    public boolean canDropInCreative(Player player, Material material) {
        return groupPermissionCheck(player, material, "creativedrop");
    }

    /**
     * Check if a player has the permission to enchant a material.
     *
     * @param player   player to check
     * @param material material to check
     * @return true if the player has the permission
     */
    public boolean canEnchant(Player player, Material material) {
        return groupPermissionCheck(player, material, "enchant");
    }

    /**
     * Check if a player has the permission to pickup a material.
     *
     * @param player   player to check
     * @param material material to check
     * @return true if the player has the permission
     */
    public boolean canPickup(Player player, Material material) {
        return groupPermissionCheck(player, material, "pickup");
    }

    /**
     * Check if a player has the permission to interact with a material.
     *
     * @param player   player to check
     * @param material material to check
     * @return true if the player has the permission
     */
    public boolean canInteract(Player player, Material material) {
        return groupPermissionCheck(player, material, "interact");
    }

    /**
     * Check if a player has the permission to interact with a entity.
     *
     * @param player     player to check
     * @param entityType entity type to check
     * @return true if the player has the permission
     */
    public boolean canInteract(Player player, EntityType entityType) {
        return groupPermissionCheck(player, entityType, "interact");
    }

    /**
     * Check if a player has the permission to throw a material.
     *
     * @param player   player to check
     * @param material material to check
     * @return true if the player has the permission
     */
    public boolean canThrow(Player player, Material material) {
        return groupPermissionCheck(player, material, "throw");
    }

    /**
     * Check if a player has the permission to throw a splash potion.
     *
     * @param player     player to check
     * @param potionType potion type to check
     * @return true if the player has the permission
     */
    public boolean canThrowSplashPotion(Player player, PotionType potionType) {
        return groupPermissionCheck(player, potionType, "throw", "potion", "splash");
    }

    /**
     * Check if a player has the permission to throw a lingering potion.
     *
     * @param player     player to check
     * @param potionType potion type to check
     * @return true if the player has the permission
     */
    public boolean canThrowLingeringPotion(Player player, PotionType potionType) {
        return groupPermissionCheck(player, potionType, "throw", "potion", "lingering");
    }

    /**
     * Check if a player has the permission to throw a material.
     *
     * @param player   player to check
     * @param material material to check
     * @return true if the player has the permission
     */
    public boolean canUse(Player player, Material material) {
        return groupPermissionCheck(player, material, "use");
    }

    /**
     * Check if a player has the permission to place a material.
     *
     * @param player   player to check
     * @param material material to check
     * @return true if the player has the permission
     */
    public boolean canPlace(Player player, Material material) {
        return groupPermissionCheck(player, material, "place");
    }

    /**
     * Check if a player has the permission to break a material.
     *
     * @param player   player to check
     * @param material material to check
     * @return true if the player has the permission
     */
    public boolean canBreak(Player player, Material material) {
        return groupPermissionCheck(player, material, "break");
    }

    /**
     * Check if a player has the permission to breed a entity.
     *
     * @param player     player to check
     * @param entityType entity type to check
     * @return true if the player has the permission
     */
    public boolean canBreed(Player player, EntityType entityType) {
        return groupPermissionCheck(player, entityType, "breed");
    }

    /**
     * Check if a player has the permission to break and receive a material.
     *
     * @param player   player to check
     * @param material material to check
     * @return true if the player has the permission
     */
    public boolean canBreakAndReceive(Player player, Material material) {
        return groupPermissionCheck(player, material, "breakandreceive");
    }

    /**
     * Check if a player has the permission to deal damage to an entity.
     *
     * @param player     player to check
     * @param entityType entity type to check
     * @return true if the player has the permission
     */
    public boolean canDealDamageTo(Player player, EntityType entityType) {
        return groupPermissionCheck(player, entityType, "dealdamage");
    }

    /**
     * Check if a player has the permission to take damage from an entity.
     *
     * @param player     player to check
     * @param entityType entity type to check
     * @return true if the player has the permission
     */
    public boolean canTakeDamageFrom(Player player, EntityType entityType) {
        return groupPermissionCheck(player, entityType, "takedamage");
    }

    /**
     * Check if a player has the permission to take damage from a material.
     *
     * @param player     player to check
     * @param entityType entity type to check
     * @return true if the player has the permission
     */
    public boolean canTakeDamageFrom(Player player, Material entityType) {
        return groupPermissionCheck(player, entityType, "takedamage");
    }

    /**
     * Check if a player has the permission to take damage by a specific cause.
     *
     * @param player player to check
     * @param cause  damage cause
     * @return true if the player has the permission
     */
    public boolean canReceiveDamageThrough(Player player, DamageCause cause) {
        return groupPermissionCheck(player, cause, "receivedamage");
    }

    /**
     * Check if a player has the permission to ride a entity.
     *
     * @param player     player to check
     * @param entityType entity type to check
     * @return true if the player has the permission
     */
    public boolean canRide(Player player, EntityType entityType) {
        return groupPermissionCheck(player, entityType, "ride");
    }

    /**
     * Check if a player has the permission to tame a entity.
     *
     * @param player     player to check
     * @param entityType entity type to check
     * @return true if the player has the permission
     */
    public boolean canTame(Player player, EntityType entityType) {
        return groupPermissionCheck(player, entityType, "tame");
    }

    /**
     * Check if a player has the permission to be the target of a entity.
     *
     * @param player     player to check
     * @param entityType entity type to check
     * @return true if the player has the permission
     */
    public boolean canBeMobTarget(Player player, EntityType entityType) {
        return groupPermissionCheck(player, entityType, "mobtarget");
    }

    /**
     * Check if a player has the permission to get hungry.
     *
     * @param player player to check
     * @return true if the player has the permission
     */
    public boolean canReceiveHunger(Player player) {
        return rawPermissionCheck(player, "receivehunger");
    }

    /**
     * Check if a player has the permission to receive loot from an entity.
     *
     * @param player     player to check
     * @param entityType entity type to check
     * @return true if the player has the permission
     */
    public boolean canLoot(Player player, EntityType entityType) {
        return groupPermissionCheck(player, entityType, "loot");
    }

    /**
     * Check if a player has the permission to smelt a material.
     *
     * @param player   player to check
     * @param material material to check
     * @return true if the player has the permission
     */
    public boolean canSmelt(Player player, Material material) {
        return groupPermissionCheck(player, material, "smelt");
    }

    /**
     * Check if a player has the permission to consume a material.
     *
     * @param player   player to check
     * @param material material to check
     * @return true if the player has the permission
     */
    public boolean canConsume(Player player, Material material) {
        return groupPermissionCheck(player, material, "consume");
    }

    /**
     * Check if a player has the permission to fish a material.
     *
     * @param player   player to check
     * @param material material to check
     * @return true if the player has the permission
     */
    public boolean canFish(Player player, Material material) {
        return groupPermissionCheck(player, material, "fish");
    }

    /**
     * Check if a player has the permission to shear a entity.
     *
     * @param player     player to check
     * @param entityType entity type to check
     * @return true if the player has the permission
     */
    public boolean canShear(Player player, EntityType entityType) {
        return groupPermissionCheck(player, entityType, "shear");
    }


    private boolean groupPermissionCheck(Player player, Material material, String... perms) {
        return checkGroupPermission(player, material.toString(), this.groups.getGroups(material), perms);
    }


    private boolean groupPermissionCheck(Player player, DamageCause damageCause, String... perms) {
        return checkGroupPermission(player, damageCause.toString(), this.groups.getGroups(damageCause), perms);
    }


    private boolean groupPermissionCheck(Player player, EntityType entityType, String... perms) {
        return checkGroupPermission(player, entityType.toString(), this.groups.getGroups(entityType), perms);
    }


    private boolean groupPermissionCheck(Player player, PotionType potionType, String... perms) {
        return checkGroupPermission(player, potionType.toString(), this.groups.getGroups(potionType), perms);
    }

    /**
     * Check if a enum const has a permission group assigned and check for the permission groups if present.
     *
     * @param player    player object
     * @param enumConst enum const as string
     * @param groups    optional set of groups
     * @param perms     permission nodes for root building
     * @return true if the player has the permission
     */
    private boolean checkGroupPermission(Player player, String enumConst, Set<String> groups, String... perms) {
        String perm = String.join(".", Arrays.asList(perms));

        // If no groups were set just perform a normal permission lookup.
        if (groups.isEmpty()) {
            return rawPermissionCheck(player, perm, enumConst);
        }

        if (rawPermissionCheck(player, perm, enumConst)) {
            return true;
        }

        // Check for each group if the user has a permission for one of them.

        for (var group : groups) {
            if (rawPermissionCheck(player, perm, "group", group)) {
                return true;
            }
        }

        return false;

    }

    @Override
    public void reload(SharedData data) {
        groups = data.getGroups();
    }

}
