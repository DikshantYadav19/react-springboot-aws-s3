package com.dikshant.io.aws.s3.upload.profile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/user-profile")
@CrossOrigin("http://localhost:3000")
public class UserProfileController {

    private Logger logger = LoggerFactory.getLogger(UserProfileController.class);

    @Autowired
    private UserProfileService userProfileService;

    @GetMapping
    public List<UserProfile> getUserProfiles() {
        return userProfileService.getUserProfiles();
    }

    @PostMapping(
            path = "{userProfileId}/image/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void uploadUserProfileImage(@PathVariable("userProfileId") UUID userProfileId,
                                       @RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            logger.error("File cannot be null");
            throw new IllegalStateException("Cannot upload empty/null file");
        }
        logger.info("Received file : {} for user : {}", file.getOriginalFilename(), userProfileId);
        userProfileService.uploadUserProfileImage(userProfileId, file);
    }

    @GetMapping("{userProfileId}/image/download")
    public byte[] downloadUserProfileImage(@PathVariable("userProfileId") UUID userProfileId) {
        logger.info("Received request to download profile image for user : {}", userProfileId);
        return userProfileService.downloadUserProfileImage(userProfileId);
    }
}
