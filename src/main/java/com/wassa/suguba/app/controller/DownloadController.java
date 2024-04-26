package com.wassa.suguba.app.controller;

import com.wassa.suguba.app.constante.UploadPath;
import lombok.AllArgsConstructor;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;

@RestController
@RequestMapping("/downloads")
@AllArgsConstructor
public class DownloadController {
    @GetMapping("/{folderName}/{fileName}")
    public void downloadFiles(HttpServletResponse response,
                              @PathVariable("fileName") String fileName,
                              @PathVariable("folderName") String resourceName) throws IOException {

        String path = UploadPath.DOWNLOAD_LINK + "/" + resourceName;
        File file = new File(path + "/" + fileName);
        if (file.exists()) {

            //get the mimetype
            String mimeType = URLConnection.guessContentTypeFromName(file.getName());
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }

            response.setContentType(mimeType);
            response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
            response.setContentLength((int) file.length());

            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

            FileCopyUtils.copy(inputStream, response.getOutputStream());
        }
    }
}
