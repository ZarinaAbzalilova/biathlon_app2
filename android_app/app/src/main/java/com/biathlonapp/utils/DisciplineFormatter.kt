package com.biathlonapp.utils

object DisciplineFormatter {

    fun format(discipline: String?): String {
        return when (discipline) {
            "BT_Sprint" -> "Спринт"
            "BT_Pursuit" -> "Гонка преследования"
            "BT_Individual" -> "Индивидуальная"
            "BT_Mass" -> "Масс-старт"
            "BT_MassStart" -> "Масс-старт"
            "BT_Mass60" -> "Масс-старт 60"
            "BT_Relay" -> "Эстафета"
            "BT_SingleMixedRelay" -> "Одиночная смешанная эстафета"
            "BT_SuperSprint" -> "Супер-спринт"
            "BT_MixedRelay" -> "Смешанная эстафета"
            "BT_SingleRelay" -> "Одиночная эстафета"
            "BT_Marathon" -> "Марафон"
            "BT_SuperPursuit" -> "Супер-пасьют"
            else -> discipline ?: ""
        }
    }
}