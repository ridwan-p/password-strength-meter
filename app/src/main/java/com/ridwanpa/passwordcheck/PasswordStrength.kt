package com.ridwanpa.passwordcheck

enum class Strength {
    VERY_SECURE,
    SECURE,
    VERY_STRONG,
    STRONG,
    AVERAGE,
    WEAK,
    VERY_WEAK
}

class PasswordStrength(_password: String) {
    private val upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private val lowerCase = "abcdefghijklmnopqrstuvwxyz";
    private val number = "0123456789";
    private val characters = "!@#$%^&*?_~"

    var lowerCaseCount = 0
    var upperCaseCount = 0
    var numberCount = 0
    var characterCount = 0

    var password = _password
        set(value) {
            field = value
            setCount((value))
        }

    val score: Int
        get() = lengthScore() +
                lowerCaseScore() +
                upperCaseScore() +
                numberCaseScore() +
                characterCaseScore() +
                bonusCaseScore()

    val strength
        get() = if (score >= 90) Strength.VERY_SECURE
        else if (score >= 80) Strength.SECURE
        else if (score >= 70) Strength.VERY_STRONG
        else if (score >= 60) Strength.STRONG
        else if (score >= 50) Strength.AVERAGE
        else if (score >= 25) Strength.WEAK
        else Strength.VERY_WEAK

    override fun toString(): String {
        return when (strength) {
            Strength.VERY_SECURE -> "Very Secure"
            Strength.SECURE -> "Secure"
            Strength.VERY_STRONG -> "Very Strong"
            Strength.STRONG -> "Strong"
            Strength.AVERAGE -> "Average"
            Strength.WEAK -> "Weak"
            else -> "Very Weak"
        }
    }

    private fun lowerCaseScore(): Int {
        return if (lowerCaseCount > 0) 10 else 0
    }

    private fun upperCaseScore(): Int {

        return if (upperCaseCount > 0) 10 else 0
    }

    private fun numberCaseScore(): Int {
        return if (numberCount > 2) 20
        else if (numberCount > 0) 10
        else 0
    }

    private fun characterCaseScore(): Int {

        return if (characterCount > 1) 25
        else if (characterCount > 0) 10
        else 0
    }

    private fun bonusCaseScore(): Int {
        var score = 0
        val lowerUpperCount = lowerCaseCount + upperCaseCount
        if (numberCount != 0 && lowerUpperCount != 0) score += 2
        if (numberCount != 0 && lowerUpperCount != 0 && characterCount != 0) score += 3
        if (numberCount != 0 && lowerCaseCount != 0 && upperCaseCount != 0 && characterCount != 0) score += 5

        return score
    }


    private fun lengthScore(): Int {
        return if (password.length >= 8) 25
        else if (password.length >= 5) 10
        else if (password.isNotEmpty()) 5
        else 0
    }

    private fun countContain(password: String, characters: String): Int {
        var count = 0
        for (c in password) {
            if (characters.indexOf(c) > -1) count++
        }
        return count
    }

    private fun setCount(password: String) {
        lowerCaseCount = countContain(password, lowerCase)
        upperCaseCount = countContain(password, upperCase)
        characterCount = countContain(password, characters)
        numberCount = countContain(password, number)
    }
}