package com.kaishengit.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @Author: chuzhaohui
 * @Date: Created in 21:56 2018/7/23
 */
@Controller
@RequestMapping("/file")
public class FileUpAndDown {

    @PostMapping("/upload")
    public void fileUpload(MultipartFile file, RedirectAttributes redirectAttributes){
        if(file.isEmpty()){
            redirectAttributes.addFlashAttribute("message","请选择文件");
        }else{
            File direction = new File("F:/temp/img/");
            if(!direction.exists()){
                direction.mkdirs();
            }
            try {
                file.transferTo(direction);
            } catch (IOException e) {
                e.printStackTrace();
            }
            /* try {
                InputStream inputStream = file.getInputStream();
                OutputStream outputStream = new FileOutputStream(new File(direction,file.getOriginalFilename()));

                IOUtils.copy(inputStream,outputStream);

                outputStream.flush();
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
    }

    @GetMapping("/download")
    public void fileDownload(HttpServletResponse response){
        try {
            File file = new File("F:/temp/img/1.jpg");
            InputStream inputStream = new FileInputStream(file);

            OutputStream outputStream = response.getOutputStream();

            IOUtils.copy(inputStream,outputStream);
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author: chuzhaohui
     * @Date: Created in 9:29 2018/7/24
     */
    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "资源未找到")
    public static class NotFountException extends  RuntimeException {
        /**
         * Constructs a new runtime exception with {@code null} as its
         * detail message.  The cause is not initialized, and may subsequently be
         * initialized by a call to {@link #initCause}.
         */
        public NotFountException() {
        }

        /**
         * Constructs a new runtime exception with the specified detail message.
         * The cause is not initialized, and may subsequently be initialized by a
         * call to {@link #initCause}.
         *
         * @param message the detail message. The detail message is saved for
         *                later retrieval by the {@link #getMessage()} method.
         */
        public NotFountException(String message) {
            super(message);
        }
    }
}
