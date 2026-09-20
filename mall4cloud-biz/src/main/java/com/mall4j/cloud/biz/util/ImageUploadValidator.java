package com.mall4j.cloud.biz.util;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

/**
 * 图片上传校验。
 */
public final class ImageUploadValidator {

    public static final long MAX_FILE_SIZE = 2L * 1024 * 1024;
    private static final long MAX_PIXELS = 16L * 1024 * 1024;
    private static final Map<String, String> FORMAT_CONTENT_TYPES = Map.of(
            "jpeg", "image/jpeg",
            "png", "image/png",
            "gif", "image/gif"
    );

    private ImageUploadValidator() {
    }

    /**
     * 校验图片内容，并返回由图片真实格式决定的 Content-Type。
     *
     * @param content 图片二进制内容
     * @return 图片真实 Content-Type
     * @throws IllegalArgumentException 图片不符合上传限制
     * @throws IOException 无法读取图片内容
     */
    public static String validate(byte[] content) throws IOException {
        if (content == null || content.length == 0) {
            throw new IllegalArgumentException("上传图片不能为空");
        }
        if (content.length > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("图片大小不能超过2MB");
        }

        try (ImageInputStream imageInputStream = ImageIO.createImageInputStream(new ByteArrayInputStream(content))) {
            Iterator<ImageReader> readers = ImageIO.getImageReaders(imageInputStream);
            if (!readers.hasNext()) {
                throw new IllegalArgumentException("仅支持上传 JPEG、PNG 或 GIF 图片");
            }

            ImageReader reader = readers.next();
            try {
                reader.setInput(imageInputStream);
                String contentType = FORMAT_CONTENT_TYPES.get(reader.getFormatName().toLowerCase(Locale.ROOT));
                if (contentType == null) {
                    throw new IllegalArgumentException("仅支持上传 JPEG、PNG 或 GIF 图片");
                }
                long width = reader.getWidth(0);
                long height = reader.getHeight(0);
                if (width * height > MAX_PIXELS) {
                    throw new IllegalArgumentException("图片分辨率不能超过1600万像素");
                }
                return contentType;
            } finally {
                reader.dispose();
            }
        }
    }
}
