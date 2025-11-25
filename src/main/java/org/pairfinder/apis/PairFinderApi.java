package org.pairfinder.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/pair-finder")
public class PairFinderApi {

    @Autowired
    private PairFinderApiHelper helper;

    @Operation(summary = "Upload CSV file to find longest working pair")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Invalid file")
    })
    @CrossOrigin(origins = "*")
    @RequestMapping(
            path = "/upload",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam(value = "datePattern", defaultValue = "yyyy-MM-dd") String datePattern) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty!");
        }

        return ResponseEntity.ok(helper.getLongestWorkingPair(file, datePattern));
    }

}
