# BOSS ChatGPT

chatgpt.com in a right sidebar panel.

A small embedded-browser panel with its own navigation toolbar, so ChatGPT sits beside your
work instead of in a separate window. It is a browser embed and nothing more: there is no API
integration, no prompt handling, and no session management of its own. You sign in inside the
embedded page exactly as you would in a browser.

Note the naming: the repository and plugin id say `fluck`, the panel is titled **ChatGPT**, and
the built jar is `boss-plugin-fluck-*.jar`.

## What it does

- **Embeds chatgpt.com** through the host `BrowserService`, created on first composition and
  disposed when the panel leaves composition.
- **Navigation toolbar**: Back, Forward, Reload and Home. Back and Forward disable themselves
  when there is nowhere to go.
- **Live page title and URL** on a single line beside the controls.
- **Downloads and fullscreen** are enabled. Devtools are not.
- **Clear fallbacks**: a spinner while loading, "Failed to load browser" on an invalid handle,
  and an explanatory message when no `BrowserService` is available.

## Requirements

- BOSS >= 8.16.30, boss-plugin-api >= 1.0.20
- The host `BrowserService` with JxBrowser configured. Without it the panel explains that
  rather than rendering blank.
- Network egress to chatgpt.com.

This plugin contributes no MCP tools and declares no permissions.

## Build

```bash
./gradlew buildPluginJar
cp build/libs/boss-plugin-fluck-*.jar ~/.boss/plugins/
```

See [AGENTS.md](AGENTS.md) for architecture and conventions.

## License

Proprietary - Risa Labs Inc.
