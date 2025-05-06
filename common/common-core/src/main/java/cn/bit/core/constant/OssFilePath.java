package cn.bit.core.constant;

@SuppressWarnings("checkstyle:InterfaceIsType")
public interface OssFilePath {
    String SEPARATOR = "/";
    String IMAGE_FOLDER = "img";
    String SHOP_LOGO_FOLDER = IMAGE_FOLDER + SEPARATOR + "shopLogo";
    String BRAND_LOGO_FOLDER = IMAGE_FOLDER + SEPARATOR + "brandLogo";
    String PRODUCT_SPU_IMAGE_FOLDER = IMAGE_FOLDER + SEPARATOR + "productSpuImage";
}
