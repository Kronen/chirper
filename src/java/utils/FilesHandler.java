package utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import jpa.entities.Profile;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Alberto G. Lagos
 */
@ManagedBean
@RequestScoped
public class FilesHandler {
    
    public static boolean imageExists(String image) {
        try  {
            String imagePath = "/uploads/" + image;
            URL resource = ContextHandler.externalContext().getResource(imagePath);
            return (resource != null);
        } catch (MalformedURLException ex) {
            Logger.getLogger(FilesHandler.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    /** 
     * Saves an image to the path defined in the UPLOADED_PATH environment variable
     * with a random string generated added to the filename for avoiding collisions.
     *
     * @param image the uploaded file to save as image in the disk
     * @return String the generated filename
     */
    public static String saveImageToDisk(UploadedFile image) {          
        String filename = FilenameUtils.getBaseName(image.getFileName()); 
        String extension = FilenameUtils.getExtension(image.getFileName());            

        try {
            Path folder = Paths.get(System.getenv("UPLOAD_PATH"));
            Path file = Files.createTempFile(folder, filename + "-", "." + extension);
            InputStream input = image.getInputstream();
            Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
            return file.getFileName().toString();
        } catch (IOException ex) {
            MessageHandler.addErrorMessage("Error uploading file", null);
        }
        return null;
    }
    
    public String getAvatar(String image) {
        if(image == null || image.isEmpty())
           return "/Chirper/faces/javax.faces.resource/user-blue.png?ln=images";
        return "/uploads/" + image;
    }
    
    public String streamedImage(String image) {
        return "/uploads/" + image;
    }
    
    
}
