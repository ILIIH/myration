package com.example.myration.navigation

enum class NavigationRouteSubMenu() {
    GENERAL_MENU_TAB,
    ADD_PRODUCT_TAB
}

enum class NavigationRoute(val route: String) {
    COOKING_TAB("cooking_tab"),
    ADD_PRODUCTS_TAB("add_products_tab"),
    GROCERIES_LIST_TAB("groceries_list_tab"),
    ADD_PRODUCT_MANUALLY("add_product_manually"),
    ADD_PRODUCT_VOICE("add_product_voice"),
    SCAN_PRODUCTS_SCREEN("scan_products_screen"),
    RECIPE_DETAILS_SCREEN("recipe_details_screen/{recipeId}"),
    PROFILE_TAB("profile_tab"),
    FOOD_PLAN_SCREEN("food_plan"),
    MANAGE_FOOD_PLAN_SCREEN("manage_food_plan"),
    RATION_HISTORY_SCREEN("ration_history_screen"),
    PRODUCT_DETAILS_SCREEN("product_details_screen/{productId}");

    fun withArgsRecipieID(recipeId: Int): String {
        return "recipe_details_screen/$recipeId"
    }

    fun withArgsProductID(productId: Int): String {
        return "product_details_screen/$productId"
    }
}
