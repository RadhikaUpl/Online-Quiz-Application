package com.radhaa.QUIZAPPLICATIONBackend.Service;

import com.radhaa.QUIZAPPLICATIONBackend.Entity.fileUploadEntity;
import com.radhaa.QUIZAPPLICATIONBackend.Repo.fileUploadRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class fileUploadService
{
    @Autowired
    fileUploadRepo fileUploadRepo;

    public ResponseEntity<String> upload(MultipartFile file,String Name) throws IOException {
        //by builder we can directly build the object of fileUploadEntity
      fileUploadRepo.save( fileUploadEntity.builder().fileName(file.getOriginalFilename())
              .fileType(file.getContentType())
              .name(Name)
              .fileData(file.getBytes())
              .build()
      );
       return ResponseEntity.ok(" file saved successfully");
    }

    public ResponseEntity<byte[]> getFile(int id)
    {
        fileUploadEntity file = fileUploadRepo.findById(id).orElseThrow(() -> new RuntimeException("file not found"));

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.getFileType())).header(HttpHeaders.CONTENT_DISPOSITION,"inline ; filename=\""+file.getFileName()+"\"" ).body(file.getFileData());
        //inline because want to open the file IF I HAVE USED ATTACHMENT then it will directly download file

    }

    public ResponseEntity<List<fileUploadEntity>> getAll()
    {
        List<fileUploadEntity> all = fileUploadRepo.findAll();
       return ResponseEntity.ok(all);
    }
}
