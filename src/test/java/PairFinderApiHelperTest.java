import org.junit.jupiter.api.Test;
import org.pairfinder.apis.PairFinderApiHelper;
import org.pairfinder.models.WorkingPair;
import org.springframework.mock.web.MockMultipartFile;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PairFinderApiHelperTest {

    private final PairFinderApiHelper helper = new PairFinderApiHelper();

    @Test
    void testGetLongestWorkingPair() throws Exception {
        // Load CSV from test resources
        InputStream csvStream = getClass().getClassLoader()
                .getResourceAsStream("files/data.csv");

        if (csvStream == null) {
            throw new IllegalArgumentException("CSV file not found in test resources");
        }

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "data.csv",
                "text/csv",
                csvStream.readAllBytes()
        );

        WorkingPair result = helper.getLongestWorkingPair(file, "yyyy-MM-dd");

        // Example assertions
        assertEquals(5L, result.getFirstEmployeeId());
        assertEquals(1L, result.getSecondEmployeeId());
        assertEquals(104L, result.getProjectId());
        assertEquals(976L, result.getTimeSpent());
    }
}
