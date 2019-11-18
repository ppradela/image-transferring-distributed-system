package com.pradela.imagetransferring.soapclient;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

class SoapClient {

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static void main(String[] args) {
        Logger logger = LogManager.getLogger();

        logger.info("Starting application");

        if (args.length == 0) {
            logger.error("No filepath as a command-line argument");
            logger.info("Closing application");
            System.exit(0);
        } else if (args.length > 1) {
            logger.error("Too many arguments");
            logger.info("Closing application");
            System.exit(0);
        } else if (!FilenameUtils.getExtension(args[0]).equals("jpg")) {
            logger.error("Proper image file format: JPEG");
            logger.info("Closing application");
            System.exit(0);
        } else if (new File(args[0]).length() > 1000000) {
            logger.error("File too large. Maximum file size: 100 MB");
            logger.info("Closing application");
            System.exit(0);
        }

        String imageHexString = null;

        try {
            logger.info("Processing image");
            BufferedImage bufferedImage = ImageIO.read(new File(args[0]));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
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
            logger.info("Converted successfully");
        } catch (IOException e) {
            logger.fatal(e.getMessage());
            logger.info("Closing application");
            System.exit(0);
        }

        try {
            SoapService service = new SoapService();
            logger.info("Creating SOAP connection");
            Soap soap = service.getSoapPort();
            logger.info("Sending image");
            soap.sendImageHexString(imageHexString);
            logger.info("Image sent");
            logger.info("Closing application");
        } catch (Exception e) {
            logger.fatal(e.getMessage());
            logger.info("Closing application");
            System.exit(0);
        }
    }
}