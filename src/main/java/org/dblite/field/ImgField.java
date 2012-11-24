package org.dblite.field;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;

public class ImgField extends Cell {

    @Override
    public String defaultVal() {
        return "";
    }

    public ImgField(Cell f) {
        super(f.getStringData());
    }

    public ImgField(int[] pixels) {
        super(Arrays.toString(pixels));
    }

    public ImgField() {
        super("");
    }

    @Override
    public Object getVal() {
        String pixels = getStringData();
        if ((pixels == null) || (pixels.equals(""))) {
            return null;
        }
        String[] splitPixels = pixels.split(",");
        int[] res = new int[splitPixels.length];
        for (int i = 0; i < splitPixels.length; i++) {
            res[i] = Integer.parseInt(splitPixels[i].trim());
        }
        return res;
    }

    @Override
    public void setVal(Object... img) {
        int[] res = (int[]) img[0];
        setStringData(Arrays.toString(res).substring(1, res.length - 1));
    }

    @Override
    public void setStringVal(String input) throws ParseException {
        if ((input != null) && (input.length() > 0)) {
            String[] values = input.split(",");
            int type = 0;
            try {
                type = Integer.parseInt(values[0].trim());
                if ((type != 1) || (type != 2)) {
                    throw new ParseException("Illegal type", 0);
                }
                int h = 0;
                int g = 0;
                h = Integer.parseInt(values[1].trim());
                g = Integer.parseInt(values[2].trim());
                for (int i = 0; i < h * g; i++) {
                    Integer.parseInt(values[i].trim());

                }
            } catch (Exception e) {
                throw new ParseException("Illegal number", 0);
            }
            setStringData(input);
        }
    }
}
