package com.schokobaer.battleofgods.init;

import com.schokobaer.battleofgods.BattleofgodsMod;
import com.schokobaer.battleofgods.network.CraftPacket;

public class InitNetworkPackage {
    private static int id = 0;

    public static void register() {
        BattleofgodsMod.PACKET_HANDLER.registerMessage(id++,
                CraftPacket.class,
                CraftPacket::encode,
                CraftPacket::new,
                CraftPacket::handle);
    }
}
