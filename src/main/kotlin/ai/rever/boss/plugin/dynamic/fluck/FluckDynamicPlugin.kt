package ai.rever.boss.plugin.dynamic.fluck

import ai.rever.boss.plugin.api.DynamicPlugin
import ai.rever.boss.plugin.api.PluginContext

/**
 * ChatGPT dynamic plugin - Loaded from external JAR.
 *
 * ChatGPT integration for AI assistance
 */
class FluckDynamicPlugin : DynamicPlugin {
    override val pluginId: String = "ai.rever.boss.plugin.dynamic.fluck"
    override val displayName: String = "ChatGPT (Dynamic)"
    override val version: String = "1.0.0"
    override val description: String = "ChatGPT integration for AI assistance"
    override val author: String = "Risa Labs"
    override val url: String = "https://github.com/risa-labs-inc/boss-plugin-fluck"

    override fun register(context: PluginContext) {
        context.panelRegistry.registerPanel(FluckInfo) { ctx, panelInfo ->
            FluckComponent(ctx, panelInfo)
        }
    }
}
