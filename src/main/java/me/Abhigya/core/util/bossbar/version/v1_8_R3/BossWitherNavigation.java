package me.Abhigya.core.util.bossbar.version.v1_8_R3;

import net.minecraft.server.v1_8_R3.Navigation;
import net.minecraft.server.v1_8_R3.World;

public class BossWitherNavigation extends Navigation {

    protected final BossWither boss;

    public BossWitherNavigation(BossWither boss, World world) {
        super(boss, world);

        this.boss = boss;
    }

    @Override // canNavigate
    protected boolean b() {
        return false;
    }

}
