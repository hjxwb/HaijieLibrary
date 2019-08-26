package com.haijie.utilslibrary.image.glide;

public class GlideHelper {

    private static GlideHelper mInstance;

    private GlideHelper() {

    }

    /**
     * 获取实例
     *
     * @return 实例对象
     */
    public static GlideHelper getInstance() {
        if (null == mInstance) {
            synchronized (GlideHelper.class) {
                if (null == mInstance) {
                    mInstance = new GlideHelper();
                }
            }
        }
        return mInstance;
    }

//    public loadImageByUrl(Context context, String url, )
}
