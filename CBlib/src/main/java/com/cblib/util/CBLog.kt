package com.cblib.util

import android.util.Log
import com.cblib.BuildConfig

class CBLog {
    companion object {
        private const val TAG = "###CBFramework##"
        private val DEBUG = BuildConfig.BUILD_TYPE.equals("debug", true)

        @JvmStatic
        fun i(msg: String): String {
            if (DEBUG) {
                if (msg.length > 4000) {
                    val chunkCount = msg.length / 4000 // integer division
                    for (i in 0..chunkCount) {
                        val max = 4000 * (i + 1)
                        if (max >= msg.length) {
                            Log.i(TAG, msg.substring(4000 * i))
                        } else {
                            Log.i(TAG, msg.substring(4000 * i, max))
                        }
                    }
                } else {
                    Log.i(TAG, msg)
                }
            }

            return msg
        }

        @JvmStatic
        fun d(msg: String): String {
            if (DEBUG) {
                if (msg.length > 4000) {
                    val chunkCount = msg.length / 4000 // integer division
                    for (i in 0..chunkCount) {
                        val max = 4000 * (i + 1)
                        if (max >= msg.length) {
                            Log.d(TAG, msg.substring(4000 * i))
                        } else {
                            Log.d(TAG, msg.substring(4000 * i, max))
                        }
                    }
                } else {
                    Log.i(TAG, msg)
                }
            }

            return msg
        }

        @JvmStatic
        fun d(tag: String, msg: String): String {
            if (DEBUG) {
                if (msg.length > 4000) {
                    val chunkCount = msg.length / 4000 // integer division
                    for (i in 0..chunkCount) {
                        val max = 4000 * (i + 1)
                        if (max >= msg.length) {
                            Log.d(tag, msg.substring(4000 * i))
                        } else {
                            Log.d(tag, msg.substring(4000 * i, max))
                        }
                    }
                } else {
                    Log.i(tag, msg)
                }
            }

            return msg
        }

        @JvmStatic
        fun iS(msgSend: String): String {
            if (DEBUG) {
                if (msgSend.length > 4000) {
                    val chunkCount = msgSend.length / 4000 // integer division
                    for (i in 0..chunkCount) {
                        val max = 4000 * (i + 1)
                        if (max >= msgSend.length) {
                            Log.i(TAG, " --> " + msgSend.substring(4000 * i))
                        } else {
                            Log.i(TAG, " --> " + msgSend.substring(4000 * i, max))
                        }
                    }
                } else {
                    Log.i(TAG, " --> $msgSend")
                }
            }
            return msgSend
        }

        @JvmStatic
        fun iR(msgReceived: String): String {
            if (DEBUG) {
                if (msgReceived.length > 4000) {
                    val chunkCount = msgReceived.length / 4000 // integer
                    // division
                    for (i in 0..chunkCount) {
                        val max = 4000 * (i + 1)
                        if (max >= msgReceived.length) {
                            Log.i(TAG, " <--" + i + " " + msgReceived.substring(4000 * i))
                        } else {
                            Log.i(TAG, " <--" + i + " " + msgReceived.substring(4000 * i, max))
                        }
                    }
                } else {
                    Log.i(TAG, " <-- $msgReceived")
                }
            }

            return msgReceived
        }

        @JvmStatic
        fun i(tag: String, msg: String): String {
            if (DEBUG) {
                if (msg.length > 4000) {
                    val chunkCount = msg.length / 4000 // integer division
                    for (i in 0..chunkCount) {
                        val max = 4000 * (i + 1)
                        if (max >= msg.length) {
                            Log.i(tag, msg.substring(4000 * i))
                        } else {
                            Log.i(tag, msg.substring(4000 * i, max))
                        }
                    }
                } else {
                    Log.i(tag, msg)
                }
            }
            return msg
        }

        @JvmStatic
        fun e(msg: String): String {
            if (DEBUG) {
                if (msg.length > 4000) {
                    val chunkCount = msg.length / 4000 // integer division
                    for (i in 0..chunkCount) {
                        val max = 4000 * (i + 1)
                        if (max >= msg.length) {
                            Log.e(TAG, msg.substring(4000 * i))
                        } else {
                            Log.e(TAG, msg.substring(4000 * i, max))
                        }
                    }
                } else {
                    Log.e(TAG, msg)
                }
            }
            return msg
        }
    }
}