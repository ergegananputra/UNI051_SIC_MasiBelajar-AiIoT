package com.sic6.masibelajar.ui.screens.article

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.sic6.masibelajar.domain.entities.Article
import com.sic6.masibelajar.ui.screens.article.components.ArticleCard
import com.sic6.masibelajar.ui.screens.components.CircleBackground
import com.sic6.masibelajar.ui.theme.MasiBelajarDashboardTheme

@Preview(
    name = "Light Mode",
    showSystemUi = true,
    showBackground = true,
)
@Composable
private fun ArticleScreenDeveloperPreview() {
    MasiBelajarDashboardTheme {
        ArticleScreen()
    }
}

@Composable
fun ArticleScreen(
    modifier: Modifier = Modifier,
    onArticleClick: (Article) -> Unit = {}
) {
    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {
            items(articles) { article ->
                ArticleCard(
                    article = article,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    onArticleClick(article)
                }
            }
        }

        CircleBackground(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = 360.dp)
                .zIndex(-1f)
        )
    }
}

// stub
val articles = listOf(
    Article(
        title = "Yuk, Latih Bayi untuk Tidur Sendiri dengan Metode Ferber",
        author = "Fanny Eka Putri",
        date = "Jumat, 05 Maret 2025",
        content = "Sebagian orang tua mungkin merasa khawatir jika membiarkan bayi mereka tidur sendirian di kamarnya. Padahal, sebenarnya bayi sudah bisa diajarkan untuk tidur sendiri, lho. Salah satu cara yang bisa Bunda dan Ayah lakukan untuk melatih Si Kecil tidur sendiri adalah dengan menerapkan metode Ferber.\n\nSaat melakukan metode Ferber, bayi akan dilatih untuk tidur sendiri saat ia merasa mengantuk dan untuk tidur kembali jika ia terbangun di tengah tidurnya. Metode ini pertama kali dicetuskan oleh seorang dokter spesialis anak bernama Richard Ferber.\n\nSelain dapat membuat bayi mampu tidur sendiri, menerapkan metode Ferber juga diyakini dapat membuat bayi bisa tidur lebih nyenyak, mudah tidur kembali jika terbangun, dan memiliki pola tidur yang teratur."
    ),
    Article(
        title = "Gangguan Pencernaan pada Bayi, Inilah Tanda-Tandanya",
        author = "Fanny Eka Putri",
        date = "Jumat, 04 Maret 2025",
        content = "Gangguan pencernaan pada bayi bisa membuat orang tua khawatir. Pasalnya, kondisi ini bisa membuat Si Kecil rewel karena merasa tidak nyaman. Oleh karena itu, penting bagi Bunda dan Ayah untuk mengetahui tanda-tanda gangguan pencernaan pada bayi agar bisa segera mendapatkan penanganan yang tepat.\n\nBeberapa tanda umum gangguan pencernaan pada bayi meliputi: sering gumoh, perut kembung, sering buang gas, diare, dan sembelit. Jika Si Kecil mengalami tanda-tanda tersebut, segera konsultasikan dengan dokter untuk mendapatkan penanganan yang tepat."
    ),
    Article(
        title = "Kapan Bayi Boleh Dibawa Naik Pesawat?",
        author = "Fanny Eka Putri",
        date = "Jumat, 03 Maret 2025",
        content = "Bepergian dengan pesawat bersama bayi memang membutuhkan persiapan yang lebih matang. Namun, bukan berarti hal ini tidak mungkin dilakukan. Sebenarnya, tidak ada aturan baku mengenai usia minimal bayi boleh naik pesawat. Namun, sebagian besar maskapai penerbangan menyarankan agar bayi berusia minimal 2 minggu sebelum melakukan perjalanan udara. Hal ini dikarenakan sistem kekebalan tubuh bayi yang baru lahir masih belum sempurna.\n\nSelain itu, perubahan tekanan udara di dalam kabin pesawat juga bisa memengaruhi kesehatan bayi. Oleh karena itu, sebelum memutuskan untuk membawa bayi naik pesawat, sebaiknya konsultasikan terlebih dahulu dengan dokter anak."
    ),
    Article(
        title = "Faktor Penyebab ASI Sedikit dan Solusi Mengatasinya",
        author = "Fanny Eka Putri",
        date = "Jumat, 02 Maret 2025",
        content = "ASI (Air Susu Ibu) merupakan makanan terbaik untuk bayi. Namun, beberapa ibu mungkin mengalami masalah produksi ASI yang sedikit. Ada beberapa faktor yang bisa menyebabkan produksi ASI sedikit, di antaranya: stres, kelelahan, penggunaan alat kontrasepsi hormonal, dan masalah kesehatan tertentu.\n\nUntuk mengatasi masalah ini, ada beberapa solusi yang bisa Bunda coba, seperti: menyusui lebih sering, mengonsumsi makanan bergizi, dan istirahat yang cukup. Jika produksi ASI tetap sedikit, segera konsultasikan dengan dokter atau konselor laktasi."
    ),
    Article(
        title = "Tips Merawat Bayi Tumbuh Gigi yang Perlu Bunda Ketahui",
        author = "Fanny Eka Putri",
        date = "Jumat, 01 Maret 2025",
        content = "Tumbuh gigi merupakan salah satu tahapan penting dalam perkembangan bayi. Namun, proses ini seringkali membuat bayi merasa tidak nyaman. Oleh karena itu, penting bagi Bunda untuk mengetahui tips merawat bayi tumbuh gigi agar Si Kecil tetap merasa nyaman.\n\nBeberapa tips yang bisa Bunda lakukan adalah: memberikan teether atau mainan gigit, mengusap gusi bayi dengan kain bersih, memberikan makanan dingin, dan memberikan obat pereda nyeri jika perlu. Selain itu, Bunda juga perlu menjaga kebersihan mulut bayi dengan membersihkan gusinya secara rutin."
    ),
)