package ai.rever.boss.plugin.dynamic.fluck

import ai.rever.boss.plugin.api.Panel.Companion.right
import ai.rever.boss.plugin.api.Panel.Companion.top
import ai.rever.boss.plugin.api.PanelId
import ai.rever.boss.plugin.api.PanelInfo
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SmartToy

object FluckInfo : PanelInfo {
    override val id = PanelId("fluck", 15)
    override val displayName = "ChatGPT"
    override val icon = Icons.Outlined.SmartToy
    override val defaultSlotPosition = right.top.top
}
