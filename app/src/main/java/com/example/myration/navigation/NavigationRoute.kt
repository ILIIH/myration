package com.example.myration.navigation

enum class NavigationRoute(val route: String) {
    COOKING_TAB("cooking_tab"),
    ADD_PRODUCTS_TAB("add_products_tab"),
    GROCERIES_LIST_TAB("groceries_list_tab"),
    ADD_PRODUCT_MANUALLY("add_product_manually"),
    RECIPE_DETAILS_SCREEN("recipe_details_screen/{recipeId}");
    fun withArgs(recipeId: Int): String {
        return "recipe_details_screen/$recipeId"
    }
}

