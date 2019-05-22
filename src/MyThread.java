import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyThread extends Thread
{
    private static final int IMAGES_NUMBER = 12;
    private static final int SLEEP_MSECS = 5000;
    private static final String ACCESS_TOKEN = "";
    private static final String IMAGE_FORMAT = "png";
    private static final String DATE_FORMAT = "yyyyMMdd_HHmmss";
    private static final String SCREENSHOTS_FOLDER = "/java_screenshots/";

    public void run() {
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/ram0973_java_test").build();
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        for(int i = 1; i <= IMAGES_NUMBER; i++) {
            try {
                // capture screenshot to BufferedImage image
                BufferedImage image = null;
                image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().
                getScreenSize()));
                // convert BufferedImage image to InputStream input_stream
                if(image != null) {
                    ByteArrayOutputStream output_stream = new ByteArrayOutputStream();
                    ImageIO.write(image, IMAGE_FORMAT, output_stream);
                    InputStream input_stream = new ByteArrayInputStream(output_stream.toByteArray());
                    // Give a name to image with current date/time
                    String image_name = dateFormat.format(new Date());
                    // Upload screenshot to Dropbox
                    String image_filename = image_name + "." + IMAGE_FORMAT;
                    System.out.println(image_filename);
                    FileMetadata metadata = client.files().uploadBuilder(SCREENSHOTS_FOLDER + image_filename).
                        uploadAndFinish(input_stream);
                    System.out.println("Image number: " + i + " Image file name: " + image_filename);
                }
                // sleep for MSECS_TO_SLEEP milliseconds
                sleep(SLEEP_MSECS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
