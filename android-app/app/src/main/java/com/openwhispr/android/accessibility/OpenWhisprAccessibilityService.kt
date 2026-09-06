package com.openwhispr.android.accessibility

import android.accessibilityservice.AccessibilityService
import android.os.Bundle
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

/**
 * Accessibility service to inject text into apps that don't support InputConnection.
 */
class OpenWhisprAccessibilityService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Handle events if needed
    }

    override fun onInterrupt() {
        // Handle interrupt
    }

    /**
     * Injects text into the currently focused text field.
     * @param text The text to inject.
     */
    fun injectText(text: String) {
        val rootNode = rootInActiveWindow ?: return
        val focusedNode = findFocusedNode(rootNode)
        
        focusedNode?.let { node ->
            if (node.isEditable) {
                val arguments = Bundle()
                arguments.putCharSequence(
                    AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
                    text
                )
                node.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments)
            }
            node.recycle()
        }
        rootNode.recycle()
    }

    private fun findFocusedNode(nodeInfo: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        if (nodeInfo.isFocused) {
            return nodeInfo
        }
        for (i in 0 until nodeInfo.childCount) {
            val childNode = nodeInfo.getChild(i)
            if (childNode != null) {
                val focusedChild = findFocusedNode(childNode)
                if (focusedChild != null) {
                    return focusedChild
                }
                childNode.recycle()
            }
        }
        return null
    }
}
