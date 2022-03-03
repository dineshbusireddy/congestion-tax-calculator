package testdata;

import com.volvo.dto.TaxCalculatorResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TestDataProvider {
    private List<TestData> testDataDetails;

    @Getter
    @Setter
    public static class TestData {
        private String testCaseName;
        private TaxCalculatorRequest input;
        private TaxCalculatorResponse expected;

        @Override
        public String toString() {
            return testCaseName;
        }
    }

    @Getter
    @Setter
    public static class TaxCalculatorRequest {
        private String vehicleType;
        private List<String> dates;
    }
}
