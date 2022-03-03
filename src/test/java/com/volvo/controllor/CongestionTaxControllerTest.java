package com.volvo.controllor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.volvo.constants.TestConstants;
import com.volvo.dto.TaxCalculatorResponse;
import com.volvo.util.CollectionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import testdata.TestDataProvider;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc(addFilters = false)
public class CongestionTaxControllerTest {
    private static final String RESOURCE_PATH = "/api/v1/congestion-tax/calculator";
    private static final String INVALID_PAYLOAD = "invalid";
    private static final String TEST_DATA_FILE_PATH = "test-data.yml";
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @ParameterizedTest
    @MethodSource("testDataSource")
    void testCalculateTax(TestDataProvider.TestData testData) throws Exception {
        TaxCalculatorResponse expected = testData.getExpected();
        ResultMatcher expectedStatus = status().isOk();
        if (CollectionUtil.isNotEmpty(expected.getErrors())) {
            expectedStatus = status().isBadRequest();
        }
        TaxCalculatorResponse actual = invokeApiRequest(testData.getInput(), expectedStatus);

        if (CollectionUtil.isNotEmpty(expected.getErrors())) {
            Assertions.assertEquals(expected.getErrors().size(), actual.getErrors().size());
            for (String error : expected.getErrors()) {
                Assertions.assertTrue(actual.getErrors().contains(error));
            }
        } else {
            Assertions.assertEquals(expected.getTax(), actual.getTax());
        }
    }

    @Test
    void testInvalidRequest() throws Exception {
        TaxCalculatorResponse actual = invokeApiRequest(INVALID_PAYLOAD, status().isBadRequest());
        Assertions.assertEquals(TestConstants.ONE, actual.getErrors().size());
        Assertions.assertTrue(actual.getErrors().contains("Invalid request"));
    }

    private static Stream<Arguments> testDataSource() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        InputStream inputStream = TestDataProvider.class.getClassLoader()
                .getResourceAsStream(TEST_DATA_FILE_PATH);
        TestDataProvider testDataProvider = mapper.
                readValue(inputStream, TestDataProvider.class);
        List<Arguments> arguments = testDataProvider.getTestDataDetails()
                .stream().map(Arguments::of).collect(Collectors.toList());

        return arguments.stream();
    }

    private TaxCalculatorResponse invokeApiRequest(TestDataProvider.TaxCalculatorRequest request,
                                                   ResultMatcher expectedStatus) throws Exception {
        return invokeApiRequest(objectMapper.writeValueAsString(request), expectedStatus);
    }

    private TaxCalculatorResponse invokeApiRequest(String payload,
                                                   ResultMatcher expectedStatus) throws Exception {
        return objectMapper.readValue(mockMvc.perform(
                post(RESOURCE_PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(payload))
                .andDo(print()).andExpect(expectedStatus).andReturn()
                .getResponse().getContentAsString(), TaxCalculatorResponse.class);
    }

}
