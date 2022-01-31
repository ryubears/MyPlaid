package com.yeryu.myplaid.api

// IMPORTANT: This is for demo purposes only.
// In actual production, you should never put credentials in plain file.
class ApiCredentials {
    companion object {
        @JvmStatic
        var plaidClientId: String = ""
        @JvmStatic
        val plaidClientSecret: String = ""
        @JvmStatic
        val polygonApiKey: String = ""
    }
}