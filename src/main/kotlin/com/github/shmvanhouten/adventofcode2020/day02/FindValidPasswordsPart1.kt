package com.github.shmvanhouten.adventofcode2020.day02


fun countValidPasswords(passwords: List<String>): Int {
    return countValidPolicyToPasswords(passwords.map { toPolicyAndPassword(it) })
}

private fun countValidPolicyToPasswords(policyToPasswords: List<PolicyToPassword>): Int {
    return policyToPasswords.count { isValid(it) }
}

private fun isValid(policyToPassword: PolicyToPassword): Boolean {
    return isValid(policyToPassword.policy, policyToPassword.password)
}

private fun isValid(policy: Policy, password: Password): Boolean {
    val charCount = password.count { it == policy.char }
    return charCount >= policy.min
            && charCount <= policy.max
}


