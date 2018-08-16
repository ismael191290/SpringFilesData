/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sedesoo.Sedesoo.Controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author GS-Server
 */
@org.springframework.stereotype.Controller
@RequestMapping(value = "/...")
public class Controller implements Filter {

    @Autowired
    ServletContext context;

    @RequestMapping(value = "/files", method = RequestMethod.POST )
    public ResponseEntity<?> processFile(@RequestParam("fotos") MultipartFile[] file, @NotNull @RequestParam("usuario") String user) {
        
        System.out.println("parametros "+user);
        imagen(file);
        return new ResponseEntity("{OK: 'true'}", HttpStatus.CREATED);
    }

    public String[] imagen(MultipartFile[] files) {

        // crear carpeta si no existe
        File convFiles = new File(context.getRealPath("/") + "files");
        if (!convFiles.exists()) {
            convFiles.mkdir();
        }
// cantidad de imagenes que gusten enviar
        String[] ima = new String[files.length];
        
        // recorer el arriglo de imagenes enviadas
        int cont = 0;
        
        for (MultipartFile archivo : files) {

            String type = archivo.getContentType();
            String[] extension = type.split("/");
            // podemos regresar los nombres de los archivos craedos y poderlos almacenar
            ima[cont] = archivo.getOriginalFilename();
            
            
            String filename = archivo.getOriginalFilename().substring(0, (archivo.getOriginalFilename().length() - extension[1].length()));

            // saber que tipo de archivo viene
            if (extension[0].equals("image")) {
                // tipo de formato a guardar la imagen
                filename = Math.random() + ".png";

            } else if (extension[0].equals("video")) {
                // tipo de formato a guardar el video
               //filename = Math.random() + ".qt";
               filename = Math.random() + ".3gp";

            }
           
            File convFile = new File(context.getRealPath("/") + "files\\" + filename);
            // ruta del proyecto, creando carpeta de contenedor de archivos

            try {
                 // crear el archivo obtenido y diseñado 
                convFile.createNewFile();
                FileOutputStream fos = new FileOutputStream(convFile);
                fos.write(archivo.getBytes());
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            cont++;
        }
        return ima;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
       
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // cabezeras poder hacer peticiones de varias direcciones
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpResponse.setHeader("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        httpResponse.setHeader("Access-Control-Max-Age", "4800");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
       
    }

}
