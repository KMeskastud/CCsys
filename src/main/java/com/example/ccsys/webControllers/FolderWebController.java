package com.example.ccsys.webControllers;

import com.example.ccsys.ds.Course;
import com.example.ccsys.ds.File;
import com.example.ccsys.ds.Folder;
import com.example.ccsys.utils.DbQuerys;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/folder", produces = MediaType.APPLICATION_JSON_VALUE)
public class FolderWebController {

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Folder> getFolders (@RequestParam("courseId") int courseId) throws SQLException {
        return DbQuerys.getFolders(courseId);
    }

    @DeleteMapping(value = "/delete")
    public String deleteFolder (@RequestParam("folderId") int folderId) throws SQLException {
        DbQuerys.deleteFolder(folderId);
        return "Success";
    }

    @PostMapping(value = "/create")
    public String createFolder (@RequestParam("courseId") int courseId, @RequestParam("parentId") int parentId, @RequestBody Map<String, String> properties) throws SQLException {
        String name = properties.get("name");
        Folder folder = new Folder (parentId, courseId, name);
        DbQuerys.createFolder(folder);
        return "Success";
    }

    @PutMapping(value = "/update")
    public String updateFolder (@RequestBody Map<String, String> properties, @RequestParam("folderId") int folderId) throws SQLException {
        String name = properties.get("name");
        Folder folder = new Folder (folderId, name);
        DbQuerys.editFolder(folder);
        return "Success";
    }
}
