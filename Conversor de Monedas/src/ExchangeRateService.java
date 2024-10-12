import java.util.Map;

public interface ExchangeRateService {
    Map<String, Double> getExchangeRates(String baseCurrency) throws Exception;
}
