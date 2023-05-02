import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.vought.databinding.FragmentRegisterPasswordBinding
import com.example.vought.home.HomeActivity

class RegisterWelcomeFragment : Fragment() {

  private lateinit var binding: FragmentRegisterPasswordBinding

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = FragmentRegisterPasswordBinding.inflate(inflater)
    val view = binding.root

    binding.btnEnter.setOnClickListener {
      val intent = Intent(requireContext(), HomeActivity::class.java)
      startActivity(intent)
    }

    return view
  }
}
