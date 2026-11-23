package com.sr3launcher;

import java.util.ArrayList;
import java.util.List;

/** Описание игр, которые настраиваются общим экраном GameActivity: ключи файла настроек, варианты значений, пресеты. */
final class Games {
    private Games() {}

    /** Один параметр: пишется во все перечисленные ключи (имя с «~» в начале: значение в формате «v, v, v»). */
    static final class Opt {
        final String title, hint, type;
        final String[] names, path, labels, values;
        final boolean q;   // следует пресетам «Минимум…Ультра»
        final int thr;     // для переключателей: с какого уровня пресета включено (5: никогда)
        Opt(String title, String hint, String[] names, String[] path, String type, String[] labels, String[] values, boolean q, int thr) {
            this.title = title; this.hint = hint; this.names = names; this.path = path; this.type = type;
            this.labels = labels; this.values = values; this.q = q; this.thr = thr;
        }
    }

    static final class Group {
        final String title; final Opt[] opts;
        Group(String title, Opt... opts) { this.title = title; this.opts = opts; }
    }

    static final class Spec {
        String id, pkg, name, title, note;
        String[] setup;
        int bg, card, btn2, accent, text, muted;
        int[] presetRes = {540, 720, 900, 1080, 1080};
        String[][] force = new String[0][];
        Group[] groups;
    }

    static String hexD(double d) {
        long b = Double.doubleToLongBits(d);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) sb.append(String.format("%02x", (b >> (8 * i)) & 0xff));
        return sb.toString();
    }

    private static String[] a(String... s) { return s; }

    private static Opt ladder(String title, String hint, String[] names, String[] path, String[] labels, String[] values) {
        return new Opt(title, hint, names, path, "string", labels, values, true, 0);
    }

    private static Opt toggle(String title, String hint, String[] names, String[] path, int thr) {
        return new Opt(title, hint, names, path, "integer", a(Fr.t("Выкл."), Fr.t("Вкл.")), a("0", "1"), thr < 5, thr);
    }

    private static Opt fixed(String title, String hint, String[] names, String[] path, String type, String[] labels, String[] values) {
        return new Opt(title, hint, names, path, type, labels, values, false, 5);
    }

    private static String[] prof(String base) { return a(base, base + "_battery", base + "_graphics"); }

    private static Spec base(String id, String pkg, String name, String title, String vendor, String game,
                             int bg, int card, int btn2, int accent) {
        Spec s = new Spec();
        s.id = id; s.pkg = pkg; s.name = name; s.title = title;
        s.setup = a("HKEY_CURRENT_USER", "Software", vendor, game, "Setup");
        s.bg = bg; s.card = card; s.btn2 = btn2; s.accent = accent;
        s.text = 0xFFEFF2F5; s.muted = 0xFF93A0AB;
        return s;
    }

    private static final String[] L_LOW4 = {Fr.t("Минимум"), Fr.t("Низкое"), Fr.t("Среднее"), Fr.t("Высокое")};
    private static final String[] L_NUM4 = {Fr.t("Минимум"), Fr.t("Низко"), Fr.t("Средне"), Fr.t("Высоко")};
    private static final String[] L_OFF4 = {Fr.t("Выкл."), Fr.t("Низкое"), Fr.t("Среднее"), Fr.t("Высокое")};
    private static final String[] L_LMH = {Fr.t("Низкое"), Fr.t("Среднее"), Fr.t("Высокое")};

    private static List<Spec> all;

    static List<Spec> all() {
        if (all != null) return all;
        List<Spec> l = new ArrayList<Spec>();

        // ---- GRID Autosport
        Spec g = base("gas", "com.feralinteractive.gridautosport_edition_android", "GRID Autosport", "GRID AUTOSPORT",
            "Feral Interactive", "GRID Autosport", 0xFF14110A, 0xFF211C12, 0xFF37301F, 0xFFFFB020);
        g.force = new String[][] {{"gfxconfig_preset_name", "custom"}, {"gfxconfig_high_preset_name", "custom"}};
        g.note = Fr.t("Пишется в профили «обычный» и «высокий» (gfxconfig_ и gfxconfig_high_). Профиль энергосбережения не трогаем.");
        g.groups = new Group[] {
            new Group(Fr.t("Графика"),
                ladder(Fr.t("Тени"), "", a("gfxconfig_shadows", "gfxconfig_high_shadows"), null, L_LOW4, a("ultralow", "low", "medium", "high")),
                ladder(Fr.t("Текстуры"), "", a("gfxconfig_textures", "gfxconfig_high_textures"), null, L_LMH, a("low", "medium", "high")),
                ladder(Fr.t("Вода"), "", a("gfxconfig_water", "gfxconfig_high_water"), null, L_LMH, a("low", "medium", "high")),
                ladder(Fr.t("Частицы"), "", a("gfxconfig_particles", "gfxconfig_high_particles"), null, L_OFF4, a("off", "low", "medium", "high")),
                ladder(Fr.t("Толпа"), "", a("gfxconfig_crowd", "gfxconfig_high_crowd"), null, L_LOW4, a("ultralow", "low", "medium", "high")),
                ladder(Fr.t("Отражения на машинах"), "", a("gfxconfig_car_reflection", "gfxconfig_high_car_reflection"), null, L_LMH, a("low", "medium", "high")),
                ladder(Fr.t("Затенение углов (AO)"), Fr.t("Чем выше, тем темнее картинка"), a("gfxconfig_dynamic_ambient_occ", "gfxconfig_high_dynamic_ambient_occ"), null, L_LOW4, a("ultralow", "low", "medium", "high")),
                ladder(Fr.t("Фильтрация текстур"), "", a("gfxconfig_anisotropic_filtering", "gfxconfig_high_anisotropic_filtering"), null, L_OFF4, a("off", "low", "medium", "high")),
                new Opt(Fr.t("Масштаб рендера"), Fr.t("Процент от выбранного разрешения"), a("gfxconfig_window_scale", "gfxconfig_high_window_scale"), null, "integer",
                    a("60%", "70%", "80%", "90%", "100%"), a("60", "70", "80", "90", "100"), true, 0),
                toggle(Fr.t("Сглаживание FXAA"), "", a("FeralUseFXAA"), null, 1)),
            new Group(Fr.t("Динамическое разрешение"),
                toggle(Fr.t("Включено"), Fr.t("Разрешение подстраивается под нагрузку"), a("Enable"), a("DynamicResolutionScaling"), 5),
                new Opt(Fr.t("Минимальный масштаб"), Fr.t("Ниже этого DRS не опускает"), a("MinScaling"), a("DynamicResolutionScaling"), "binary",
                    a("50%", "60%", "70%", "80%", "90%", "100%"),
                    a(hexD(0.5), hexD(0.6), hexD(0.7), hexD(0.8), hexD(0.9), hexD(1.0)), true, 0)),
            new Group(Fr.t("Частота кадров"),
                fixed(Fr.t("Лимит FPS"), "", a("gfxconfig_max_fps", "gfxconfig_high_max_fps"), null, "integer",
                    a("30", "60", "90", "120"), a("30", "60", "90", "120")))
        };
        l.add(g);

        // ---- Hitman: Blood Money
        Spec b = base("bm", "com.feralinteractive.hitmanbloodmoney_android", "Hitman: Blood Money", "HITMAN: BLOOD MONEY",
            "Feral Interactive", "Hitman Blood Money", 0xFF0E0B0B, 0xFF1D1416, 0xFF32222A, 0xFFE04848);
        b.note = Fr.t("В файле игры немного графических параметров: тени, апскейлер и FXAA. Основной рычаг: разрешение.");
        String[] sh = a("512", "1024", "1536", "2048");
        String[] shl = a("512", "1024", "1536", "2048");
        b.groups = new Group[] {
            new Group(Fr.t("Графика"),
                new Opt(Fr.t("Разрешение теней"), "", a("ShadowResolution"), null, "integer", shl, sh, true, 0),
                new Opt(Fr.t("Тени от солнца"), Fr.t("Записывается и в профили энергосбережения/производительности"), a("DirectionalShadowResolution",
                    "~DirectionalShadowResolution_battery", "~DirectionalShadowResolution_graphics", "~DirectionalShadowResolution_perf", "~DirectionalShadowResolution_plus"),
                    null, "integer", shl, sh, true, 0),
                toggle(Fr.t("Апскейлер"), Fr.t("Рисует в меньшем разрешении и растягивает"), a("UseUpscaler"), null, 5),
                fixed(Fr.t("Сглаживание FXAA"), Fr.t("Значения 1…3 не проверены"), a("~FXAAPreset_battery", "~FXAAPreset_graphics", "~FXAAPreset_perf", "~FXAAPreset_plus"), null, "string",
                    a(Fr.t("Выкл."), "1", "2", "3"), a("0", "1", "2", "3")))
        };
        l.add(b);

        // ---- XCOM 2
        Spec x = base("xc", "com.feralinteractive.xcom2_android", "XCOM 2 Collection", "XCOM 2 COLLECTION",
            "Feral Interactive", "XCOM 2", 0xFF0A1416, 0xFF122428, 0xFF1D3A3F, 0xFF3FD2C7);
        x.note = Fr.t("Параметры пишутся во все три профиля игры (обычный, батарея, графика). Максимальные значения (3) проверены не везде.");
        x.groups = new Group[] {
            new Group(Fr.t("Графика"),
                ladder2(Fr.t("Качество теней"), "GFXShadowQuality"),
                ladder2(Fr.t("Текстуры"), "GFXTextureDetail"),
                ladder2(Fr.t("Фильтрация текстур"), "GFXTextureFiltering"),
                ladder2(Fr.t("Дальность прорисовки"), "GFXMaxDrawDistance"),
                ladder2(Fr.t("Эффекты"), "GFXEffects"),
                toggle2(Fr.t("Затенение углов (AO)"), "GFXAmbientOcclusion", 3),
                toggle2(Fr.t("Свечение (Bloom)"), "GFXBloom", 2),
                toggle2(Fr.t("Следы и декали"), "GFXDecals", 1),
                toggle2(Fr.t("Глубина резкости"), "GFXDepthOfField", 4),
                toggle2(Fr.t("Грязь на объективе"), "GFXDirtyLens", 5),
                toggle2(Fr.t("Отражения в экране (SSR)"), "GFXScreenSpaceReflections", 4),
                toggle2(Fr.t("Подповерхностное рассеяние"), "GFXSubsurfaceScattering", 3),
                toggle2(Fr.t("Сглаживание FXAA"), "GFXFXAA", 1),
                new Opt(Fr.t("Масштаб разрешения"), Fr.t("Умножается на выбранное разрешение; 100% = без масштабирования"), a("GameResolutionScaleNumerator"), null, "integer",
                    a("50%", "60%", "70%", "80%", "90%", "100%"), a("5000", "6000", "7000", "8000", "9000", "10000"), true, 0)),
            new Group(Fr.t("Частота кадров"),
                fixed(Fr.t("Лимит FPS"), "", prof("NonIdleFPS"), null, "integer", a("30", "60", "90", "120"), a("30", "60", "90", "120")))
        };
        l.add(x);

        // ---- Tropico
        Spec t = base("tp", "com.feralinteractive.tropico3", "Tropico", "TROPICO",
            "Haemimont Games", "Tropico 3: Gold Edition", 0xFF0B1410, 0xFF13241B, 0xFF1F3B2D, 0xFF4FC36A);
        String[] gfx = a("GFX");
        t.note = Fr.t("Значения «Среднее» и «Низкое» у некоторых параметров игра может не знать, тогда оставит ближайшее.");
        t.groups = new Group[] {
            new Group(Fr.t("Графика"),
                ladder(Fr.t("Тени"), "", a("ShadowQuality"), gfx, L_LMH, a("Low", "Medium", "High")),
                ladder(Fr.t("Текстуры"), "", a("TextureLevel"), gfx, L_LMH, a("Low", "Medium", "High")),
                ladder(Fr.t("Дальность прорисовки"), "", a("ViewDistance"), gfx, L_LMH, a("Low", "Medium", "High")),
                ladder(Fr.t("Вода"), "", a("WaterQuality"), gfx, L_LMH, a("Low", "Medium", "High")),
                ladder(Fr.t("Ландшафт"), "", a("TerrainQuality"), gfx, L_LMH, a("Low", "Medium", "High")),
                ladder(Fr.t("Трава"), "", a("GrassQuality"), gfx, L_OFF4, a("Off", "Low", "Medium", "High")),
                ladder(Fr.t("Фильтрация текстур"), "", a("Anisotropy"), gfx, L_OFF4, a("Off", "Low", "Medium", "High")))
        };
        l.add(t);

        all = l;
        return l;
    }

    private static Opt ladder2(String title, String key) {
        return new Opt(title, "", prof(key), null, "integer", L_NUM4, a("0", "1", "2", "3"), true, 0);
    }

    private static Opt toggle2(String title, String key, int thr) {
        return toggle(title, "", prof(key), null, thr);
    }

    static Spec byId(String id) {
        for (Spec s : all()) if (s.id.equals(id)) return s;
        return null;
    }
}
