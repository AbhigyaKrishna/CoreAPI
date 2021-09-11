package me.Abhigya.core.util.npc;

import me.Abhigya.core.util.tasks.WorkloadThread;
import me.Abhigya.core.util.npc.npclib.api.skin.MineSkinFetcher;
import me.Abhigya.core.util.npc.npclib.api.skin.Skin;
import me.Abhigya.core.util.npc.npclib.api.state.NPCSlot;
import me.Abhigya.core.util.npc.npclib.api.state.NPCState;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class PacketNPC implements NPC {

    private final me.Abhigya.core.util.npc.npclib.api.NPC npc;
    private final NPCManager manager;
    private final List<UUID> shown;
    private List<UUID> playersLookingAt;
    private List<NPCClickAction> actions;
    private WorkloadThread thread;
    private double lookingDistance;

    public PacketNPC(NPCManager manager, List<String> linesAboveNPC, Location location, WorkloadThread thread) {
        this.manager = manager;
        this.thread = thread;
        this.shown = new ArrayList<>();
        this.playersLookingAt = new ArrayList<>();
        this.actions = new ArrayList<>();
        if (linesAboveNPC == null) {
            this.npc = manager.getNPCLib().createNPC();
            return;
        }
        if (linesAboveNPC.size() == 0) {
            this.npc = manager.getNPCLib().createNPC();
            return;
        }
        this.npc = manager.getNPCLib().createNPC(linesAboveNPC);
        this.npc.setLocation(location);
        this.npc.create();
    }

    public PacketNPC show(Player player) {
        this.npc.show(player);
        this.shown.add(player.getUniqueId());
        return this;
    }

    public PacketNPC show(Player... players) {
        for (Player player : players) {
            this.npc.show(player);
            this.shown.add(player.getUniqueId());
        }
        return this;
    }

    public PacketNPC show() {
        if (this.npc.getLocation().getWorld() != null) {
            for (Player player : this.npc.getLocation().getWorld().getPlayers()) {
                this.npc.show(player);
                this.shown.add(player.getUniqueId());
            }
        }

        return this;
    }

    public PacketNPC setLocation(Location location) {
        this.npc.setLocation(location);
        return this;
    }

    public PacketNPC setLocation(Location location, float yaw, float pitch) {
        this.npc.setLocation(new Location(location.getWorld(), location.getX(), location.getY(), location.getZ(), yaw, pitch));
        return this;
    }

    public PacketNPC setYaw(float yaw) {
        Location location = this.npc.getLocation().clone();
        this.npc.setLocation(new Location(location.getWorld(), location.getX(), location.getY(), location.getZ(), yaw, location.getPitch()));
        return this;
    }

    public PacketNPC setPitch(float pitch) {
        Location location = this.npc.getLocation().clone();
        this.npc.setLocation(new Location(location.getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(), pitch));
        return this;
    }

    public PacketNPC setPlayerLine(String line, Player player) {
        this.npc.setText(player, Collections.singletonList(line));
        return this;
    }

    public PacketNPC setPlayerLines(List<String> lines, Player player) {
        this.npc.setText(player, lines);
        return this;
    }

    public PacketNPC removePlayerLines(Player player) {
        this.npc.removeText(player);
        return this;
    }

    public PacketNPC crouch() {
        this.npc.toggleState(NPCState.CROUCHED);
        return this;
    }

    public PacketNPC invis() {
        this.npc.toggleState(NPCState.INVISIBLE);
        return this;
    }

    public PacketNPC setOnFire() {
        this.npc.toggleState(NPCState.ON_FIRE);
        return this;
    }

    public PacketNPC lookAtLocation(Location location) {
        this.npc.lookAt(location);
        return this;
    }

    public PacketNPC startLookingAtPlayer(UUID playerUUID) {
        this.checkThread();
        this.playersLookingAt.add(playerUUID);
        return this;
    }

    public PacketNPC startLookingAtPlayer(Player player) {
        this.checkThread();
        this.playersLookingAt.add(player.getUniqueId());
        return this;
    }

    public PacketNPC startLookingAtPlayer(List<UUID> playerUUIDs) {
        this.checkThread();
        this.playersLookingAt.addAll(playerUUIDs);
        return this;
    }

    public PacketNPC stopLookingAtPlayer(UUID playerUUID) {
        this.playersLookingAt.remove(playerUUID);
        return this;
    }

    public PacketNPC stopLookingAtPlayer(Player player) {
        this.playersLookingAt.remove(player.getUniqueId());
        return this;
    }

    public PacketNPC stopLookingAtPlayer(List<UUID> playerUUIDs) {
        playerUUIDs.forEach(uuid -> this.playersLookingAt.remove(uuid));
        return this;
    }

    public PacketNPC setLookingDistance(float lookingDistance) {
        this.lookingDistance = lookingDistance;
        return this;
    }

    public double getLookingDistance() {
        return this.lookingDistance;
    }

    public PacketNPC setSkin(int skinID) {
        MineSkinFetcher.fetchSkinFromIdAsync(skinID, skinData -> {
            this.npc.updateSkin(new Skin(skinData.getValue(), skinData.getSignature()));
        });
        return this;
    }

    public PacketNPC setItemInHand(ItemStack itemInHand) {
        this.setItemInHand(itemInHand, true);
        return this;
    }

    public PacketNPC setItemInHand(ItemStack itemInHand, boolean mainHand) {
        if (mainHand) {
            this.npc.setItem(NPCSlot.MAINHAND, itemInHand);
        }
        return this;
    }

    public PacketNPC setHelemt(ItemStack helemt) {
        this.npc.setItem(NPCSlot.HELMET, helemt);
        return this;
    }

    public PacketNPC setChestPlate(ItemStack chestPlate) {
        this.npc.setItem(NPCSlot.CHESTPLATE, chestPlate);
        return this;
    }

    public PacketNPC setLeggings(ItemStack leggings) {
        this.npc.setItem(NPCSlot.LEGGINGS, leggings);
        return this;
    }

    public PacketNPC setBoots(ItemStack boots) {
        this.npc.setItem(NPCSlot.BOOTS, boots);
        return this;
    }

    @Override
    public NPC addClickAction(NPCClickAction action) {
        this.actions.add(action);
        return this;
    }

    protected void onInteract(Player player, NPCManager.ClickType clickType) {
        this.actions.forEach(a -> a.onInteract(this, player, clickType));
    }

    public List<UUID> getPlayersLookingAt() {
        return Collections.unmodifiableList(this.playersLookingAt);
    }

    @Override
    public List<UUID> getShown() {
        return Collections.unmodifiableList(this.shown);
    }

    private void checkThread() {
        if (thread == null) {
            throw new IllegalStateException("Can't look at player without workload thread");
        }
    }

    public me.Abhigya.core.util.npc.npclib.api.NPC getLibNPC() {
        return npc;
    }

}

