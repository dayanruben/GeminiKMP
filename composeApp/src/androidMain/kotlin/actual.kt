import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import kotlinx.coroutines.launch
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

actual fun ByteArray.toComposeImageBitmap(): ImageBitmap {
    return BitmapFactory.decodeByteArray(this, 0, size).asImageBitmap()
}


@OptIn(ExperimentalEncodingApi::class)
@Composable
actual fun ImagePicker(
    show: Boolean,
    initialDirectory: String?,
    title: String?,
    onImageSelected: ImageFileImported,
) {
    val coroutineScope = rememberCoroutineScope()

    val fileExtensions = listOf("jpg", "png")
    FilePicker(show = show, fileExtensions = fileExtensions) { file ->
        coroutineScope.launch {
            val data = file?.getFileByteArray()
            data?.let {
                val base64EncodedImageData = Base64.encode(data)
                onImageSelected(file.path, base64EncodedImageData)
            }
        }
    }
}