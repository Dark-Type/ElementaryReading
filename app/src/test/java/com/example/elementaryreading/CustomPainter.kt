package com.example.elementaryreading

open class CustomPainter {
    open fun getStack(): List<Widget>? {
        val widgetsOnStack: List<Widget> = List()
        widgetsOnStack.add(getSlice())
        widgetsOnStack.add(getGestureDetector())
        return widgetsOnStack
    }
}
