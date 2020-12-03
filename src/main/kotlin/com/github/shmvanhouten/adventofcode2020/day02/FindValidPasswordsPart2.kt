package com.github.shmvanhouten.adventofcode2020.day02

fun countValidPasswords2(passwords: List<String>): Int {
    return countValidPasswords(passwords.map { toPolicyAndPassword(it) })
}

fun countValidPasswords(passwords: List<PolicyToPassword>): Int {
    return passwords.count { isValid(it) }
}

private fun isValid(policyToPassword: PolicyToPassword): Boolean {
    return isValid(policyToPassword.policy, policyToPassword.password)
}

private fun isValid(
    policy: Policy,
    password: Password
) =
    nand(password[policy.min - 1] == policy.char,
            password[policy.max - 1] == policy.char)

fun nand(a: Boolean, b: Boolean): Boolean {
    return !(a && b) && (a || b)
}
