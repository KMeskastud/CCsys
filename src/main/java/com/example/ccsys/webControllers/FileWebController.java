package com.example.ccsys.webControllers;


import com.example.ccsys.ds.Course;
import com.example.ccsys.ds.File;
import com.example.ccsys.ds.User;
import com.example.ccsys.utils.DbQuerys;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/file", produces = MediaType.APPLICATION_JSON_VALUE)
public class FileWebController {

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<File> getFiles (@RequestParam("folderId") int folderId) throws SQLException {
        return DbQuerys.getFiles(folderId);
    }

    @DeleteMapping(value = "/delete")
    public String deleteFile (@RequestParam("fileId") int fileId) throws SQLException {
        DbQuerys.deleteFile(fileId);
        return "Success";
    }

    @PostMapping(value = "/create")
    public String createFile (@RequestParam("folderId") int folderId, @RequestBody Map<String, String> properties) throws SQLException {
        String name = properties.get("name");
        File file = new File (name, folderId);
        DbQuerys.createFile(file);
        return "Success";
    }

    @PutMapping(value = "/update")
    public String updateFile (@RequestBody Map<String, String> properties, @RequestParam("fileId") int fileId) throws SQLException {
        String name = properties.get("name");
        File file = new File (fileId, name);
        DbQuerys.editFile(file);
        return "Success";
    }
}
