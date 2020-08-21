package com.eneserdogan.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    Button btn;
    EditText editText;
    ListView sehirLv;

    String[] iller = { "Adana", "Adiyaman", "Afyonkarahisar", "Agri",
            "Amasya", "Ankara", "Antalya", "Artvin", "Aydin", "Balikesir",
            "Bilecik", "Bingol", "Bitlis", "Bolu", "Burdur", "Bursa",
            "Çanakkale", "Çankiri", "Çorum", "Denizli", "Diyarbakir",
            "Edirne", "Elazig", "Erzincan", "Erzurum", "Eskisehir",
            "Gaziantep", "Giresun", "Hakkari", "Hatay",
            "Isparta", "İstanbul", "İzmir", "Kars", "Kastamonu",
            "Kayseri", "Kirklareli", "Kirsehir", "Kocaeli", "Konya",
            "Kutahya", "Malatya", "Manisa", "Kahramanmaras", "Mardin",
            "Mugla", "Mus", "Nevsehir", "Nigde", "Ordu", "Rize", "Sakarya",
            "Samsun", "Siirt", "Sinop", "Sivas", "Tekirdag", "Tokat",
            "Trabzon", "Tunceli", "Sanliurfa", "Usak", "Van", "Yozgat",
            "Zonguldak", "Aksaray", "Bayburt", "Karaman", "Kirikkale",
            "Batman", "sirnak", "Bartin", "Ardahan", "Igdir", "Yalova",
            "Karabuk", "Kilis", "Osmaniye", "Duzce" };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sehirLv=findViewById(R.id.ListView);
        btn=findViewById(R.id.button);
        editText=findViewById(R.id.edittext);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,iller);
        sehirLv.setAdapter(adapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city=editText.getText().toString();
                Intent intent=new Intent(MainActivity.this,WeatherActivity.class);
                intent.putExtra("sehir",city);
                startActivity(intent);
            }
        });

        sehirLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MainActivity.this,WeatherActivity.class);
                intent.putExtra("sehir",iller[position].toString());
                startActivity(intent);

            }
        });
    }
}
