package com.tubepilot.studio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

private data class KpiItem(
    val title: String,
    val value: String,
    val delta: String,
    val isPositive: Boolean,
    val accent: Color
)

private data class TopVideoItem(
    val title: String,
    val views: String,
    val duration: String,
    val badge: String
)

private data class AuditMetric(
    val label: String,
    val score: Int,
    val color: Color
)

private data class CompetitorBench(
    val title: String,
    val views: String,
    val retention: String,
    val takeaway: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val proDarkScheme = darkColorScheme(
                primary = Color(0xFF8B5CF6),
                onPrimary = Color.White,
                primaryContainer = Color(0xFF2E1A5E),
                onPrimaryContainer = Color(0xFFEDE9FE),
                secondary = Color(0xFF10B981),
                secondaryContainer = Color(0xFF112E26),
                onSecondaryContainer = Color(0xFFD1FAE5),
                background = Color(0xFF0B0B10),
                onBackground = Color(0xFFF4F4F6),
                surface = Color(0xFF13131C),
                onSurface = Color(0xFFF4F4F6),
                surfaceVariant = Color(0xFF1C1C29),
                onSurfaceVariant = Color(0xFFA1A1B5)
            )

            MaterialTheme(colorScheme = proDarkScheme) {
                TubePilotProApp()
            }
        }
    }
}

@Composable
fun TubePilotProApp() {
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(
                                    if (selectedTab == 0) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                        )
                    },
                    label = { Text("Analytics") }
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(
                                    if (selectedTab == 1) MaterialTheme.colorScheme.secondary
                                    else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                        )
                    },
                    label = { Text("Revenue Lab") }
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(
                                    if (selectedTab == 2) Color(0xFFF59E0B)
                                    else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                        )
                    },
                    label = { Text("AI Audit") }
                )
            }
        }
    ) { innerPadding ->
        when (selectedTab) {
            0 -> AnalyticsDashboardScreen(modifier = Modifier.padding(innerPadding))
            1 -> RevenueLabScreen(modifier = Modifier.padding(innerPadding))
            else -> AiVideoAuditScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}

@Composable
private fun AiVideoAuditScreen(modifier: Modifier = Modifier) {
    var topicInput by remember { mutableStateOf("Creator Growth & Reach Secrets") }
    var isShortsAudit by remember { mutableStateOf(true) }
    var auditRunCount by remember { mutableIntStateOf(1) }

    val cleanTopic = topicInput.trim().ifEmpty { "Creator Growth & Reach Secrets" }
    val baseSuccess = if (isShortsAudit) 74 else 81
    val dynamicBoost = ((auditRunCount - 1) * 4) % 12
    val successRate = (baseSuccess + dynamicBoost).coerceIn(55, 96)
    val hookScore = (82 + dynamicBoost).coerceIn(60, 98)
    val visualAudioScore = (88 + (dynamicBoost / 2)).coerceIn(65, 98)
    val valueScore = (79 + dynamicBoost).coerceIn(60, 96)
    val retentionScore = (if (isShortsAudit) 73 else 77) + dynamicBoost

    val isReadyToPublish = successRate >= 72
    val readinessLabel = if (isReadyToPublish) {
        "READY TO PUBLISH • Strong Reach Potential"
    } else {
        "NEEDS POLISH • Tighten Hook & Contrast First"
    }

    val metrics = listOf(
        AuditMetric("First 5s Hook & Attention", hookScore, Color(0xFF10B981)),
        AuditMetric("Visual & Audio Clarity", visualAudioScore, Color(0xFF38BDF8)),
        AuditMetric("Topic Value & Relevance", valueScore, Color(0xFF8B5CF6)),
        AuditMetric("Audience Retention Forecast", retentionScore.coerceIn(55, 96), Color(0xFFF59E0B))
    )

    val suggestedTitles = if (isShortsAudit) {
        listOf(
            "Watch Till End: $cleanTopic in 45s",
            "Stop Doing This in $cleanTopic!",
            "3 Fast Rules for $cleanTopic That Work"
        )
    } else {
        listOf(
            "$cleanTopic: Full Breakdown & Real Results",
            "Why Most Creators Fail at $cleanTopic (And How to Fix It)",
            "Complete $cleanTopic Playbook for Higher Views"
        )
    }

    val thumbnailIdeas = listOf(
        "Split frame: Before (dim/flat) vs. After (bright contrast + bold 2-word tag)",
        "Close-up expressive reaction + clear arrow pointing to key number/metric",
        "Dark clean background + high-contrast highlight box + minimal text"
    )

    val competitors = listOf(
        CompetitorBench(
            title = "Top Niche Benchmark #1",
            views = "240K views",
            retention = "76% avg view",
            takeaway = "Opens with a bold result in the first 2 seconds and cuts dead air."
        ),
        CompetitorBench(
            title = "Top Niche Benchmark #2",
            views = "118K views",
            retention = "68% avg view",
            takeaway = "Uses punchy on-screen captions and clear pacing shifts every 8–10s."
        )
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "AI Video Audit",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Readiness check, titles, thumbnails & before/after diff",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(999.dp))
                    .background(Color(0xFF281D45))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "AI SCAN #$auditRunCount",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFC4B5FD)
                )
            }
        }

        // Input & Scan Controls
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(
                        selected = isShortsAudit,
                        onClick = { isShortsAudit = true },
                        label = { Text("Shorts Format") }
                    )
                    FilterChip(
                        selected = !isShortsAudit,
                        onClick = { isShortsAudit = false },
                        label = { Text("Long-Form") }
                    )
                }

                OutlinedTextField(
                    value = topicInput,
                    onValueChange = { topicInput = it },
                    label = { Text("Video Topic or Working Title") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = { auditRunCount += 1 },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Run AI Video Inspection")
                }
            }
        }

        // Readiness & Success Score Card
        Card(
            colors = CardDefaults.cardColors(
                containerColor = if (isReadyToPublish) Color(0xFF102A22) else Color(0xFF2C1D11)
            ),
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = readinessLabel,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isReadyToPublish) Color(0xFF34D399) else Color(0xFFFBBF24)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text(
                            text = "Success Probability",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "$successRate%",
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "Intro Rating",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "$hookScore / 100",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF38BDF8)
                        )
                    }
                }
            }
        }

        // Detailed Breakdown Bars
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Diagnostic Breakdown",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
                metrics.forEach { metric ->
                    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = metric.label,
                                fontSize = 12.sp,
                                color = Color.White
                            )
                            Text(
                                text = "${metric.score}/100",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = metric.color
                            )
                        }
                        LinearProgressIndicator(
                            progress = { metric.score / 100f },
                            color = metric.color,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(999.dp))
                        )
                    }
                }
            }
        }

        // Before vs After Optimization + Exact Changes Explained
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Before vs. After Optimization",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // BEFORE box
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(14.dp))
                            .background(Color(0xFF23161B))
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "BEFORE (Raw Draft)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFF87171)
                        )
                        Text(
                            text = "Slow intro, flat lighting & contrast, generic title, no strong CTA.",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    // AFTER box
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(14.dp))
                            .background(Color(0xFF122820))
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "AFTER (AI Enhanced)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF34D399)
                        )
                        Text(
                            text = "Instant hook, punchy color/contrast, high-CTR title & clear next step.",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                HorizontalDivider()

                Text(
                    text = "What Changed & Why:",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFFC4B5FD)
                )
                Text(
                    text = "• Opening Hook: Moved the core payoff to 0:00–0:04 to cut early drop-off.",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "• Visual Polish: Boosted contrast, clarity, and focal framing for mobile feeds.",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "• Packaging & CTA: Paired a curiosity-driven title with a clear end prompt.",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Suggested Titles & Thumbnails
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Title & Thumbnail Suggestions",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
                suggestedTitles.forEachIndexed { idx, title ->
                    Text(
                        text = "${idx + 1}. $title",
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }
                HorizontalDivider()
                Text(
                    text = "Thumbnail Concepts:",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF38BDF8)
                )
                thumbnailIdeas.forEach { idea ->
                    Text(
                        text = "• $idea",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Competitor Comparison
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Competitor Benchmark Comparison",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
                competitors.forEach { comp ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = comp.title,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                            Text(
                                text = "${comp.views} • ${comp.retention}",
                                fontSize = 11.sp,
                                color = Color(0xFF34D399)
                            )
                        }
                        Text(
                            text = comp.takeaway,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AnalyticsDashboardScreen(modifier: Modifier = Modifier) {
    var selectedPeriod by remember { mutableIntStateOf(1) } // 0: 7D, 1: 28D, 2: 90D
    var selectedFocus by remember { mutableIntStateOf(0) } // 0: Overview, 1: Audience, 2: Top Content

    val periods = listOf("Last 7 Days", "Last 28 Days", "Last 90 Days")

    val kpis = when (selectedPeriod) {
        0 -> listOf(
            KpiItem("Views", "84.6K", "+18.4%", true, Color(0xFFEF4444)),
            KpiItem("Watch Time", "4.2K hrs", "+21.0%", true, Color(0xFF10B981)),
            KpiItem("New Subscribers", "1.1K", "+14.2%", true, Color(0xFFF59E0B)),
            KpiItem("Engagement", "6.8%", "-0.6%", false, Color(0xFF38BDF8))
        )
        2 -> listOf(
            KpiItem("Views", "979.2K", "+31.0%", true, Color(0xFFEF4444)),
            KpiItem("Watch Time", "45.4K hrs", "+37.0%", true, Color(0xFF10B981)),
            KpiItem("New Subscribers", "12.3K", "+29.0%", true, Color(0xFFF59E0B)),
            KpiItem("Engagement", "7.4%", "+3.2%", true, Color(0xFF38BDF8))
        )
        else -> listOf(
            KpiItem("Views", "322.4K", "+41.0%", true, Color(0xFFEF4444)),
            KpiItem("Watch Time", "15.5K hrs", "+51.0%", true, Color(0xFF10B981)),
            KpiItem("New Subscribers", "4.1K", "+48.0%", true, Color(0xFFF59E0B)),
            KpiItem("Engagement", "6.3%", "-2.1%", false, Color(0xFF38BDF8))
        )
    }

    val trendPoints = when (selectedPeriod) {
        0 -> listOf(0.28f, 0.35f, 0.31f, 0.52f, 0.48f, 0.76f, 0.88f)
        2 -> listOf(0.22f, 0.34f, 0.41f, 0.38f, 0.65f, 0.72f, 0.68f, 0.92f, 0.85f, 0.96f)
        else -> listOf(0.25f, 0.22f, 0.31f, 0.29f, 0.78f, 0.36f, 0.35f, 0.42f, 0.89f, 0.38f)
    }

    val hourlyActivity = listOf(0.22f, 0.30f, 0.45f, 0.58f, 0.74f, 0.96f, 0.81f, 0.52f)
    val hourLabels = listOf("12a", "3a", "6a", "9a", "12p", "3p", "9p", "11p")

    val topVideos = listOf(
        TopVideoItem("How to Build a Creator Brand in 2026", "184.0K views", "5:42", "Viral"),
        TopVideoItem("5 Mistakes Hurting Your Reach Fast", "92.0K views", "4:28", "High CTR"),
        TopVideoItem("Full Shorts Retention Breakdown", "64.5K views", "0:54", "Steady")
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "TubePilot Studio",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Creator Analytics • ${periods[selectedPeriod]}",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(999.dp))
                    .background(Color(0xFF3A2A0A))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "PRO",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFBBF24)
                )
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            periods.forEachIndexed { index, label ->
                FilterChip(
                    selected = selectedPeriod == index,
                    onClick = { selectedPeriod = index },
                    label = { Text(label, fontSize = 12.sp) }
                )
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Overview", "Audience", "Top Content").forEachIndexed { index, tabLabel ->
                FilterChip(
                    selected = selectedFocus == index,
                    onClick = { selectedFocus = index },
                    label = { Text(tabLabel, fontSize = 12.sp) }
                )
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                KpiCard(item = kpis[0], modifier = Modifier.weight(1f))
                KpiCard(item = kpis[1], modifier = Modifier.weight(1f))
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                KpiCard(item = kpis[2], modifier = Modifier.weight(1f))
                KpiCard(item = kpis[3], modifier = Modifier.weight(1f))
            }
        }

        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (selectedFocus == 1) "Audience Growth Curve" else "Views Momentum Curve",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    Text(
                        text = kpis[0].delta,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF34D399)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                LineTrendChart(
                    points = trendPoints,
                    lineColor = if (selectedFocus == 1) Color(0xFF8B5CF6) else Color(0xFFEF4444),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(145.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Start", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("Mid-Period Spike", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("Latest", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }

        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "When Is Your Audience Active?",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
                Text(
                    text = "Peak publishing window: Evening prime slot (9:00 PM)",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(14.dp))
                ActivityBarsChart(
                    values = hourlyActivity,
                    labels = hourLabels,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                )
            }
        }

        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Top Performing Content",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
                topVideos.forEach { video ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = video.title,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = video.views,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0xFF281D45))
                                        .padding(horizontal = 8.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = video.badge,
                                        fontSize = 11.sp,
                                        color = Color(0xFFC4B5FD)
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFF111118))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = video.duration,
                                fontSize = 11.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun KpiCard(item: KpiItem, modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(18.dp),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.delta,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (item.isPositive) Color(0xFF34D399) else Color(0xFFF87171)
                )
                Box(
                    modifier = Modifier
                        .size(26.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(item.accent.copy(alpha = 0.16f)),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(item.accent)
                    )
                }
            }
            Text(
                text = item.value,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = item.title,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun LineTrendChart(
    points: List<Float>,
    lineColor: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        if (points.size < 2) return@Canvas
        val padX = 12f
        val padY = 16f
        val usableW = size.width - padX * 2
        val usableH = size.height - padY * 2
        val stepX = usableW / (points.size - 1)

        listOf(0.25f, 0.5f, 0.75f).forEach { ratio ->
            val y = padY + usableH * ratio
            drawLine(
                color = Color.White.copy(alpha = 0.08f),
                start = Offset(padX, y),
                end = Offset(size.width - padX, y),
                strokeWidth = 1.5f
            )
        }

        val coords = points.mapIndexed { index, value ->
            val clamped = value.coerceIn(0.05f, 1.0f)
            Offset(
                x = padX + index * stepX,
                y = padY + usableH * (1f - clamped)
            )
        }

        val path = Path().apply {
            moveTo(coords.first().x, coords.first().y)
            for (i in 1 until coords.size) {
                val prev = coords[i - 1]
                val curr = coords[i]
                val midX = (prev.x + curr.x) / 2f
                cubicTo(midX, prev.y, midX, curr.y, curr.x, curr.y)
            }
        }

        drawPath(
            path = path,
            color = lineColor,
            style = Stroke(width = 5f, cap = StrokeCap.Round)
        )

        coords.forEachIndexed { idx, pt ->
            val isPeak = idx == coords.indices.maxByOrNull { points[it] }
            drawCircle(
                color = if (isPeak) Color.White else lineColor,
                radius = if (isPeak) 7f else 4.5f,
                center = pt
            )
        }
    }
}

@Composable
private fun ActivityBarsChart(
    values: List<Float>,
    labels: List<String>,
    modifier: Modifier = Modifier
) {
    val peakIndex = values.indices.maxByOrNull { values[it] } ?: 0
    Column(modifier = modifier) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            val count = values.size
            if (count == 0) return@Canvas
            val slotW = size.width / count
            val barW = (slotW * 0.48f).coerceAtMost(46f)

            values.forEachIndexed { i, v ->
                val clamped = v.coerceIn(0.1f, 1f)
                val barH = size.height * clamped
                val left = i * slotW + (slotW - barW) / 2f
                val top = size.height - barH

                drawRoundRect(
                    color = if (i == peakIndex) Color(0xFF2DD4BF) else Color(0xFF27273A),
                    topLeft = Offset(left, top),
                    size = Size(barW, barH),
                    cornerRadius = CornerRadius(10f, 10f)
                )
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            labels.forEachIndexed { idx, lbl ->
                Text(
                    text = lbl,
                    fontSize = 11.sp,
                    fontWeight = if (idx == peakIndex) FontWeight.Bold else FontWeight.Normal,
                    color = if (idx == peakIndex) Color(0xFF2DD4BF) else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun RevenueLabScreen(modifier: Modifier = Modifier) {
    var viewsInput by remember { mutableStateOf("120000") }
    var rpmInput by remember { mutableStateOf("2.80") }
    var videosPerMonthInput by remember { mutableStateOf("12") }
    var isShortsMode by remember { mutableStateOf(false) }

    val viewsPerVideo = viewsInput.toDoubleOrNull()?.coerceAtLeast(0.0) ?: 0.0
    val baseRpm = rpmInput.toDoubleOrNull()?.coerceAtLeast(0.0) ?: 0.0
    val videosPerMonth = videosPerMonthInput.toIntOrNull()?.coerceAtLeast(1) ?: 1

    val effectiveRpm = if (isShortsMode) baseRpm * 0.15 else baseRpm
    val revenuePerVideo = (viewsPerVideo / 1000.0) * effectiveRpm
    val monthlyRevenue = revenuePerVideo * videosPerMonth
    val dailyAverageRevenue = monthlyRevenue / 30.0
    val conservativeRev = monthlyRevenue * 0.65
    val viralRev = monthlyRevenue * 3.0

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Revenue & Growth Lab",
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = "Forecast monthly payouts, scenarios, and RPM impact",
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            FilterChip(
                selected = !isShortsMode,
                onClick = { isShortsMode = false },
                label = { Text("Long Videos") }
            )
            FilterChip(
                selected = isShortsMode,
                onClick = { isShortsMode = true },
                label = { Text("Shorts Mode") }
            )
        }

        OutlinedTextField(
            value = viewsInput,
            onValueChange = { if (it.all { ch -> ch.isDigit() }) viewsInput = it },
            label = { Text("Avg. Views per Video") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = rpmInput,
                onValueChange = {
                    if (it.count { ch -> ch == '.' } <= 1 && it.all { ch -> ch.isDigit() || ch == '.' }) {
                        rpmInput = it
                    }
                },
                label = { Text("Base RPM ($)") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = videosPerMonthInput,
                onValueChange = { if (it.all { ch -> ch.isDigit() }) videosPerMonthInput = it },
                label = { Text("Videos / Mo") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("1.00", "2.50", "4.50", "8.00").forEach { preset ->
                OutlinedButton(
                    onClick = { rpmInput = preset },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("$$preset", fontSize = 12.sp)
                }
            }
        }

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Projected Monthly Revenue",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = "$${String.format(Locale.US, "%.2f", monthlyRevenue)}",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                HorizontalDivider()
                Text(
                    text = "Per Video: $${String.format(Locale.US, "%.2f", revenuePerVideo)}  |  Daily Avg: $${String.format(Locale.US, "%.2f", dailyAverageRevenue)}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = "Effective RPM: $${String.format(Locale.US, "%.2f", effectiveRpm)} / 1K views",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Scenario Breakdown",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
                Text(
                    text = "Conservative (-35%): $${String.format(Locale.US, "%.2f", conservativeRev)}",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Base Forecast: $${String.format(Locale.US, "%.2f", monthlyRevenue)}",
                    fontSize = 13.sp,
                    color = Color.White
                )
                Text(
                    text = "Breakout / Viral (3x): $${String.format(Locale.US, "%.2f", viralRev)}",
                    fontSize = 13.sp,
                    color = Color(0xFF34D399)
                )
            }
        }

        Button(
            onClick = {
                viewsInput = "120000"
                rpmInput = "2.80"
                videosPerMonthInput = "12"
                isShortsMode = false
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Reset Forecast")
        }
    }
}
