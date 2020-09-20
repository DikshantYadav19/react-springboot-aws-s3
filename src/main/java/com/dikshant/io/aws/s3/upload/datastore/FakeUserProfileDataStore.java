package com.dikshant.io.aws.s3.upload.datastore;

import com.dikshant.io.aws.s3.upload.profile.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FakeUserProfileDataStore {

    private static final List<UserProfile> USER_PROFILES = new ArrayList<>();

    static {
        USER_PROFILES.add(new UserProfile(UUID.fromString("8f3c5dd9-84a3-461a-9d9d-59f4ca8eb756"), "dikshantyadav19", null));
        USER_PROFILES.add(new UserProfile(UUID.fromString("b81f1ebd-fbf4-42b6-80c3-628088b96f81"), "messibarca10", null));
        USER_PROFILES.add(new UserProfile(UUID.fromString("05c0854c-35a8-41ca-83f5-8e98ca5c10a3"), "kakamilan8", null));
    }

    public List<UserProfile> getUserProfiles() {
        return USER_PROFILES;
    }
}
