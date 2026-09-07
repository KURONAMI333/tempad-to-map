package com.kuronami.tempadtomap.sync;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tempad 2.x の色導出（名前+UUID ハッシュ）の性質テスト。
 * 決定論的・範囲内・染料代表 RGB 表の健全性を機械保証する。
 *
 * <p>#46（Xaero 版）の PointColorTest から流用。同テスト 4 本目の
 * {@code DyeColors.nearestDyeIndex} 往復一致は Xaero 専用の変換層への検査のため
 * JM 版にはその層が存在せず、表の健全性検査（16 色・全て異なる）で置き換えた。</p>
 */
class PointColorTest {

    @Test
    void deriveIsDeterministic() {
        UUID id = UUID.fromString("3f2b8c1e-1234-4abc-9d0e-5f6a7b8c9d01");
        assertEquals(PointColor.derive("Base Camp", id), PointColor.derive("Base Camp", id));
        assertEquals(PointColor.derive(null, id), PointColor.derive(null, id));
    }

    @Test
    void deriveChangesWhenIdentityChanges() {
        // 名前が同じでも UUID が違えば（確率的に）別の色になりうる。
        // 少なくとも「同一入力→同一色」が全入力で成り立つことを別観点で確認する
        Set<Integer> seen = new HashSet<>();
        for (int i = 0; i < 32; i++) {
            seen.add(PointColor.derive("same name", new UUID(0, i)));
        }
        assertTrue(seen.size() > 1, "UUID を変えても色が一切変わらないのはハッシュ壊れ");
    }

    @Test
    void derivedRgbIsAlwaysADyeRepresentative() {
        for (int i = 0; i < 64; i++) {
            int rgb = PointColor.derive("point" + i, new UUID(i, i));
            boolean isDyeColor = false;
            for (int dye : PointColor.DYE_RGB) {
                if (dye == rgb) {
                    isDyeColor = true;
                    break;
                }
            }
            assertTrue(isDyeColor, "染料代表 RGB 以外の値: " + rgb);
        }
    }

    @Test
    void dyeTableHasSixteenDistinctEntries() {
        // JM のウェイポイント色はこの表から直接来るので、16 色が揃っていて
        // 全部異なること（= 色の多様性が潰れていないこと）を保証する
        assertEquals(16, PointColor.DYE_RGB.length);
        Set<Integer> distinct = new HashSet<>();
        for (int dye : PointColor.DYE_RGB) {
            distinct.add(dye);
        }
        assertEquals(16, distinct.size());
    }
}
