package cn.bit.core.constant;

/**
 * <p>OSS文件路径</p>
 * Date:2025/05/07 20:16:29
 *
 * @author <a href="mailto:18086270070@163.com">Luofei Wang</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("checkstyle:InterfaceIsType")
public interface OssFilePath {
    String SEPARATOR = "/";
    String IMAGE_FOLDER = "img";
    String SHOP_LOGO_FOLDER = IMAGE_FOLDER + SEPARATOR + "shopLogo";
    String BRAND_LOGO_FOLDER = IMAGE_FOLDER + SEPARATOR + "brandLogo";
    String PRODUCT_SPU_IMAGE_FOLDER = IMAGE_FOLDER + SEPARATOR + "productSpuImage";
}
