package com.owasp.top.mobile.demo.utils

import android.content.Context
import com.owasp.top.mobile.demo.R

class HelperPassword(private val context: Context) {

    fun validatorMinSize(value: String): ResultValidator {
        if (value.length < 8) {
            return ResultValidator(
                false,
                context.getString(R.string.policy_min_size)
            )
        }
        return ResultValidator(true)
    }

    fun validatorMaxSize(value: String): ResultValidator {
        if (value.length > 30) {
            return ResultValidator(
                false,
                context.getString(R.string.policy_max_size)
            )
        }
        return ResultValidator(true)
    }

    fun validatorHasUppercase(value: String): ResultValidator {
        if (!value.any { it.isUpperCase() }){
            return ResultValidator(
                false,
                context.getString(R.string.policy_has_uppercase)
            )
        }
        return ResultValidator(true)
    }

    fun validatorHasLowercase(value: String): ResultValidator {
        if (!value.any { it.isLowerCase() }){
            return ResultValidator(
                false,
                context.getString(R.string.policy_has_lowercase)
            )
        }
        return ResultValidator(true)
    }

    fun validatorHasDigit(value: String): ResultValidator {
        if (!value.any { it.isDigit() }){
            return ResultValidator(
                false,
                context.getString(R.string.policy_has_digits)
            )
        }
        return ResultValidator(true)
    }

    fun validatorHasSymbol(value: String): ResultValidator {
        val symbols = context.getString(R.string.symbols_valids).split(",")

        if(!value.any { symbols.contains(it.toString()) }) {
            return ResultValidator(
                false,
                context.getString(R.string.policy_has_symbol)
            )
        }

        return ResultValidator(true)
    }

    fun validatorNotSpace(value: String): ResultValidator {
        if (value.any { it.isWhitespace() }){
            return ResultValidator(
                false,
                context.getString(R.string.policy_not_space)
            )
        }
        return ResultValidator(true)
    }

    fun validatorPassConf(value: String, value2: String): ResultValidator {
        if (value != value2){
            return ResultValidator(false, "La contraseña no coincide")
        }
        return ResultValidator(true)
    }
}

data class ResultValidator(
    val isValid: Boolean,
    val message: String = ""
)