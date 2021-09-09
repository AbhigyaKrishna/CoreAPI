package me.Abhigya.core.util.npc;

import me.Abhigya.core.util.tasks.WorkloadThread;
import net.jitse.npclib.api.skin.MineSkinFetcher;
import net.jitse.npclib.api.skin.Skin;
import net.jitse.npclib.api.state.NPCSlot;
import net.jitse.npclib.api.state.NPCState;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class PacketNPC implements NPC {

    net.jitse.npclib.api.NPC npc;
    NPCManager manager;
    private List<UUID> playersLookingAt;
    private WorkloadThread thread;
    private double lookingDistance;

    public PacketNPC(NPCManager manager, List<String> linesAboveNPC, List<UUID> uuidOfPlayersToShowNPC, Location location, WorkloadThread thread) {
        this.manager = manager;
        this.thread = thread;
        playersLookingAt = new ArrayList<>();
        if (linesAboveNPC == null) {
            npc = manager.getNPCLib().createNPC();
            return;
        }
        if (linesAboveNPC.size() == 0) {
            npc = manager.getNPCLib().createNPC();
            return;
        }
        npc = manager.getNPCLib().createNPC(linesAboveNPC);
        npc.setLocation(location);
        npc.create();
        uuidOfPlayersToShowNPC.forEach(uuid -> {
            npc.show(Bukkit.getPlayer(uuid));
        });
    }

    public PacketNPC setLocation(Location location) {
        npc.setLocation(location);
        return this;
    }

    public PacketNPC setLocation(Location location, float yaw, float pitch) {
        npc.setLocation(new Location(location.getWorld(), location.getX(), location.getY(), location.getZ(), yaw, pitch));
        return this;
    }

    public PacketNPC setYaw(float yaw) {
        Location location = npc.getLocation().clone();
        npc.setLocation(new Location(location.getWorld(), location.getX(), location.getY(), location.getZ(), yaw, location.getPitch()));
        return this;
    }

    public PacketNPC setPitch(float pitch) {
        Location location = npc.getLocation().clone();
        npc.setLocation(new Location(location.getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(), pitch));
        return this;
    }

    public PacketNPC setPlayerLine(String line, Player player) {
        npc.setPlayerLines(Arrays.asList(line), player, true);
        return this;
    }

    public PacketNPC setPlayerLines(List<String> lines, Player player) {
        npc.setPlayerLines(lines, player, true);
        return this;
    }

    public PacketNPC setPlayerLines(List<String> lines, Player player, boolean sendUpdatePackets) {
        npc.setPlayerLines(lines, player, true);
        return this;
    }

    public PacketNPC setPlayerLines(List<String> lines, List<UUID> players, boolean sendUpdatePackets) {
        players.forEach(player -> {
            setPlayerLines(lines, Bukkit.getPlayer(player), sendUpdatePackets);
        });
        return this;
    }

    public PacketNPC setPlayerLines(List<String> lines, UUID playerUUID) {
        setPlayerLines(lines, Bukkit.getPlayer(playerUUID), true);
        return this;
    }

    public PacketNPC removePlayerLines(Player player) {
        npc.removePlayerLines(player, true);
        return this;
    }

    public PacketNPC removePlayerLines(Player player, boolean sendUpdatePackets) {
        npc.removePlayerLines(player, true);
        return this;
    }

    public PacketNPC removePlayerLines(List<UUID> playerUUIDs, boolean sendUpdatePackets) {
        playerUUIDs.forEach(uuid -> {
            npc.removePlayerLines(Bukkit.getPlayer(uuid), sendUpdatePackets);
        });
        return this;
    }

    public PacketNPC removePlayerLines(UUID uuid) {
        npc.removePlayerLines(Bukkit.getPlayer(uuid), true);
        return this;
    }

    public PacketNPC removePlayerLines(UUID uuid, boolean sendUpdatePackets) {
        npc.removePlayerLines(Bukkit.getPlayer(uuid), sendUpdatePackets);
        return this;
    }

    public PacketNPC crouch() {
        npc.toggleState(NPCState.CROUCHED);
        return this;
    }

    public PacketNPC invis() {
        npc.toggleState(NPCState.INVISIBLE);
        return this;
    }

    public PacketNPC setOnFire() {
        npc.toggleState(NPCState.ON_FIRE);
        return this;
    }

    public PacketNPC lookAtLocation(Location location) {
        npc.lookAt(location);
        return this;
    }

    public PacketNPC startLookingAtPlayer(UUID playerUUID) {
        checkThread();
        playersLookingAt.add(playerUUID);
        return this;
    }

    public PacketNPC startLookingAtPlayer(Player player) {
        checkThread();
        playersLookingAt.add(player.getUniqueId());
        return this;
    }

    public PacketNPC startLookingAtPlayer(List<UUID> playerUUIDs) {
        checkThread();
        playerUUIDs.forEach(uuid -> {
            playersLookingAt.add(uuid);
        });
        return this;
    }

    public PacketNPC stopLookingAtPlayer(UUID playerUUID) {
        playersLookingAt.remove(playerUUID);
        return this;
    }

    public PacketNPC stopLookingAtPlayer(Player player) {
        playersLookingAt.remove(player.getUniqueId());
        return this;
    }

    public PacketNPC stopLookingAtPlayer(List<UUID> playerUUIDs) {
        playerUUIDs.forEach(uuid -> {
            playersLookingAt.remove(uuid);
        });
        return this;
    }

    public PacketNPC setLookingDistance(float lookingDistance) {
        this.lookingDistance = lookingDistance;
        return this;
    }

    public double getLookingDistance() {
        double distance = this.lookingDistance;
        return distance;
    }

    private List<UUID> getAllPlayerUUIDs() {
        List<UUID> uuids = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach(player -> {
            uuids.add(player.getUniqueId());
        });
        return uuids;
    }

    public PacketNPC setSkin(int skinIdFromMineSkin) {
        npc.updateSkin(getSkin(skinIdFromMineSkin));
        return this;
    }

    public PacketNPC setItemInHand(ItemStack itemInHand) {
        setItemInHand(itemInHand, true);
        return this;
    }

    public PacketNPC setItemInHand(ItemStack itemInHand, boolean mainHand) {
        if (mainHand) {
            npc.setItem(NPCSlot.MAINHAND, itemInHand);
        }
        return this;
    }

    public PacketNPC setHelemt(ItemStack helemt) {
        npc.setItem(NPCSlot.HELMET, helemt);
        return this;
    }

    public PacketNPC setChestPlate(ItemStack chestPlate) {
        npc.setItem(NPCSlot.CHESTPLATE, chestPlate);
        return this;
    }

    public PacketNPC setLeggings(ItemStack leggings) {
        npc.setItem(NPCSlot.LEGGINGS, leggings);
        return this;
    }

    public PacketNPC setBoots(ItemStack boots) {
        npc.setItem(NPCSlot.BOOTS, boots);
        return this;
    }

    private Skin getSkin(int skinID) {
        AtomicReference<Skin> skin = null;
        MineSkinFetcher.fetchSkinFromIdAsync(skinID, skinData -> {
            skin.set(skinData);
        });
        return skin.get();
    }

    public List<UUID> getPlayersLookingAt() {
        return Collections.unmodifiableList(playersLookingAt);
    }

    private void checkThread() {
        if (thread == null) {
            throw new IllegalStateException("Can't look at player without workload thread");
        }
    }

    public net.jitse.npclib.api.NPC getLibNPC() {
        return npc;
    }

}

