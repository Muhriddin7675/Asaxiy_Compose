package uz.gita.asaxiyappcompose.pdf

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.VerticalPdfReaderState
import com.rizzi.bouquet.rememberVerticalPdfReaderState
import java.io.File

class PdfScreen(
    private val path: String,
) : Screen {
    @Composable
    override fun Content() {
        PdfContent(path = path)
    }
}

@Composable
fun PdfContent(path: String) {

    val pdfState = rememberVerticalPdfReaderState(
        resource = ResourceType.Local(Uri.fromFile(File(path))),
        isZoomEnable = true
    )

    val pdfVerticalReaderState = VerticalPdfReaderState(
        resource = ResourceType.Local(Uri.fromFile(File(path))),
        isZoomEnable = true
    )

    VerticalPDFReader(
        state = pdfState,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray)
    )
}
