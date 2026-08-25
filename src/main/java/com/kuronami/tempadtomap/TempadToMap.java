package com.kuronami.tempadtomap;

import com.kuronami.tempadtomap.client.TtmClient;
import com.kuronami.tempadtomap.network.TtmNetwork;
import com.kuronami.tempadtomap.server.ServerLocationWatcher;
import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;

import org.slf4j.Logger;

/**
 * Tempad to Map — Tempad に登録した地点を JourneyMap のウェイポイントとして
 * 自動表示するブリッジ（両サイド型）。
 *
 * <p>構成: サーバーが overworld attachment {@code player_points} から各プレイヤー自身の
 * 地点を読み、変化時だけ S2C payload で送る。クライアントは受信集合を JourneyMap の
 * ウェイポイントへ差分同期する。シングルプレイでは統合サーバーが同じ経路で動く。</p>
 *
 * <p>設計方針（v0.1）: config 無しの固定挙動。常時同期・永続ウェイポイント・片方向。</p>
 */
@Mod(TempadToMap.MODID)
public final class TempadToMap {

    public static final String MODID = "tempadtomap";
    public static final Logger LOGGER = LogUtils.getLogger();

    public TempadToMap(IEventBus modEventBus, ModContainer modContainer) {
        // payload 登録は両 dist で必須（サーバーが送るにも登録が要る）
        modEventBus.addListener(TtmNetwork::register);

        if (FMLEnvironment.dist.isClient()) {
            // クライアント専用クラスへの参照はこの分岐の中だけで行う
            TtmClient.init(modEventBus);
        }
        // サーバー側の監視ループ。イベント自体が server 専用なので
        // クライアント dist ではハンドラが走らず Tempad 関連クラスもロードされない
        ServerLocationWatcher.init();
    }
}
