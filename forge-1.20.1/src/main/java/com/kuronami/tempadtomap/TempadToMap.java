package com.kuronami.tempadtomap;

import com.kuronami.tempadtomap.client.TtmClient;
import com.kuronami.tempadtomap.network.TtmNetwork;
import com.kuronami.tempadtomap.server.ServerLocationWatcher;

import com.mojang.logging.LogUtils;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

import org.slf4j.Logger;

/**
 * Tempad to Map — Tempad に登録した地点を JourneyMap のウェイポイントとして
 * 自動表示するブリッジ（両サイド型・Forge 1.20.1 / Tempad 2.x 系）。
 *
 * <p>構成は 075 本体（NeoForge 1.21.1）と同型。差分は payload 層のみ
 * （1.20.1 Forge = SimpleChannel + FriendlyByteBuf の手書き codec）と、
 * サーバー読取が Tempad 2.x の {@code TempadLocationHandler.getLocations} 直参照になった点。</p>
 *
 * <p>設計方針（v0.1）: config 無しの固定挙動。常時同期・永続ウェイポイント・片方向。</p>
 */
@Mod(TempadToMap.MODID)
public final class TempadToMap {

    public static final String MODID = "tempadtomap";
    public static final Logger LOGGER = LogUtils.getLogger();

    public TempadToMap() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(TempadToMap::onCommonSetup);

        if (FMLEnvironment.dist.isClient()) {
            // クライアント専用クラスへの参照はこの分岐の中だけで行う
            TtmClient.init();
        }
        // サーバー側の監視ループ（統合サーバーでも同じ経路で動く）。
        // イベント自体が server 専用なのでクライアント dist ではハンドラが走らず
        // Tempad 関連クラスもロードされない
        MinecraftForge.EVENT_BUS.register(ServerLocationWatcher.class);
    }

    private static void onCommonSetup(FMLCommonSetupEvent event) {
        // SimpleChannel への message 登録は deferred work で行う（mod-003 forge-1.20.1 実績方式）
        event.enqueueWork(TtmNetwork::register);
    }
}
