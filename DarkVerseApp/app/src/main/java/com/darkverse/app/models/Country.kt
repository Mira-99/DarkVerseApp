package com.darkverse.app.models

data class Country(
    val code: String = "",
    val nameAr: String = "",
    val nameEn: String = "",
    val flagUrl: String = "",
    val languages: List<Language> = emptyList()
)

data class Language(
    val code: String = "",
    val nameAr: String = "",
    val nameEn: String = "",
    val nativeName: String = ""
)

object CountriesData {
    val countries = listOf(
        Country(
            code = "KRD",
            nameAr = "كردستان",
            nameEn = "Kurdistan",
            flagUrl = "kurdistan_flag",
            languages = listOf(
                Language("ku", "الكردية", "Kurdish", "Kurdî"),
                Language("ku-latn", "الكردية اللاتينية", "Kurdish Latin", "Kurdî (Latin)"),
                Language("ar", "العربية", "Arabic", "العربية"),
                Language("en", "الإنجليزية", "English", "English")
            )
        ),
        Country(
            code = "SA",
            nameAr = "السعودية",
            nameEn = "Saudi Arabia",
            flagUrl = "saudi_flag",
            languages = listOf(
                Language("ar", "العربية", "Arabic", "العربية"),
                Language("en", "الإنجليزية", "English", "English")
            )
        ),
        Country(
            code = "EG",
            nameAr = "مصر",
            nameEn = "Egypt",
            flagUrl = "egypt_flag",
            languages = listOf(
                Language("ar", "العربية", "Arabic", "العربية"),
                Language("en", "الإنجليزية", "English", "English")
            )
        ),
        Country(
            code = "IQ",
            nameAr = "العراق",
            nameEn = "Iraq",
            flagUrl = "iraq_flag",
            languages = listOf(
                Language("ar", "العربية", "Arabic", "العربية"),
                Language("ku", "الكردية", "Kurdish", "Kurdî"),
                Language("en", "الإنجليزية", "English", "English")
            )
        ),
        Country(
            code = "SY",
            nameAr = "سوريا",
            nameEn = "Syria",
            flagUrl = "syria_flag",
            languages = listOf(
                Language("ar", "العربية", "Arabic", "العربية"),
                Language("ku", "الكردية", "Kurdish", "Kurdî"),
                Language("en", "الإنجليزية", "English", "English")
            )
        ),
        Country(
            code = "TR",
            nameAr = "تركيا",
            nameEn = "Turkey",
            flagUrl = "turkey_flag",
            languages = listOf(
                Language("tr", "التركية", "Turkish", "Türkçe"),
                Language("ku", "الكردية", "Kurdish", "Kurdî"),
                Language("ar", "العربية", "Arabic", "العربية"),
                Language("en", "الإنجليزية", "English", "English")
            )
        ),
        Country(
            code = "IR",
            nameAr = "إيران",
            nameEn = "Iran",
            flagUrl = "iran_flag",
            languages = listOf(
                Language("fa", "الفارسية", "Persian", "فارسی"),
                Language("ku", "الكردية", "Kurdish", "Kurdî"),
                Language("ar", "العربية", "Arabic", "العربية"),
                Language("en", "الإنجليزية", "English", "English")
            )
        ),
        Country(
            code = "US",
            nameAr = "الولايات المتحدة",
            nameEn = "United States",
            flagUrl = "usa_flag",
            languages = listOf(
                Language("en", "الإنجليزية", "English", "English"),
                Language("es", "الإسبانية", "Spanish", "Español")
            )
        ),
        Country(
            code = "JP",
            nameAr = "اليابان",
            nameEn = "Japan",
            flagUrl = "japan_flag",
            languages = listOf(
                Language("ja", "اليابانية", "Japanese", "日本語"),
                Language("en", "الإنجليزية", "English", "English")
            )
        )
    )
    
    fun getCountryByCode(code: String): Country? {
        return countries.find { it.code == code }
    }
    
    fun getLanguageByCode(code: String): Language? {
        return countries.flatMap { it.languages }.find { it.code == code }
    }
}

