package com.github.shmvanhouten.adventofcode2020.day04

private const val BIRTH_YEAR = "byr"
private const val ISSUE_YEAR = "iyr"
private const val EXPIRATION_YEAR = "eyr"
private const val HEIGHT = "hgt"
private const val HAIR_COLOR = "hcl"
private const val EYE_COLOR = "ecl"
private const val PASSPORT_ID = "pid"

class Passport(val data: Map<String, String>) {

    fun replace(key: String, value: String): Passport {
        val mutableData = data.toMutableMap()
        mutableData[key] = value
        return Passport(mutableData.toMap())
    }

    fun isValid(): Boolean {
        return isValidYear(BIRTH_YEAR, min = 1920, max = 2002)
                && isValidYear(ISSUE_YEAR, min = 2010, max = 2020)
                && isValidYear(EXPIRATION_YEAR, min = 2020, max = 2030)
                && isValidHeight(data[HEIGHT])
                && isValidHairColor(data[HAIR_COLOR])
                && isValidEyeColor(data[EYE_COLOR])
                && isValidPassportID(data[PASSPORT_ID])
    }

    private fun isValidPassportID(passportID: String?): Boolean {
        return passportID != null
                && passportID.length == 9
                && passportID.matches(Regex("[0-9]+"))
    }

    private fun isValidEyeColor(eyeColor: String?): Boolean {
        return setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
            .contains(eyeColor)
    }

    private fun isValidHairColor(hairColor: String?): Boolean {
        return hairColor != null
                && hairColor[0] == '#'
                && hairColor.length == 7
                && hairColor.takeLast(6).isHex()
    }

    private fun isValidHeight(height: String?): Boolean {
        return height != null
                && (isValidHeightInCentimeters(height) || isValidHeightInInches(height))
    }

    private fun isValidHeightInCentimeters(height: String): Boolean {
        val unit = height.dropWhile { it.isDigit() }
        val amount = height.takeWhile { it.isDigit() }.toInt()
        return unit == "cm"
                && amount >= 150
                && amount <= 193
    }

    private fun isValidHeightInInches(height: String): Boolean {
        val unit = height.dropWhile { it.isDigit() }
        val amount = height.takeWhile { it.isDigit() }.toInt()
        return unit == "in"
                && amount >= 59
                && amount <= 76
    }

    private fun isValidYear(year: String, min: Int, max: Int) =
        isValidYear(data[year]?.toInt(), min, max)

    private fun isValidYear(year: Int?, min: Int, max: Int): Boolean {
        return year != null
                && year >= min
                && year <= max
    }
}

private fun String.isHex(): Boolean {
    return this.matches(Regex("[0-9a-f]+"))
}
