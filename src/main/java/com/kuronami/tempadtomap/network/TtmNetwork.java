package com.kuronami.tempadtomap.network;

import com.kuronami.tempadtomap.TempadToMap;
import com.kuronami.tempadtomap.client.TtmClient;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

/**
 * payload の登録（MOD バス・両 dist で実施。サーバーが送れるようにするため）と
 * クライアント受信ハンドラ。
 *
 * <p>1.21.1 では handler は既定で main thread 実行なので enqueueWork は使わない
 * （NEW_MOD_GUIDE networking 慣例）。</p>
 */
public final class TtmNetwork {

    private TtmNetwork() {
    }

    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        registrar.playToClient(TempadSyncPayload.TYPE, TempadSyncPayload.STREAM_CODEC, TtmNetwork::handleOnClient);
    }

    private static void handleOnClient(TempadSyncPayload payload, net.neoforged.neoforge.network.handling.IPayloadContext context) {
        // この handler はクライアントでしか発火しない。TtmClient（client 専用クラス）
        // への参照はここで初めて解決される＝サーバー dist ではロードされない。
        TtmClient.onLocationsFromServer(payload);
    }
}
