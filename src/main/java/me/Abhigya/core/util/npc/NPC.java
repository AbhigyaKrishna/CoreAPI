package me.Abhigya.core.util.npc;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public interface NPC {

    NPC show(Player player);

    NPC show(Player... player);

    NPC setLocation(Location location);

    NPC setLocation(Location location, float yaw, float pitch);

    NPC setYaw(float yaw);

    NPC setPitch(float pitch);

    NPC setPlayerLine(String line, Player player);

    NPC setPlayerLines(List<String> lines, Player player);

    NPC removePlayerLines(Player player);

    NPC crouch();

    NPC invis();

    NPC setOnFire();

    NPC lookAtLocation(Location location);

    NPC startLookingAtPlayer(UUID playerUUID);

    NPC startLookingAtPlayer(Player player);

    NPC startLookingAtPlayer(List<UUID> playerUUIDs);

    NPC stopLookingAtPlayer(UUID playerUUID);

    NPC stopLookingAtPlayer(Player player);

    NPC stopLookingAtPlayer(List<UUID> playerUUIDs);

    NPC setLookingDistance(float lookingDistance);

    double getLookingDistance();

    NPC setSkin(int skinIdFromMineSkin);

    NPC setItemInHand(ItemStack itemInHand);

    NPC setItemInHand(ItemStack itemInHand, boolean mainHand);

    NPC setHelemt(ItemStack helemt);

    NPC setChestPlate(ItemStack chestPlate);

    NPC setLeggings(ItemStack leggings);

    NPC setBoots(ItemStack boots);

    NPC addClickAction(NPCClickAction action);

    List<UUID> getPlayersLookingAt();

    List<UUID> getShown();

    me.Abhigya.core.util.npc.npclib.api.NPC getLibNPC();
}

