package me.Abhigya.core.placeholder.message;

import me.Abhigya.core.placeholder.PlaceholderRegistry;
import me.Abhigya.core.placeholder.PlaceholderValue;
import me.Abhigya.core.util.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.SequenceNode;
import org.yaml.snakeyaml.nodes.Tag;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Message {

    private final List<PlaceholderValue<String>> lines;

    public Message(List<PlaceholderValue<String>> lines) {
        this.lines = lines;
    }

    public Message(Node node) {
        checkTag(node, Arrays.asList(Tag.STR, Tag.SEQ));
        if (node.getTag() == Tag.STR) {
            String messageStr = ((ScalarNode) node).getValue();
            lines = Collections.singletonList(PlaceholderValue.stringValue(messageStr));
        } else {// node.getTag() == Tag.SEQ
            lines = ((SequenceNode) node).getValue().stream()
                    .map(lineNode -> {
                        checkTag(lineNode, Tag.STR);
                        return PlaceholderValue.stringValue(((ScalarNode) lineNode).getValue());
                    })
                    .collect(Collectors.toList());
        }
    }

    public static Message fromConfig(Object obj) {
        if (obj == null) return null;
        if (obj instanceof Collection) {
            return new Message(
                    ((Collection<?>) obj).stream()
                            .map(o -> PlaceholderValue.stringValue(o.toString()))
                            .collect(Collectors.toList())
            );
        } else {
            return new Message(Collections.singletonList(PlaceholderValue.stringValue(obj.toString())));
        }
    }

    public static Message fromText(List<String> lines) {
        return new Message(lines.stream().map(PlaceholderValue::fake).collect(Collectors.toList()));
    }

    public static Message fromText(String... lines) {
        return fromText(Arrays.asList(lines));
    }

    public static void checkTag(Node node, Tag expected) {
        if (node.getTag() != expected) {
            throw new IllegalStateException("Wrong type: found " + node.getTag() + ", expected: " +
                    Arrays.stream(new Tag[]{expected}).map(Tag::getValue).collect(Collectors.joining(", ")));
        }
    }

    public static void checkTag(Node node, Collection<Tag> expected) {
        if (!expected.contains(node.getTag())) {
            throw new IllegalStateException("Wrong type: found " + node.getTag() + ", expected: " +
                    Arrays.stream(expected.toArray(new Tag[0])).map(Tag::getValue).collect(Collectors.joining(", ")));
        }
    }

    //--------------FILTER

    public List<String> get(Player player) {
        return lines.stream().map(p -> p.resolve(player)).collect(Collectors.toList());
    }

    public List<String> get(Player player, PlaceholderRegistry<?> placeholders) {
        return lines.stream().map(p -> p.resolve(player, placeholders)).collect(Collectors.toList());
    }

    public List<String> get(Player player, String k1, String v2) {
        return get(player, PlaceholderRegistry.wrap(
                k1, v2
        ));
    }

    public List<String> get(Player player, String k1, String v1, String k2, String v2) {
        return get(player, PlaceholderRegistry.wrap(
                k1, v1,
                k2, v2
        ));
    }


    //--------------SEND

    public List<String> get(Player player, String k1, String v1, String k2, String v2, String k3, String v3) {
        return get(player, PlaceholderRegistry.wrap(
                k1, v1,
                k2, v2,
                k3, v3
        ));
    }

    public Message filter(PlaceholderRegistry<?> reg) {
        return new Message(
                lines.stream()
                        .map(p -> PlaceholderValue.rawStringValue(p.resolve(null, reg)))
                        .collect(Collectors.toList())
        );
    }

    public Message filter(String k1, String v1) {
        return filter(PlaceholderRegistry.wrap(
                k1, v1
        ));
    }

    public Message filter(String k1, String v1, String k2, String v2) {
        return filter(PlaceholderRegistry.wrap(
                k1, v1,
                k2, v2
        ));
    }

    public Message filter(String k1, String v1, String k2, String v2, String k3, String v3) {
        return filter(PlaceholderRegistry.wrap(
                k1, v1,
                k2, v2,
                k3, v3
        ));
    }

    public void send(Player player) {
        for (PlaceholderValue<String> message : lines)
            player.sendMessage(StringUtils.translateAlternateColorCodes(message.resolve(player)));
    }

    public void send(Player player, PlaceholderRegistry<?> placeholders) {
        for (PlaceholderValue<String> message : lines)
            player.sendMessage(StringUtils.translateAlternateColorCodes(message.resolve(player, placeholders)));
    }

    public void send(Player player, String k1, String v1) {
        send(player, PlaceholderRegistry.wrap(
                k1, v1
        ));
    }

    public void send(Player player, String k1, String v1, String k2, String v2) {
        send(player, PlaceholderRegistry.wrap(
                k1, v1,
                k2, v2
        ));
    }

    public void send(Player player, String k1, String v1, String k2, String v2, String k3, String v3) {
        send(player, PlaceholderRegistry.wrap(
                k1, v1,
                k2, v2,
                k3, v3
        ));
    }

    public void send(CommandSender sender) {
        for (PlaceholderValue<String> message : lines)
            sender.sendMessage(StringUtils.translateAlternateColorCodes(message.resolve(null)));
    }

    public void send(CommandSender sender, PlaceholderRegistry<?> placeholders) {
        for (PlaceholderValue<String> message : lines)
            sender.sendMessage(StringUtils.translateAlternateColorCodes(message.resolve(null, placeholders)));
    }

    public void send(CommandSender sender, String k1, String v1) {
        send(sender, PlaceholderRegistry.wrap(
                k1, v1
        ));
    }

    public void send(CommandSender sender, String k1, String v1, String k2, String v2) {
        send(sender, PlaceholderRegistry.wrap(
                k1, v1,
                k2, v2
        ));
    }

    public void send(CommandSender sender, String k1, String v1, String k2, String v2, String k3, String v3) {
        send(sender, PlaceholderRegistry.wrap(
                k1, v1,
                k2, v2,
                k3, v3
        ));
    }

    //--------------BROADCAST
    public void broadcast(Iterable<? extends Player> players) {
        for (Player player : players)
            for (PlaceholderValue<String> message : lines)
                player.sendMessage(message.resolve(player));
    }

    public void broadcast(Iterable<? extends Player> players, PlaceholderRegistry<?> placeholders) {
        for (Player player : players)
            for (PlaceholderValue<String> message : lines)
                player.sendMessage(message.resolve(player, placeholders));
    }

    public void broadcast(Iterable<? extends Player> players, String k1, String v1) {
        broadcast(players, PlaceholderRegistry.wrap(
                k1, v1
        ));
    }

    public void broadcast(Iterable<? extends Player> players, String k1, String v1, String k2, String v2) {
        broadcast(players, PlaceholderRegistry.wrap(
                k1, v1,
                k2, v2
        ));
    }

    public void broadcast(Iterable<? extends Player> players, String k1, String v1, String k2, String v2, String k3, String v3) {
        broadcast(players, PlaceholderRegistry.wrap(
                k1, v1,
                k2, v2,
                k3, v3
        ));
    }

    public void broadcast() {
        broadcast(Bukkit.getOnlinePlayers());
    }

    public void broadcast(PlaceholderRegistry<?> placeholders) {
        broadcast(Bukkit.getOnlinePlayers(), placeholders);
    }

    public void broadcast(String k1, String v1) {
        broadcast(Bukkit.getOnlinePlayers(), PlaceholderRegistry.wrap(
                k1, v1
        ));
    }

    public void broadcast(String k1, String v1, String k2, String v2) {
        broadcast(Bukkit.getOnlinePlayers(), PlaceholderRegistry.wrap(
                k1, v1,
                k2, v2
        ));
    }

    public void broadcast(String k1, String v1, String k2, String v2, String k3, String v3) {
        broadcast(Bukkit.getOnlinePlayers(), PlaceholderRegistry.wrap(
                k1, v1,
                k2, v2,
                k3, v3
        ));
    }

    //--------------SIGNS
    public void setSign(Sign sign, Player player, PlaceholderRegistry<?> placeholders) {
        int i = 0;
        for (; i < Math.min(4, lines.size()); i++) {
            sign.setLine(i, lines.get(i).resolve(player, placeholders));
        }
        for (; i < 4; i++) {
            sign.setLine(i, "");
        }
        sign.update();
    }

    public List<PlaceholderValue<String>> getLines() {
        return lines;
    }
}
