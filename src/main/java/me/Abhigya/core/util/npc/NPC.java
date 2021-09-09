package me.Abhigya.core.util.npc;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public interface NPC {

    NPC setLocation(Location location);

    NPC setLocation(Location location, float yaw, float pitch);

    NPC setYaw(float yaw);

    NPC setPitch(float pitch);

    NPC setPlayerLine(String line, Player player);

    NPC setPlayerLines(List<String> lines, Player player);

    NPC setPlayerLines(List<String> lines, Player player, boolean sendUpdatePackets);

    NPC setPlayerLines(List<String> lines, List<UUID> players, boolean sendUpdatePackets);

    NPC setPlayerLines(List<String> lines, UUID playerUUID);

    NPC removePlayerLines(Player player);

    NPC removePlayerLines(Player player, boolean sendUpdatePackets);

    NPC removePlayerLines(List<UUID> playerUUIDs, boolean sendUpdatePackets);

    NPC removePlayerLines(UUID uuid);

    NPC removePlayerLines(UUID uuid, boolean sendUpdatePackets);

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

    List<UUID> getPlayersLookingAt();

    net.jitse.npclib.api.NPC getLibNPC();
}

