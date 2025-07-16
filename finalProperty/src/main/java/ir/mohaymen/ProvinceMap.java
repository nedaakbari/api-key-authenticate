package ir.mohaymen;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ProvinceMap {
    private static final Map<String, String> provinceCode = new HashMap<String, String>() {{
        put("041", "آذربایجان شرقی");
        put("044", "آذربایجان غربی");
        put("045", "اردبیل");
    }};


    ////////////////////////////////////TODO روش‌های حرفه‌ای برای محافظت از داده‌های Map
    //TODO ////////////////////////////////////////////// 1. استفاده از Collections.unmodifiableMap
/*
این روش جلوی تغییر از بیرون رو می‌گیره، ولی هنوز یه نکته داره:
اگر کسی reference به مپ اصلی (provinceCode) داشته باشه، باز هم می‌تونه تغییر بده!
بنابراین، بهتره فقط داخل کلاس مقداردهی بشه و expose نشه.
*/
    public static Map<String, String> getProvinceCodeMap() {
        return Collections.unmodifiableMap(provinceCode);
    }

    //TODO ////////////////////////////////////////////// 2. استفاده از Map.of(...) (از جاوا 9 به بعد)
    //اگر دیتا ثابته و تغییر نمی‌کنه، این بهترین گزینه‌ست:
    //غیرقابل تغییره (Immutable)
    //با performance خیلی بهتر ساخته می‌شه
    //خطا می‌ده اگه مقدار تکراری بدی یا بخوای بعداً تغییر بدی
    private static final Map<String, String> provinceCodeImmutable = Map.of(
            "041", "آذربایجان شرقی",
            "044", "آذربایجان غربی",
            "045", "اردبیل"
    );

    //TODO ////////////////////////////////////////////// 3. استفاده از Guava ImmutableMap (اگر از Guava استفاده می‌کنی)
    private static final Map<String, String> provinceCodeImmutableMap = ImmutableMap.<String, String>builder()
            .put("041", "آذربایجان شرقی")
            .put("044", "آذربایجان غربی")
            .build();

    //📦 مزیتش اینه که:
    //Thread-safe هست
    //Read-only و Immutable
    //سرعت بالا
    //TODO ////////////////////////////////////////////// 🔹 ۴. استفاده از Singleton Utility Pattern برای مخفی کردن دیتا
    public final class ProvinceMapClass {
        private static final Map<String, String> provinceCode = Map.of(
                "041", "آذربایجان شرقی",
                "044", "آذربایجان غربی"
        );

        private ProvinceMapClass() {
        } // جلوگیری از ساخت نمونه

        public static Set<String> getAllCodes() {
            return Set.copyOf(provinceCode.keySet());
        }
        //}
        //✅ تو این حالت دیگه هیچ‌کس دسترسی مستقیم به provinceCode نداره، ولی همه کاری لازم باشه از طریق متد انجام می‌شه.


        ////////////////////////////////////////////💡 پیشنهاد نهایی
        //اگه:
        //دیتا تغییر نمی‌کنه →
        // از Map.of(...) یا ImmutableMap استفاده کن

        //دیتا تغییر می‌کنه →
        // Collections.unmodifiableMap(...) با API مناسب

        //امنیت و خوانایی برات مهمه →
        // expose نکن، فقط متد بده (مثل getProvinceName())

    }
}