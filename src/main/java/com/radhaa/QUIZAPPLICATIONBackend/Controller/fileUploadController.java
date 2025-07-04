package com.radhaa.QUIZAPPLICATIONBackend.Controller;

import com.radhaa.QUIZAPPLICATIONBackend.Entity.fileUploadEntity;
import com.radhaa.QUIZAPPLICATIONBackend.Service.fileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/file")
public class fileUploadController
{
    @Autowired
    fileUploadService fileUploadService;
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,@RequestParam String name) throws IOException {
        return fileUploadService.upload(file,name);
    }

    @PostMapping("/get")
    public ResponseEntity<byte[]> getFile(@RequestParam int id)
    {
        return fileUploadService.getFile(id);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<fileUploadEntity>> getAll()
    {
        return fileUploadService.getAll();
    }
}
