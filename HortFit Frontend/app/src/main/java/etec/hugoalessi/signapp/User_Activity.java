package etec.hugoalessi.signapp;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class User_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // Referência ao TabLayout
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        // Configurar abas
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_home).setText("Home"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_profile).setText("Profile"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_gallery).setText("Gallery"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_publications).setText("Publications"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_search).setText("Search"));

        // Configurar o comportamento de clique nas abas
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        replaceFragment(new HomeFragment());
                        break;
                    case 1:
                        replaceFragment(new ProfileFragment());
                        break;
                    case 2:
                        replaceFragment(new GalleryFragment());
                        break;
                    case 3:
                        replaceFragment(new PublicationsFragment());
                        break;
                    case 4:
                        replaceFragment(new SearchFragment());
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Pode ser usado para redefinir estados visuais se necessário
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Comportamento ao clicar novamente em uma aba já selecionada
            }
        });

        // Inicialize o fragmento inicial (primeira aba)
        if (savedInstanceState == null) {
            replaceFragment(new HomeFragment());
        }
    }

    // Método para trocar o fragmento
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        fragmentTransaction.commit();
    }
}
