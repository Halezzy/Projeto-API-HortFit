package classes;

import android.content.Context;
import android.content.Intent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;

public class User {
    private String name;
    private String email;
    private String password;
    private String gender;
    private LocalDate birthDate;

    public User(String name, String email, String password, String gender, LocalDate birthDate) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.birthDate = birthDate;
    }

    public static User cadastrar(String name, String email, String password, String gender, LocalDate birthDate) throws Exception {
        if (emailExists(email)) {
            throw new IllegalArgumentException("Email já está em uso.");
        }

        User user = new User(name, email, password, gender, birthDate);

        // Realiza a requisição POST para a API
        URL url = new URL("http://localhost:8080/api/users");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        String jsonInputString = String.format(
                "{\"name\":\"%s\", \"email\":\"%s\", \"password\":\"%s\", \"gender\":\"%s\", \"birthDate\":\"%s\"}",
                name, email, password, gender, birthDate.toString());

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString()); // Para verificar a resposta da API
        }

        return user;
    }

    public boolean checarLogin(String enteredEmail, String enteredPassword) {
        return this.email.equals(enteredEmail) && this.password.equals(enteredPassword);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    private static boolean emailExists(String email) {
        // Lógica para verificar se o email já está em uso (pode ser conectado a um banco de dados)
        return false; // Supondo que o email não existe neste exemplo
    }

    public void abrirPerfil(Context context) {
        // Cria um Intent para abrir a nova atividade (ProfileActivity)
        Intent intent = new Intent(context, User.class);

        // Passa o nome do usuário como extra para a nova atividade
        intent.putExtra("name", this.name);

        // Inicia a nova atividade
        context.startActivity(intent);
    }
}
