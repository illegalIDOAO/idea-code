package com.kaishengit.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

}
