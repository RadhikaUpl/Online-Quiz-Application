package com.radhaa.QUIZAPPLICATIONBackend.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class fileUploadEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fileId;
    private String name;
    private String fileName;
    private String fileType;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] fileData;

}
