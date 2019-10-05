package wsclient;

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
    public static void main(String[] args) throws IOException {
        Logger logger = LogManager.getLogger("SoapClient");
        Layout layout = new PatternLayout("%d{yyyy-MM-dd HH:mm:ss.SSS} [%p] %c - %m%n");
        Appender appender = new FileAppender(layout, Paths.get(".").normalize().toAbsolutePath() + File.separator
                + "wsclient" + File.separator + "SoapClient.log");
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

        byte[] data = null;

        try {
            System.out.println("Processing image");
            logger.info("Processing image");
            BufferedImage bufferimage = ImageIO.read(new File(args[0]));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            System.out.println("Converting to PNG");
            logger.info("Converting to PNG");
            ImageIO.write(bufferimage, "png", output);
            data = output.toByteArray();
            System.out.println("Converted successfully");
            logger.info("Converted successfully");
        } catch (IOException e) {
            System.err.println(e.getMessage());
            logger.fatal(e.getMessage());
            logger.info("Closing application");
            e.printStackTrace();
            System.exit(0);
        }

        SoapService service = new SoapService();
        System.out.println("Creating SOAP connection to " + service.getServiceName());
        logger.info("Creating SOAP connection to " + service.getServiceName());
        Soap soap = service.getSoapPort();
        System.out.println("Sending image");
        logger.info("Sending image");
        soap.sendData(data);
        System.out.println("Image sended");
        logger.info("Image sended");

        System.out.println("Closing application");
        logger.info("Closing application");
    }
}