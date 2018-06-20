package ru.iqdevelop.barterme.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.iqdevelop.barterme.entities.PhotoEntity;
import ru.iqdevelop.barterme.models.common.AnswerMessage;
import ru.iqdevelop.barterme.services.PhotoService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;

@Controller
@RequestMapping("/image")
public class PhotoController {

    @Autowired
    PhotoService photoService;

    private static final Logger logger = LoggerFactory.getLogger(PhotoController.class);

    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = {"multipart/form-data"}, produces={"application/json"})
    public @ResponseBody AnswerMessage uploadPhoto(@RequestPart("uploadimage") MultipartFile file) {
        try {
            PhotoEntity newPhoto = photoService.saveImage(file.getOriginalFilename(), file.getBytes());
            return AnswerMessage.getSuccessMessage(newPhoto);
        } catch (Exception ex) {
            return AnswerMessage.getFailMessage("Не удалось сохранить файл");
        }
    }

    @RequestMapping(value = "/get/{imageName}")
    @ResponseBody
    public void getImage(@PathVariable(value = "imageName") String imageName, HttpServletRequest request, HttpServletResponse httpRes) {
        try {
            InputStream in = null;
            if (imageName.equals("default-photo.jpg")) {
                ClassLoader classloader = Thread.currentThread().getContextClassLoader();
                in = classloader.getResourceAsStream("default-photo.jpg");
            } else {
                File serverFile = photoService.getTempFilePath(imageName);
                if (serverFile.length() == 0) {
                    byte[] nullArr = new byte[0];
                    in = new ByteArrayInputStream(nullArr);
                } else {
                    in = new FileInputStream(serverFile);
                }
            }

            try {
                httpRes.setContentType("application/ms-excel; charset=UTF-8");
                //response.setCharacterEncoding("UTF-8");
                httpRes.setHeader("Content-Disposition", "attachment; filename=" + URLDecoder.decode(URLEncoder.encode("picture.jpg", "UTF-8"), "ISO8859_1"));

                ServletOutputStream out = httpRes.getOutputStream();

                byte[] outputByte = new byte[4096];
                int bytesRead = 0;
                while ((bytesRead = in.read(outputByte, 0, 4096)) != -1) {
                    out.write(outputByte, 0, bytesRead);
                }
                in.close();
                out.flush();
                out.close();

                return;
            } finally {
                in.close();
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /*
    @ResourceMapping(value = "GetFileById")
    @ResponseBody
    public void getFileById(ResourceRequest request, ResourceResponse response) throws IOException {
        try {
            HttpServletRequest servletReq = PortalUtil
                    .getOriginalServletRequest(PortalUtil.getHttpServletRequest(request));
            String fileId = servletReq.getParameter("fileId");

            FileEntity fileEnt = repFiles.get(Helpers.tryParseLong(fileId));

            File file = new File(fileEnt.getPath());
            InputStream in = null;
            if (file.length() == 0) {
                byte[] nullArr = new byte[0];
                in = new ByteArrayInputStream(nullArr);
            } else {
                in = new FileInputStream(file);
            }

            try {
                HttpServletResponse httpRes = PortalUtil.getHttpServletResponse(response);
                httpRes.setContentType(ConstantsUtil.CONTENT_TYPE);
                response.setCharacterEncoding(ConstantsUtil.CHARACTER_ENCODING);
                httpRes.setHeader("Content-Disposition", "attachment; filename=" + URLDecoder.decode(URLEncoder.encode(fileEnt.getName(), ConstantsUtil.CHARACTER_ENCODING), "ISO8859_1"));

                ServletOutputStream out = httpRes.getOutputStream();

                byte[] outputByte = new byte[ConstantsUtil.OUTPUT_BLOCK_SIZE];
                int bytesRead = 0;
                while ((bytesRead = in.read(outputByte, 0, ConstantsUtil.OUTPUT_BLOCK_SIZE)) != -1) {
                    out.write(outputByte, 0, bytesRead);
                }
                in.close();
                out.flush();
                out.close();

                return;
            } finally {
                in.close();
            }

        } catch (Exception e) {
            logger.error(e);
            JSONObject jsonObj = AnswerMessage.getFailMessage(ConstantsUtil.SAVE_DATA_ERR_MSG);
            response.getWriter().write(jsonObj.toJSONString());
        }
    }
     */

}
