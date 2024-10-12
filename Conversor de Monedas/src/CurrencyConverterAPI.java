import com.google.gson.Gson;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class CurrencyConverterAPI implements ExchangeRateService {
    private static final String API_KEY = "9652b8051ddef5a5dd59da18";
    private static final String API_URL_TEMPLATE = "https://v6.exchangerate-api.com/v6/%s/latest/%s";

    @Override
    public Map<String, Double> getExchangeRates(String baseCurrency) throws Exception {
        String apiUrl = String.format(API_URL_TEMPLATE, API_KEY, baseCurrency);
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int status = conn.getResponseCode();
        if (status != 200) {
            throw new RuntimeException("Error al conectar con la API: Código de respuesta " + status);
        }

        try (InputStreamReader reader = new InputStreamReader(conn.getInputStream())) {
            Gson gson = new Gson();
            ExchangeRateResponse response = gson.fromJson(reader, ExchangeRateResponse.class);

            if (!"success".equalsIgnoreCase(response.result())) {
                throw new RuntimeException("Error en la respuesta de la API: " + response.result());
            }

            return response.conversion_rates();
        } finally {
            conn.disconnect();
        }
    }
}
