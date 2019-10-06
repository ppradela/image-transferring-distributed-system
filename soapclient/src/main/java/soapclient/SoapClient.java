package soapclient;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class SoapClient {
    
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    
    public static void main(String[] args) throws IOException {
        Logger logger = LogManager.getLogger("SoapClient");
        Layout layout = new PatternLayout("%d{yyyy-MM-dd HH:mm:ss.SSS} [%p] %c - %m%n");
        Appender appender = new FileAppender(layout, Paths.get(".").normalize().toAbsolutePath() + File.separator
                + "soapclient" + File.separator + "SoapClient.log");
        logger.addAppender(appender);

        System.out.println("Starting application");
        logger.info("Starting application");

        if (args.length == 0) {
            System.err.println("Proper usage: java program image.jpg");
            logger.error("No filepath as a command-line argument");
            System.out.println("Closing application");
            logger.info("Closing application");
            System.exit(0);
        } else if (!FilenameUtils.getExtension(args[0]).equals("jpg")) {
            System.err.println("Proper image file format: JPEG");
            System.out.println("Closing application");
            logger.info("Closing application");
            System.exit(0);
        } else if (new File(args[0]).length() > 1000000) {
            System.err.println("Maximum file size: 100 MB");
            logger.error("File too large");
            System.out.println("Closing application");
            logger.info("Closing application");
            System.exit(0);
        }

        String imageHexString=null;

        try {
            System.out.println("Processing image");
            logger.info("Processing image");
            BufferedImage bufferedImage = ImageIO.read(new File(args[0]));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            System.out.println("Converting to PNG");
            logger.info("Converting to PNG");
            ImageIO.write(bufferedImage, "png", output);
            byte[] imageBytes = output.toByteArray();
            char[] imageHexChars = new char[imageBytes.length * 2];
            for (int j = 0; j < imageBytes.length; j++) {
                int v = imageBytes[j] & 0xFF;
                imageHexChars[j * 2] = HEX_ARRAY[v >>> 4];
                imageHexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
            }
            imageHexString = new String(imageHexChars);
            System.out.println("Converted successfully");
            logger.info("Converted successfully");
        } catch (IOException e) {
            System.err.println(e.getMessage());
            logger.fatal(e.getMessage());
            logger.info("Closing application");
            e.printStackTrace();
            System.exit(0);
        }

        System.out.println(imageHexString);

        SoapService service = new SoapService();
        System.out.println("Creating SOAP connection");
        logger.info("Creating SOAP connection");
        Soap soap = service.getSoapPort();
        System.out.println("Sending image");
        logger.info("Sending image");
        soap.sendImageHexString(imageHexString);
        System.out.println("Image sended");
        logger.info("Image sended");

        System.out.println("Closing application");
        logger.info("Closing application");
    }
}