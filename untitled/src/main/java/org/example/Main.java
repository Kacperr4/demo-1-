package org.example;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        Connection conn = null;
        String url = "jdbc:sqlite:songs.db";

        try {
            conn = DriverManager.getConnection(url);
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM song");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                System.out.println(rs.getString("artist"));
            }
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            BufferedImage image = ImageIO.read(new File("img.jpg"));
            int brightness = 100;
            for(int y=0;y<image.getHeight();y++){
                for(int x=0;x<image.getWidth();x++){
                    int rgb = image.getRGB(x,y);
                    int b=rgb&0xFF;
                    int g=(rgb&0xFF00)>>8;
                    int r=(rgb&0xFF0000)>>16;
                    b=clamp(b+brightness,0,255);
                    g=clamp(g+brightness,0,255);
                    r=clamp(r+brightness,0,255);
                    rgb=(r<<16)+(g<<8)+b;
                    image.setRGB(x,y,rgb);

                }
            }
            ImageIO.write(image, "jpg", new File("img.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}