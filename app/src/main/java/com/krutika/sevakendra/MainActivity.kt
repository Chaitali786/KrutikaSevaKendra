package com.krutika.sevakendra

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ---------- Shop details (edit here) ----------
private const val OWNER_MR = "पवन पगारे"
private const val OWNER_EN = "Pavan Pagare"
private const val WHATSAPP_NUMBER = "7028032929"   // WhatsApp + call
private const val SECOND_NUMBER = "7774981051"

private val Crimson = Color(0xFFB5123B)
private val Cream = Color(0xFFFFF8F3)

data class Service(val emoji: String, val mr: String, val en: String)

private val services = listOf(
    Service("🏦", "बँक खाते उघडा", "Open a bank account"),
    Service("💵", "कोणत्याही बँकेतून पैसे काढणे", "Withdraw cash from any bank"),
    Service("📥", "कोणत्याही बँकेत पैसे जमा करा", "Deposit cash in any bank"),
    Service("📤", "संपूर्ण भारतात कोणत्याही बँकेत पैसे पाठवा", "Send money to any bank across India"),
    Service("🛡️", "विमा हप्ता भरणे", "Pay insurance premium"),
    Service("👵", "श्रावणबाळ योजनेचे पैसे काढणे", "Shravan Bal Yojana payout withdrawal"),
    Service("🧾", "जनधन खात्यातील पैसे काढणे", "Jan Dhan account withdrawal"),
    Service("🪪", "पॅन कार्ड तयार करून मिळेल", "PAN card application"),
    Service("🏍️", "टू व्हीलर / फोर व्हीलर व मेडिक्लेम इन्शुरन्स", "Two-wheeler, four-wheeler & Mediclaim insurance"),
    Service("💡", "वीजबिल, मोबाईल व डी.टी.एच रिचार्ज", "Electricity bill, mobile & DTH recharge"),
    Service("🔥", "गॅस सिलेंडर बुकिंग", "Gas cylinder booking"),
    Service("📊", "बँक बॅलन्स तपासणी व कर्जाचा हप्ता भरणे", "Bank balance check & loan EMI payment"),
    Service("⌨️", "इंग्रजी व मराठी टायपिंग", "English & Marathi typing"),
    Service("📜", "७/१२ उतारा काढून मिळेल", "7/12 land record extract"),
    Service("🏧", "मिनी ATM – कोणत्याही बँकेच्या कार्डने व आधार कार्डने पैसे काढा", "Mini ATM – withdraw with any bank's card or Aadhaar"),
)

private val banks = listOf(
    "State Bank of India", "Bank of Baroda", "ICICI Bank", "Fino Payments Bank",
    "Axis Bank", "Union Bank of India", "Central Bank of India", "Bank of India",
    "Bank of Maharashtra", "Punjab National Bank", "HDFC Bank", "IDBI Bank",
    "Pay Point India", "Star Health Insurance",
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = lightColorScheme(primary = Crimson, background = Cream)) {
                HomeScreen()
            }
        }
    }
}

private fun Context.dial(number: String) =
    startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:+91$number")))

private fun Context.whatsapp(message: String) {
    val uri = Uri.parse("https://wa.me/91$WHATSAPP_NUMBER?text=${Uri.encode(message)}")
    try {
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(this, "WhatsApp / browser not found", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun HomeScreen() {
    val ctx = LocalContext.current
    var marathi by remember { mutableStateOf(true) }
    fun t(mr: String, en: String) = if (marathi) mr else en

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Cream),
        contentPadding = PaddingValues(bottom = 24.dp),
    ) {
        // Header
        item {
            Column(
                Modifier.fillMaxWidth().background(Crimson)
                    .statusBarsPadding().padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("|| श्रीराम समर्थ ||   || श्री रेणुका माता प्रसन्न ||",
                    color = Color(0xFFFFE0B2), fontSize = 11.sp)
                Spacer(Modifier.height(8.dp))
                Text("कृतिका ग्राहक सेवा केंद्र", color = Color.White,
                    fontSize = 28.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                Text("Krutika Grahak Seva Kendra", color = Color.White.copy(alpha = 0.9f), fontSize = 14.sp)
                Spacer(Modifier.height(4.dp))
                Text(t(OWNER_MR, OWNER_EN), color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Spacer(Modifier.height(12.dp))
                Row(
                    Modifier.clip(RoundedCornerShape(50)).background(Color.White.copy(alpha = 0.2f)).padding(4.dp)
                ) {
                    LangChip("मराठी", marathi) { marathi = true }
                    LangChip("English", !marathi) { marathi = false }
                }
            }
        }
        // Contact buttons
        item {
            Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(onClick = { ctx.dial(WHATSAPP_NUMBER) }, modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Crimson)) {
                    Text("📞 " + t("कॉल करा", "Call"))
                }
                Button(onClick = { ctx.whatsapp("नमस्कार, मला माहिती हवी होती. / Hello, I need some information.") },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366))) {
                    Text("💬 WhatsApp")
                }
            }
        }
        item {
            Text(t("सेवा", "Our Services"), fontSize = 20.sp, fontWeight = FontWeight.Bold,
                color = Crimson, modifier = Modifier.padding(start = 16.dp, bottom = 4.dp))
            Text(t("सेवेवर टॅप करून WhatsApp वर चौकशी करा", "Tap a service to enquire on WhatsApp"),
                fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(start = 16.dp, bottom = 8.dp))
        }
        items(services) { s ->
            Card(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 5.dp)
                    .clickable { ctx.whatsapp("नमस्कार, मला या सेवेबद्दल माहिती हवी: ${s.mr}\nHello, I need help with: ${s.en}") },
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp),
            ) {
                Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(44.dp).clip(CircleShape).background(Color(0xFFFCE4EC)),
                        contentAlignment = Alignment.Center) { Text(s.emoji, fontSize = 22.sp) }
                    Spacer(Modifier.width(12.dp))
                    Text(t(s.mr, s.en), fontSize = 15.sp, fontWeight = FontWeight.Medium)
                }
            }
        }
        // Banks
        item {
            Spacer(Modifier.height(16.dp))
            Text(t("उपलब्ध बँका व भागीदार", "Banks & Partners"), fontSize = 20.sp,
                fontWeight = FontWeight.Bold, color = Crimson, modifier = Modifier.padding(horizontal = 16.dp))
            Spacer(Modifier.height(8.dp))
            banks.chunked(2).forEach { row ->
                Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 3.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    row.forEach { b ->
                        Box(Modifier.weight(1f).clip(RoundedCornerShape(10.dp)).background(Color.White)
                            .padding(10.dp), contentAlignment = Alignment.Center) {
                            Text(b, fontSize = 13.sp, textAlign = TextAlign.Center)
                        }
                    }
                    if (row.size == 1) Spacer(Modifier.weight(1f))
                }
            }
        }
        // Contact footer
        item {
            Spacer(Modifier.height(20.dp))
            Card(Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Crimson)) {
                Column(Modifier.padding(16.dp)) {
                    Text(t("संपर्क", "Contact"), color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(6.dp))
                    Text(t(OWNER_MR, OWNER_EN), color = Color.White)
                    Text("📞 $WHATSAPP_NUMBER", color = Color.White,
                        modifier = Modifier.clickable { ctx.dial(WHATSAPP_NUMBER) }.padding(vertical = 4.dp))
                    Text("📞 $SECOND_NUMBER", color = Color.White,
                        modifier = Modifier.clickable { ctx.dial(SECOND_NUMBER) }.padding(vertical = 4.dp))
                }
            }
        }
    }
}

@Composable
private fun LangChip(label: String, selected: Boolean, onClick: () -> Unit) {
    Text(
        label,
        color = if (selected) Crimson else Color.White,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.clip(RoundedCornerShape(50))
            .background(if (selected) Color.White else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 6.dp),
    )
}
