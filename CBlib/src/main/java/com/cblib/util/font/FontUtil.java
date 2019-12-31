package com.cblib.util.font;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;

public final class FontUtil {

    private final static String FONT_ASSETS_PATH_PREFIX = "fonts/";
    private final static String FONT_EXTENSION = ".ttf";

//    public final static String TSN_B2_EXTRA_LIGHT = "TheSans-B2ExtraLight.otf";
//    public final static String TSN_B2_EXTRA_LIGHT_ITALIC = "TheSans-B2ExtraLightItalic.otf";
//    public final static String TSN_B3_LIGHT = "TheSans-B3Light.otf";
//    public final static String TSN_B3_LIGHT_ITALIC = "TheSans-B3LightItalic.otf";
//    public final static String TSN_B4_SEMI_LIGHT = "TheSans-B4SemiLight.otf";
//    public final static String TSN_B4_SEMI_LIGHT_ITALIC = "TheSans-B4SemiLightItalic.otf";
    //Style Guide
    public final static String TSN_B5_PLAIN = "TheSans-B5Plain.otf";
//    public final static String TSN_B5_PLAIN_ITALIC = "TheSans-B5PlainItalic.otf";
//    public final static String TSN_B6_SEMI_BOLD = "TheSans-B6SemiBold.otf";
//    public final static String TSN_B6_SEMI_BOLD_ITALIC = "TheSans-B6SemiBoldItalic.otf";
//    public final static String TSN_B7_BOLD = "TheSans-B7Bold.otf";
//    public final static String TSN_B7_BOLD_ITALIC = "TheSans-B7BoldItalic.otf";
    //Style Guide
    public final static String TSN_B8_EXTRA_BOLD = "TheSans-B8ExtraBold.otf";
//    public final static String TSN_B8_EXTRA_BOLD_ITALIC = "TheSans-B8ExtraBoldItalic.otf";
//    public final static String TSN_B9_BLACK = "TheSans-B9Black.otf";
//    public final static String TSN_B9_BLACK_ITALIC = "TheSans-B9BlackItalic.otf";


    private FontUtil() {
        // KokHou, Utility can't be new [best practice]
    }

    public static Typeface getTypeFace(Context context, String type) {
        return Typeface.createFromAsset(context.getAssets(), getPath(type));
    }

    private static String getPath(String fontType) {
        return FONT_ASSETS_PATH_PREFIX + fontType;
    }


    public static CharSequence initTypeface(Context context, String value) {
        SpannableString text1 = new SpannableString(value);
        Typeface tf1 = FontUtil.getTypeFace(context, FontUtil.TSN_B5_PLAIN);
        text1.setSpan(new CustomTypefaceSpan("", tf1), 0, text1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        SpannableStringBuilder title = new SpannableStringBuilder();
        title.append(text1);
        return title;
    }
}
