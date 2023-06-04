import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class WardrobeActivity extends AppCompatActivity {

    private RecyclerView wardrobeRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wardrobe);

        wardrobeRecyclerView = findViewById(R.id.wardrobeRecyclerView);

        // TODO: Set up the RecyclerView with a GridLayoutManager and an adapter
    }
}