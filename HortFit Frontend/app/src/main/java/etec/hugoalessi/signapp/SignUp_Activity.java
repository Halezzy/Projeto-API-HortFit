package etec.hugoalessi.signapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignUp_Activity extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        Button buttonSignUp = findViewById(R.id.buttonSignUp);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignUp_Activity.this, "Por favor, preencha todos os campos!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Cria um novo usuário através da API
                new Thread(() -> {
                    try {
                        // URL do endpoint da API para criar um novo usuário
                        URL url = new URL("http://localhost:8080/api/users");
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Content-Type", "application/json; utf-8");
                        conn.setRequestProperty("Accept", "application/json");
                        conn.setDoOutput(true);

                        // Cria a string JSON com os dados do usuário
                        String jsonInputString = String.format(
                                "{\"name\":\"%s\", \"email\":\"%s\", \"password\":\"%s\"}",
                                name, email, password);

                        try (OutputStream os = conn.getOutputStream()) {
                            byte[] input = jsonInputString.getBytes("utf-8");
                            os.write(input, 0, input.length);
                        }

                        // Lê a resposta da API
                        try (BufferedReader br = new BufferedReader(
                                new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                            StringBuilder response = new StringBuilder();
                            String responseLine;
                            while ((responseLine = br.readLine()) != null) {
                                response.append(responseLine.trim());
                            }
                            runOnUiThread(() -> {
                                Toast.makeText(SignUp_Activity.this, "Cadastro bem-sucedido!", Toast.LENGTH_SHORT).show();
                                // Redireciona para a tela de perfil após o cadastro bem-sucedido
                                Intent intent = new Intent(SignUp_Activity.this, User_Activity.class);
                                intent.putExtra("name", name); // Passa o nome para a próxima atividade
                                startActivity(intent);
                                finish(); // Finaliza a tela de cadastro para evitar que o usuário volte a ela
                            });
                        }
                    } catch (Exception e) {
                        runOnUiThread(() -> Toast.makeText(SignUp_Activity.this, "Erro: " + e.getMessage(), Toast.LENGTH_LONG).show());
                    }
                }).start();
            }
        });
    }
}
