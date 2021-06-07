package mx.com.disoftware.blogapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.google.firebase.Timestamp
import mx.com.disoftware.blogapp.R
import mx.com.disoftware.blogapp.data.model.Post
import mx.com.disoftware.blogapp.databinding.FragmentHomeScreenBinding
import mx.com.disoftware.blogapp.ui.home.adapter.HomeScreenAdapter

class HomeScreenFragment : Fragment(R.layout.fragment_home_screen) {

    private lateinit var binding: FragmentHomeScreenBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeScreenBinding.bind(view)

        // Valores de prueba
        val postList = listOf(
            Post(
                "https://cdn.computerhoy.com/sites/navi.axelspringer.es/public/styles/480/public/media/image/2018/08/fotos-perfil-whatsapp_16.jpg?itok=aqeTumbO",
                "Melchiah Developer",
                Timestamp.now(),
                "https://comunidad.iebschool.com/iebs/files/2015/07/post-perfecto-facebook.jpg"
            ),
            Post(
                "https://cdn.computerhoy.com/sites/navi.axelspringer.es/public/styles/480/public/media/image/2018/08/fotos-perfil-whatsapp_16.jpg?itok=aqeTumbO",
                "Melchiah Developer",
                Timestamp.now(),
                "https://comunidad.iebschool.com/iebs/files/2015/07/post-perfecto-facebook.jpg"
            ),
            Post(
                "https://cdn.computerhoy.com/sites/navi.axelspringer.es/public/styles/480/public/media/image/2018/08/fotos-perfil-whatsapp_16.jpg?itok=aqeTumbO",
                "Melchiah Developer",
                Timestamp.now(),
                "https://comunidad.iebschool.com/iebs/files/2015/07/post-perfecto-facebook.jpg"
            )
        )

        binding.rvHome.adapter = HomeScreenAdapter(postList)

    }
}