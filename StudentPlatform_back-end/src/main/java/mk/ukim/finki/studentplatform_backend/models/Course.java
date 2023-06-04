package mk.ukim.finki.studentplatform_backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer courseId;
    String name;
    String description;
    Integer participants;
    Boolean done;
    String imageUrl;

    public Course(String name, String description) {
        this.name = name;
        this.description = description;
        this.participants = 0;
        this.done = false;
        List<String> imageUrls = Stream.of("http://drive.google.com/uc?export=view&id=1vTSfsw3pCyOLzVWWc7-esuOScoVG-1yB",
                "http://drive.google.com/uc?export=view&id=1wLsjeoduXZxtUTFORXOG43rAizw43_ke",
                "http://drive.google.com/uc?export=view&id=1B52Y_mWkW0xNFP3pWJCa7WMm2F4OYxmS",
                "http://drive.google.com/uc?export=view&id=1RfYx3rnMcH8JtPdXon7iaPUOogWzT18z",
                "http://drive.google.com/uc?export=view&id=1uWwpCsFHbHfOERFI3iFJbBwkTen68UXn",
                "http://drive.google.com/uc?export=view&id=1dXybNI0kuxjGYtzXpg67eVuP7a1irOVH",
                "http://drive.google.com/uc?export=view&id=1OiIxCGGUIhfY2WPpvU9zGne0Nk8InQob",
                "http://drive.google.com/uc?export=view&id=11Kh6yycZgnucpsyz0z41Jvo64-Ue1o9_",
                "http://drive.google.com/uc?export=view&id=1xfkrOW_PSEcY6fKftBxLYY9CKniP9_8Y",
                "http://drive.google.com/uc?export=view&id=1IssObe7LSv-qN5NDUt6HwAnH0JcuRKKe")
                .collect(Collectors.toList());
        Random random = new Random();
        int randomIndex = random.nextInt(imageUrls.size());
        this.imageUrl = imageUrls.get(randomIndex);
    }

    public Course(String name, String description, Integer participants, Boolean done) {
        this.name = name;
        this.description = description;
        this.participants = participants;
        this.done = done;
        List<String> imageUrls = Stream.of("http://drive.google.com/uc?export=view&id=1vTSfsw3pCyOLzVWWc7-esuOScoVG-1yB",
                "http://drive.google.com/uc?export=view&id=1wLsjeoduXZxtUTFORXOG43rAizw43_ke",
                "http://drive.google.com/uc?export=view&id=1B52Y_mWkW0xNFP3pWJCa7WMm2F4OYxmS",
                "http://drive.google.com/uc?export=view&id=1RfYx3rnMcH8JtPdXon7iaPUOogWzT18z",
                "http://drive.google.com/uc?export=view&id=1uWwpCsFHbHfOERFI3iFJbBwkTen68UXn",
                "http://drive.google.com/uc?export=view&id=1dXybNI0kuxjGYtzXpg67eVuP7a1irOVH",
                "http://drive.google.com/uc?export=view&id=1OiIxCGGUIhfY2WPpvU9zGne0Nk8InQob",
                "http://drive.google.com/uc?export=view&id=11Kh6yycZgnucpsyz0z41Jvo64-Ue1o9_",
                "http://drive.google.com/uc?export=view&id=1xfkrOW_PSEcY6fKftBxLYY9CKniP9_8Y",
                "http://drive.google.com/uc?export=view&id=1IssObe7LSv-qN5NDUt6HwAnH0JcuRKKe")
                .collect(Collectors.toList());
        Random random = new Random();
        int randomIndex = random.nextInt(imageUrls.size());
        this.imageUrl = imageUrls.get(randomIndex);
    }
}
