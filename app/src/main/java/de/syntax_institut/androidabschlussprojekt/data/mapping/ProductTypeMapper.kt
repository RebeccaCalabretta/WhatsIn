package de.syntax_institut.androidabschlussprojekt.data.mapping

import android.util.Log
import de.syntax_institut.androidabschlussprojekt.helper.ProductType

object ProductTypeMapper {

    private val beautyCategories = setOf(
        "en:beauty",
        "en:cosmetics",
        "en:face-care",
        "en:skin-care",
        "en:body-care",
        "en:shower-gels",
        "en:shampoos",
        "en:hair-care",
        "en:oral-care",
        "en:toothpastes",
        "en:deodorants",
        "en:make-up",
        "en:lipsticks",
        "en:nail-polishes",
        "en:moisturizers",
        "en:eye-care-products",
        "en:cleansing-products",
        "en:makeup-removers",
        "en:personal-care",
        "en:perfumes",
        "en:cotton-swabs",
        "en:cosmetic-accessories",
        "en:bath-products"
    )

    private val knownBeautyBrands = setOf(
        "nivea",
        "ebelin",
        "lavera",
        "balea",
        "alverde",
        "cd",
        "dove",
        "neutrogena",
        "sebamed",
        "schaebens",
        "maybelline",
        "loreal",
        "essence",
        "garnier",
        "weleda",
        "fa",
        "isdin",
        "louis widmer",
        "paediprotect",
        "diadermine",
        "rituals",
        "olaz",
        "cien",
        "syoss",
        "sante",
        "vichy",
        "medipharma",
        "kneipp",
        "florena",
        "john frieda"
    )

    fun determineProductType(
        categoryTags: List<String>?,
        fallback: ProductType,
        brand: String?
    ): ProductType {
        if (categoryTags.isNullOrEmpty() && brand.isNullOrBlank()) return fallback
        Log.d("ProductTypeMapper", "Erhaltene Kategorie-Tags: $categoryTags")

        if (!categoryTags.isNullOrEmpty() && categoryTags.any { it in beautyCategories }) {
            val match = categoryTags.firstOrNull { it in beautyCategories }
            Log.d("ProductTypeMapper", "Beauty-Kategorie erkannt: $match")
            return ProductType.BEAUTY
        }

        val brandName = brand?.lowercase()?.trim()
        if (!brandName.isNullOrBlank()) {
            val brandMatch = knownBeautyBrands.firstOrNull { brandName.contains(it) }
            if (brandMatch != null) {
                Log.d("ProductTypeMapper", "Beauty-Marke erkannt: $brandMatch in $brand")
                return ProductType.BEAUTY
            }
        }

        return fallback
    }
}