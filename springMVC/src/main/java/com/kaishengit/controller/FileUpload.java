package com.kaishengit.controller;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @Author: chuzhaohui
 * @Date: Created in 10:51 2018/7/23
 */
@Controller
@RequestMapping("/file")
public class FileUpload {

    @GetMapping("/upload")
    public String  upload(){
        return "file/upload";
    }

    @PostMapping("/upload")
    public String upload(MultipartFile file,String fileName, RedirectAttributes redirectAttributes){
        System.out.println(fileName);
        if(file.isEmpty()){
            redirectAttributes.addFlashAttribute("message","请选择文件");
        }else {

            //是否为空
            System.out.println(file.isEmpty());
            //获得MIME类型
            System.out.println(file.getContentType());
            //获得上传空间的name属性
            System.out.println(file.getName());
            //获得原始名称
            System.out.println(file.getOriginalFilename());
            //获得文件大小
            System.out.println(FileUtils.byteCountToDisplaySize(file.getSize()));

            File direction = new File("F:/temp/img/");
            if(!direction.exists()){
                direction.mkdirs();
            }
            try {
                file.transferTo(new File(direction,file.getOriginalFilename()));
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
        return "redirect:/file/upload";
    }

    @GetMapping("/download")
    public void download(HttpServletResponse response){

        File file = new File("F:/temp/img/1.jpg");

        try {
            InputStream inputStream = new FileInputStream(file);
            try {
                OutputStream outputStream = response.getOutputStream();
                IOUtils.copy(inputStream,outputStream);
                outputStream.flush();
                outputStream.close();
                inputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @GetMapping("/download2") //匹配的是href中的download请求
    public ResponseEntity<byte[]> download(HttpServletRequest request, String filename,
                                           Model model) throws IOException{

        System.out.println("hello");
        //从我们的上传文件夹中去取
        String downloadFilePath="F:\\temp\\img";

        //新建一个文件
        File file = new File(downloadFilePath + File.separator + filename);

        //http头信息
        HttpHeaders headers = new HttpHeaders();

        //设置编码
        String downloadFileName = new String(filename.getBytes("UTF-8"),"iso-8859-1");

        headers.setContentDispositionFormData("attachment", downloadFileName);

        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        //MediaType:互联网媒介类型  contentType：具体请求中的媒体类型信息

        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers,HttpStatus.CREATED);
    }

}
