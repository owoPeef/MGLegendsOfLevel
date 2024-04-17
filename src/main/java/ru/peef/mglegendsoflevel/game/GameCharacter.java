package ru.peef.mglegendsoflevel.game;

import org.bukkit.potion.PotionEffect;
import ru.peef.chilove.Utils;

import java.util.ArrayList;
import java.util.List;

public abstract class GameCharacter {
    private final String name;
    private final String title;
    private final String type;
    private String description;
    private final double mobDamageMultiplier;
    private final double mobReceivedDamageMultiplier;
    private List<PotionEffect> effects = new ArrayList<>();

    public GameCharacter(String name, String title, String type, String description, double mobDamageMultiplier, double mobReceivedDamageMultiplier) {
        this.name = name.toLowerCase();
        this.title = Utils.format(title);
        this.type = Utils.format(type);
        this.description = Utils.format(description);
        this.mobDamageMultiplier = mobDamageMultiplier;
        this.mobReceivedDamageMultiplier = mobReceivedDamageMultiplier;
    }

    public GameCharacter(String name, String title, String type, String description, double mobDamageMultiplier, double mobReceivedDamageMultiplier, List<PotionEffect> effects) {
        this.name = name.toLowerCase();
        this.title = Utils.format(title);
        this.type = Utils.format(type);
        this.description = Utils.format(description);
        this.mobDamageMultiplier = mobDamageMultiplier;
        this.mobReceivedDamageMultiplier = mobReceivedDamageMultiplier;
        this.effects = effects;
    }

    public abstract double getMobDamageMultiplier();
    public abstract double getMobReceivedDamageMultiplier();

    public double getOriginalMobDamageMultiplier() { return mobDamageMultiplier; }
    public double getOriginalMobReceivedDamageMultiplier() { return mobReceivedDamageMultiplier; }
    public String getName() { return this.name; }
    public String getTitle() { return this.title; }
    public String getType() { return this.type; }
    public String getDescription() { return this.description; }
    public List<PotionEffect> getEffects() { return this.effects; }
}
