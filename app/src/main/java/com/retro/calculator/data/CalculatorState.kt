package com.retro.calculator.data

data class CalculatorState(
    val step: CalculatorStep = CalculatorStep.QUANTITY,
    val quantity: Double = 0.0,
    val unit: UnitType? = null,
    val totalPrice: Double = 0.0,
    val unitPrice: Double = 0.0,
    val robotMessage: String = "",
    val showConfirmAnimation: Boolean = false
)

enum class CalculatorStep {
    QUANTITY,
    UNIT,
    PRICE,
    RESULT
}

enum class UnitType(val displayName: String) {
    KG("kg"),
    GRAM("gram"),
    QUINTAL("quintal"),
    TON("ton"),
    LITER("liter"),
    ML("ml"),
    GALLON("gallon"),
    PIECE("piece"),
    DOZEN("dozen"),
    METER("meter"),
    CM("cm"),
    INCH("inch"),
    FOOT("foot")
}