package mx.com.disoftware.blogapp.data.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Post(
    val profile_picture: String = "",
    val profile_name: String = "",
    /* Indica al sevidor que si este dato se manda en nulo, debe crear un timestamp con la
    *  fecha del servidor que se creó en FiraBase.
    *
    *  Nota: esta es la forma recomendada ya que si nosostros obtener la fecha del teléfono, esta
    *  puede ser alterada por el usuario, creando inconsistencia en los datos.
    *  */
    @ServerTimestamp
    var created_at: Date? = null,
    val post_image: String = "",
    val post_description: String = "",
    val uid: String = ""
)