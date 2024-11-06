package com.sutonglabs.tracestore.data

import com.sutonglabs.tracestore.models.CartItem

object DemoCartItems {
    val items = listOf(
        CartItem(id = 1, name = "Lakdong Turmeric", price = 100.0, quantity = 1, image = "images\\1725349665733-41-510x510.png"),
        CartItem(id = 2, name = "Mandarin", price = 150.0, quantity = 2, image = "https://via.placeholder.com/150/00FF00/FFFFFF?text=Product+2"),
        CartItem(id = 3, name = "Bamboo Shoot", price = 200.0, quantity = 1, image = "https://via.placeholder.com/150/0000FF/FFFFFF?text=Product+3"),
        CartItem(id = 4, name = "Khasi Honey", price = 250.0, quantity = 1, image = "https://via.placeholder.com/150/FFFF00/FFFFFF?text=Product+4"),
        CartItem(id = 5, name = "Pineapple", price = 300.0, quantity = 3, image = "https://via.placeholder.com/150/FF00FF/FFFFFF?text=Product+5")
    )
}
