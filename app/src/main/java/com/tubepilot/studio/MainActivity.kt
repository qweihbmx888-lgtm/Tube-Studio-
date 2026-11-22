package com.tubepilot.studio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val darkScheme = darkColorScheme(
                primary = Color(0xFF7C3AED),
                background = Color(0xFF0A0A12),
                surface = Color(0xFF141424),
                surfaceVariant = Color(0xFF1E1E36),
                onSurface = Color.White,
                onSurfaceVariant = Color(0xFFA5A6C4)
            )
            MaterialTheme(colorScheme = darkScheme) {
                TubePilotAuditApp()
            }
        }
    }
}

@Composable
fun TubePilotAuditApp() {
    var tab by remember { mutableIntStateOf(0) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                NavigationBarItem(
                    selected = tab == 0,
                    onClick = { tab = 0 },
                    icon = { Box(Modifier.size(8.dp).clip(CircleShape).background(Color(0xFF8B5CF6))) },
                    label = { Text("AI Audit") }
                )
                NavigationBarItem(
                    selected = tab == 1,
                    onClick = { tab = 1 },
                    icon = { Box(Modifier.size(8.dp).clip(CircleShape).background(Color(0xFF10B981))) },
                    label = { Text("Titles & Thumbs") }
                )
                NavigationBarItem(
                    selected = tab == 2,
                    onClick = { tab = 2 },
                    icon = { Box(Modifier.size(8.dp).clip(CircleShape).background(Color(0xFF38BDF8))) },
                    label = { Text("Competitors") }
                )
            }
        }
    ) { padding ->
        when (tab) {
            0 -> VideoAuditMainScreen(Modifier.padding(padding))
            1 -> TitlesAndThumbnailsScreen(Modifier.padding(padding))
            else -> CompetitorsScreen(Modifier.padding(padding))
        }
    }
}

@Composable
private fun VideoAuditMainScreen(modifier: Modifier = Modifier) {
    var videoTitle by remember { mutableStateOf("Sunset Story • 45s") }
    var analyzed by remember { mutableStateOf(true) }
    var boost by remember { mutableIntStateOf(0) }

    val introScore = (82 + boost).coerceAtMost(98)
    val successRate = (68 + boost).coerceAtMost(96)
    val hookScore = (85 + boost).coerceAtMost(99)
    val qualityScore = (90 + (boost / 2)).coerceAtMost(99)
    val valueScore = (78 + boost).coerceAtMost(96)
    val retentionScore = (70 + boost).coerceAtMost(95)

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "TubePilot Studio • AI Video Audit",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        // بطاقة الفيديو المرفوع + زر التحليل
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(175.dp)
                        .clip(RoundedCornerShape(12.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.video_before),
                        contentDescription = "Uploaded Video",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Text(
                        text = "00:00 / 00:45",
                        fontSize = 11.sp,
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(8.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color.Black.copy(alpha = 0.65f))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                OutlinedTextField(
                    value = videoTitle,
                    onValueChange = { videoTitle = it },
                    label = { Text("Video Topic / Title") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        analyzed = true
                        boost = (boost + 4) % 12
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Start AI Video Audit")
                }

                // حالة جاهزية النشر
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0E2A1E)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = if (successRate >= 72) "READY TO PUBLISH" else "NEEDS MINOR TWEAKS",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF34D399)
                        )
                        Text(
                            text = "Strong chance of high reach with quick intro & contrast polish.",
                            fontSize = 12.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }

        if (analyzed) {
            // نتيجة التحليل الشاملة (عداد دائري + نسبة النجاح + المقدمة)
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Overall Audit Result",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ScoreCircle(score = introScore, label = "Intro", modifier = Modifier.size(96.dp))

                        Column(
                            modifier = Modifier.weight(1f).padding(start = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color(0xFF102820))
                                    .padding(10.dp)
                            ) {
                                Column {
                                    Text("Success Chance", fontSize = 11.sp, color = Color(0xFF6EE7B7))
                                    Text("$successRate%", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color(0xFF152238))
                                    .padding(10.dp)
                            ) {
                                Column {
                                    Text("Strong Hook?", fontSize = 11.sp, color = Color(0xFF7DD3FC))
                                    Text("YES • Engaging", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                }
                            }
                        }
                    }

                    HorizontalDivider()

                    ScoreBar("First 5s Attention", hookScore, Color(0xFF10B981))
                    ScoreBar("Video & Audio Quality", qualityScore, Color(0xFF38BDF8))
                    ScoreBar("Content Value", valueScore, Color(0xFF8B5CF6))
                    ScoreBar("Viewer Retention", retentionScore, Color(0xFFF59E0B))
                }
            }

            // قبل التحسين وبعده + شرح ما تغير
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF221542)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Before vs. After Enhancement",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.video_before),
                                contentDescription = "Before",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(105.dp)
                                    .clip(RoundedCornerShape(10.dp))
                            )
                            Text("BEFORE", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFF87171))
                            Text("Flat colors, slow start & plain title.", fontSize = 11.sp, color = Color.White)
                        }

                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.video_after),
                                contentDescription = "After",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(105.dp)
                                    .clip(RoundedCornerShape(10.dp))
                            )
                            Text("AFTER", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF34D399))
                            Text("Sharper contrast, fast hook & strong CTA.", fontSize = 11.sp, color = Color.White)
                        }
                    }

                    HorizontalDivider()

                    Text("What Changed:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFFC4B5FD))
                    Text("• Added an instant hook in the first 3–5 seconds.", fontSize = 12.sp, color = Color.White)
                    Text("• Boosted lighting, color contrast, and visual clarity.", fontSize = 12.sp, color = Color.White)
                    Text("• Improved title curiosity and added a clear end CTA.", fontSize = 12.sp, color = Color.White)
                }
            }
        }
    }
}

@Composable
private fun TitlesAndThumbnailsScreen(modifier: Modifier = Modifier) {
    var selectedStyle by remember { mutableIntStateOf(0) }
    val styles = listOf("High CTR", "Curiosity", "List / How-To")

    val titles = when (selectedStyle) {
        1 -> listOf(
            "Why This 45s Scene Hooks Everyone Immediately",
            "Nobody Notices This Detail Until the End",
            "What Happens When You Fix Your Opening Hook"
        )
        2 -> listOf(
            "3 Fast Fixes to Boost Retention Before Posting",
            "How to Make Any Short Look Clean & Crisp",
            "5 Quick Checks Before You Hit Publish"
        )
        else -> listOf(
            "Watch Till the End: Best Visual & Hook Upgrade",
            "Stop Posting Raw Clips — Do This First",
            "Instant Upgrade for Higher Reach & Views"
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Titles & Thumbnail Ideas", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            styles.forEachIndexed { idx, label ->
                FilterChip(
                    selected = selectedStyle == idx,
                    onClick = { selectedStyle = idx },
                    label = { Text(label, fontSize = 12.sp) }
                )
            }
        }

        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Suggested Titles", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.White)
                titles.forEachIndexed { i, t ->
                    Text("${i + 1}. $t", fontSize = 13.sp, color = Color(0xFFE5E7EB))
                }
            }
        }

        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Thumbnail Concepts", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF38BDF8))
                Image(
                    painter = painterResource(id = R.drawable.video_after),
                    contentDescription = "Thumbnail Sample",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(145.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
                Text("• Bright warm focal point + clean 2–3 word overlay.", fontSize = 12.sp, color = Color.White)
                Text("• High contrast between subject and background.", fontSize = 12.sp, color = Color(0xFFA5A6C4))
            }
        }
    }
}

@Composable
private fun CompetitorsScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Competitor Benchmarks", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)

        listOf(
            Triple("Top Niche Video #1", "210K views • 75% retention", "Starts with an immediate visual payoff and bold on-screen text."),
            Triple("Top Niche Video #2", "98K views • 69% retention", "Keeps pacing tight with zero dead silence in the first 5s."),
            Triple("Top Niche Video #3", "64K views • 66% retention", "Ends with a clear prompt that drives comments and shares.")
        ).forEach { (name, stats, tip) ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Text(stats, fontSize = 11.sp, color = Color(0xFF34D399))
                    }
                    Text(tip, fontSize = 12.sp, color = Color(0xFFA5A6C4))
                }
            }
        }
    }
}

@Composable
private fun ScoreCircle(score: Int, label: String, modifier: Modifier = Modifier) {
    val sweep = (score.coerceIn(0, 100) / 100f) * 360f
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(Modifier.fillMaxSize()) {
            val w = 9.dp.toPx()
            val s = Size(size.width - w, size.height - w)
            drawArc(Color.White.copy(alpha = 0.12f), 0f, 360f, false, Offset(w / 2f, w / 2f), s, style = Stroke(w, cap = StrokeCap.Round))
            drawArc(Color(0xFF10B981), -90f, sweep, false, Offset(w / 2f, w / 2f), s, style = Stroke(w, cap = StrokeCap.Round))
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("$score/100", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Text(label, fontSize = 11.sp, color = Color(0xFF34D399))
        }
    }
}

@Composable
private fun ScoreBar(title: String, score: Int, color: Color) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(title, fontSize = 12.sp, color = Color.White)
            Text("$score/100", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = color)
        }
        LinearProgressIndicator(
            progress = { score / 100f },
            color = color,
            trackColor = Color(0xFF23233A),
            modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape)
        )
    }
}
