package ai.rever.boss.plugin.dynamic.fluck

import ai.rever.boss.plugin.browser.BrowserConfig
import ai.rever.boss.plugin.browser.BrowserHandle
import ai.rever.boss.plugin.browser.BrowserService
import ai.rever.boss.plugin.ui.BossTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.SmartToy
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

private const val CHATGPT_URL = "https://chatgpt.com"

@Composable
fun FluckContent(browserService: BrowserService?) {
    BossTheme {
        if (browserService == null || !browserService.isAvailable()) {
            NoBrowserMessage()
        } else {
            ChatGPTBrowser(browserService)
        }
    }
}

@Composable
private fun NoBrowserMessage() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.SmartToy,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colors.primary.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "ChatGPT",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Browser service not available",
                fontSize = 13.sp,
                color = MaterialTheme.colors.onBackground.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Please ensure JxBrowser is properly configured",
                fontSize = 11.sp,
                color = MaterialTheme.colors.onBackground.copy(alpha = 0.4f)
            )
        }
    }
}

@Composable
private fun ChatGPTBrowser(browserService: BrowserService) {
    val coroutineScope = rememberCoroutineScope()
    var browserHandle by remember { mutableStateOf<BrowserHandle?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var currentUrl by remember { mutableStateOf(CHATGPT_URL) }
    var currentTitle by remember { mutableStateOf("ChatGPT") }

    // Create browser on first composition
    LaunchedEffect(Unit) {
        val config = BrowserConfig(
            url = CHATGPT_URL,
            enableDevTools = false,
            enableDownloads = true,
            enableFullscreen = true
        )
        browserHandle = browserService.createBrowser(config)
        isLoading = false

        browserHandle?.let { handle ->
            handle.addNavigationListener { url ->
                currentUrl = url
            }
            handle.addTitleListener { title ->
                currentTitle = title.ifEmpty { "ChatGPT" }
            }
        }
    }

    // Dispose browser when composable leaves composition
    DisposableEffect(Unit) {
        onDispose {
            browserHandle?.dispose()
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Toolbar
            BrowserToolbar(
                title = currentTitle,
                url = currentUrl,
                canGoBack = browserHandle?.canGoBack() ?: false,
                canGoForward = browserHandle?.canGoForward() ?: false,
                onBack = { browserHandle?.goBack() },
                onForward = { browserHandle?.goForward() },
                onReload = { browserHandle?.reload() },
                onHome = {
                    coroutineScope.launch {
                        browserHandle?.loadUrl(CHATGPT_URL)
                    }
                }
            )

            Divider(color = MaterialTheme.colors.onBackground.copy(alpha = 0.1f))

            // Browser content
            Box(modifier = Modifier.fillMaxSize()) {
                if (isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else if (browserHandle != null && browserHandle!!.isValid) {
                    browserHandle!!.Content()
                } else {
                    BrowserErrorMessage()
                }
            }
        }
    }
}

@Composable
private fun BrowserToolbar(
    title: String,
    url: String,
    canGoBack: Boolean,
    canGoForward: Boolean,
    onBack: () -> Unit,
    onForward: () -> Unit,
    onReload: () -> Unit,
    onHome: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
            .background(MaterialTheme.colors.surface)
            .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Navigation buttons
        IconButton(
            onClick = onBack,
            modifier = Modifier.size(28.dp),
            enabled = canGoBack
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.size(16.dp),
                tint = if (canGoBack) MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                       else MaterialTheme.colors.onSurface.copy(alpha = 0.3f)
            )
        }

        IconButton(
            onClick = onForward,
            modifier = Modifier.size(28.dp),
            enabled = canGoForward
        ) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Forward",
                modifier = Modifier.size(16.dp),
                tint = if (canGoForward) MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                       else MaterialTheme.colors.onSurface.copy(alpha = 0.3f)
            )
        }

        IconButton(
            onClick = onReload,
            modifier = Modifier.size(28.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Reload",
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
            )
        }

        IconButton(
            onClick = onHome,
            modifier = Modifier.size(28.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Home",
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Title/URL display
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colors.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = url,
                fontSize = 9.sp,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun BrowserErrorMessage() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Error,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = Color(0xFFEF5350)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Failed to load browser",
                fontSize = 13.sp,
                color = MaterialTheme.colors.onBackground.copy(alpha = 0.6f)
            )
        }
    }
}
