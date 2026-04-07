package org.jason.testapp.android.stella

class StellaNative constructor() {

    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }

    external fun initialize()

    external fun destroy()

}