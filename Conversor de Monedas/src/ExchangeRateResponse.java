import java.util.Map;

public record ExchangeRateResponse(
        String result,
        String documentation,
        String terms_of_use,
        String time_last_update_unix,
        String time_last_update_utc,
        String time_next_update_unix,
        String time_next_update_utc,
        String base_code,
        Map<String, Double> conversion_rates
) {}
