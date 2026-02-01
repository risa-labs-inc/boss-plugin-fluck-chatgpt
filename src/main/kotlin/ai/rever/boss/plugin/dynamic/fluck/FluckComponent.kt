package ai.rever.boss.plugin.dynamic.fluck

import ai.rever.boss.plugin.api.PanelComponentWithUI
import ai.rever.boss.plugin.api.PanelInfo
import ai.rever.boss.plugin.browser.BrowserService
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext

/**
 * ChatGPT panel component (Dynamic Plugin)
 *
 * Embeds ChatGPT browser using BrowserService from PluginContext.
 */
class FluckComponent(
    ctx: ComponentContext,
    override val panelInfo: PanelInfo,
    private val browserService: BrowserService?
) : PanelComponentWithUI, ComponentContext by ctx {

    @Composable
    override fun Content() {
        FluckContent(browserService)
    }
}
