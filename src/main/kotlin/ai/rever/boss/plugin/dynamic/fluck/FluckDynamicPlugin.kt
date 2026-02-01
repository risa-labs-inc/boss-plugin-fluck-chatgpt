package ai.rever.boss.plugin.dynamic.fluck

import ai.rever.boss.plugin.api.DynamicPlugin
import ai.rever.boss.plugin.api.PluginContext
import ai.rever.boss.plugin.browser.BrowserService

/**
 * ChatGPT dynamic plugin - Loaded from external JAR.
 *
 * Provides embedded ChatGPT browser panel using BrowserService from PluginContext.
 */
class FluckDynamicPlugin : DynamicPlugin {
    override val pluginId: String = "ai.rever.boss.plugin.dynamic.fluck"
    override val displayName: String = "ChatGPT (Dynamic)"
    override val version: String = "1.0.2"
    override val description: String = "ChatGPT integration for AI assistance"
    override val author: String = "Risa Labs"
    override val url: String = "https://github.com/risa-labs-inc/boss-plugin-fluck"

    private var browserService: BrowserService? = null

    override fun register(context: PluginContext) {
        browserService = context.browserService

        context.panelRegistry.registerPanel(FluckInfo) { ctx, panelInfo ->
            FluckComponent(ctx, panelInfo, browserService)
        }
    }
}
