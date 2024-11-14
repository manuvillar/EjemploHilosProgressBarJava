package es.iesoretania.prcticahilosjava;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import es.iesoretania.prcticahilosjava.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configurar ViewBinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Configurar los botones
        binding.btnDescargarHiloPrincipal.setOnClickListener(v -> descargarDatosHiloPrincipal());
        binding.btnDescargarHiloSecundario.setOnClickListener(v -> descargarDatosHiloSecundario());
    }

    // Simula una tarea larga en el hilo principal
    private void descargarDatosHiloPrincipal() {
        binding.txtResultado.setText("Descargando en hilo principal...");
        binding.progressBar.setVisibility(View.VISIBLE);

        try {
            // Simula tarea larga con pausa de 5 segundos
            Thread.sleep(5000);
            binding.txtResultado.setText("Descarga completada en hilo principal");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        binding.progressBar.setVisibility(View.GONE);
    }

    // Simula una tarea larga en un hilo secundario
    private void descargarDatosHiloSecundario() {
        binding.txtResultado.setText("Descargando en hilo secundario...");
        binding.progressBar.setVisibility(View.VISIBLE);

        new Thread(() -> {
            try {
                // Simula tarea larga de 5 segundos
                for (int i = 1; i <= 5; i++) {
                    Thread.sleep(1000); // Espera de 1 segundo
                    int progreso = i * 20;

                    // Actualiza el progreso en el hilo principal
                    runOnUiThread(() -> binding.progressBar.setProgress(progreso));
                }

                // Actualiza el mensaje final en el hilo principal
                runOnUiThread(() -> {
                    binding.txtResultado.setText("Descarga completada en hilo secundario");
                    binding.progressBar.setVisibility(View.GONE);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
